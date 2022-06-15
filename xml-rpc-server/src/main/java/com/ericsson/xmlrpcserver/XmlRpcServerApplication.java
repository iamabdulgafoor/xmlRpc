package com.ericsson.xmlrpcserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.ericsson.xmlrpcserver" })
public class XmlRpcServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(XmlRpcServerApplication.class, args);

	}

}