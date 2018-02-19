package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class BinaryOpAst extends ExprAst implements BinaryOp {
	public static BinaryOpAst create(JavaOperator operator, ExprAst left, ExprAst right) {
		return new AutoValue_BinaryOpAst.Builder()
				.setOperator(operator)
				.setLeft(left)
				.setRight(right)
				.build();
	}

	public abstract JavaOperator getOperator();
	public abstract ExprAst getLeft();
	public abstract ExprAst getRight();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		JavaType left = getLeft().getResultType();
		JavaType right = getRight().getResultType();

		Preconditions.checkState(left.equals(right),
				"Types should be equal.");

		return left;
	}

	@Override
	public BinaryOpAst astMap(AstMapper mapper) {
		ExprAst mappedLeft = getLeft().astMap(mapper);
		ExprAst mappedRight = getRight().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setLeft(mappedLeft)
				.setRight(mappedRight);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setOperator(JavaOperator operator);
		public abstract Builder setLeft(ExprAst expr);
		public abstract Builder setRight(ExprAst expr);

		public abstract BinaryOpAst build();
	}
}
