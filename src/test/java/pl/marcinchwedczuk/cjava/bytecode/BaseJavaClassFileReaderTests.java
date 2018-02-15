package pl.marcinchwedczuk.cjava.bytecode;

import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithCode;

import java.io.IOException;

public class BaseJavaClassFileReaderTests {
	protected static JavaClassFile load(Class<?> klass) throws IOException {
		byte[] klassBytes = TestUtils.readClassBytes(klass);

		JavaClassFileLoader loader = new JavaClassFileLoader();
		return loader.load(klassBytes);
	}
}
