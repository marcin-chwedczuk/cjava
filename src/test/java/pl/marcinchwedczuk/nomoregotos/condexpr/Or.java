package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Or extends CondExpr {
	public final ImmutableSet<TermsAnd> operands;

	public Or(ImmutableSet<TermsAnd> operands) {
		this.operands = operands;
	}

	@Override
	public Or not() {
		List<TermsOr> ors = operands.stream()
				.map(TermsAnd::not)
				.collect(toList());

		ImmutableSet<TermsAnd> termAnds = andCrossProduct(ors, ors.size() - 1);

		if (termAnds.isEmpty()) {
			TermsAnd falseAnd = new TermsAnd(ImmutableSet.of(False.instance));
			return new Or(ImmutableSet.of(falseAnd));
		}

		return new Or(termAnds);
	}

	private ImmutableSet<TermsAnd> andCrossProduct(List<TermsOr> ors, int to) {
		// andCrossProduct (x|y|z)&(!x|!y|z) -> (x&!x)|(y&!x)|(z&!x)...

		if (to == 0) {
			return ImmutableSet.of();
		}

		Set<TermsAnd> previousCombinations = andCrossProduct(ors, to-1);
		TermsOr alternativeOfTerms = ors.get(to);

		Set<TermsAnd> result = new HashSet<>();

		for (Term term : alternativeOfTerms.operands) {
			for (TermsAnd andExpr : previousCombinations) {
				TermsAnd extendedAndExpr = andExpr.and(term);

				// F|x <=> x
				if (extendedAndExpr.isAlwaysFalse())
					continue;

				result.add(extendedAndExpr);
			}
		}

		return ImmutableSet.copyOf(result);
	}

	@Override
	public String toString() {
		return "(" + operands.stream()
				.map(CondExpr::toString)
				.collect(joining(") || (")) + ")";
	}
}
