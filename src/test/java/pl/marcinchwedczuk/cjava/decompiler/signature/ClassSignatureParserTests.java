package pl.marcinchwedczuk.cjava.decompiler.signature;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassSignatureParserTests {

	@Test
	public void canParseClassSignature() throws Exception {
		String input = "<T:Ljava/lang/Object;B:Ljava/lang/Enum<TB;>;>Ljava/lang/Object;";

		ClassSignatureParser parser = new ClassSignatureParser(new TokenStream(input));

		assertThat(parser.parse().toJavaString())
				.isEqualTo("<T extends java.lang.Object, B extends java.lang.Enum<B>> extends java.lang.Object");

	}
}