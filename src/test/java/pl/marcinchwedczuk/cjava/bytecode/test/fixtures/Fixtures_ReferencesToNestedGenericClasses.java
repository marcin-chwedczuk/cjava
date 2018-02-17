package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

public class Fixtures_ReferencesToNestedGenericClasses {
	private static class G1<T> {
		public class G1Child<X> {
			public T t;
			public X x;
		}

		public G1Child<String> create() { return new G1Child<>(); }

		public static class G1StaticChild<X> {
			// public T t; - not allowed in static class
			public X x;
		}
	}

	public G1<String>.G1Child<String> foo() {
		G1<String> g1 = new G1<>();

		G1<String>.G1Child<String> foo = g1.create();
		return foo;
	}
}
