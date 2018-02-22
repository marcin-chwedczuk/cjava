package pl.marcinchwedczuk.cjava.decompiler.typesystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType.MetaType.PRIMITIVE_TYPE;

public enum PrimitiveType implements JavaType {
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

	PrimitiveType(char bytecodeConstant) {
		this.bytecodeConstant = bytecodeConstant;
	}

	@Override
	public String asSourceCodeString() {
		return name().toLowerCase();
	}

	@Override
	public ImmutableList<JavaType> decomposeToRawTypes() {
		return ImmutableList.of(this);
	}

	@Override
	public MetaType getMetaType() {
		return PRIMITIVE_TYPE;
	}

	private boolean representsConstant(char constant) {
		return bytecodeConstant == constant;
	}

	public static PrimitiveType parse(char bytecodeConstant) {
		for (PrimitiveType primitiveType : values()) {
			if (primitiveType.representsConstant(bytecodeConstant)) {
				return primitiveType;
			}
		}

		throw new IllegalArgumentException(
				"Unknown PrimitiveType constant: " + bytecodeConstant + ".");
	}

}
