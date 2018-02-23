package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

public class Fixture_Statements {
	public void ifElseStatement(int a) {
		System.out.println("before");

		if (a > 0) {
			System.out.println("a > 0");
		} else {
			System.out.println("a < 0");
		}

		System.out.println("after");
	}
}
