package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sendinblue.ApiClient;
import sibApi.TransactionalEmailsApi;

@Configuration
public class SendInBlueConfig {
    @Value("${sendinblue.api.key}")
    private String apiKey;

    @Bean
    public TransactionalEmailsApi transactionalEmailsApi() {
        ApiClient apiClient = new ApiClient();
        apiClient.setApiKey(apiKey);

        return new TransactionalEmailsApi(apiClient);
    }
}
