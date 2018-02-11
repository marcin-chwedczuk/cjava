package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.InvalidSignatureException;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaTypeParserTests {

	@Test
	public void canParseSimpleTypeSignatures() throws Exception {
		assertThat(parse("B").asSourceCodeString()).isEqualTo("byte");
		assertThat(parse("C").asSourceCodeString()).isEqualTo("char");
		assertThat(parse("D").asSourceCodeString()).isEqualTo("double");
		assertThat(parse("F").asSourceCodeString()).isEqualTo("float");
		assertThat(parse("I").asSourceCodeString()).isEqualTo("int");
		assertThat(parse("J").asSourceCodeString()).isEqualTo("long");
		assertThat(parse("S").asSourceCodeString()).isEqualTo("short");
		assertThat(parse("Z").asSourceCodeString()).isEqualTo("boolean");
	}

	@Test
	public void canParseNonGenericTypeSignatures() throws Exception {
		assertThat(parse("Ljava/lang/Boolean;").asSourceCodeString())
				.isEqualTo("java.lang.Boolean");

		assertThat(parse("[S").asSourceCodeString())
				.isEqualTo("short[]");

		assertThat(parse("[Ljava/lang/Short;").asSourceCodeString())
				.isEqualTo("java.lang.Short[]");
	}

	@Test
	public void canParseGenericTypeSignatures() throws Exception {
		assertThat(parse("Ljava/util/List<[Ljava/lang/Short;>;").asSourceCodeString())
				.isEqualTo("java.util.List<java.lang.Short[]>");

		assertThat(parse("Ljava/util/Map<Ljava/lang/Integer;Ljava/util/List<Ljava/lang/String;>;>;").asSourceCodeString())
				.isEqualTo("java.util.Map<java.lang.Integer, java.util.List<java.lang.String>>");
	}

	@Test
	public void canParseGenericTypeSignaturesWithWildcards() throws Exception {
		assertThat(parse("Ljava/util/Map<*+Ljava/lang/String;>;").asSourceCodeString())
				.isEqualTo("java.util.Map<?, ? extends java.lang.String>");

		assertThat(parse("Ljava/util/List<+Ljava/lang/Class<*>;>;").asSourceCodeString())
				.isEqualTo("java.util.List<? extends java.lang.Class<?>>");
	}

	@Test
	public void canParseGenericTypeSignaturesWithTypeParameters() throws Exception {
		assertThat(parse("Ljava/util/List<TT;>;").asSourceCodeString())
				.isEqualTo("java.util.List<T>");

		assertThat(parse("Ljava/util/List<Ljava/util/List<TT;>;>;").asSourceCodeString())
				.isEqualTo("java.util.List<java.util.List<T>>");

		assertThat(parse("Ljava/util/List<TB;>;").asSourceCodeString())
				.isEqualTo("java.util.List<B>");
	}

	@Test(expected = InvalidSignatureException.class)
	public void givenEmptyInputThrowsException() throws Exception {
		parse("");
	}

	public static JavaType parse(String input) throws Exception {
		return new JavaTypeSignatureParser(new TokenStream(input)).parse();
	}
}