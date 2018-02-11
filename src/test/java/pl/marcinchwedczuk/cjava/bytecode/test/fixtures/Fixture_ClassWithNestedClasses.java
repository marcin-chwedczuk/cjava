package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

public class Fixture_ClassWithNestedClasses {
	public class InnerClass { }

	public class InnerGenericClass<T> {
		public T holder;
	}

	public static class NestedClass { }

	public interface NestedInterface { }
}
