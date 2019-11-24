package com.mservicetech.schedule;


import com.mservicetech.schedule.publisher.CdcPublisher;
import com.mservicetech.schedule.publisher.LocalCdcLogPublisher;
import com.networknt.config.Config;
import com.networknt.db.GenericDataSource;
import com.networknt.eventuate.jdbc.EventuateSchema;
import com.networknt.eventuate.server.common.PublishingStrategy;
import com.networknt.service.SingletonServiceFactory;
import com.networknt.tram.cdc.mysql.connector.MessageWithDestination;
import com.networknt.tram.cdc.polling.connector.MessagePollingDataProvider;
import com.networknt.tram.cdc.polling.connector.PollingDao;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MessagingPollingDataProviderTest {

    static String CDC_CONFIG_NAME = "cdc";
    static CdcConfig cdcConfig = (CdcConfig) Config.getInstance().getJsonObjectConfig(CDC_CONFIG_NAME, CdcConfig.class);

    static GenericDataSource genericDataSource = SingletonServiceFactory.getBean(GenericDataSource.class);

    @Test
    public void testSchema(){
        EventuateSchema schema = (EventuateSchema) SingletonServiceFactory.getBean(EventuateSchema.class);

        MessagePollingDataProvider pollingDataProvider= (MessagePollingDataProvider) SingletonServiceFactory.getBean(MessagePollingDataProvider.class);
        System.out.println(pollingDataProvider.table());
        assertNotNull(pollingDataProvider.table());
    }

    @Test
    public void test2(){
        MessagePollingDataProvider pollingDataProvider= (MessagePollingDataProvider) SingletonServiceFactory.getBean(MessagePollingDataProvider.class);


        PublishingStrategy<MessageWithDestination> publishingStrategy = SingletonServiceFactory.getBean(PublishingStrategy.class);

        CdcPublisher<MessageWithDestination> messageCdcJmsPublisher = new LocalCdcLogPublisher<>(publishingStrategy);
        DataSource ds = genericDataSource.getDataSource();
        PollingDao pollingDao =  new PollingDao(pollingDataProvider, ds,
                cdcConfig.getMaxEventsPerPolling(),
                cdcConfig.getMaxAttemptsForPolling(),
                cdcConfig.getPollingRetryIntervalInMilliseconds());
        List eventsToPublish = pollingDao.findEventsToPublish();

        assertTrue(eventsToPublish.size()>0);

        if (!eventsToPublish.isEmpty()) {

            pollingDao.markEventsAsPublished(eventsToPublish);
        }
    }

    @Test
    public void test3(){
    /*    MessagePollingDataProvider pollingDataProvider= (MessagePollingDataProvider) SingletonServiceFactory.getBean(MessagePollingDataProvider.class);
        if (pullingConfig != null) pollingDataProvider.reset(pullingConfig.getTableName(), pullingConfig.getIdField(), pullingConfig.getPublishedField(),
                pullingConfig.getHeaders(), pullingConfig.getDestination(), pullingConfig.getPayload());
        PollingDao pollingDao =  new PollingDao(pollingDataProvider, ds,
                cdcConfig.getMaxEventsPerPolling(),
                cdcConfig.getMaxAttemptsForPolling(),
                cdcConfig.getPollingRetryIntervalInMilliseconds());

        List<MessageWithDestination>  list = pollingDao.findEventsToPublish();
        System.out.println("list size:" + list);*/
    }
}
