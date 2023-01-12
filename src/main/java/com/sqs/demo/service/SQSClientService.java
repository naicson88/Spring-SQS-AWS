package com.sqs.demo.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Service
public class SQSClientService {
	
	private AmazonSQS client;
	
	@PostConstruct
	private void initializeAmazonSQSClient() {
		System.out.println("Initializer");
		this.client = AmazonSQSClientBuilder.standard()
				.withCredentials(getAwsCredentialProvider())
				.withRegion(Region.getRegion(Regions.US_EAST_1).getName())
				.build();
				
	}

	private AWSCredentialsProvider getAwsCredentialProvider() {
		AWSCredentials credentials = new BasicAWSCredentials("key", "secret key");
		return new AWSStaticCredentialsProvider(credentials);
	}
	
	public AmazonSQS getClient() {
		return client;
	}
}
