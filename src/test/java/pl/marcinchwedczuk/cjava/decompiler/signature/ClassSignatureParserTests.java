package pl.marcinchwedczuk.cjava.decompiler.signature;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassSignatureParserTests {

	@Test
	public void canParseClassSignature() throws Exception {
		String input = "<T:Ljava/lang/Object;B:Ljava/lang/Enum<TB;>;>Ljava/lang/Object;";

		assertThat(parse(input).asJavaSourceCode())
				.isEqualTo("<T extends java.lang.Object, B extends java.lang.Enum<B>> extends java.lang.Object");
	}

	@Test
	public void regressionForBug_1() {
		String input = "<ParamA::Ljava/io/Serializable;ParamB::Ljava/util/List<TParamA;>;>Ljava/lang/Object;Lpl/marcinchwedczuk/cjava/bytecode/test/fixtures/Fixture_GenericInterface<TParamA;>;";

		assertThat(parse(input).asJavaSourceCode())
				.isEqualTo("<ParamA extends java.io.Serializable, ParamB extends java.util.List<ParamA>> extends java.lang.Object implements pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericInterface<ParamA>");
	}

	private static ClassSignature parse(String input) {
		return new ClassSignatureParser(new TokenStream(input)).parse();
	}
}