package com.scm.azure.service.bus.controller;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.scm.azure.service.bus.dto.AzureObject;
import com.scm.azure.service.bus.service.QueueMessageProducerService;

@RestController
@RequestMapping("/azure")
public class PublisherController {

	private QueueMessageProducerService service = new QueueMessageProducerService();
	@Autowired
	private JmsTemplate jmsTemplate;

	@PostMapping("/publish")
	public String publish(@RequestParam("file") MultipartFile file) throws JMSException, IOException, NamingException {
		jmsTemplate.convertAndSend("living-goods-queue", service.sendFileAsBytesMessage(file));
		return "Success";
	}

	@GetMapping("/publish/{message}")
	public String publish(@PathVariable String message) throws JMSException, IOException, NamingException {
		jmsTemplate.convertAndSend("living-goods-queue", service.createTextMessage(message));
		return "Success";
	}
	
	@PostMapping("/publishObject")
	public String publish(@RequestBody AzureObject object) throws JmsException, JMSException, NamingException {
		jmsTemplate.convertAndSend("living-goods-queue", service.createTextMessage(object.toString()));
		return "Success";
	}
}