package lin.louis.clean.adapter.idgenerator;

import java.util.concurrent.atomic.AtomicLong;

import lin.louis.clean.usecase.port.IdGenerator;

public class LongIdGenerator implements IdGenerator {

	private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

	@Override
	public String generate() {
		return Long.toString(ID_GENERATOR.incrementAndGet());
	}
}
