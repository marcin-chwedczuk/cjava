package pl.marcinchwedczuk.cjava.bytecode;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPoolIndex;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class TestUtils {
	private TestUtils() { }

	public static String readExpectedDecompiledSourceCode(Class<?> klass) throws IOException {
		String className = klass.getSimpleName();
		URL resourceUrl = Resources.getResource(className + ".decompiled");
		return Resources.toString(resourceUrl, Charsets.UTF_8);
	}

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

	public static ConstantPoolIndex idx(int index) {
		return ConstantPoolIndex.fromInteger(index);
	}
}
