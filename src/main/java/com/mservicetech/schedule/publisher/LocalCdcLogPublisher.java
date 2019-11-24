package com.mservicetech.schedule.publisher;


import com.networknt.eventuate.server.common.PublishingStrategy;
import com.networknt.eventuate.server.common.exception.EventuateLocalPublishingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocalCdcLogPublisher<EVENT> extends CdcPublisher<EVENT> {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public LocalCdcLogPublisher(PublishingStrategy<EVENT> publishingStrategy) {
    super(publishingStrategy);
  }

  @Override
  public void handleEvent(EVENT event) throws EventuateLocalPublishingException {
    logger.trace("Got record " + event.toString());

  //  String aggregateTopic = publishingStrategy.topicFor(event);
    String json = publishingStrategy.toJson(event);

    System.out.println("message out--->:" + json);

    try {
        logger.info("Local message send to log file:" + json);
	} catch (Exception e) {
		String msg = "notification message send failed ";
		logger.error(msg+ e.getMessage());
		throw new EventuateLocalPublishingException(msg, e);
	}

  }
}
