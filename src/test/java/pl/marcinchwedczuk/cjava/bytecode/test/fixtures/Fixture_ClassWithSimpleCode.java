package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Fixture_ClassWithSimpleCode {
	public static String methodThatUsesLocalVariables(int a, int b) {
		int sum = a+b;
		int mul = a*b;
		int someExpr = (int) (Math.sin(sum) * Math.cos(mul));

		return String.format("%s %s %s", sum, mul, someExpr);
	}
}
