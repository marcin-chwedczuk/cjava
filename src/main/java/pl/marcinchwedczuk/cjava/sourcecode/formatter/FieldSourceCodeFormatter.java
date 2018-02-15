package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FieldSourceCodeFormatter extends MemberSourceCodeFormatter {
	private final FieldDeclarationAst fieldDeclarationAst;

	public FieldSourceCodeFormatter(FieldDeclarationAst fieldDeclarationAst, JavaCodeWriter codeWriter) {
		super(codeWriter);
		this.fieldDeclarationAst = requireNonNull(fieldDeclarationAst);
	}

	@Override
	public void convertAstToJavaCode() {
		printAnnotations(fieldDeclarationAst.getAnnotations());
		printVisibility(fieldDeclarationAst.getVisibility());
		printModifiers();
		printFieldDeclaration();
	}

	private void printModifiers() {
		if (fieldDeclarationAst.isStatic()) {
			codeWriter.print("static ");
		}

		if (fieldDeclarationAst.isFinal()) {
			codeWriter.print("final ");
		}

		if (fieldDeclarationAst.isVolatile()) {
			codeWriter.print("volatile ");
		}

		if (fieldDeclarationAst.isTransient()) {
			codeWriter.print("transient ");
		}
	}

	private void printFieldDeclaration() {
		codeWriter
				.print(fieldDeclarationAst.getFieldType().asSourceCodeString())
				.print(" ")
				.print(fieldDeclarationAst.getFieldName())
				.print(";");
	}

}
