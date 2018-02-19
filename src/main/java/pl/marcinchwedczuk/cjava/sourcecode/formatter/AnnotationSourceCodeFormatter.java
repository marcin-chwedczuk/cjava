package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter.writeList;

public class AnnotationSourceCodeFormatter implements SourceCodeFormatter {
	private final AnnotationAst annotationAst;
	private final JavaCodeWriter codeWriter;

	public AnnotationSourceCodeFormatter(AnnotationAst annotationAst, JavaCodeWriter codeWriter) {
		this.annotationAst = requireNonNull(annotationAst);
		this.codeWriter = requireNonNull(codeWriter);
	}

	public void convertAstToJavaCode() {
		codeWriter
				.print("@")
				.print(annotationAst.getAnnotationType().asSourceCodeString());

		if (annotationAst.hasPropertiesAssignments()) {
			writeAssignments();
		}
	}

	private void writeAssignments() {
		writeList(annotationAst.getPropertiesAssignments())
				.before(() -> codeWriter.print("("))
				.element((assignmentAst, position) -> {
					codeWriter
							.print(assignmentAst.getPropertyName())
							.print(" = ");

					new ExpressionSourceCodeFormatter(codeWriter)
							.convertAstToJavaCode(assignmentAst.getPropertyValue());

				})
				.between(() -> codeWriter.print(", "))
				.after(() -> codeWriter.print(")"))
				.write();
	}
}
