package biz.advanceitgroup.taxirideserver;

import biz.advanceitgroup.taxirideserver.authentification.configurations.AppProperties;
import biz.advanceitgroup.taxirideserver.commons.helpers.TaxiRideStorageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {
		TaxirideApplication .class,
		Jsr310JpaConverters.class,
                
})
@EnableConfigurationProperties(AppProperties.class)
public class TaxirideApplication implements CommandLineRunner {

	@Autowired
	TaxiRideStorageServices taxiRideStorageServices;

	public static void main(String[] args) {
		SpringApplication.run(TaxirideApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Initialiser le répertoire dans lequel il faut effectuer le stockage des fichiers
		taxiRideStorageServices.init();
	}
}
