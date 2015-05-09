package com.example.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IndexService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate2;

    @Autowired
    private NewTransactionService newTransactionService;

    public String doService() throws Exception {
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from ora_table");
        String val = result.stream().map(m -> m.get("f").toString()).collect(Collectors.joining(","));

        jdbcTemplate.update("insert into ora_table (f) values (?)", 5);
        jdbcTemplate2.update("insert into pg_table (f) values (?)", 20);

        newTransactionService.doService();

        int a = 1;
        if (a == 1) {
            throw new Exception("exception occured");
        }

        List<Map<String, Object>> result2 = jdbcTemplate2.queryForList("select * from pg_table");
        String val2 = result2.stream().map(m -> m.get("f").toString()).collect(Collectors.joining(","));

        return String.format("Hello, World!! [%s], [%s]", val, val2);
        //        return String.format("Hello, World!!  [%s]", val2);
    }
}
