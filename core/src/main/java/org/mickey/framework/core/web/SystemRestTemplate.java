package org.mickey.framework.core.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class SystemRestTemplate extends RestTemplate {
    public SystemRestTemplate() {
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        Integer poolSize = Integer.valueOf(Runtime.getRuntime().availableProcessors() * 2 - 1);
        poolSize = Integer.valueOf((poolSize.intValue() < 4) ? 4 : poolSize.intValue());
        httpClientBuilder.setMaxConnTotal(poolSize.intValue() * 10);
        httpClientBuilder.setMaxConnPerRoute(poolSize.intValue());
        httpClientBuilder.disableCookieManagement();

        httpClientBuilder.disableAuthCaching();

        httpClientBuilder.setConnectionTimeToLive(60L, TimeUnit.SECONDS);

        RequestConfig.Builder builder = RequestConfig.custom();

        httpClientBuilder.setDefaultRequestConfig(builder.build());
        httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE);
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
        setRequestFactory(httpComponentsClientHttpRequestFactory);
    }


    public SystemRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
    }


    protected ClientHttpRequest createRequest(URI url, HttpMethod method) throws IOException {
        ClientHttpRequest request = super.createRequest(url, method);

        HttpHeaders headers = request.getHeaders();

        RestTemplateUtils.addSystemContextToHeader(headers);
        return request;
    }
}
