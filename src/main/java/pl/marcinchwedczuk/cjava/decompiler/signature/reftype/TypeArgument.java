package pl.marcinchwedczuk.cjava.decompiler.signature.reftype;

public class TypeArgument {
	private final TypeArgumentWildcardIndicator wildcardIndicator;
	private final JavaTypeSignature type;
	private boolean wildcard;

	public TypeArgument(
			TypeArgumentWildcardIndicator wildcardIndicator,
			JavaTypeSignature type,
			boolean wildcard) {
		this.wildcardIndicator = wildcardIndicator;
		this.type = type;
		this.wildcard = wildcard;
	}

	public String toJavaString() {
		StringBuilder javaString = new StringBuilder();

		if (wildcard) {
			javaString.append("?");
		}

		if (wildcardIndicator != null) {
			javaString.append("? ");
			if (wildcardIndicator == TypeArgumentWildcardIndicator.EXTENDS) {
				javaString.append("extends ");
			} else {
				javaString.append("super ");
			}
		}

		if (type != null) {
			javaString.append(type.toJavaType());
		}

		return javaString.toString();
	}
}
