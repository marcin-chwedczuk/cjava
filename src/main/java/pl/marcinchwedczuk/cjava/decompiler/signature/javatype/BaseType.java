package pl.marcinchwedczuk.cjava.decompiler.signature.javatype;

public enum BaseType implements JavaType {
	BYTE('B'),
	CHAR('C'),
	DOUBLE('D'),
	FLOAT('F'),
	INT('I'),
	LONG('J'),
	SHORT('S'),
	BOOLEAN('Z'),
	VOID('V');

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
