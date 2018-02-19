package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class StringLiteral extends LiteralAst {

	public static StringLiteral of(String string) {
		return new AutoValue_StringLiteral(string);
	}

	public abstract String getValue();

	@Override
	public Object getRawValue() {
		return getValue();
	}

	@Override
	public JavaType getResultType() {
		return ClassType.of(String.class);
	}

	@Override
	public StringLiteral astMap(AstMapper mapper) {
		return mapper.map(this);
	}
}
