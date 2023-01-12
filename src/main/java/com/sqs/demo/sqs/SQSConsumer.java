package com.sqs.demo.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


@Service
public class SQSConsumer {
	
	Logger logger = LoggerFactory.getLogger(SQSConsumer.class);
	
	@JmsListener(destination = "First")
	public void process(String data) {
		logger.info("Received!: {}", data);
	}
}
