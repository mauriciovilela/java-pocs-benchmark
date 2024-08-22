//package com.msv.course.config;
//
//import io.awspring.cloud.sqs.MessageExecutionThreadFactory;
//import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
//import io.awspring.cloud.sqs.operations.SqsTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//import software.amazon.awssdk.auth.credentials.AwsCredentials;
//import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
//import software.amazon.awssdk.core.SdkSystemSetting;
//import software.amazon.awssdk.regions.Region;
//import software.amazon.awssdk.services.sqs.SqsAsyncClient;
//
//import java.net.URI;
//import java.util.concurrent.ThreadFactory;
//
//@Component
//public class AwsConfiguration {
//
//    @Bean
//    public SqsAsyncClient sqsAsyncClient() {
//        return SqsAsyncClient.builder()
//                .credentialsProvider(buildAwsCredentialsProvider())
//
//                .region(Region.of("us-east-1"))
//                .endpointOverride(URI.create("http://localhost:9324"))
//                .build();
//    }
//
//    @Bean
//    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory() {
//        return SqsMessageListenerContainerFactory
//                .builder()
//                .configure(options -> options.componentsTaskExecutor(customTaskExecutorSqs()))
//                .sqsAsyncClient(sqsAsyncClient())
//                .build();
//    }
//
//    @Bean
//    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
//        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
//    }
//
//    private AwsCredentialsProvider buildAwsCredentialsProvider() {
//        return () -> new AwsCredentials() {
//            @Override
//            public String accessKeyId() {
//                return System.getenv(SdkSystemSetting.AWS_ACCESS_KEY_ID.environmentVariable());
//            }
//
//            @Override
//            public String secretAccessKey() {
//                return System.getenv(SdkSystemSetting.AWS_SECRET_ACCESS_KEY.environmentVariable());
//            }
//        };
//    }
//
//    private TaskExecutor customTaskExecutorSqs() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setMaxPoolSize(50);
//        executor.setAllowCoreThreadTimeOut(true);
//        executor.setThreadFactory(customThreadFactorySqs());
//        executor.afterPropertiesSet();
//        return executor;
//    }
//
//    private ThreadFactory customThreadFactorySqs() {
//        MessageExecutionThreadFactory threadFactory = new MessageExecutionThreadFactory();
//        threadFactory.setThreadNamePrefix("SQS_thread_");
//        return threadFactory;
//    }
//
//}
