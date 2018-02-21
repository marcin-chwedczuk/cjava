package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedClass;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedGenericClass;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.InnerGenericClass;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.InnerClass;

import java.util.Map;
import java.util.Map.Entry;


public class Fixture_UsingInnerClasses {
	public void createInnerClass() {
		Fixture_ClassWithNestedClasses parent =
				new Fixture_ClassWithNestedClasses();

		InnerClass inner = parent.new InnerClass();

		System.out.println(inner.toString());
	}

	public void createInnerGenericClass() {
		Fixture_ClassWithNestedClasses parent =
				new Fixture_ClassWithNestedClasses();

		InnerGenericClass<String> inner = parent.new InnerGenericClass<>();

		System.out.println(inner.toString());
	}

	public void createInnerStaticClass() {
		NestedClass inner = new NestedClass();
		System.out.println(inner);
	}


	public void createInnerStaticGenericClass() {
		NestedGenericClass inner = new NestedGenericClass<String>();

		System.out.println(inner);

		Entry<Integer, String> x;
	}
}
