package com.example.finance.config;

import com.openapi.client.fastbank.api.FastBankApi;
import com.openapi.client.solidbank.api.SolidBankApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public FastBankApi fastBankApi() {
        return new FastBankApi();
    }

    @Bean
    public SolidBankApi solidBankApi() {
        return new SolidBankApi();
    }
}
