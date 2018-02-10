package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.util.List;

public class Fixture_ComplexClassWithoutCode extends Fixture_EmptyClass
	implements Fixture_EmptyInterface, Fixture_EmptyInterface2 {

	public static Fixture_ComplexClassWithoutCode fromFooAndBar(String foo, int bar) {
		return new Fixture_ComplexClassWithoutCode(foo, bar);
	}

	private String foo;
	private int bar;

	private Fixture_ComplexClassWithoutCode(String foo, int bar) {
		this.foo = foo;
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public int getBar() {
		return bar;
	}

	@Override
	public String toString() {
		return "NOT-IMPORTANT";
	}
}
