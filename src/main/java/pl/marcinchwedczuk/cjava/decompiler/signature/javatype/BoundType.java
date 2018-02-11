package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public enum BoundType {
	EXTENDS('+'),
	SUPER('-');

	private final char signatureConstant;

	BoundType(char signatureConstant) {
		this.signatureConstant = signatureConstant;
	}

	private boolean representsConstant(char signatureConstant) {
		return this.signatureConstant == signatureConstant;
	}

	public static BoundType parse(char signatureConstant) {
		for (BoundType wildcardIndicator : values()) {
			if (wildcardIndicator.representsConstant(signatureConstant)) {
				return wildcardIndicator;
			}
		}

		throw new IllegalArgumentException(
				"Cannot find wildcard indicator for constant: " + signatureConstant + ".");
	}
}
