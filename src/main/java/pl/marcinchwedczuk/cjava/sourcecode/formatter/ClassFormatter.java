package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import static java.util.Objects.requireNonNull;

public class ClassFormatter extends BaseSourceCodeFormatter {
	private final ClassDeclarationAst classDeclarationAst;

	public ClassFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, ClassDeclarationAst classDeclarationAst) {
		super(typeNameRenderer, codeWriter);
		this.classDeclarationAst = requireNonNull(classDeclarationAst);
	}

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
			new AnnotationSourceCodeFormatter(typeNameRenderer, codeWriter, annotationAst)
					.convertAstToJavaCode();

			codeWriter
					.printNewLine()
					.printIndent();
		}
	}

	private void printClassDeclaration() {
		new ClassDeclarationFormatter(typeNameRenderer, codeWriter, classDeclarationAst)
				.convertAstToJavaCode();
	}

	private void printClassFields() {
		codeWriter.increaseIndent(1);

		for (FieldDeclarationAst fieldDeclarationAst : classDeclarationAst.getFields()) {
			codeWriter
					.printNewLine()
					.printNewLine()
					.printIndent();

			new FieldSourceCodeFormatter(typeNameRenderer, codeWriter, fieldDeclarationAst)
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

			new MethodSourceCodeFormatter(typeNameRenderer, codeWriter, methodDeclaration)
					.convertAstToJavaCode();
		}

		codeWriter.decreaseIndent(1);
	}

}
