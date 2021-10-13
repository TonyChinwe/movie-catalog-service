package com.phisoft.moviecatalogservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ProjectConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getTemplate(){
        HttpComponentsClientHttpRequestFactory requestFactory=new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        return new RestTemplate(requestFactory);
    }
    @Bean
    public WebClient.Builder getWebClientBuilder(){
        return WebClient.builder();
    }
}
