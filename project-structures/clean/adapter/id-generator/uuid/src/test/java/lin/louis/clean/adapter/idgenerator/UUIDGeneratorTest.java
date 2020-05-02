package lin.louis.clean.adapter.idgenerator;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.clean.usecase.port.IdGenerator;

class UUIDGeneratorTest {

	private static final IdGenerator ID_GENERATOR = new UUIDGenerator();

	@Test
	void generate() {
		var id = ID_GENERATOR.generate();

		Assertions.assertDoesNotThrow(() -> UUID.fromString(id));
	}
}
