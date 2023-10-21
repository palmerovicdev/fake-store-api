package com.suehay.fsastorageservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Month;
import java.util.Random;

@Slf4j
@Component
public class Tasks {

    @Value("${spring.server.url}")
    String SERVER_URL;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        // run some command in zsh
        for (int i = 0; i < 100; i++) {
            // generate random numbers between 1 and 30
            int randomDay = new Random().nextInt(30) + 1;
            try {
                var year = 23;
                var month = Month.OCTOBER.getValue();
                var date = month + "/" + randomDay + "/" + year;
                Runtime.getRuntime().exec(new String[]{"zsh", "-c",
                        "cd \"/Volumes/TuMaletin/Victor/Personal/fsa-storage-service/\" && echo \"a\" >> " +
                                "\"/Volumes/TuMaletin/Victor/Personal/fsa-storage-service/src/main/resources/contributions-file.txt\" &&" +
                                " git add --all && " +
                                "git commit -m \"feat: add new change\" --date " + date});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Swagger URL: {}swagger-ui/swagger-ui.html", SERVER_URL);
    }
}