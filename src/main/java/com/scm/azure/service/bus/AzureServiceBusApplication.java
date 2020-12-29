package com.scm.azure.service.bus;

import javax.jms.JMSException;
import javax.naming.NamingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class AzureServiceBusApplication {

	public static void main(String[] args) throws JMSException, NamingException {
		SpringApplication.run(AzureServiceBusApplication.class, args);
	}
}