package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import javax.annotation.Nullable;
import java.util.List;

@AutoValue
public abstract class NewInstanceAst extends ExprAst {

	public static NewInstanceAst create(JavaType type,
										ClassType classContainingMethod,
										String methodName,
										MethodSignature methodSignature,
										List<ExprAst> methodArguments) {
		return new AutoValue_NewInstanceAst.Builder()
				.setType(type)
				.setClassContainingMethod(classContainingMethod)
				.setMethodName(methodName)
				.setMethodSignature(methodSignature)
				.setMethodArguments(methodArguments)
				.build();
	}

	public abstract JavaType getType();

	public abstract ClassType getClassContainingMethod();
	public abstract String getMethodName();
	public abstract MethodSignature getMethodSignature();
	public abstract ImmutableList<ExprAst> getMethodArguments();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getType();
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		Builder mapped = this
				.toBuilder();

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setType(JavaType typeToCreate);
		public abstract Builder setClassContainingMethod(ClassType containingClass);
		public abstract Builder setMethodName(String methodName);
		public abstract Builder setMethodSignature(MethodSignature signature);
		public abstract Builder setMethodArguments(List<ExprAst> arguments);

		public abstract NewInstanceAst build();
	}
}
