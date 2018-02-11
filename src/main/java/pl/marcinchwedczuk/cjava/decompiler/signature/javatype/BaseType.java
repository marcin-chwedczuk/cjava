package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public enum BaseType implements JavaType {
	// signed byte
	BYTE('B'),

	// Unicode character code point in the Basic Multilingual Plane, encoded with UTF-16
	CHAR('C'),

	// double-precision floating-point value
	DOUBLE('D'),

	// single-precision floating-point value
	FLOAT('F'),

	// integer
	INT('I'),

	// long integer
	LONG('J'),

	// signed short
	SHORT('S'),

	// true or false
	BOOLEAN('Z');

	private final char bytecodeConstant;

	BaseType(char bytecodeConstant) {
		this.bytecodeConstant = bytecodeConstant;
	}

	@Override
	public String asSourceCodeString() {
		return name().toLowerCase();
	}

	private boolean representsConstant(char constant) {
		return bytecodeConstant == constant;
	}

	public static BaseType parse(char bytecodeConstant) {
		for (BaseType baseType : values()) {
			if (baseType.representsConstant(bytecodeConstant)) {
				return baseType;
			}
		}

		throw new IllegalArgumentException(
				"Unknown BaseType constant: " + bytecodeConstant + ".");
	}

}
