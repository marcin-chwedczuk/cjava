package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class TermsOr extends CondExpr {
	public final ImmutableSet<Term> operands;

	public TermsOr(ImmutableSet<Term> operands) {
		boolean isTrue = false;

		for (Term t : operands) {
			if (operands.contains(t.not())) {
				isTrue = true;
				break;
			}
		}

		if (isTrue) {
			this.operands = ImmutableSet.of(True.instance);
		} else {
			this.operands = operands;
		}
	}

	@Override
	public TermsAnd not() {
		return new TermsAnd(operands.stream()
				.map(Term::not)
				.collect(toImmutableSet()));
	}

	@Override
	public String toString() {
		return "(" + operands.stream()
				.map(CondExpr::toString)
				.collect(joining(") || (")) + ")";
	}
}
