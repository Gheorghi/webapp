package com.endava.webapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@SpringBootApplication
public class WebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> executionTimeLoggingFilter() {
        return new FilterRegistrationBean<>() {{
            setOrder((OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER));
            setFilter(new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
                        throws ServletException, IOException {
                    StopWatch watch = new StopWatch();
                    watch.start();
                    try {
                        chain.doFilter(req, res);
                    } finally {
                        watch.stop();
                        log.info("REQUEST: {} completed in {} ms",
                                getUriWithMethodAndQuery(req), watch.getTotalTimeMillis());
                    }
                }

                private String getUriWithMethodAndQuery(HttpServletRequest req) {
                    return req.getMethod() + ": " + req.getRequestURI();
                }
            });
        }};
    }

}
