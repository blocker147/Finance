package com.example.finance.controller;

import com.example.finance.config.JacksonConfig;
import com.example.finance.service.ApplicationService;
import com.openapi.finance.model.Application;
import com.openapi.finance.model.ApplicationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(JacksonConfig.class)
@WebFluxTest(controllers = FinanceController.class)
public class FinanceControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ApplicationService applicationService;

    @Test
    void testAddApplication() {
        var applicationRequest = new ApplicationRequest(
                "+37126000000",
                "john.doe@klix.app",
                new BigDecimal(1000),
                new BigDecimal(150),
                new BigDecimal(0),
                0,
                true,
                new BigDecimal(300),
                ApplicationRequest.MaritalStatusEnum.SINGLE,
                true);

        var application = new Application();
        application.id("587b56c0-3bf4-7efd-2883-1dd48b844b52");
        application.status(Application.StatusEnum.DRAFT);
        application.offer(null);

        when(applicationService.addApplication(any())).thenReturn(Flux.just(application));

        webTestClient.post()
                .uri("/api/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(applicationRequest), ApplicationRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Application.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertTrue(response.getResponseBody().contains(application));
                });
    }

    @Test
    void testGetApplication() {
        var application = new Application();
        application.id("587b56c0-3bf4-7efd-2883-1dd48b844b52");
        application.status(Application.StatusEnum.DRAFT);
        application.offer(null);

        when(applicationService.getApplicationById(any())).thenReturn(Mono.just(application));

        webTestClient.get()
                .uri("/api/applications/587b56c0-3bf4-7efd-2883-1dd48b844b52")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Application.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals(application, response.getResponseBody());
                });
    }

    @Test
    void testGetApplicationReturnsEmpty() {
        when(applicationService.getApplicationById(any())).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/applications/587b56c0-3bf4-7efd-2883-1dd48b844b52")
                .exchange()
                .expectStatus().isNotFound();
    }
}
