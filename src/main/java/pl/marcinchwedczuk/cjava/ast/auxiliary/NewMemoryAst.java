package pl.marcinchwedczuk.cjava.ast.auxiliary;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewMemoryAst extends ExprAst {

	public static NewMemoryAst create(JavaType type) {
		return new AutoValue_NewMemoryAst.Builder()
				.setType(type)
				.build();
	}

	public abstract JavaType getType();

	public abstract Builder toBuilder();

	@Override
	public JavaType getResultType() {
		return getType();
	}

	@Override
	public ExprAst astMap(AstMapper mapper) {
		throw new AssertionError(
				"Auxiliary AST should not show up in decompiled AST structure.");
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setType(JavaType typeToCreate);
		public abstract NewMemoryAst build();
	}
}
