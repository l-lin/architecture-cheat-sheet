package lin.louis.modular.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import lin.louis.modular.order.OrderPackage;
import lin.louis.modular.order.dao.impl.MemoryOrderDAO;
import lin.louis.modular.order.service.OrderService;
import lin.louis.modular.order.service.impl.SimpleOrderService;
import lin.louis.modular.pet.service.PetService;

/**
 * PetStoreConfig is used to declare Spring beans, implementation to use, etc...
 */
@Configuration
@ComponentScan(basePackageClasses = OrderPackage.class)
public class OrderConfig {

	@Bean
	OrderService orderService(PetService petService) {
		return new SimpleOrderService(petService, new MemoryOrderDAO());
	}
}
