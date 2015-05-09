package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class TransactionManagerConfig {
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JtaTransactionManager();
    }
}
