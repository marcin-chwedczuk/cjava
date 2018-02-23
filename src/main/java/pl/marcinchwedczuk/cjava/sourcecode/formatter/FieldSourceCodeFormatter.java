package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class FieldSourceCodeFormatter extends MemberSourceCodeFormatter {
	private final FieldDeclarationAst fieldDeclarationAst;

	public FieldSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, FieldDeclarationAst fieldDeclarationAst) {
		super(typeNameRenderer, codeWriter);
		this.fieldDeclarationAst = requireNonNull(fieldDeclarationAst);
	}

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
				.print(typeName(fieldDeclarationAst.getFieldType()))
				.print(" ")
				.print(fieldDeclarationAst.getFieldName())
				.print(";");
	}

}
