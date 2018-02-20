package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import pl.marcinchwedczuk.cjava.ast.expr.ArrayAccess;

import java.util.ArrayList;
import java.util.List;

public class Fixture_ClassWithReferencesToOtherTypes extends ArrayList<String> {

	private List<Integer> genericList;
	private ArrayList<Integer> ints;
	private ArrayList<Boolean> bools;

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("foo").append("bar");
		System.out.println(sb.toString());
	}

	public static void foo() {
		throw new RuntimeException("foo");
	}

	public static void math() {
		double x = Math.sin(3) + Math.cos(3);
		System.out.println(x);
	}
}
