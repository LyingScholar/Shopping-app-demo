package com.ufund.api.ufundapi;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UfundApiApplication {

	public static void main(String[] args) {
		// Cupboard cupboard = null;
		// try {
		// 	cupboard = new Cupboard("needs.json", null);
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// HelperController helperController = new HelperController(cupboard);
		SpringApplication.run(UfundApiApplication.class, args);
	}

}
