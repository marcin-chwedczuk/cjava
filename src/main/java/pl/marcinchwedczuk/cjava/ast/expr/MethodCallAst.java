package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import javax.annotation.Nullable;
import java.util.List;

@AutoValue
public abstract class MethodCallAst extends ExprAst {
	public static MethodCallAst create(ClassType classContainingMethod,
						 String methodName,
						 MethodSignature methodSignature,
						 ExprAst thisArgument,
						 List<ExprAst> methodArguments) {

		return new AutoValue_MethodCallAst(classContainingMethod,
				methodName, methodSignature,
				thisArgument, ImmutableList.copyOf(methodArguments));
	}

	public abstract ClassType getClassContainingMethod();
	public abstract String getMethodName();
	public abstract MethodSignature getMethodSignature();

	@Nullable
	public abstract ExprAst getThisArgument();
	public abstract ImmutableList<ExprAst> getMethodArguments();
}
