package com.msv.course.integration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:sendMessage")
                .to("aws2-sqs://test-queue-camel?amazonSQSClient=#sqsClient");
    }

}