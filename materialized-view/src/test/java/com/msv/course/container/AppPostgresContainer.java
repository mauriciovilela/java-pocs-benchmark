package com.msv.course.container;

import org.testcontainers.containers.PostgreSQLContainer;

public class AppPostgresContainer extends PostgreSQLContainer<AppPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:13.1-alpine";

    private static AppPostgresContainer container;

    private AppPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static AppPostgresContainer getInstance() {
        if (container == null) {
            container = new AppPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        System.out.println("######################### STARTING POSTGRES #########################");
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}