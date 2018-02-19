package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.util.Random;

public class Fixture_Expressions {
	public static int integerArithmentic(int a, int b, int c) {
		return (a + b + c) * b + c * (a - b - c) / (a + b);
	}

	public static double doubleArithmeticWithMethodCalls(double a, double b) {
		return Math.cos(b * Math.sin(new Random().nextDouble() + a)) / Math.atan2(a, b);
	}

	public static String[] createArray() {
		return new String[] { "foo", "bar" };
	}

	public static String methodThatUsesLocalVariables(int a, int b) {
		int sum = a+b;
		int mul = a*b;
		int someExpr = (int) (Math.sin(sum) * Math.cos(mul));

		return String.format("%s %s %s", sum, mul, someExpr);
	}
}
