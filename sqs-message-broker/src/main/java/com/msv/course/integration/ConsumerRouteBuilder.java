package com.msv.course.integration;

import com.msv.course.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.aggregate.GroupedExchangeAggregationStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsumerRouteBuilder extends RouteBuilder {

    private final MessageService messageService;

    @Override
    public void configure() throws Exception {
        from("aws2-sqs://test-queue-camel?amazonSQSClient=#sqsClient&maxMessagesPerPoll=10")
                .aggregate(new GroupedExchangeAggregationStrategy())
                .constant(true)
                .completionTimeout(50L)
                .completionSize(100)
                 .process(this::process)
                .to("mock:test");
    }

    private void process(Exchange exchange) {
        List<Exchange> list = (List<Exchange>) exchange.getIn().getBody();
        log.info("body {}", list);
        for (Exchange item : list) {
            messageService.processMessageQueue(item.getIn().getBody().toString());
        }
    }

}