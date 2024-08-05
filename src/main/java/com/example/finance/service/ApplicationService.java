package com.example.finance.service;

import com.example.finance.mapper.ApplicationMapper;
import com.example.finance.mapper.FastBankMapper;
import com.example.finance.mapper.SolidBankMapper;
import com.openapi.client.fastbank.api.FastBankApi;
import com.openapi.client.solidbank.api.SolidBankApi;
import com.openapi.finance.model.Application;
import com.openapi.finance.model.ApplicationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class ApplicationService {
    private final FastBankApi fastBankApi;
    private final FastBankMapper fastBankMapper;
    private final SolidBankApi solidBankApi;
    private final SolidBankMapper solidBankMapper;
    private final ApplicationMapper applicationMapper;

    public ApplicationService(
            FastBankApi fastBankApi,
            FastBankMapper fastBankMapper,
            SolidBankApi solidBankApi,
            SolidBankMapper solidBankMapper,
            ApplicationMapper applicationMapper) {
        this.fastBankApi = fastBankApi;
        this.fastBankMapper = fastBankMapper;
        this.solidBankApi = solidBankApi;
        this.solidBankMapper = solidBankMapper;
        this.applicationMapper = applicationMapper;
    }

    public Flux<Application> addApplication(Mono<ApplicationRequest> applicationRequest) {
        log.info("Adding application: {}", applicationRequest);

        var cachedRequest = applicationRequest.cache();

        var fastBankMono = cachedRequest
                .flatMap(applicationRequestValue -> {
                    var fastBankApplicationRequest = applicationMapper.toFastBankApplicationRequest(applicationRequestValue);
                    return fastBankApi.addApplication(fastBankApplicationRequest);
                })
                .map(application -> {
                    log.info("FastBank Application response: {}", application);
                    return fastBankMapper.fromClient(application);
                });

        var solidBankMono = cachedRequest
                .flatMap(applicationRequestValue -> {
                    var solidBankApplicationRequest = applicationMapper.toSolidBankApplicationRequest(applicationRequestValue);
                    return solidBankApi.addApplication(solidBankApplicationRequest);
                })
                .map(application -> {
                    log.info("SolidBank Application response: {}", application);
                    return solidBankMapper.fromClient(application);
                });

        return Flux.merge(fastBankMono, solidBankMono);
    }

    public Mono<Application> getApplicationById(UUID id) {
        log.info("getApplication: {}", id);

        var fastBankMono = Mono.just(id)
                .flatMap(fastBankApi::getApplicationById)
                .map(application -> {
                    log.info("FastBank Application response: {}", application);
                    return fastBankMapper.fromClient(application);
                })
                .onErrorResume(e -> {
                    log.error("Error from FastBank", e);
                    return Mono.empty();
                });

        var solidBankMono = Mono.just(id)
                .flatMap(solidBankApi::getApplicationById)
                .map(application -> {
                    log.info("SolidBank Application response: {}", application);
                    return solidBankMapper.fromClient(application);
                })
                .onErrorResume(e -> {
                    log.error("Error from SolidBank", e);
                    return Mono.empty();
                });

        return Mono.firstWithValue(fastBankMono, solidBankMono);
    }
}
