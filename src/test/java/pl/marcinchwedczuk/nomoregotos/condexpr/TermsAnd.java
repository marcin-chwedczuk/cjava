package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import pl.marcinchwedczuk.cjava.util.ImmutableUtils;

import java.util.stream.Collectors;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.joining;
import static pl.marcinchwedczuk.cjava.util.ImmutableUtils.remove;
import static pl.marcinchwedczuk.cjava.util.ImmutableUtils.replaceElement;

public class TermsAnd extends CondExpr {
	public final ImmutableSet<Term> terms;

	public TermsAnd(Term... terms) {
		this(ImmutableSet.<Term>builder().add(terms).build());
	}

	public TermsAnd(ImmutableSet<Term> terms) {
		Preconditions.checkArgument(!terms.isEmpty());

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

	/**
	 * For expression (a && b && !c) returns:
	 * (!a && b && !c), (a && !b && !c), (a && b && c)
	 * so the and-terms that can be achieved by using single
	 * negation.
	 */
	public ImmutableSet<TermsAnd> computeSingleNegationClosure() {
		ImmutableSet.Builder<TermsAnd> builder = ImmutableSet.builder();

		for (Term term : terms) {
			ImmutableSet<Term> termsWithSingleNegation =
					replaceElement(terms, term, term.not());

			builder.add(new TermsAnd(termsWithSingleNegation));
		}

		return builder.build();
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

	public TermsAnd simplifyClosure(TermsAnd tWithNegation) {
		Term termToRemove = terms.stream()
				.filter(t -> tWithNegation.terms.contains(t.not()))
				.findFirst()
				.get();

		return new TermsAnd(remove(terms, termToRemove));
	}

	public boolean isMoreSpecific(TermsAnd other) {
		if (other.terms.size() >= this.terms.size()) {
			return false;
		}

		// (x && y && z) is more specific than (x && y)
		return other.terms.stream().allMatch(this.terms::contains);
	}
}
