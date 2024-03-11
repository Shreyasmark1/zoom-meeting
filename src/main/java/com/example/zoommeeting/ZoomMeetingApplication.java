package com.example.zoommeeting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.zoommeeting.db")
@SpringBootApplication
public class ZoomMeetingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZoomMeetingApplication.class, args);
	}

}
