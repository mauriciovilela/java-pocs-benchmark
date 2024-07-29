package com.msv.course;

import com.msv.course.container.AppPostgresContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.IntStream;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class MaterializedViewAppTests {

	@Autowired
	private WebTestClient webTestClient;

	public static PostgreSQLContainer<?> postgresContainer = AppPostgresContainer.getInstance();
	private static final Long MAX_REQUESTS = 100L;

	@BeforeAll
    static void beforeAll() {
		postgresContainer.start();
	}

	@Test
	void testAndCompareResults() {

		// requests with default query
		Instant startTimeTables = Instant.now();
		IntStream.rangeClosed(1, MAX_REQUESTS.intValue())
				.forEach(i -> this.webTestClient
						.get()
						.uri("/query-with-tables")
						.exchange()
						.expectStatus()
						.isOk());
		long totalInMsTables = Duration.between(startTimeTables, Instant.now()).toMillis();

		// requests with materialized view
		Instant startTimeView = Instant.now();
		IntStream.rangeClosed(1, MAX_REQUESTS.intValue())
				.forEach(i -> this.webTestClient
						.get()
						.uri("/query-with-view")
						.exchange()
						.expectStatus()
						.isOk());
		long totalInMsView = Duration.between(startTimeView, Instant.now()).toMillis();

		long totalTimePerReqView = (totalInMsView / MAX_REQUESTS);
		long totalTimePerReqTables = (totalInMsTables / MAX_REQUESTS);

		System.out.printf("Report for default query ### timePerRequest=%d(ms) totalRequests=%d%n",
				totalTimePerReqTables, MAX_REQUESTS);
		System.out.printf("Report for materialized view ### timePerRequest=%d(ms) totalRequests=%d%n",
				totalTimePerReqView, MAX_REQUESTS);
	}

}
