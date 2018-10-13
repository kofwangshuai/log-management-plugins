package com.kof.log.management.plugins.configs.resttemplates;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ConditionalOnClass(RestTemplate.class)
@Configuration
public class RestTemplateConfig {


    @Autowired
    RestTemplateAutoConfiguration restTemplateAutoConfiguration;

    @Bean
    @Primary
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        interceptors.add(new RestTemplateLogRequestInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
