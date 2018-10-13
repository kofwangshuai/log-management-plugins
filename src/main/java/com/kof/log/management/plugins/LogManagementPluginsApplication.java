package com.kof.log.management.plugins;

import com.alibaba.fastjson.JSON;
import com.kof.log.management.plugins.configs.resttemplates.LogRestTemplate;
import com.kof.log.management.plugins.configs.resttemplates.RestTemplateLogRequestInterceptor;
import com.kof.log.management.plugins.entity.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class LogManagementPluginsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogManagementPluginsApplication.class, args);
    }

    Logger logger=LoggerFactory.getLogger(LogManagementPluginsApplication.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LogRestTemplate logRestTemplate;

    @RequestMapping("/restTempalte/get/logs")
    public String restTemplateGetLogs(){

        LogInfo logInfo = new LogInfo();
        logInfo.setComsumer("122222");
        logInfo.setProvider("33333333");
        logInfo.setFunctiongName("test");
        logInfo.setMethodName("3342423423");
        ResponseEntity<String> forEntity = null;
        try {
            forEntity = getStringResponseEntity(logRestTemplate, logInfo);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return forEntity.getBody();
    }

    private ResponseEntity<String> getStringResponseEntity(LogRestTemplate logRestTemplate, LogInfo logInfo) throws RestClientException, URISyntaxException {
        RequestEntity<?> requestEntity  =new RequestEntity(HttpMethod.GET,new URI("http://localhost:8080/get/logs"))  ;
        ResponseEntity<String> exchange = logRestTemplate.exchange(logInfo, requestEntity, String.class);
        return  exchange;
    }


    @RequestMapping("/get/logs")
    public String getLogTest(){
        return "test logs interface";
    }

}
