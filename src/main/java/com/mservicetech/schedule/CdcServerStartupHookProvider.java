package com.mservicetech.schedule;



import com.mservicetech.schedule.publisher.CdcPublisher;
import com.mservicetech.schedule.publisher.LocalCdcLogPublisher;
import com.networknt.config.Config;
import com.networknt.db.GenericDataSource;
import com.networknt.eventuate.server.common.CdcProcessor;
import com.networknt.eventuate.server.common.PublishingStrategy;
import com.networknt.eventuate.server.common.exception.EventuateLocalPublishingException;
import com.networknt.server.StartupHookProvider;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.tram.cdc.mysql.connector.MessageWithDestination;
import com.networknt.tram.cdc.polling.connector.MessagePollingDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;


/**
 * CdcServer StartupHookProvider. start cdc service
 */
public class CdcServerStartupHookProvider implements StartupHookProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    static String CDC_CONFIG_NAME = "cdc";
    static CdcConfig cdcConfig = (CdcConfig) Config.getInstance().getJsonObjectConfig(CDC_CONFIG_NAME, CdcConfig.class);
    static CdcPublisher<MessageWithDestination> messageCdcPublisher;
    @Override
    @SuppressWarnings("unchecked")
    public void onStartup() {


        MessagePollingDataProvider pollingDataProvider=  SingletonServiceFactory.getBean(MessagePollingDataProvider.class);

        PublishingStrategy<MessageWithDestination> publishingStrategy = SingletonServiceFactory.getBean(PublishingStrategy.class);
        GenericDataSource genericDataSource = SingletonServiceFactory.getBean(GenericDataSource.class);
        DataSource ds = genericDataSource.getDataSource();


        messageCdcPublisher = new LocalCdcLogPublisher<>(publishingStrategy);

        PollingDao pollingDao =  new PollingDao(pollingDataProvider, ds,
                cdcConfig.getMaxEventsPerPolling(),
                cdcConfig.getMaxAttemptsForPolling(),
                cdcConfig.getPollingRetryIntervalInMilliseconds());


        CdcProcessor<MessageWithDestination> pollingCdcProcessor = new PollingCdcProcessor<>(pollingDao, cdcConfig.getPollingIntervalInMilliseconds());

        messageCdcPublisher.start();
        try {
            pollingCdcProcessor.start(publishedEvent -> {
                try {
                    messageCdcPublisher.handleEvent(publishedEvent);
                } catch (EventuateLocalPublishingException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            if (e.getCause() instanceof EventuateLocalPublishingException) {
                logger.error("Stopping capturing changes due to exception:", e);
                messageCdcPublisher.stop();
            }
        }

        System.out.println("CdcServerStartupHookProvider is called");
    }


}
