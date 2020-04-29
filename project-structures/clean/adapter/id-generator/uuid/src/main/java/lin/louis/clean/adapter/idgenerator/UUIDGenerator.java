package lin.louis.clean.adapter.idgenerator;

import java.util.UUID;

import lin.louis.clean.usecase.port.IdGenerator;

public class UUIDGenerator implements IdGenerator {

	@Override
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
