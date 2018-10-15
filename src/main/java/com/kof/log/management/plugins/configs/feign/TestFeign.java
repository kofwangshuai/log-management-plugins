package com.kof.log.management.plugins.configs.feign;

import feign.Feign;
import feign.Logger;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(configuration =TestFeign.TestFeignConfig.class )
public interface TestFeign {

    @RequestMapping(value = "/get/logs" ,method = RequestMethod.GET)
    public String getLogTest();


    class TestFeignConfig{

        @Bean
        @Primary
        public Logger feignLogger() {
            Logger.JavaLogger javaLogger = new Logger.JavaLogger();
            return javaLogger;
        }

    }
}
