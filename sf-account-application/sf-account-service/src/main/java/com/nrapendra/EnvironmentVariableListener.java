package com.nrapendra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class EnvironmentVariableListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\" + ".env");
            props.load(fis);

            props.forEach((key, value) -> {
                System.setProperty(key.toString(), value.toString());
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
