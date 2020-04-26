package lin.louis.flat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.flat.dao.memory.MemoryOrderDAO;
import lin.louis.flat.dao.memory.MemoryPetDAO;
import lin.louis.flat.service.OrderService;
import lin.louis.flat.service.PetService;
import lin.louis.flat.service.simple.SimpleOrderService;
import lin.louis.flat.service.simple.SimplePetService;

/**
 * PetStoreConfig is used to declare Spring beans, implementation to use, etc...
 */
@Configuration
public class PetStoreConfig {

	@Bean
	PetService petService() {
		return new SimplePetService(new MemoryPetDAO());
	}

	@Bean
	OrderService orderService(PetService petService) {
		return new SimpleOrderService(petService, new MemoryOrderDAO());
	}
}
