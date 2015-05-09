package com.example.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class DatasourceConfig {
    @Bean(name="ds1")
    @Primary
    public DataSource ds1() {
        System.out.println("yn dataSourceOne created");
        DataSource dataSource = createJndiDataSourceBy("java:/jdbc/XAds1");
//        DataSource dataSource = createJndiDataSourceBy("java:jboss/jdbc/HogeSystemXA");
        System.out.println("dataSourceOne:" + dataSource);
        return dataSource;
    }

    @Bean(name="ds2")
    public DataSource ds2() {
        System.out.println("yn dataSourceTwo created");
        DataSource dataSource = createJndiDataSourceBy("java:/jdbc/XAds2");
//        DataSource dataSource = createJndiDataSourceBy("java:jboss/jdbc/FooSystemXA");
        System.out.println("dataSourceTwo:" + dataSource);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("ds1") DataSource dataSource) {
        System.out.println("yn jdbcTemplate created");
        System.out.println("dataSource:" + dataSource);
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate2(@Qualifier("ds2") DataSource dataSource) {
        System.out.println("yn jdbcTemplate2 created");
        System.out.println("dataSource:" + dataSource);
        return new JdbcTemplate(dataSource);
    }

    private DataSource createJndiDataSourceBy(String jndiName) {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        DataSource dataSource = dataSourceLookup.getDataSource(jndiName);
        return dataSource;
    }
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JtaTransactionManager tm = new JtaTransactionManager();
        return tm;
    }
}
