package com.nrapendra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class EnvironmentVariableListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        try {
            var props = new Properties();
            var path =  System.getProperty("user.dir") + "/.env";
            var pathValue = Paths.get(path);
            var isFileExist = Files.exists(pathValue);
            if(isFileExist) {
                var fis = new FileInputStream(path);
                props.load(fis);

                props.forEach((key, value) -> {
                    System.setProperty(key.toString(), value.toString());
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
