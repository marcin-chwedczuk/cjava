package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.ImmutableSet.toImmutableSet;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DnfBooleanExpression extends CondExpr {
	public final ImmutableSet<TermsAnd> operands;

	public DnfBooleanExpression(TermsAnd... operands) {
		this(ImmutableSet.<TermsAnd>builder().add(operands).build());
	}

	public DnfBooleanExpression(ImmutableSet<TermsAnd> operands) {
		Preconditions.checkArgument(!operands.isEmpty());

		// F|x <=> x
		operands = operands.stream()
				.filter(t -> !t.isAlwaysFalse())
				.collect(toImmutableSet());

		if (operands.isEmpty()) {
			operands = ImmutableSet.of(
					new TermsAnd(ImmutableSet.of(False.instance)));
		}

		// (x && !y && z) || (x && y && z) -> (x && y)
		operands = simplifyExpression(operands);

		this.operands = operands;
	}

	private ImmutableSet<TermsAnd> simplifyExpression(ImmutableSet<TermsAnd> operands) {
		Set<TermsAnd> simplified = Sets.newHashSet(operands);

		boolean changed = true;

		while (changed) {
			changed = false;

			// copy to prevent concurrent modification exception
			for (TermsAnd t : Sets.newHashSet(simplified)) {
				if (!simplified.contains(t)) { continue; }

				for (TermsAnd tWithNegation : t.computeSingleNegationClosure()) {
					if (simplified.contains(tWithNegation)) {
						changed = true;

						TermsAnd merged = t.simplifyClosure(tWithNegation);
						simplified.remove(t);
						simplified.remove(tWithNegation);
						simplified.add(merged);
					}
				}
			}
		}

		for (TermsAnd t : Sets.newHashSet(simplified)) {
			simplified.removeIf(tt -> tt.isMoreSpecific(t));
		}

		return ImmutableSet.copyOf(simplified);
	}

	@Override
	public DnfBooleanExpression not() {
		List<TermsOr> ors = operands.stream()
				.map(TermsAnd::not)
				.collect(toList());

		ImmutableSet<TermsAnd> termAnds = andCrossProduct(ors, ors.size() - 1);

		if (termAnds.isEmpty()) {
			TermsAnd falseAnd = new TermsAnd(ImmutableSet.of(False.instance));
			return new DnfBooleanExpression(ImmutableSet.of(falseAnd));
		}

		return new DnfBooleanExpression(termAnds);
	}

	public DnfBooleanExpression or(DnfBooleanExpression other) {
		return new DnfBooleanExpression(
				ImmutableSet.<TermsAnd>builder()
					.addAll(this.operands)
					.addAll(other.operands)
					.build())
				// Double negation - jiggle terms to introduce simplifications
				.not().not();
	}


	public DnfBooleanExpression and(DnfBooleanExpression other) {
		Set<TermsAnd> conjuctions = new HashSet<>();

		for (TermsAnd otherOperand : other.operands) {
			for (TermsAnd thisOperand : this.operands) {
				TermsAnd conjunctedOperands =
						thisOperand.and(otherOperand);

				if (!conjunctedOperands.isAlwaysFalse()) {
					conjuctions.add(conjunctedOperands);
				}
			}
		}

		if (conjuctions.isEmpty()) {
			conjuctions.add(new TermsAnd(False.instance));
		}

		return new DnfBooleanExpression(ImmutableSet.copyOf(conjuctions))
				// double negation to introduce further simplifications
				.not().not();
	}

	private ImmutableSet<TermsAnd> andCrossProduct(List<TermsOr> ors, int to) {
		// andCrossProduct (x|y|z)&(!x|!y|z) -> (x&!x)|(y&!x)|(z&!x)...

		if (to == 0) {
			TermsOr first = ors.get(0);

			return ImmutableSet.copyOf(first.operands.stream()
					.map(TermsAnd::new)
					.collect(toList()));
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DnfBooleanExpression or = (DnfBooleanExpression) o;

		return operands.equals(or.operands);
	}

	@Override
	public int hashCode() {
		return operands.hashCode();
	}

	@Override
	public String toString() {
		return "(" + operands.stream()
				.map(CondExpr::toString)
				.collect(joining(") || (")) + ")";
	}

}
