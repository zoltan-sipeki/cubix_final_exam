package hu.cubix.zoltan_sipeki.logistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import hu.cubix.zoltan_sipeki.logistics.configuration.DelayConfig;
import hu.cubix.zoltan_sipeki.logistics.security.JWTConfig;
import hu.cubix.zoltan_sipeki.logistics.service.InitDbService;

@SpringBootApplication
@EnableConfigurationProperties({ DelayConfig.class, JWTConfig.class })
public class LogisticsApplication implements CommandLineRunner {

	@Autowired
	private InitDbService initDb;

	public static void main(String[] args) {
		SpringApplication.run(LogisticsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initDb.initAll();
	}

}
