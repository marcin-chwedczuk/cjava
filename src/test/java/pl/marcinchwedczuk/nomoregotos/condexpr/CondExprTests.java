package pl.marcinchwedczuk.nomoregotos.condexpr;

import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.Var;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

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
	public void negationOfAndTermsWorks() throws Exception {
		Term x = new Variable("x");
		Term notY = new Variable("y").not();
		Term z = new Variable("z");

		TermsAnd termsAnd = new TermsAnd(ImmutableSet.of(x, notY, z));

		TermsOr negation = termsAnd.not();

		assertThat(negation.toString())
				.isEqualTo("(!x) || (y) || (!z)");
	}


}
