package com.msv.course.config;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Component
public class AwsConfiguration {

    @Bean
    @Primary
    public AmazonSQSAsync amazonSqsAsync() {
        final var clientBuilder = AmazonSQSAsyncClientBuilder.standard();
        clientBuilder.setEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "us-east-1"));
        clientBuilder.setCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials("AWS_ACCESS_KEY", "AWS_SECRET")));
        return clientBuilder.build();
    }

    @Bean(name = "sqsClient")
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .endpointOverride(URI.create("http://localhost:9324"))
                .region(Region.US_EAST_1)
                .credentialsProvider(buildAwsCredentialsProvider())
                .build();
    }

    private AwsCredentialsProvider buildAwsCredentialsProvider() {
        return () -> new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return System.getenv(SdkSystemSetting.AWS_ACCESS_KEY_ID.environmentVariable());
            }

            @Override
            public String secretAccessKey() {
                return System.getenv(SdkSystemSetting.AWS_SECRET_ACCESS_KEY.environmentVariable());
            }
        };
    }


}
