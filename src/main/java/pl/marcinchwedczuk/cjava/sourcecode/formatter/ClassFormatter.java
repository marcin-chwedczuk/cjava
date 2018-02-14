package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
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

		codeWriter.print(" {")
				.printNewLine()
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
}
