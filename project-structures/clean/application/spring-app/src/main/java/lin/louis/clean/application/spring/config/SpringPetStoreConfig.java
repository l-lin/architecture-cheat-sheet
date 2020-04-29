package lin.louis.clean.application.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lin.louis.clean.adapter.controller.OrderController;
import lin.louis.clean.adapter.controller.PetController;
import lin.louis.clean.adapter.controller.dto.mapper.OrderMapper;
import lin.louis.clean.adapter.controller.dto.mapper.PetMapper;
import lin.louis.clean.adapter.idgenerator.LongIdGenerator;
import lin.louis.clean.adapter.repository.MemoryOrderRepository;
import lin.louis.clean.adapter.repository.MemoryPetRepository;
import lin.louis.clean.usecase.OrderFinder;
import lin.louis.clean.usecase.PetFinder;
import lin.louis.clean.usecase.PetOrderer;
import lin.louis.clean.usecase.PetRegister;
import lin.louis.clean.usecase.port.IdGenerator;
import lin.louis.clean.usecase.port.OrderRepository;
import lin.louis.clean.usecase.port.PetRepository;

@Configuration
public class SpringPetStoreConfig {

	@Bean
	IdGenerator idGenerator() {
		return new LongIdGenerator();
	}

	@Bean
	PetRepository petRepository() {
		return new MemoryPetRepository();
	}

	@Bean
	OrderRepository orderRepository() {
		return new MemoryOrderRepository();
	}

	@Bean
	PetRegister petRegister(IdGenerator idGenerator, PetRepository petRepository) {
		return new PetRegister(idGenerator, petRepository);
	}

	@Bean
	PetFinder petFinder(PetRepository petRepository) {
		return new PetFinder(petRepository);
	}

	@Bean
	PetOrderer petOrderer(IdGenerator idGenerator, PetRepository petRepository, OrderRepository orderRepository) {
		return new PetOrderer(idGenerator, petRepository, orderRepository);
	}

	@Bean
	OrderFinder orderFinder(OrderRepository orderRepository) {
		return new OrderFinder(orderRepository);
	}

	@Bean
	PetMapper petMapper() {
		return new PetMapper();
	}

	@Bean
	OrderMapper orderMapper(PetMapper petMapper) {
		return new OrderMapper(petMapper);
	}

	@Bean
	PetController petController(
			PetRegister petRegister,
			PetFinder petFinder,
			PetOrderer petOrderer,
			PetMapper petMapper,
			OrderMapper orderMapper
	) {
		return new PetController(petRegister, petFinder, petOrderer, petMapper, orderMapper);
	}

	@Bean
	OrderController orderController(OrderFinder orderFinder, OrderMapper orderMapper) {
		return new OrderController(orderFinder, orderMapper);
	}
}
