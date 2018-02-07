package pl.marcinchwedczuk.cjava.bytecode;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {
	private TestUtils() { }

	public static byte[] readClassBytes(Class<?> klass) {
		Preconditions.checkNotNull(klass);

		// from: https://stackoverflow.com/a/2036139/1779504

		String className = klass.getName();
		String classAsPath = className.replace('.', '/') + ".class";

		try (InputStream classBytes = TestUtils.class
				.getClassLoader()
				.getResourceAsStream(classAsPath)) {

			return ByteStreams.toByteArray(classBytes);
		}
		catch (IOException e) {
			throw new RuntimeException("Cannot load class: " + klass.getName() + ".", e);
		}
	}
}
