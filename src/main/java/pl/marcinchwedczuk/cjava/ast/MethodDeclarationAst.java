package pl.marcinchwedczuk.cjava.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@AutoValue
public abstract class MethodDeclarationAst {
	public static Builder builder(String methodName, MethodSignature methodSignature) {
		AutoValue_MethodDeclarationAst.Builder builder =
				new AutoValue_MethodDeclarationAst.Builder();

		// Specify default values
		return builder.setMethodName(methodName)
				.setMethodSignature(methodSignature)
				.setVisibility(Visibility.PACKAGE)
				.setMethodBody(StatementBlockAst.empty())

				.setStatic(false)
				.setAbstract(false)
				.setFinal(false)
				.setNative(false)
				.setSynchronized(false)
				.setVarargs(false)
				.setStrictFP(false)

				.setConstructor(false)
				.setAnnotations(ImmutableList.of());
	}

	public abstract Visibility getVisibility();
	public abstract String getMethodName();
	public abstract MethodSignature getMethodSignature();

	/**
	 * Applicable only to instance methods.
	 * @return Type type of {@code this} expression.
	 */
	@Nullable
	public abstract JavaType getThisParameterType();

	public abstract StatementBlockAst getMethodBody();

	public abstract boolean isStatic();
	public abstract boolean isAbstract();
	public abstract boolean isFinal();
	public abstract boolean isNative();
	public abstract boolean isSynchronized();
	public abstract boolean isVarargs();
	public abstract boolean isStrictFP();
	public abstract ImmutableList<AnnotationAst> getAnnotations();

	public abstract boolean isConstructor();

	public MethodDeclarationAst withMethodName(String newMethodName) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(newMethodName));

		return toBuilder()
				.setMethodName(newMethodName)
				.build();
	}

	abstract Builder toBuilder();

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setMethodName(String methodName);
		public abstract Builder setMethodSignature(MethodSignature methodSignature);
		public abstract Builder setMethodBody(StatementBlockAst methodBody);

		public abstract Builder setVisibility(Visibility visibility);
		public abstract Builder setConstructor(boolean constructor);

		public abstract Builder setStatic(boolean isStatic);
		public abstract Builder setFinal(boolean aFinal);
		public abstract Builder setAbstract(boolean anAbstract);
		public abstract Builder setSynchronized(boolean aSynchronized);
		public abstract Builder setVarargs(boolean varargs);
		public abstract Builder setNative(boolean aNative);
		public abstract Builder setStrictFP(boolean strictFP);

		public abstract Builder setAnnotations(List<AnnotationAst> annotations);
		public abstract Builder setAnnotations(AnnotationAst... annotations);

		public abstract Builder setThisParameterType(JavaType thisType);

		public abstract MethodDeclarationAst build();
	}
}
