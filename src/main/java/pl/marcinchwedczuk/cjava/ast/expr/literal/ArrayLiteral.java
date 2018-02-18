package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;

import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class ArrayLiteral extends LiteralAst {
	public static ArrayLiteral ofElements(List<ExprAst> elements) {
		return new AutoValue_ArrayLiteral(ImmutableList.copyOf(elements));
	}

	public abstract ImmutableList<ExprAst> getElements();

	@Override
	public Object getRawValue() {
		return getElements().stream()
				.map(ExprAst::getRawValue)
				.toArray();
	}

	@Override
	public JavaType getResultType() {
		// TODO: Compute common type or better find out what
		// type is expected
		throw new RuntimeException("Not implemented!");
	}
}
