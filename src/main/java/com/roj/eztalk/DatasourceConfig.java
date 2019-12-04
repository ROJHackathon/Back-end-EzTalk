package com.roj.eztalk;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatasourceConfig {
    @Bean
    public DataSource datasource() {
        String host = "eztalk-db.mysql.database.azure.com";
        String port = "3306";
        String name = "test";
        String url = 
        String.format("jdbc:mysql://%s:%s/%s?useSSL=true&requireSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", host, port, name);
        return DataSourceBuilder.create()
        .driverClassName("com.mysql.cj.jdbc.Driver")
        //.url("jdbc:mysql://eztalk-db.mysql.database.azure.com:3306/test?useSSL=true&requireSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC")
        .url(url)
        .username("rojhackathon@eztalk-db")
        .password("Wong1215")
        .build();
    }
}