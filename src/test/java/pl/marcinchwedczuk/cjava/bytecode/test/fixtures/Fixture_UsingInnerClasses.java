package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import org.junit.Test;

public class Fixture_UsingInnerClasses {
	public void createInnerClass() {
		Fixture_ClassWithNestedClasses parent =
				new Fixture_ClassWithNestedClasses();

		Fixture_ClassWithNestedClasses.InnerClass inner =
				parent.new InnerClass();

		System.out.println(inner.toString());
	}

	public void createInnerGenericClass() {
		Fixture_ClassWithNestedClasses parent =
				new Fixture_ClassWithNestedClasses();


		Fixture_ClassWithNestedClasses.InnerGenericClass<String> inner =
				parent.new InnerGenericClass<>();

		System.out.println(inner.toString());
	}

	public void createInnerStaticClass() {
		Fixture_ClassWithNestedClasses.NestedClass inner =
				new Fixture_ClassWithNestedClasses.NestedClass();

		System.out.println(inner);
	}


	public void createInnerStaticGenericClass() {
		Fixture_ClassWithNestedClasses.NestedGenericClass inner =
				new Fixture_ClassWithNestedClasses.NestedGenericClass<String>();

		System.out.println(inner);
	}
}
