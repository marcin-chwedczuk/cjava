package pl.marcinchwedczuk.cjava.ast.expr.literal;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.visitor.AstMapper;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

@AutoValue
public abstract class ArrayLiteral extends LiteralAst {
	public static ArrayLiteral ofElements(List<ExprAst> elements) {
		return new AutoValue_ArrayLiteral.Builder()
				.setElements(elements)
				.build();
	}

	public abstract ImmutableList<ExprAst> getElements();

	public abstract Builder toBuilder();

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
		// throw new RuntimeException("Not implemented!");

		// Return dummy type
		return ClassType.of(Object.class);
	}

	@Override
	public ArrayLiteral astMap(AstMapper mapper) {
		List<ExprAst> mappedElements = getElements().stream()
				.map(e -> e.astMap(mapper))
				.collect(toList());

		Builder mapped = this
				.toBuilder()
				.setElements(mappedElements);

		return mapper.map(this, mapped);
	}

	@AutoValue.Builder
	public static abstract class Builder {
		public abstract Builder setElements(List<ExprAst> elements);
		public abstract ArrayLiteral build();
	}
}
