package com.example.finance.controller;

import com.example.finance.service.ApplicationService;
import com.openapi.finance.api.ApplicationsApiDelegate;
import com.openapi.finance.model.Application;
import com.openapi.finance.model.ApplicationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/applications")
public class FinanceController implements ApplicationsApiDelegate {
    private final ApplicationService applicationService;

    public FinanceController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<Flux<Application>>> addApplication(
            Mono<ApplicationRequest> applicationRequest,
            ServerWebExchange exchange) {
        var applicationFlux = applicationService.addApplication(applicationRequest);
        return Mono.just(ResponseEntity.ok(applicationFlux));
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Application>> getApplicationById(
            @PathVariable UUID id,
            ServerWebExchange exchange) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
