package com.carlos.ifoodtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

@Component
public class LogRequestFilter implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogRequestFilter.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        traceRequest(httpRequest, body);
        ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, body);
        traceResponse(clientHttpResponse);

        return clientHttpResponse;
    }

    private void traceResponse(ClientHttpResponse clientHttpResponse) throws IOException {
        logger.debug("Response: ");
//        logger.debug(getBodyString(clientHttpResponse));
    }


    private String getBodyString(ClientHttpResponse response) {
        try {
            if (response != null && response.getBody() != null) {
                StringBuilder inputStringBuilder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
                String line = bufferedReader.readLine();
                while (line != null) {
                    inputStringBuilder.append(line);
                    inputStringBuilder.append('\n');
                    line = bufferedReader.readLine();
                }
                return inputStringBuilder.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    private void traceRequest(HttpRequest httpRequest, byte[] body) {
        logger.debug("Request ");
        logger.debug("Method:" + httpRequest.getMethod());
        logger.debug("URI: " + httpRequest.getURI());
        for (Map.Entry<String, List<String>> entry :
                httpRequest.getHeaders().entrySet()) {

            logger.debug("Header: " + entry.getKey() + " value: " + entry.getValue());
        }
        logger.debug("body: " + body);
    }
}
