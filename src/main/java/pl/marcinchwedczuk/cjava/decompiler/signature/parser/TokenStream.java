package pl.marcinchwedczuk.cjava.decompiler.signature.parser;

import com.google.common.collect.Sets;
import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableSet;

public class TokenStream {
	private static final Set<Character> IDENTIFIER_DELIMITERS = unmodifiableSet(newHashSet(
			'.', ';', '[', '/', '<', '>', ':'
	));

	private final String input;
	private final Stack<Integer> oldPositions;

	private int currentPosition;

	public TokenStream(String input) {
		this.input = Objects.requireNonNull(input);
		this.currentPosition = 0;
		this.oldPositions = new Stack<>();
	}

	public void savePosition() {
		oldPositions.push(currentPosition);
	}

	public void restoreSavedPosition() {
		currentPosition = oldPositions.pop();
	}

	public void dropSavedPosition() {
		oldPositions.pop();
	}

	public boolean currentIs(char character) {
		return character == current();
	}

	public char current() {
		if (currentPosition < input.length()) {
			return input.charAt(currentPosition);
		}

		return '\0';
	}

	public void match(char character) {
		if (character != current()) {
			throwInvalidSignature("expecting '" + character + "' character, found none");
		}

		currentPosition++;
	}

	private void throwInvalidSignature(String message) {
		throw new InvalidSignatureException(
				"Error parsing signature: " + message + ". " +
				"Position: " + currentPosition + ". " +
				"Signature: '" + input + "'.");
	}

	public String matchIdentifier() {
		StringBuilder identifier = new StringBuilder();

		// Characters `. ; [ / < >` separate identifiers.
		// Remember that constructor has <init> name that is
		// valid identifier.
		char c = current();
		while (!IDENTIFIER_DELIMITERS.contains(c)) {
			identifier.append(c);

			currentPosition++;
			c = current();
		}

		return identifier.toString();
	}

	public boolean currentIsAnyOf(String characters) {
		return characters.contains(Character.toString(current()));
	}

	public void matchCurrent() {
		match(current());
	}
}
