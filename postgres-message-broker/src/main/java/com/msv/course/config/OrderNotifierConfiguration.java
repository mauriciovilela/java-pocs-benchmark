package com.msv.course.config;

import com.msv.course.service.NotifierService;
import com.msv.course.consumer.NotificationConsumer;
import com.zaxxer.hikari.util.DriverDataSource;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.util.Properties;
import java.util.function.Consumer;

@Configuration
@Slf4j
public class OrderNotifierConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    NotifierService notifier(DataSourceProperties props) {
        DriverDataSource ds = new DriverDataSource(
                props.determineUrl(),
                props.determineDriverClassName(),
                new Properties(),
                props.determineUsername(),
                props.determinePassword());
        JdbcTemplate tpl = new JdbcTemplate(ds);
        return new NotifierService(tpl);
    }

    @Bean
    CommandLineRunner startListener(NotifierService notifier, NotificationConsumer notificationConsumer) {
        return (args) -> {
            log.info("Starting order listener thread...");
            for (PgTopicEnum topicName : PgTopicEnum.values()) {
                Runnable listener = createNotificationHandler(notificationConsumer, topicName);
                Thread thread = new Thread(listener, "t-" + topicName.getValue());
                thread.start();
            }
        };
    }

    private Runnable createNotificationHandler(Consumer<PGNotification> consumer, PgTopicEnum topicName) {
        return () -> jdbcTemplate.execute((Connection connection) -> {
            log.info("Postgres notification handler for topics...");
            connection.createStatement().execute("LISTEN " + topicName.getValue());
            PGConnection pgConnection = connection.unwrap(PGConnection.class);
            while (!Thread.currentThread().isInterrupted()) {
                PGNotification[] nts = pgConnection.getNotifications(10000);
                if (nts == null) {
                    continue;
                }
                for (PGNotification nt : nts) {
                    consumer.accept(nt);
                }
            }
            return 0;
        });
    }

}