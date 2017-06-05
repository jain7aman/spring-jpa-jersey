package br.com.cinq.sample.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "br.com.cinq.sample")
public class AppConfiguration {
	/*
	 * This class is mainly providing the component-scanning
	 * and annotation support.
	 */
}
