package com.atguigu.gmall.sms.config;


import com.zaxxer.hikari.HikariDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * @author lunakss
 * @create 2020-07-24 16:40
 */
@Configuration
public class DataSourceProxyConfig {
    @Primary
    @Bean
    public DataSourceProxy dataSource(@Value("${spring.datasource.url}")String url,
                                      @Value("${spring.datasource.driver-class-name}")String driverClassName,
                                      @Value("${spring.datasource.username}")String username,
                                      @Value("${spring.datasource.password}")String password){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(url);
        hikariDataSource.setDriverClassName(driverClassName);
        hikariDataSource.setPassword(password);
        hikariDataSource.setUsername(username);
        return new DataSourceProxy(hikariDataSource);
    }
}
