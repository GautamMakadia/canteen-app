package com.botmg3002.canteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages = {"com.botmg3002.canteen", "com.botmg3002.canteen.schema.order"}
)
public class CanteenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanteenApplication.class, args);
	}

}
