package pl.marcinchwedczuk.cjava.decompiler.signature.reftype;

public enum TypeArgumentWildcardIndicator {
	EXTENDS('+'),
	SUPER('-');

	private final char signatureConstant;

	TypeArgumentWildcardIndicator(char signatureConstant) {
		this.signatureConstant = signatureConstant;
	}

	private boolean representsConstant(char signatureConstant) {
		return this.signatureConstant == signatureConstant;
	}

	public static TypeArgumentWildcardIndicator parse(char signatureConstant) {
		for (TypeArgumentWildcardIndicator wildcardIndicator : values()) {
			if (wildcardIndicator.representsConstant(signatureConstant)) {
				return wildcardIndicator;
			}
		}

		throw new IllegalArgumentException(
				"Cannot find wildcard indicator for constant: " + signatureConstant + ".");
	}
}
