package com.scm.azure.service.bus.receiver;

import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.scm.azure.service.bus.service.QueueMessageProducerService;

@Component
public class ReceiverController {

	private static final String QUEUE_NAME = "living-goods-queue";
	private final Logger log = LoggerFactory.getLogger(ReceiverController.class);
	private QueueMessageProducerService service = new QueueMessageProducerService();
	private static final String PROPERTY_NAME = "fileName";

	/*
	 * @JmsListener(destination = QUEUE_NAME, containerFactory =
	 * "jmsListenerContainerFactory") public void receiveMessage(Message message)
	 * throws JMSException, IOException { if (message instanceof TextMessage) {
	 * log.info("Text Message recevied from the Queue[{}] is - {}", QUEUE_NAME,
	 * ((TextMessage) message).getText()); } if (message instanceof BytesMessage) {
	 * log.info("File recevied from the Queue[{}] is - {}", QUEUE_NAME,
	 * message.getStringProperty(PROPERTY_NAME));
	 * service.handleBytesMessage(message,
	 * message.getStringProperty(PROPERTY_NAME)); } }
	 */
}