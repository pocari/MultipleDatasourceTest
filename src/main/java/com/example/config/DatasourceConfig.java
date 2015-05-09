package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
public class DatasourceConfig {
    @Bean(name="ds1")
    @Primary
    public DataSource ds1() {
        DataSource dataSource = createJndiDataSourceBy("java:/jdbc/XAds1");
        return dataSource;
    }

    @Bean(name="ds2")
    public DataSource ds2() {
        DataSource dataSource = createJndiDataSourceBy("java:/jdbc/XAds2");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("ds1") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate2(@Qualifier("ds2") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private DataSource createJndiDataSourceBy(String jndiName) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(jndiName);
        return dataSource;
    }

}
