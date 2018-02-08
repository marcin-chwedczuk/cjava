package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import pl.marcinchwedczuk.cjava.bytecode.interfaces.Interfaces;

public class Fixture_ClassWithThreeFields {
	public static String field1;
	private int field2;
	protected final Boolean field3;

	public Fixture_ClassWithThreeFields(Boolean field3) {
		this.field3 = field3;
	}
}
