package pl.marcinchwedczuk.cjava.ast.expr.literal;

import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;

import java.util.List;

import static pl.marcinchwedczuk.cjava.util.ListUtils.readOnlyCopy;

public class ArrayLiteral extends LiteralAst {
	private final List<ExprAst> elements;

	public ArrayLiteral(List<ExprAst> elements) {
		this.elements = readOnlyCopy(elements);
	}

	public List<ExprAst> getElements() {
		return elements;
	}

	@Override
	public Object getRawValue() {
		return elements.stream()
				.map(ExprAst::getRawValue)
				.toArray();
	}
}
