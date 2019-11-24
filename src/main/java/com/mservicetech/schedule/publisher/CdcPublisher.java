package com.mservicetech.schedule.publisher;


import com.networknt.eventuate.server.common.PublishingStrategy;
import com.networknt.eventuate.server.common.exception.EventuateLocalPublishingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CdcPublisher<EVENT> {

  protected PublishingStrategy<EVENT> publishingStrategy;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public CdcPublisher(PublishingStrategy<EVENT> publishingStrategy) {
    this.publishingStrategy = publishingStrategy;
  }

  public void start() {
    logger.debug("Starting NotificationMessageHandler");

  }

  public abstract void handleEvent(EVENT publishedEvent) throws EventuateLocalPublishingException;

  public void stop() {
    logger.debug("Stopping JMS sender ");

  }

}
