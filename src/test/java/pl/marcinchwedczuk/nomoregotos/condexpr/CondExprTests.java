package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: Rewrite this crap, toString relies on implicit order of entries
// inside hashSet.
public class CondExprTests {
	@Test
	public void termsToString() {
		Variable x = new Variable("x");
		NotVariable notX = x.not();
		Variable x2 = notX.not();

		assertThat(x.toString())
				.isEqualTo("x");

		assertThat(notX.toString())
				.isEqualTo("!x");

		assertThat(x2.toString())
				.isEqualTo("x");
	}

	@Test
	public void termsEquals() throws Exception {
		Variable x1 = new Variable("x");
		Variable x2 = new Variable("x");

		NotVariable notX1 = x1.not();
		NotVariable notX2 = x2.not();

		assertThat(x1).isEqualTo(x2);
		assertThat(notX1).isEqualTo(notX2);

		assertThat(x1).isNotEqualTo(notX1);
	}

	@Test
	public void trueAndFalse() throws Exception {
		True t = True.instance;
		False f = False.instance;

		assertThat(t.toString()).isEqualTo("T");
		assertThat(f.toString()).isEqualTo("F");

		assertThat(t).isNotEqualTo(f);
	}

	@Test
	public void andOfTerms() throws Exception {
		Term t = True.instance;
		Term x = new Variable("x");
		Term notY = new Variable("y").not();

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(t, x, notY));

