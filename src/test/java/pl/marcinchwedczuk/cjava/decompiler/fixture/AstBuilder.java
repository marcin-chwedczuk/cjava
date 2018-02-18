package pl.marcinchwedczuk.cjava.decompiler.fixture;

import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.DOUBLE;

public class AstBuilder {
	public static BinaryOpAst binOp(BinaryOperator operator, ExprAst left, ExprAst right) {
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
}
