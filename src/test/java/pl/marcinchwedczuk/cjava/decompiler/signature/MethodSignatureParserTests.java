package pl.marcinchwedczuk.cjava.decompiler.signature;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodSignatureParserTests {
	@Test
	public void canParseSimpleGenericMethodSignature() throws Exception {
		String signature = "(TT;)V";

		MethodSignature methodSignature = parse(signature);

		assertThat(methodSignature.asJavaSoucrceCode())
				.isEqualTo("void(T)");
	}

	@Test
	public void canParserSignatureOfMethodWithGenericParameter() throws Exception {
		String signature = "<E:Ljava/lang/Object;>(TE;TE;)Ljava/util/List<TE;>;";

		MethodSignature methodSignature = parse(signature);

		assertThat(methodSignature.asJavaSoucrceCode())
				.isEqualTo("<E extends java.lang.Object> java.util.List<E>(E, E)");
	}

	@Test
	public void canParseSignatureWithGenericThrows() throws Exception {
		String signature = "<T:Ljava/lang/Exception;>(TT;)V^TT;";

		MethodSignature methodSignature = parse(signature);

		assertThat(methodSignature.asJavaSoucrceCode())
				.isEqualTo("<T extends java.lang.Exception> void(T) throws T");
	}


	private static MethodSignature parse(String signature) {
		MethodSignatureParser parser =
				new MethodSignatureParser(new TokenStream(signature));

		return parser.parseMethodSignature();
	}
}
