package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;

import static java.util.Objects.requireNonNull;

public class ClassFormatter implements SourceCodeFormatter {
	private final JavaCodeWriter codeWriter;
	private final ClassDeclarationAst classDeclarationAst;

	public ClassFormatter(JavaCodeWriter codeWriter, ClassDeclarationAst classDeclarationAst) {
		this.codeWriter = requireNonNull(codeWriter);
		this.classDeclarationAst = requireNonNull(classDeclarationAst);
	}

	@Override
	public void convertAstToJavaCode() {
		printClassAnnotations();
		printClassDeclaration();

		codeWriter.print(" {");

		printClassFields();
		printClassMethods();

		codeWriter
				.printNewLine()
				.printIndent()
				.print("}")
				.printNewLine();
	}

	private void printClassAnnotations() {
		for (AnnotationAst annotationAst : classDeclarationAst.getAnnotations()) {
			new AnnotationSourceCodeFormatter(annotationAst, codeWriter)
					.convertAstToJavaCode();

			codeWriter
					.printNewLine()
					.printIndent();
		}
	}

	private void printClassDeclaration() {
		new ClassDeclarationFormatter(codeWriter, classDeclarationAst)
				.convertAstToJavaCode();
	}

	private void printClassFields() {
		codeWriter.increaseIndent(1);

		for (FieldDeclarationAst fieldDeclarationAst : classDeclarationAst.getFields()) {
			codeWriter
					.printNewLine()
					.printNewLine()
					.printIndent();

			new FieldSourceCodeFormatter(fieldDeclarationAst, codeWriter)
					.convertAstToJavaCode();
		}

		codeWriter.decreaseIndent(1);
	}

	private void printClassMethods() {
		codeWriter.increaseIndent(1);

		for (MethodDeclarationAst methodDeclaration : classDeclarationAst.getMethods()) {
			codeWriter
					.printNewLine()
					.printNewLine()
					.printIndent();

			new MethodSourceCodeFormatter(methodDeclaration, codeWriter)
					.convertAstToJavaCode();
		}

		codeWriter.decreaseIndent(1);
	}

}
