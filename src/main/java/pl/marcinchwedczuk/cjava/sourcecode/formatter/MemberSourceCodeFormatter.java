package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class MemberSourceCodeFormatter implements SourceCodeFormatter {

	protected final JavaCodeWriter codeWriter;

	protected MemberSourceCodeFormatter(JavaCodeWriter codeWriter) {
		this.codeWriter = requireNonNull(codeWriter);
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
			new AnnotationSourceCodeFormatter(annotationAst, codeWriter)
					.convertAstToJavaCode();

			codeWriter
					.printNewLine()
					.printIndent();
		}
	}
}
