package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewInstanceAst extends ExprAst {

	public static NewInstanceAst create(JavaType type) {
		return new AutoValue_NewInstanceAst.Builder()
				.setType(type)
				.build();
	}

	public abstract JavaType getType();
	// TODO: Constructor parameters

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

		public abstract NewInstanceAst build();
	}
}
