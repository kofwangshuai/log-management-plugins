package com.kof.log.management.plugins.configs.resttemplates;



import com.alibaba.fastjson.JSON;
import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.URI;
import java.util.Date;
import java.util.List;

public class RestTemplateLogRequestInterceptor implements ClientHttpRequestInterceptor {

    Logger logger=LoggerFactory.getLogger(RestTemplateLogRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        HttpMethod method = httpRequest.getMethod();
        logger.info("HttpMethod>>>>>>>>>>>"+JSON.toJSONString(method));
        String methodValue = httpRequest.getMethodValue();
        logger.info("methodValue>>>>>>>>>>>"+JSON.toJSONString(methodValue));
        URI uri = httpRequest.getURI();
        logger.info("uri>>>>>>>>>>>"+JSON.toJSONString(uri));
        HttpHeaders headers = httpRequest.getHeaders();
        logger.info("headers>>>>>>>>>>>"+JSON.toJSONString(headers));
        MediaType contentType = headers.getContentType();

        List<String> strings = headers.get("x-loginfo-data");
        logger.info("x-loginfo-data>>>>>>>>>>>"+JSON.toJSONString(strings));

        List<String> remove = headers.remove("x-loginfo-data");
        logger.info("x-loginfo-data remove >>>>>>>>>>>"+JSON.toJSONString(remove));

        if (contentType!=null&&(contentType.isCompatibleWith(MediaType.APPLICATION_JSON)||contentType.isCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
                ||contentType.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED))){
            String data = new String(bytes, "utf-8");
            logger.info("request-data>>>>>>>>>>>"+data);
        }
         long startTime=System.currentTimeMillis();
        logger.info("request-startTime>>>>>>>>>>>"+new Date(startTime));
        ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, bytes);
        long endTime=System.currentTimeMillis();
        logger.info("request-endTime>>>>>>>>>>>"+new Date(endTime));
        long times=(endTime-startTime);
        String s = (times % 1000) == 0 ? ((times / 1000) + "seconds") : ((times / 1000) + "seconds" + (times % 1000) + "miniseconds");
        logger.info("request-(endTime-startTime = )>>>>>>>>>>>"+s);

        int rawStatusCode = clientHttpResponse.getRawStatusCode();
        logger.info("response-rawStatusCode>>>>>>>>>>>  "+rawStatusCode);
        HttpStatus statusCode = clientHttpResponse.getStatusCode();
        logger.info("response-statusCode>>>>>>>>>>> "+statusCode.toString());
        String statusText = clientHttpResponse.getStatusText();
        logger.info("response-statusText>>>>>>>>>>>  "+statusText);
        HttpHeaders headers1 = clientHttpResponse.getHeaders();
//        InputStream body = clientHttpResponse.getBody();
//        Writer output=new StringBuilderWriter();
//        org.apache.commons.io.IOUtils.copy(body,output);
//        StringBuilder builder = ((StringBuilderWriter) output).getBuilder();
        MediaType contentType1 = headers1.getContentType();
        if (contentType1.isCompatibleWith(MediaType.APPLICATION_JSON)||contentType1.isCompatibleWith(MediaType.APPLICATION_JSON_UTF8)
                ||contentType1.isCompatibleWith(MediaType.APPLICATION_FORM_URLENCODED)){
            String data = new String(bytes, "utf-8");
            logger.info("response-data>>>>>>>>>>>"+JSON.toJSONString(clientHttpResponse));
        }

        logger.info("response-data>>>>>>>>>>>"+JSON.toJSONString(clientHttpResponse));
        return clientHttpResponse;
    }
}
