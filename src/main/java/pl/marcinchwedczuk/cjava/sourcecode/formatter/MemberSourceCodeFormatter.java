package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.List;

import static java.util.Objects.requireNonNull;

public abstract class MemberSourceCodeFormatter extends BaseSourceCodeFormatter {

	public MemberSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter) {
		super(typeNameRenderer, codeWriter);
	}

	protected void printVisibility(Visibility visibility) {
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

	protected void printAnnotations(List<AnnotationAst> annotations) {
		for (AnnotationAst annotationAst : annotations) {
			new AnnotationSourceCodeFormatter(typeNameRenderer, codeWriter, annotationAst)
					.convertAstToJavaCode();

			codeWriter
					.printNewLine()
					.printIndent();
		}
	}
}
