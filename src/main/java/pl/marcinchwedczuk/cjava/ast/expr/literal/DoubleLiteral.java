package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

@AutoValue
public abstract class DoubleLiteral extends LiteralAst {
	public static DoubleLiteral of(double value) {
		return new AutoValue_DoubleLiteral(value);
	}

	public abstract double getValue();

	@Override
	public Object getRawValue() {
		return getValue();
	}

	@Override
	public JavaType getResultType() {
		return PrimitiveType.DOUBLE;
	}

	@Override
	public DoubleLiteral astMap(AstMapper mapper) {
		return mapper.map(this);
	}
}
