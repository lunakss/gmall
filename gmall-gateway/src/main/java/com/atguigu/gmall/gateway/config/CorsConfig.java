package com.atguigu.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @author lunakss
 * @create 2020-07-21 10:18
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);//允许携带cookie
        config.addAllowedOrigin("http://manager.gmall.com");//允许跨域访问的域名。不要写"*"，写*无法携带cookie
        config.addAllowedHeader("*");//允许携带头信息
        config.addAllowedMethod("*");//允许访问的方式

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);

        return new CorsWebFilter(source);
    }
}
