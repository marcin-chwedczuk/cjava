package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.InvalidSignatureException;
import pl.marcinchwedczuk.cjava.decompiler.signature.parser.TokenStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaTypeSignatureParserTests {

	@Test
	public void canParseSimpleTypeSignatures() throws Exception {
		assertThat(parse("B").toJavaType()).isEqualTo("byte");
		assertThat(parse("C").toJavaType()).isEqualTo("char");
		assertThat(parse("D").toJavaType()).isEqualTo("double");
		assertThat(parse("F").toJavaType()).isEqualTo("float");
		assertThat(parse("I").toJavaType()).isEqualTo("int");
		assertThat(parse("J").toJavaType()).isEqualTo("long");
		assertThat(parse("S").toJavaType()).isEqualTo("short");
		assertThat(parse("Z").toJavaType()).isEqualTo("boolean");
	}

	@Test
	public void canParseNonGenericTypeSignatures() throws Exception {
		assertThat(parse("Ljava/lang/Boolean;").toJavaType())
				.isEqualTo("java.lang.Boolean");

		assertThat(parse("[S").toJavaType())
				.isEqualTo("short[]");

		assertThat(parse("[Ljava/lang/Short;").toJavaType())
				.isEqualTo("java.lang.Short[]");
	}

	@Test
	public void canParseGenericTypeSignatures() throws Exception {
		assertThat(parse("Ljava/util/List<[Ljava/lang/Short;>;").toJavaType())
				.isEqualTo("java.util.List<java.lang.Short[]>");

		assertThat(parse("Ljava/util/Map<Ljava/lang/Integer;Ljava/util/List<Ljava/lang/String;>;>;").toJavaType())
				.isEqualTo("java.util.Map<java.lang.Integer, java.util.List<java.lang.String>>");
	}

	@Test
	public void canParseGenericTypeSignaturesWithWildcards() throws Exception {
		assertThat(parse("Ljava/util/Map<*+Ljava/lang/String;>;").toJavaType())
				.isEqualTo("java.util.Map<?, ? extends java.lang.String>");

		assertThat(parse("Ljava/util/List<+Ljava/lang/Class<*>;>;").toJavaType())
				.isEqualTo("java.util.List<? extends java.lang.Class<?>>");
	}

	@Test
	public void canParseGenericTypeSignaturesWithTypeParameters() throws Exception {
		assertThat(parse("Ljava/util/List<TT;>;").toJavaType())
				.isEqualTo("java.util.List<T>");

		assertThat(parse("Ljava/util/List<Ljava/util/List<TT;>;>;").toJavaType())
				.isEqualTo("java.util.List<java.util.List<T>>");

		assertThat(parse("Ljava/util/List<TB;>;").toJavaType())
				.isEqualTo("java.util.List<B>");
	}

	@Test(expected = InvalidSignatureException.class)
	public void givenEmptyInputThrowsException() throws Exception {
		parse("");
	}

	public static JavaTypeSignature parse(String input) throws Exception {
		return new JavaTypeSignatureParser(new TokenStream(input)).parse();
	}
}