package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import com.google.common.base.Strings;

public class JavaLiteralUtil {

	public static String javaEscape(String s) {
		if (Strings.isNullOrEmpty(s))
			return s;

		StringBuilder escapedString = new StringBuilder();

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			switch (c) {
				case '\b': escapedString.append("\\b"); break;
				case '\t': escapedString.append("\\t"); break;
				case '\n': escapedString.append("\\n"); break;
				case '\f': escapedString.append("\\f"); break;
				case '\r': escapedString.append("\\r"); break;
				case '\"': escapedString.append("\\\""); break;
				case '\'': escapedString.append("\\\'"); break;
				case '\\': escapedString.append("\\\\"); break;

				default:
					if (isPrintableAscii(c)) {
						escapedString.append(c);
					}
					else {
						escapedString.append(toUnicodeEscape(c));
					}
					break;
			}
		}

		return escapedString.toString();
	}

	private static String toUnicodeEscape(char c) {
		return "\\u" + Integer.toHexString(c);
	}

	private static boolean isPrintableAscii(char c) {
		// Range of printable ASCII characters
		return (0x20 <= c && c <= 0x7E);
	}
}
