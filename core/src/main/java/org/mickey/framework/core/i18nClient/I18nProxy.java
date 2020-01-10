package org.mickey.framework.core.i18nClient;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.dto.ErrorInfo;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Component
@Slf4j
public class I18nProxy {

    @Autowired
    @LoadBalanced
    SystemRestTemplate restTemplate;

    public ActionResult<Map<String, String>> queryByAppId() {
        String uri = "http://I18N-SERVICE/api/I18nDictionary/appId/" + SystemContext.getAppId();

        ResponseEntity<ActionResult<Map<String, String>>> exchange = restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<ActionResult<Map<String, String>>>() {
        });
        log.info("I18nProxy queryByAppId exchange is : " + exchange.toString());
        if (exchange.getStatusCode() == HttpStatus.OK) {
            return exchange.getBody();
        }
        return ActionResult.errors(new ErrorInfo(exchange.getStatusCodeValue(), exchange.toString()));
    }
}
