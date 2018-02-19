package pl.marcinchwedczuk.cjava.decompiler.fixture;

import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import javax.print.DocFlavor;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.DOUBLE;

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
					NewInstanceAst.create(ClassType.of(Random.class)),
					emptyList());
	}

	public static CastAst castToInt(ExprAst expr) {
		return CastAst.create(integer(), expr);
	}

	public static JavaType string() {
		return ClassType.of(String.class);
	}

	public static JavaType integer() {
		return PrimitiveType.INT;
	}

	public static JavaType stringArray() {
		return ArrayType.create(1, string());
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
}
