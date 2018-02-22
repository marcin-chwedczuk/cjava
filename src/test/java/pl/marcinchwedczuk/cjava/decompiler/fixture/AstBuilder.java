package pl.marcinchwedczuk.cjava.decompiler.fixture;

import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.DOUBLE;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.VOID;

public class AstBuilder {
	public static BinaryOpAst binOp(JavaOperator operator, ExprAst left, ExprAst right) {
		return BinaryOpAst.create(operator, left, right);
	}

	public static ParameterValueAst intParam(String name) {
		return ParameterValueAst.forParameter(MethodParameter.create(PrimitiveType.INT, name));
	}

	public static ParameterValueAst doubleParam(String name) {
		return ParameterValueAst.forParameter(MethodParameter.create(PrimitiveType.DOUBLE, name));
	}

	public static ParameterValueAst param(JavaType type, String name) {
		return ParameterValueAst.forParameter(MethodParameter.create(type, name));
	}

	public static MethodCallAst mathMethod(String methodName, ExprAst argument) {
		return MethodCallAst.create(
				ClassType.of(Math.class),
				methodName,
				MethodSignature.basic(DOUBLE, DOUBLE),
				null, // this
				singletonList(argument));
	}

	public static MethodCallAst mathMethod(String methodName, ExprAst argument1, ExprAst argument2) {
		return MethodCallAst.create(
				ClassType.of(Math.class),
				methodName,
				MethodSignature.basic(DOUBLE, DOUBLE, DOUBLE),
				null, // this
				asList(argument1, argument2));
	}

	public static MethodCallAst newRandomNextDouble() {
		return MethodCallAst.create(
				ClassType.of(Random.class),
				"nextDouble",
				MethodSignature.basic(DOUBLE),
					NewInstanceAst.create(
							ClassType.of(Random.class),
							ClassType.of(Random.class),
							"<init>",
							MethodSignature.basic(VOID),
							emptyList()),
					emptyList());
	}

	public static CastAst castToInt(ExprAst expr) {
		return CastAst.create(integer(), expr);
	}

	public static ClassType string() {
		return ClassType.of(String.class);
	}

	public static PrimitiveType integer() {
		return PrimitiveType.INT;
	}

	public static ClassType integerWrapper() {
		return ClassType.of(Integer.class);
	}

	public static ArrayType stringArray() {
		return ArrayType.create(1, string());
	}

	public static ClassType rawArrayList() {
		return ClassType.of(java.util.ArrayList.class);
	}

	public static ClassType listInterface() {
		return ClassType.of(java.util.List.class);
	}

	public static ClassType mapInterface() {
		return ClassType.of(java.util.Map.class);
	}

	public static LocalVariable var(JavaType type, String name) {
		return LocalVariable.create(type, name);
	}

	public static LocalVariableValueAst accessVar(LocalVariable var) {
		return LocalVariableValueAst.forVariable(var);
	}

	public static IntegerLiteral integer(int value) {
		return IntegerLiteral.of(value);
	}

	public static StringLiteral string(String s) {
		return StringLiteral.of(s);
	}

	public static ClassType object() {
		return ClassType.of(Object.class);
	}

	public static JavaType arrayListOfString() {
		return arrayListOf(string());
	}

	public static JavaType arrayListOfObjectArrays() {
		return arrayListOf(ArrayType.create(1, object()));
	}

	public static JavaType arrayListOf(JavaType type) {
		SimpleClassType arrayList = SimpleClassType.forGenericClass(
				ArrayList.class.getSimpleName(),
				TypeArgument.forConcreateType(type));

		PackageName packageName = PackageName.ofClass(ArrayList.class);

		return ClassType.create(packageName, arrayList);
	}
}
