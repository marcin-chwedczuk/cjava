package pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs;

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
		for (BoundType boundType : values()) {
			if (boundType.representsConstant(signatureConstant)) {
				return boundType;
			}
		}

		throw new IllegalArgumentException(
				"Cannot find BoundType for constant: " + signatureConstant + ".");
	}
}
