package org.mickey.framework.core.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.StringUtil;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.ResponseWrapper;
import java.io.IOException;
import java.util.Enumeration;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Order(99)
@WebFilter(filterName = "systemContextFilter", urlPatterns = "/*")
public class SystemContextFilter implements Filter {

    private String appId;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try{
            log.debug("system context init");
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String currentHeader = headerNames.nextElement();
                if (StringUtils.startsWithIgnoreCase(currentHeader, SystemConstant.CONTEXT_HEADER_PREFIX)) {
                    String headerValue = request.getHeader(currentHeader);
                    String requestURI = request.getRequestURI();
                    if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_USER_ID, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_USER_ID, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_USER_ID, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_ACCOUNT_ID, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_ACCOUNT_ID, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_ACCOUNT_ID, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_ACCOUNT_NAME, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_ACCOUNT_NAME, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_ACCOUNT_NAME, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_USER_NAME, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_USER_NAME, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_USER_NAME, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_USER_IP, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_USER_IP, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_USER_IP, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_USER_AGENT, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_USER_AGENT, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_USER_AGENT, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_TENANT_ID, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_TENANT_ID, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_TENANT_ID, headerValue);
                    } else if (StringUtil.equalsIgnoreCase(SystemConstant.HEADER_LOCALE, currentHeader)) {
                        SystemContext.put(SystemConstant.HEADER_LOCALE, headerValue);
                        printSystemContextSetLog(requestURI, SystemConstant.HEADER_LOCALE, headerValue);
                    } else {
                        SystemContext.put(currentHeader, headerValue);
                    }
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            SystemContext.clean();
        }
    }

    @Override
    public void destroy() {

    }

    private void printSystemContextSetLog(String requestURI, String systemConstant, String headerValue) {
        log.debug("request {} has  header: {}, with value {}", requestURI, systemConstant, headerValue);
    }
}
