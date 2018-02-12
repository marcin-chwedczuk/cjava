package pl.marcinchwedczuk.cjava.decompiler.descriptor.field;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class FieldDescriptorParserTests {
	@Test
	public void canParseBasicType() throws Exception {
		assertThat(parse("I").asSourceCodeString())
			.isEqualTo("int");
	}

	@Test
	public void canParseArrayType() throws Exception {
		assertThat(parse("[[I").asSourceCodeString())
				.isEqualTo("int[][]");
	}

	@Test
	public void canParseObjectType() throws Exception {
		assertThat(parse("Ljava/lang/Object;").asSourceCodeString())
				.isEqualTo("java.lang.Object");
	}

	@Test
	public void canParseArrayOfObjectTypes() throws Exception {
		assertThat(parse("[Ljava/lang/Object;").asSourceCodeString())
				.isEqualTo("java.lang.Object[]");
	}

	private static JavaType parse(String input) {
		return new FieldDescriptorParser(new TokenStream(input))
				.parse();
	}
}