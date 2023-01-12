package com.sqs.demo.config;

import javax.annotation.PostConstruct;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.sqs.demo.service.SQSClientService;

@Configuration
@EnableJms
public class JmsConfig {
	
	@Autowired
	private SQSClientService service;
	private SQSConnectionFactory connectionFactory;
	
	@PostConstruct
	public void init() {
		System.out.println("Config");
		ProviderConfiguration providerConfig = new ProviderConfiguration();
		connectionFactory = new SQSConnectionFactory(providerConfig, service.getClient());
	}
	
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setDestinationResolver(new DynamicDestinationResolver());
		factory.setConcurrency("3-10");
		factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
		return factory;
	}
	
	@Bean
	public JmsTemplate template() {
		return new JmsTemplate(this.connectionFactory);
	}
}
