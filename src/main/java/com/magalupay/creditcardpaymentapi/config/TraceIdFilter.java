package com.magalupay.creditcardpaymentapi.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import java.io.IOException;
import java.util.UUID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {
    private static final String TRACE_ID_HEADER = "X-Trace-ID";
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    private static final String TRACE_ID_MDC = "traceId";
    private static final String REQUEST_ID_MDC = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(TRACE_ID_HEADER);
            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString();
            }
            MDC.put(TRACE_ID_MDC, traceId);

            String requestId = request.getHeader(REQUEST_ID_HEADER);
            if (requestId != null && !requestId.isEmpty()) {
                MDC.put(REQUEST_ID_MDC, requestId);
            }

            response.addHeader(TRACE_ID_HEADER, traceId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}

