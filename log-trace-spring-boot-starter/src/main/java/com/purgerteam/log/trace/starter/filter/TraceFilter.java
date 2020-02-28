package com.purgerteam.log.trace.starter.filter;

import com.purgerteam.log.trace.starter.Constants;
import com.purgerteam.log.trace.starter.TraceContentFactory;
import com.purgerteam.log.trace.starter.wrapper.HeaderMapRequestWrapper;
import org.apache.tomcat.util.http.MimeHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author purgeyao
 * @since 1.0
 */
public class TraceFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(httpServletRequest);
        String headerTraceId = httpServletRequest.getHeader(Constants.LEGACY_TRACE_ID_NAME);
        String headerParentServiceName = httpServletRequest.getHeader(Constants.LEGACY_PARENT_SERVICE_NAME);


        Map<String, String> map = new HashMap<>(16);
        map.put(Constants.LEGACY_TRACE_ID_NAME, headerTraceId);
        map.put(Constants.LEGACY_PARENT_SERVICE_NAME, headerParentServiceName);
        TraceContentFactory.storageMDC(map);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("URL ===> {}", httpServletRequest.getRequestURI());
            LOGGER.info("IP ===> {}",httpServletRequest.getRemoteAddr());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        MDC.clear();
    }

}
