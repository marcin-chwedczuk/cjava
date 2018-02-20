package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@AutoValue
public abstract class MethodCallAst extends ExprAst {
	public static MethodCallAst create(ClassType classContainingMethod,
						 String methodName,
						 MethodSignature methodSignature,
						 ExprAst thisArgument,
						 List<ExprAst> methodArguments) {

		return new AutoValue_MethodCallAst.Builder()
				.setClassContainingMethod(classContainingMethod)
				.setMethodName(methodName)
				.setMethodSignature(methodSignature)
				.setThisArgument(thisArgument)
				.setMethodArguments(methodArguments)
				.build();
	}

	public abstract ClassType getClassContainingMethod();
	public abstract String getMethodName();
	public abstract MethodSignature getMethodSignature();

	@Nullable
	public abstract ExprAst getThisArgument();
	public abstract ImmutableList<ExprAst> getMethodArguments();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getMethodSignature().getReturnType();
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		ExprAst mappedThis = getThisArgument();
		if (mappedThis != null) {
			mappedThis = mappedThis.astMap(mapper);
		}

		List<ExprAst> mappedArguments = getMethodArguments().stream()
				.map(e -> e.astMap(mapper))
				.collect(toList());

		Builder mapped = this
				.toBuilder()
				.setThisArgument(mappedThis)
				.setMethodArguments(mappedArguments);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setClassContainingMethod(ClassType containingClass);
		public abstract Builder setMethodName(String methodName);
		public abstract Builder setMethodSignature(MethodSignature signature);
		public abstract Builder setThisArgument(ExprAst expr);
		public abstract Builder setMethodArguments(List<ExprAst> arguments);

		public abstract MethodCallAst build();
	}
}
