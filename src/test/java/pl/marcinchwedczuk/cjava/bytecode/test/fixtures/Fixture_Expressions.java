package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

public class Fixture_Expressions {
	public static int integerArithmentic(int a, int b, int c) {
		return (a + b + c) * b + c * (a - b - c) / (a + b);
	}
}
