package com.msv.course.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.SimpleMessageListenerContainerFactory;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Collections;

@Configuration
public class CustomSqsConfiguration {

    private static final String LOCAL_ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE";
    private static final String LOCAL_SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";

    @Bean
    @Primary
    public AWSCredentialsProvider credentialsDEV() {
        AWSCredentials credentials = new BasicAWSCredentials(LOCAL_ACCESS_KEY, LOCAL_SECRET_KEY);
        return new AWSStaticCredentialsProvider(credentials);
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsyncDev() {
        AmazonSQSAsync sqsAsync = AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(credentialsDEV())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:9324", "us-east-1"))
                .build();
        return new AmazonSQSBufferedAsyncClient(sqsAsync);
    }

    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
                .withCredentials(credentialsDEV())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4575", "us-east-1"))
                .build();
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard().withRegion("us-east-1").withCredentials(credentialsDEV()).build();
    }


    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSQSAsync) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setAutoStartup(Boolean.TRUE);
        factory.setMaxNumberOfMessages(10);
        factory.setTaskExecutor(createDefaultTaskExecutor());
        return factory;
    }

    protected AsyncTaskExecutor createDefaultTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix("SQSExecutor - ");
        threadPoolTaskExecutor.setCorePoolSize(100);
        threadPoolTaskExecutor.setMaxPoolSize(200);
        threadPoolTaskExecutor.afterPropertiesSet();
        return threadPoolTaskExecutor;
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory() {
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(jackson2ObjectMapperBuilder.build());
        messageConverter.setStrictContentTypeMatch(false);
        factory.setArgumentResolvers(Collections.singletonList(new PayloadMethodArgumentResolver(messageConverter)));
        return factory;
    }

//    @Bean
//    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
//        return new QueueMessagingTemplate(amazonSQSAsync);
//    }
//
//    @Bean
//    public QueueMessageHandlerFactory queueMessageHandlerFactory(final ObjectMapper mapper,
//                                                                 final AmazonSQSAsync amazonSQSAsync) {
//        final QueueMessageHandlerFactory queueHandlerFactory = new QueueMessageHandlerFactory();
//        queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
//
//        queueHandlerFactory.setArgumentResolvers(
//                Collections.singletonList(new PayloadMethodArgumentResolver(jackson2MessageConverter(mapper))));
//        return queueHandlerFactory;
//    }
//
//    private MessageConverter jackson2MessageConverter(final ObjectMapper mapper) {
//
//        final MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        // set strict content type match to false to enable the listener to handle AWS events
//        converter.setStrictContentTypeMatch(false);
//        converter.setObjectMapper(mapper);
//        return converter;
//    }
}