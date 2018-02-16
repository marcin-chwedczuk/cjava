package pl.marcinchwedczuk.cjava.ast.expr;

import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;

import java.util.List;

public class MethodCallAst extends ExprAst {
	private final ClassType classContainingMethod;
	private final String methodName;
	private final MethodSignature methodSignature;
	private final Ast thisArgument;
	private final List<Ast> methodArguments;

	public MethodCallAst(ClassType classContainingMethod,
						 String methodName,
						 MethodSignature methodSignature,
						 Ast thisArgument,
						 List<Ast> methodArguments) {
		this.classContainingMethod = classContainingMethod;
		this.methodName = methodName;
		this.methodSignature = methodSignature;
		this.thisArgument = thisArgument;
		this.methodArguments = methodArguments;
	}
}
