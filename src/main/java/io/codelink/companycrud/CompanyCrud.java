package io.codelink.companycrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CompanyCrud {

	public static void main(String[] args) {
		SpringApplication.run(CompanyCrud.class, args);
	}
	
	public static interface SummaryView {}
	
}
