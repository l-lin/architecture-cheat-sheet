package lin.louis.modular.pet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lin.louis.modular.pet.PetPackage;
import lin.louis.modular.pet.dao.impl.MemoryPetDAO;
import lin.louis.modular.pet.service.PetService;
import lin.louis.modular.pet.service.impl.SimplePetService;

/**
 * PetStoreConfig is used to declare Spring beans, implementation to use, etc...
 */
@Configuration
@ComponentScan(basePackageClasses = PetPackage.class)
public class PetConfig {

	@Bean
	PetService petService() {
		return new SimplePetService(new MemoryPetDAO());
	}
}
