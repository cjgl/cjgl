package cn.fy.cjgl;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CjglApplication {

	public static void main(String[] args) {
		//SpringApplication.run(CjglApplication.class, args);
		SpringApplication app = new SpringApplication(CjglApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
