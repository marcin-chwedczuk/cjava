package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.function.Consumer;

public class JavaCodeWriter {
	private final StringBuilder sourceCode;
	private int indentationLevel;

	public JavaCodeWriter() {
		sourceCode = new StringBuilder();
	}

	public String dumpSourceCode() {
		return sourceCode.toString();
	}

	public JavaCodeWriter print(long num) {
		print(String.valueOf(num));
		return this;
	}

	public JavaCodeWriter print(String text) {
		sourceCode.append(text);
		return this;
	}

	public Runnable printAction(String text) {
		return () -> this.print(text);
	}

	public JavaCodeWriter increaseIndent(int numberOfTabs) {
		indentationLevel += numberOfTabs;
		return this;
	}

	public JavaCodeWriter decreaseIndent(int numberOfTabs) {
		indentationLevel -= numberOfTabs;
		return this;
	}

	public JavaCodeWriter printIndent() {
		for (int i = 0; i < indentationLevel; i++) {
			sourceCode.append('\t');
		}
		return this;
	}

	public JavaCodeWriter printNewLine() {
		sourceCode.append('\n');
		return this;
	}

	public JavaCodeWriter printIf(boolean condition, String text) {
		if (condition) print(text);
		return this;
	}
}
