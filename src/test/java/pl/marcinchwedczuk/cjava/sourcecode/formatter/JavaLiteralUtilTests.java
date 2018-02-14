package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaLiteralUtilTests {
	@Test
	public void properlyEscapesJavaEscapeSequences() throws Exception {
		String input = "text\t\b\n\r\f\"\'\\\u263Atext";

		String result = JavaLiteralUtil.javaEscape(input);

		assertThat(result)
				.isEqualTo("text\\t\\b\\n\\r\\f\\\"\\\'\\\\\\u263atext");
	}
}