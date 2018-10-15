package com.kof.log.management.plugins.configs.feign;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Collection;
import java.util.Map;

public class FeignLogRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {

        Map<String, Collection<String>> headers = requestTemplate.headers();
        String url = requestTemplate.url();

        Request request = requestTemplate.request();

    }
}
