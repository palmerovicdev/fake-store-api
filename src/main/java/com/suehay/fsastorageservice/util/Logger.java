package com.suehay.fsastorageservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Logger {
    public void info(String context, String message) {
        log.info("{} : {}", context, message);
    }

    public void logError(String context, String message) {
        log.error("{} : {}", context, message);
    }

    public void logWarn(String context, String message) {
        log.warn("{} : {}", context, message);
    }

    public void logDebug(String context, String message) {
        log.debug("{} : {}", context, message);
    }
}