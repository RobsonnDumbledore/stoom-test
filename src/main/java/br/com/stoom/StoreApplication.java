package br.com.stoom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCaching
@SpringBootApplication
public class StoreApplication {

	private static final Logger LOG = LoggerFactory.getLogger(StoreApplication.class);
	private static final String DEFAULT_PROFILE = "local";

	public static void main(String[] args) {

		LOG.info("[step:to-be-init] [id:1] Inicializando o Spring");
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, DEFAULT_PROFILE);
		SpringApplication.run(StoreApplication.class, args);
	}

}
