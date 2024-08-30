package com.day4.logging_monitoring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Slf4j
@Component
public class LoggingService extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);
        logResponse(requestWrapper, responseWrapper);
    }

    private void logResponse(ContentCachingRequestWrapper requestWrapper,
                             ContentCachingResponseWrapper responseWrapper)
            throws IOException {
        log.info("URL yang diakses: {}", requestWrapper.getRequestURL());
        log.info("Method HTTP: {}", requestWrapper.getMethod());
        log.info("Status HTTP yang dikembalikan: {}", responseWrapper.getStatus());

        responseWrapper.copyBodyToResponse();
    }

}