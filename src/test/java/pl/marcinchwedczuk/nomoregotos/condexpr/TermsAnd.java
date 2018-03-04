package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.collect.ImmutableSet;

import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.joining;

public class TermsAnd extends CondExpr {
	public final ImmutableSet<Term> terms;

	public TermsAnd(ImmutableSet<Term> terms) {
		// a&T <=> a
		terms = terms.stream()
				.filter(t -> t != True.instance)
				.collect(toImmutableSet());

		boolean isFalse = false;

		for (Term t : terms) {
			if (terms.contains(t.not())) {
				isFalse = true;
				break;
			}
		}

		isFalse = isFalse || terms.contains(False.instance);

		if (!isFalse) {
			this.terms = terms;
		} else {
			this.terms = ImmutableSet.of(False.instance);
		}
	}

	public boolean isAlwaysFalse() {
		return terms.contains(False.instance);
	}

	@Override
	public TermsOr not() {
		return new TermsOr(
				terms.stream()
					.map(Term::not)
					.collect(toImmutableSet()));
	}

	public TermsAnd and(Term term) {
		return new TermsAnd(
				ImmutableSet.<Term>builder()
					.addAll(terms)
					.add(term)
					.build());
	}

	public TermsAnd and(TermsAnd other) {
		return new TermsAnd(ImmutableSet.<Term>builder()
				.addAll(terms)
				.addAll(other.terms)
				.build());
	}

	@Override
	public String toString() {
		return terms.stream()
				.map(CondExpr::toString)
				.collect(joining(" && "));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TermsAnd termsAnd = (TermsAnd) o;

		return terms.equals(termsAnd.terms);
	}

	@Override
	public int hashCode() {
		return terms.hashCode();
	}
}
