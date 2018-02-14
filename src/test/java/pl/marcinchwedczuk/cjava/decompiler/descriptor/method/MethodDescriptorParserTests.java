package pl.marcinchwedczuk.cjava.decompiler.descriptor.method;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;


public class MethodDescriptorParserTests {
	@Test
	public void canParseMethodDescriptor() throws Exception {
		String input = "(IDLjava/lang/Thread;)Ljava/lang/Object;";

		MethodSignature methodSignature = parseMethodDescriptor(input);

		assertThat(methodSignature.getParameterTypes())
				.hasSize(3);

		assertThat(methodSignature.getParameterType(0).asSourceCodeString())
				.isEqualTo("int");

		assertThat(methodSignature.getParameterType(1).asSourceCodeString())
				.isEqualTo("double");

		assertThat(methodSignature.getParameterType(2).asSourceCodeString())
				.isEqualTo("java.lang.Thread");

		assertThat(methodSignature.getReturnType().asSourceCodeString())
			.isEqualTo("java.lang.Object");
	}

	@Test
	public void canParseDescriptorWithVoidReturnType() throws Exception {
		String input = "()V";

		MethodSignature methodSignature = parseMethodDescriptor(input);

		assertThat(methodSignature.getParameterTypes())
				.isEmpty();

		assertThat(methodSignature.getReturnType().asSourceCodeString())
				.isEqualTo("void");
	}

	private static MethodSignature parseMethodDescriptor(String input) {
		MethodDescriptorParser parser =
				new MethodDescriptorParser(new TokenStream(input));

		return parser.parse();
	}
}