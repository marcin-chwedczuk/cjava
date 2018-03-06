package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.base.Preconditions;
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
		Preconditions.checkArgument(!operands.isEmpty());

		// F|x <=> x
		operands = operands.stream()
				.filter(t -> t != False.instance)
				.collect(toImmutableSet());

		if (operands.isEmpty()) {
			operands = ImmutableSet.of(False.instance);
		}

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TermsOr termsOr = (TermsOr) o;

		return operands.equals(termsOr.operands);
	}

	@Override
	public int hashCode() {
		return operands.hashCode();
	}

	@Override
	public String toString() {
		return operands.stream()
				.map(CondExpr::toString)
				.collect(joining(" || "));
	}
}
