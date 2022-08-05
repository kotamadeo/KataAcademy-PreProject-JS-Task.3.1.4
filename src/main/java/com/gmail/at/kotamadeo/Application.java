package com.gmail.at.kotamadeo;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		startPage();
	}

	@SneakyThrows
	private static void startPage() {
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("rundll32 url.dll,FileProtocolHandler " + "http://localhost:8080/");
	}
}
