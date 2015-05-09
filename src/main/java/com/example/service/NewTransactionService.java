package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewTransactionService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doService() {
        jdbcTemplate.update("insert into ora_table (f) values (?)", 100);
    }
}
