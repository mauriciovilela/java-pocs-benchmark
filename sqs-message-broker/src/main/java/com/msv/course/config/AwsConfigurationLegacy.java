package com.msv.course.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class AwsConfigurationLegacy {

    @Bean
    @Primary
    public AmazonSQSAsync amazonSqsAsync() {
        final var clientBuilder = AmazonSQSAsyncClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:9324","us-east-1"));
        clientBuilder.setCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials("AWS_ACCESS_KEY", "AWS_SECRET")));
        return clientBuilder.build();
    }

}
