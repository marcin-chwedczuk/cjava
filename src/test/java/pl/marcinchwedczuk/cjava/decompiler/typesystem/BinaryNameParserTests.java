package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.BinaryNameParser;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryNameParserTests {
	@Test
	public void canParseSimpleBinaryName() {
		JavaType type = parse("java/lang/Object");

		assertThat(type.asSourceCodeString())
				.isEqualTo("java.lang.Object");
	}

	@Test
	public void canParseBinaryNameOfNestedClass() {
		JavaType type = parse("foo/bar/Parent$Child");

		assertThat(type.asSourceCodeString())
				.isEqualTo("foo.bar.Parent.Child");
	}

	private static JavaType parse(String input) {
		return new BinaryNameParser(input).parse();
	}
}