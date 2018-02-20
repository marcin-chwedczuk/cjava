package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewArrayAst extends ExprAst {
	public static NewArrayAst create(JavaType elementType, ExprAst sizeExpr) {
		return new AutoValue_NewArrayAst.Builder()
				.setElementType(elementType)
				.setSizeExpr(sizeExpr)
				.build();
	}

	public abstract JavaType getElementType();
	public abstract ExprAst getSizeExpr();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return ArrayType.create(1, getElementType());
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		ExprAst mappedSizeExpr = getSizeExpr().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setSizeExpr(mappedSizeExpr);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setElementType(JavaType elementType);
		public abstract Builder setSizeExpr(ExprAst sizeExpr);

		public abstract NewArrayAst build();
	}
}
