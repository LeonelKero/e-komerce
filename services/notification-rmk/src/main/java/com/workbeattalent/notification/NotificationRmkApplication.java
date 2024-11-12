package com.workbeattalent.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NotificationRmkApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationRmkApplication.class, args);
	}

}
