package com.sqs.demo.sqs;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;


import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.sqs.demo.service.SQSClientService;

@Service
@DependsOn("SQSClientService")
public class SQSProducer {
	
	Logger logger = LoggerFactory.getLogger(SQSProducer.class);
	
	@Autowired
	private JmsTemplate template;
	@Autowired
	private SQSClientService service;
	
	public void send(String queueName, String body) {
		logger.info("Send to {} message --> {}", queueName, body );
		template.convertAndSend(queueName, body);
	}
	
	@PostConstruct
	public void sendMultiple() {
		SendMessageBatchRequestEntry[] dataArray = new SendMessageBatchRequestEntry[10];
		
		for(int i = 0; i < 10; i++) {
			dataArray[i] = new SendMessageBatchRequestEntry(UUID.randomUUID().toString(), " Hello from message " + i);
		}
		
		logger.info("Sending batch message to order queue");
		
		SendMessageBatchRequest request = new SendMessageBatchRequest()
				.withQueueUrl("Fila URL")
				.withEntries(dataArray);
		
		service.getClient().sendMessageBatch(request);
	}
	
}
