package com.mservicetech.schedule;


import com.networknt.server.ShutdownHookProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CDC service ShutdownHookProvider, stop CDC service
 */
public class CdcServerShutdownHookProvider implements ShutdownHookProvider {
    protected static Logger logger = LoggerFactory.getLogger(CdcServerShutdownHookProvider.class);

    @Override
    public void onShutdown() {
        if(CdcServerStartupHookProvider.messageCdcPublisher != null) {
            CdcServerStartupHookProvider.messageCdcPublisher.stop();
        }
        logger.info("CdcServerShutdownHookProvider is called");
    }
}
