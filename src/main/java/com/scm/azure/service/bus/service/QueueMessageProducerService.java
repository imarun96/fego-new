package com.scm.azure.service.bus.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;

@Component
public class QueueMessageProducerService {

	Logger log = LoggerFactory.getLogger(QueueMessageProducerService.class);
	private Hashtable<String, String> env = new Hashtable<String, String>();
	private ConnectionStringBuilder csb;
	private Context context;
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;

	public void setup() throws NamingException, JMSException {
		log.info("Initializing Connection.");
		csb = new ConnectionStringBuilder(
				"Endpoint=sb://living-goods-service-bus.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=e7TD4p5jcSlkpt9ozmj9UO/UYsrpj231wPAC0tJXyuA=");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
		env.put("connectionfactory.ServiceBusConnectionFactory",
				"amqps://" + csb.getEndpoint().getHost() + "?amqp.idleTimeout=120000&amqp.traceFrames=true");
		context = new InitialContext(env);
		log.info("Creating connection factory");
		connectionFactory = (ConnectionFactory) context.lookup("ServiceBusConnectionFactory");
		log.info("Creating connection");
		connection = connectionFactory.createConnection();
		log.info("Creating session");
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void close() {
		try {
			log.info("Cleaning up");
			session.close();
			connection.stop();
			connection.close();
			log.info("Session and connection closed");
		} catch (Exception e) {
			log.info("Exception closing session and connection");
		}
	}

	public File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}

	public BytesMessage sendFileAsBytesMessage(MultipartFile file) throws JMSException, IOException, NamingException {
		setup();
		File convertedFile = convertMultiPartToFile(file);
		BytesMessage bytesMessage = session.createBytesMessage();
		bytesMessage.setStringProperty("fileName", file.getOriginalFilename());
		bytesMessage.writeBytes(readfileAsBytes(convertedFile));
		close();
		log.info("File successfully published to the Queue.");
		return bytesMessage;
	}

	public byte[] readfileAsBytes(File file) throws IOException {
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "r")) {
			byte[] bytes = new byte[(int) accessFile.length()];
			accessFile.readFully(bytes);
			return bytes;
		}
	}

	public void writeFile(byte[] bytes, String fileName) throws IOException {
		File file = new File(fileName);
		try (RandomAccessFile accessFile = new RandomAccessFile(file, "rw")) {
			accessFile.write(bytes);
			log.info("File has been received successfully and saved in the path [{}]", fileName);
		}
	}

	public void handleBytesMessage(Message bytesMessage, String filename) throws IOException, JMSException {
		String outputfileName = "/home/arunbalaji/Documents/" + filename;
		log.info("Writing file[{}] to the Path - {}", filename, outputfileName);
		byte[] bytes = new byte[(int) ((BytesMessage) bytesMessage).getBodyLength()];
		((BytesMessage) bytesMessage).readBytes(bytes);
		writeFile(bytes, outputfileName);
	}

	public TextMessage createTextMessage(String message) throws JMSException, NamingException {
		setup();
		TextMessage textMessage = session.createTextMessage(message);
		close();
		return textMessage;
	}
}