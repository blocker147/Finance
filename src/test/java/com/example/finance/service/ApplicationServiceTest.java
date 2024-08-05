package com.example.finance.service;

import com.example.finance.mapper.ApplicationMapper;
import com.example.finance.mapper.FastBankMapper;
import com.example.finance.mapper.SolidBankMapper;
import com.openapi.client.fastbank.api.FastBankApi;
import com.openapi.finance.model.Application;
import com.openapi.finance.model.ApplicationRequest;
import com.openapi.client.solidbank.api.SolidBankApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @Mock
    private FastBankApi fastBankApi;

    @Mock
    private SolidBankApi solidBankApi;

    @Mock
    private ApplicationMapper applicationMapper;

    @Mock
    private FastBankMapper fastBankMapper;

    @Mock
    private SolidBankMapper solidBankMapper;

    @InjectMocks
    private ApplicationService target;

    @Test
    void testAddApplication() {
        var request = new ApplicationRequest();
        var application = new Application();
        var fastBankRequest = new com.openapi.client.fastbank.model.ApplicationRequest();
        var solidBankRequest = new com.openapi.client.solidbank.model.ApplicationRequest();
        var fastBankResponse = new com.openapi.client.fastbank.model.Application();
        var solidBankResponse = new com.openapi.client.solidbank.model.Application();

        when(applicationMapper.toFastBankApplicationRequest(request)).thenReturn(fastBankRequest);
        when(applicationMapper.toSolidBankApplicationRequest(request)).thenReturn(solidBankRequest);
        when(fastBankApi.addApplication(fastBankRequest)).thenReturn(Mono.just(fastBankResponse));
        when(solidBankApi.addApplication(solidBankRequest)).thenReturn(Mono.just(solidBankResponse));
        when(fastBankMapper.fromClient(fastBankResponse)).thenReturn(application);
        when(solidBankMapper.fromClient(solidBankResponse)).thenReturn(application);

        var resultFlux = target.addApplication(Mono.just(request));

        StepVerifier.create(resultFlux)
                .expectNext(application, application)
                .verifyComplete();

        verify(applicationMapper).toFastBankApplicationRequest(request);
        verify(applicationMapper).toSolidBankApplicationRequest(request);
        verify(fastBankApi).addApplication(fastBankRequest);
        verify(solidBankApi).addApplication(solidBankRequest);
        verify(fastBankMapper).fromClient(fastBankResponse);
        verify(solidBankMapper).fromClient(solidBankResponse);
    }

    @Test
    void testGetApplication() {
        var id = UUID.fromString("587b56c0-3bf4-7efd-2883-1dd48b844b52");
        var application = new Application();
        var fastBankApplication = new com.openapi.client.fastbank.model.Application();
        var fastBankResponse = new com.openapi.client.fastbank.model.Application();
        var solidBankResponse = new com.openapi.client.solidbank.model.Application();

        when(fastBankApi.getApplicationById(any())).thenReturn(Mono.just(fastBankResponse));
        when(solidBankApi.getApplicationById(any())).thenReturn(Mono.just(solidBankResponse));
        when(fastBankMapper.fromClient((com.openapi.client.fastbank.model.Application) any()))
                .thenThrow(new RuntimeException("Error in fastBankMapper"));
        when(solidBankMapper.fromClient((com.openapi.client.solidbank.model.Application) any()))
                .thenReturn(application);

        var resultMono = target.getApplicationById(id);

        StepVerifier.create(resultMono)
                .expectNext(application)
                .verifyComplete();

        verify(fastBankApi).getApplicationById(id);
        verify(fastBankMapper).fromClient(fastBankApplication);
        verify(solidBankApi).getApplicationById(id);
        verify(solidBankMapper).fromClient(solidBankResponse);
    }
}