		// t is removed
		assertThat(termsAnd.toString())
				.isEqualTo("x && !y");
	}

	@Test
	public void andOfTermsWithFalseTermIsFalse() throws Exception {
		Term t = False.instance;
		Term x = new Variable("x");
		Term notY = new Variable("y").not();

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(t, x, notY));

		assertThat(termsAnd.toString())
				.isEqualTo("F");
	}

	@Test
	public void andOfTermsWithContradictionIsFalse() throws Exception {
		Term x = new Variable("x");
		Term notX = x.not();
		Term t = True.instance;

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(x, notX, t));

		assertThat(termsAnd.toString())
				.isEqualTo("F");
	}

	@Test
	public void trueValuesAreRemovedFromAndOfTerms() throws Exception {
		Term x = new Variable("x");
		Term t = True.instance;

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(x, t));

		assertThat(termsAnd.toString())
				.isEqualTo("x");
	}

	@Test
	public void negationOfAndTermsWorks() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		Term z = new Variable("z");

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(x, notY, z));

		TermsOr negation = termsAnd.not();

		assertThat(negation.toString())
				.isEqualTo("!x || y || !z");
	}

	@Test
	public void equalsOfAndTermsWorks() throws Exception {
		TermsAnd and1, and2;

		{
			Term x = new Variable("x");
			Term notY = new Variable("y").not();
			Term z = new Variable("z");

			and1 = new TermsAnd(ImmutableSet.of(x, notY, z));
		}

		{
			Term x = new Variable("x");
			Term notY = new Variable("y").not();
			Term z = new Variable("z");

			and2 = new TermsAnd(ImmutableSet.of(x, notY, z));
		}

		assertThat(and1).isEqualTo(and2);

		and2 = and2.and(new Variable("foo"));
		assertThat(and1).isNotEqualTo(and2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void andTermsOfEmptyListThrowsException() throws Exception {
		new TermsAnd(ImmutableSet.of());
	}

	@Test
	public void orTermsToStringWorks() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();

		TermsOr termsOr = new TermsOr(ImmutableSet.of(x, notY));

		assertThat(termsOr.toString())
				.isEqualTo("x || !y");
	}

	@Test
	public void orTermsEliminatesFalseTerm() throws Exception {
		Term x = new Variable("x");
		Term f = False.instance;
		Term notY = new Variable("y").not();

		TermsOr termsOr = new TermsOr(ImmutableSet.of(x, f, notY));

		assertThat(termsOr.toString())
				.isEqualTo("x || !y");
	}

	@Test
	public void orTermsOfFalseValuesIsFalse() throws Exception {
		Term f = False.instance;

		TermsOr termsOr = new TermsOr(ImmutableSet.of(f, f, f));

		assertThat(termsOr.toString())
				.isEqualTo("F");

		assertThat(termsOr)
				.isEqualTo(new TermsOr(ImmutableSet.of(f)));
	}

	@Test
	public void orTermsOfContradictionIsTrue() throws Exception {
		Term x = new Variable("x");
		Term notX = x.not();
		Term z = new Variable("z");

		TermsOr termsOr = new TermsOr(ImmutableSet.of(x, notX, z));

		assertThat(termsOr.toString())
				.isEqualTo("T");

		assertThat(termsOr)
				.isEqualTo(new TermsOr(ImmutableSet.of(True.instance)));
	}

	@Test
	public void orTermsNegationWorks() throws Exception {
		Term x = new Variable("x");
		Term y = new Variable("y").not();
		Term z = new Variable("z");

		TermsOr termsOr = new TermsOr(ImmutableSet.of(x, y, z));

		TermsAnd negation = termsOr.not();

		assertThat(negation.toString())
				.isEqualTo("!x && y && !z");
	}

	@Test(expected = IllegalArgumentException.class)
	public void orTermsOfEmptyListThrowsException() throws Exception {
		new TermsOr(ImmutableSet.of());
	}

	@Test
	public void orToStringWorks() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		Term z = new Variable("z");
		Term w = new Variable("w");

		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd zAndW = new TermsAnd(z, w);

		DnfBooleanExpression or = new DnfBooleanExpression(xAndNotY, zAndW);

		assertThat(or.toString())
				.isEqualTo("(x && !y) || (z && w)");
	}

	@Test
	public void orNotWorks() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		Term z = new Variable("z");
		Term w = new Variable("w");

		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd zAndW = new TermsAnd(z, w);

		DnfBooleanExpression or = new DnfBooleanExpression(xAndNotY, zAndW);

		DnfBooleanExpression negation = or.not();

		assertThat(negation.toString())
				.isEqualTo("(y && !w) || (!x && !z) || (y && !z) || (!x && !w)");
	}

	@Test
	public void orNotWorks2() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		Term z = new Variable("z");

		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd zz = new TermsAnd(z);

		// (x && !y) || z
		DnfBooleanExpression or = new DnfBooleanExpression(xAndNotY, zz);

		// !z && (!x || y)
		DnfBooleanExpression negation = or.not();

		assertThat(negation.toString())
				.isEqualTo("(!x && !z) || (y && !z)");
	}

	@Test
	public void orNotWorks3() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();

		TermsAnd xAndNotY = new TermsAnd(x, notY);

		// (x && !y)
		DnfBooleanExpression or = new DnfBooleanExpression(xAndNotY);

		// !x || y
		DnfBooleanExpression negation = or.not();

		assertThat(negation.toString())
				.isEqualTo("(!x) || (y)");
	}

	@Test
	public void orEliminatesFalseAnds() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();

		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd falseAnd = new TermsAnd(False.instance);

		DnfBooleanExpression or = new DnfBooleanExpression(xAndNotY, falseAnd);

		assertThat(or.toString())
				.isEqualTo("(x && !y)");

		DnfBooleanExpression expected = new DnfBooleanExpression(xAndNotY);
		assertThat(or).isEqualTo(expected);
	}

	@Test
	public void orOringWorks() throws Exception {
		// (x && !y) || z
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd z = new TermsAnd(new Variable("z"));
		DnfBooleanExpression or1 = new DnfBooleanExpression(xAndNotY, z);

		Term y = new Variable("y");
		DnfBooleanExpression or2 = new DnfBooleanExpression(new TermsAnd(y));

		DnfBooleanExpression result = or1.or(or2);
		assertThat(result.toString())
				.isEqualTo("(x && !y) || (y) || (z)");
	}

	@Test
	public void oringEliminatesDuplicates() throws Exception {
		// (x && !y) || z
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		TermsAnd xAndNotY = new TermsAnd(x, notY);
		TermsAnd z = new TermsAnd(new Variable("z"));
		DnfBooleanExpression or1 = new DnfBooleanExpression(xAndNotY, z);

		DnfBooleanExpression result = or1.or(or1);
		assertThat(result).isEqualTo(or1);

		DnfBooleanExpression or2 = new DnfBooleanExpression(xAndNotY);
		result = or1.or(or2);

		assertThat(result.toString())
				.isEqualTo("(x && !y) || (z)");
	}

	@Test
	public void canPerformSimplifications() throws Exception {
		// (x && !y && z) || (x && y && z) || (!x && z)
		// -> (x && z) || (!x && z)
		// -> z
		Term x = new Variable("x");
		Term y = new Variable("y");
		Term z = new Variable("z");

		TermsAnd a1 = new TermsAnd(x, y.not(), z);
		TermsAnd a2 = new TermsAnd(x, y, z);
		TermsAnd a3 = new TermsAnd(x.not(), z);

		DnfBooleanExpression result = new DnfBooleanExpression(a1, a2, a3);

		assertThat(result.toString())
				.isEqualTo("(z)");
	}

	@Test
	public void canPerformSimplifications2() throws Exception {
		// (x && !y && z) || (x && y && z) || z
		// -> (x && z) || z
		// -> z
		Term x = new Variable("x");
		Term y = new Variable("y");
		Term z = new Variable("z");

		TermsAnd a1 = new TermsAnd(x, y.not(), z);
		TermsAnd a2 = new TermsAnd(x, y, z);
		TermsAnd a3 = new TermsAnd(z);

		DnfBooleanExpression result = new DnfBooleanExpression(a1, a2, a3);

		assertThat(result.toString())
				.isEqualTo("(z)");
	}

	@Test
	public void canPerformAndOfTwoBooleanExpressions() throws Exception {
		// (a || b || c) && (d || e)
		Term a = new Variable("a");
		Term b = new Variable("b");
		Term c = new Variable("c");
		Term d = new Variable("d");
		Term e = new Variable("e");

		DnfBooleanExpression abc = new DnfBooleanExpression(
				new TermsAnd(a), new TermsAnd(b), new TermsAnd(c));

		DnfBooleanExpression de = new DnfBooleanExpression(
				new TermsAnd(d), new TermsAnd(e));

		DnfBooleanExpression result = abc.and(de);

		assertThat(result.toString())
				.isEqualTo("(a && d) || (b && d) || (a && e) || (c && d) || (b && e) || (c && e)");
	}
}
