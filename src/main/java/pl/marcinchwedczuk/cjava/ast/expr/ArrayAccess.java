package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ArrayAccess extends LValueAst {
	public static ArrayAccess create(ExprAst arrayRef, ExprAst indexExpr) {
		return new AutoValue_ArrayAccess.Builder()
				.setArrayRef(arrayRef)
				.setIndexExpr(indexExpr)
				.build();
	}

	public abstract ExprAst getArrayRef();
	public abstract ExprAst getIndexExpr();

	public abstract Builder toBuilder();

	@Override
	public LValueAst astMap(AstMapper mapper) {
		ExprAst mappedArrayRef = getArrayRef().astMap(mapper);
		ExprAst mappedIndexExpr = getIndexExpr().astMap(mapper);

		Builder mapped = this
				.toBuilder()
				.setArrayRef(mappedArrayRef)
				.setIndexExpr(mappedIndexExpr);

		return mapper.map(this, mapped);
	}

	@Override
	public JavaType getResultType() {
		ArrayType arrayType = (ArrayType) getArrayRef().getResultType();
		return arrayType.getElementType();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setArrayRef(ExprAst expr);
		public abstract Builder setIndexExpr(ExprAst expr);

		public abstract ArrayAccess build();
	}
}
