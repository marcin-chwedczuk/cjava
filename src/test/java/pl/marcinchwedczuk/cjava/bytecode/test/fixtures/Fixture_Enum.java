package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BaseType;

public enum Fixture_Enum {
	FOO(1),
	BAR(2),
	NONE(0);

	private final int value;

	private Fixture_Enum(int value) {
		this.value = value;
	}

	public static boolean isSet(Fixture_Enum value) {
		return value == FOO || value == BAR;
	}
}
