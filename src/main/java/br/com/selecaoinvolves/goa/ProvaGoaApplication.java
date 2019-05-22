package br.com.selecaoinvolves.goa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.selecaoinvolves.goa.controller","br.com.selecaoinvolves.goa.web","br.com.selecaoinvolves.goa.configuration"})
public class ProvaGoaApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProvaGoaApplication.class, args);
	}

}
