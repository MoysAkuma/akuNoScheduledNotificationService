package com.akumasoft.notificationscheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationscheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationscheduledApplication.class, args);
	}

}
