package com.endava.webapp.service.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Aspect
@Slf4j
public class RequestTimeLogger {
    @Before("execution(* com.endava.webapp.controller.*.*(..))")
    private final void onStart() {
        log.info("Starting the request at: " + LocalTime.now());
    }

    @After("execution(* com.endava.webapp.controller.*.*(..))")
    private final void onEnd() {
        log.info("Ending the request at: " + LocalTime.now());
    }
}
