package lin.louis.clean.adapter.idgenerator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lin.louis.clean.usecase.port.IdGenerator;

class LongIdGeneratorTest {

	private static final IdGenerator ID_GENERATOR = new LongIdGenerator();

	@Test
	void generate() {
		var id = ID_GENERATOR.generate();

		Assertions.assertDoesNotThrow(() -> Long.parseLong(id));
	}
}
