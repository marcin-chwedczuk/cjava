package pl.marcinchwedczuk.cjava.ast.expr;

import static pl.marcinchwedczuk.cjava.ast.expr.Associativity.LEFT_TO_RIGHT;
import static pl.marcinchwedczuk.cjava.ast.expr.Associativity.RIGHT_TO_LEFT;

public enum JavaOperator {
	PARENTHESES("()", 15, LEFT_TO_RIGHT),
	ARRAY_SUBSCRIPT("[]", 15, LEFT_TO_RIGHT),
	MEMBER_SELECTION(".", 15, LEFT_TO_RIGHT),

	POST_INCREMENT("++", 14, RIGHT_TO_LEFT),
	POST_DECREMENT("--", 14, RIGHT_TO_LEFT),

	PRE_INCREMENT("++", 13, RIGHT_TO_LEFT),
	PRE_DECREMENT("--", 13, RIGHT_TO_LEFT),
	UNARY_PLUS("+", 13, RIGHT_TO_LEFT),
	UNARY_MINUS("-", 13, RIGHT_TO_LEFT),
	LOGICAL_NEGATION("!", 13, RIGHT_TO_LEFT),
	BITWISE_COMPLEMENT("~", 13, RIGHT_TO_LEFT),
	TYPE_CAST("(<type>)", 13, RIGHT_TO_LEFT),

	MULTIPLICATION("*", 12, LEFT_TO_RIGHT),
	DIVISION("/", 12, LEFT_TO_RIGHT),
	MODULUS("%", 12, LEFT_TO_RIGHT),

	ADDITION("+", 11, LEFT_TO_RIGHT),
	SUBTRACTION("-", 11, LEFT_TO_RIGHT),

	BITWISE_LEFT_SHIFT("<<", 10, LEFT_TO_RIGHT),
	BITWISE_RIGHT_SHIFT(">>", 10, LEFT_TO_RIGHT),
	BITWISE_RIGHT_SHIFT_ZERO_EXTENSION(">>>", 10, LEFT_TO_RIGHT),

	LESS_THAN("<", 9, LEFT_TO_RIGHT),
	LESS_EQUAL("<=", 9, LEFT_TO_RIGHT),
	GREATER_THAN(">", 9, LEFT_TO_RIGHT),
	GREATER_EQUAL(">=", 9, LEFT_TO_RIGHT),
	INSTANCE_OF("intanceof", 9, LEFT_TO_RIGHT),

	EQUAL("==", 8, LEFT_TO_RIGHT),
	NOT_EQUAL("!=", 8, LEFT_TO_RIGHT),

	BITWISE_AND("&", 7, LEFT_TO_RIGHT),
	BIWISE_XOR("^", 6, LEFT_TO_RIGHT),
	BITWISE_OR("|", 5, LEFT_TO_RIGHT),

	LOGICAL_AND("&&", 4, LEFT_TO_RIGHT),
	LOGICAL_OR("||", 3, LEFT_TO_RIGHT),

	TERNARY_CONDITIONAL("?:", 2, RIGHT_TO_LEFT),

	ASSIGNMENT("=", 1, RIGHT_TO_LEFT),
	ADDITION_ASSIGNMENT("+=", 1, RIGHT_TO_LEFT),
	SUBTRACTION_ASSIGNMENT("-=", 1, RIGHT_TO_LEFT),
	MULTIPLICATION_ASSIGNMENT("*=", 1, RIGHT_TO_LEFT),
	DIVISION_ASSIGNMENT("/=", 1, RIGHT_TO_LEFT),
	MODULUS_ASSIGNMENT("%=", 1, RIGHT_TO_LEFT);

	private final String text;
	private final int precedence;
	private final Associativity associativity;

	JavaOperator(String text, int precedence, Associativity associativity) {
		this.text = text;
		this.precedence = precedence;
		this.associativity = associativity;
	}

	public boolean hasLowerPrecedenceThan(JavaOperator other) {
		return this.precedence < other.precedence;
	}
}
