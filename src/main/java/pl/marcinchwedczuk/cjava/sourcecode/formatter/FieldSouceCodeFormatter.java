package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class FieldSouceCodeFormatter implements SourceCodeFormatter {
	private final FieldDeclarationAst fieldDeclarationAst;
	private final JavaCodeWriter codeWriter;

	public FieldSouceCodeFormatter(FieldDeclarationAst fieldDeclarationAst, JavaCodeWriter codeWriter) {
		this.fieldDeclarationAst = requireNonNull(fieldDeclarationAst);
		this.codeWriter = requireNonNull(codeWriter);
	}

	@Override
	public void convertAstToJavaCode() {
		printAnnotations();
		printVisibility();
		printModifiers();
		printFieldDeclaration();
	}

	private void printAnnotations() {
		for (AnnotationAst annotationAst : fieldDeclarationAst.getAnnotations()) {
			new AnnotationSourceCodeFormatter(annotationAst, codeWriter)
					.convertAstToJavaCode();

			codeWriter
					.printNewLine()
					.printIndent();
		}
	}

	private void printVisibility() {
		Visibility visibility = fieldDeclarationAst.getVisibility();

		switch (visibility) {
			case PUBLIC:
				codeWriter.print("public ");
				break;

			case PROTECTED:
				codeWriter.print("protected ");
				break;

			case PACKAGE:
				/* print nothing */
				break;

			case PRIVATE:
				codeWriter.print("private ");
				break;
		}
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
