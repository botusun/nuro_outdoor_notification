package com.voidcode.nuro_outdoor_notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NuroOutdoorNotificationApplication {

  public static void main(String[] args) {
    SpringApplication.run(NuroOutdoorNotificationApplication.class, args);
  }
}
