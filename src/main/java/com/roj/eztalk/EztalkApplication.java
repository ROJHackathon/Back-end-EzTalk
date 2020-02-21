package com.roj.eztalk;


import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.ConfigurableApplicationContext;

@EnableSwagger2Doc
@SpringBootApplication
public class EztalkApplication {

	public static void main(String[] args) {
		//ConfigurableApplicationContext applicationContext = 
		SpringApplication.run(EztalkApplication.class, args);
	}

}
