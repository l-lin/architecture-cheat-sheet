package lin.louis.layered.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.layered.domain.OrderService;
import lin.louis.layered.domain.PetService;
import lin.louis.layered.domain.simple.SimpleOrderService;
import lin.louis.layered.domain.simple.SimplePetService;
import lin.louis.layered.persistence.memory.MemoryOrderDAO;
import lin.louis.layered.persistence.memory.MemoryPetDAO;
import lin.louis.layered.web.dto.mapper.OrderMapper;
import lin.louis.layered.web.dto.mapper.PetMapper;

/**
 * PetStoreConfig is used to declare Spring beans, implementation to use, etc...
 */
@Configuration
public class LayeredPetStoreConfig {

	@Bean
	PetService petService() {
		return new SimplePetService(new MemoryPetDAO());
	}

	@Bean
	OrderService orderService(PetService petService) {
		return new SimpleOrderService(petService, new MemoryOrderDAO());
	}

	@Bean
	PetMapper petMapper() {
		return new PetMapper();
	}

	@Bean
	OrderMapper orderMapper(PetMapper petMapper) {
		return new OrderMapper(petMapper);
	}
}
