package com.kof.log.management.plugins.configs.resttemplates;

import com.kof.log.management.plugins.entity.LogInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;

@Component
public class LogRestTemplate  {

    @Autowired
    RestTemplate restTemplate;

    public <T> ResponseEntity<T> exchange(LogInfo logInfo,String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        HttpEntity<?> logRequestEntity = getLogHttpEntity(logInfo, requestEntity);
        return restTemplate.exchange(url, method, logRequestEntity, responseType, uriVariables);
    }

    public <T> ResponseEntity<T> exchange(LogInfo logInfo,String url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        HttpEntity<?> logRequestEntity = getLogHttpEntity(logInfo, requestEntity);
        return restTemplate.exchange(url, method, logRequestEntity, responseType, uriVariables);
    }


    public <T> ResponseEntity<T> exchange(LogInfo logInfo,URI url, HttpMethod method, HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        HttpEntity<?> logRequestEntity = getLogHttpEntity(logInfo, requestEntity);
        return restTemplate.exchange(url, method, logRequestEntity, responseType);
    }


    public <T> ResponseEntity<T> exchange(LogInfo logInfo,RequestEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        RequestEntity<?> logRequestEntity = getLogRequestEntity(logInfo, requestEntity);
        return restTemplate.exchange(logRequestEntity, responseType);
    }


    public <T> ResponseEntity<T> exchange(LogInfo logInfo,RequestEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        RequestEntity<?> logRequestEntity = getLogRequestEntity(logInfo, requestEntity);
        return restTemplate.exchange(logRequestEntity, responseType);
    }

    private RequestEntity<?> getLogRequestEntity(LogInfo logInfo, RequestEntity<?> requestEntity) {

        HttpHeaders logHeaders= logInfo2Map(logInfo);
//        HttpHeaders headers = requestEntity.getHeaders();
//        headers.addAll(logHeaders);
        return  new RequestEntity(requestEntity.getBody(),logHeaders,requestEntity.getMethod() ,requestEntity.getUrl(),requestEntity.getType());
    }


    private HttpEntity<?> getLogHttpEntity(LogInfo logInfo, HttpEntity<?> requestEntity) {
        MultiValueMap<String, String> logHeaders= logInfo2Map(logInfo);
        HttpHeaders headers = requestEntity.getHeaders();
        HttpHeaders Logheaders=new HttpHeaders();
        Logheaders.addAll(logHeaders);
        return new HttpEntity<>(requestEntity.getBody(),headers);
    }

    private HttpHeaders logInfo2Map( LogInfo logInfo) {

        String comsumer = logInfo.getComsumer();

        HttpHeaders multiValueMap=new HttpHeaders();
        multiValueMap.add("comsumer",logInfo.getComsumer());
        multiValueMap.add("provider",logInfo.getProvider());
        multiValueMap.add("FunctiongName",logInfo.getFunctiongName());
        multiValueMap.add("methodName",logInfo.getMethodName());
//         Class<? extends LogInfo> logInfoClass = logInfo.getClass();
//        Field[] declaredFields = logInfoClass.getDeclaredFields();
//        Arrays.stream(declaredFields).forEach(x->{
//            String name = x.getName();
//            try {
//                multiValueMap.add(name, String.valueOf(logInfoClass.getMethod("get"+name.substring(0,1).toUpperCase()
//                        +name.substring(1,name.length()), (Class<?>) null)));
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//        });
        return multiValueMap;
    }


}
