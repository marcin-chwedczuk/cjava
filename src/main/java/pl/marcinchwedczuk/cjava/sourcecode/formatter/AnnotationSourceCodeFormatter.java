package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter.writeList;

public class AnnotationSourceCodeFormatter extends BaseSourceCodeFormatter {
	private final AnnotationAst annotationAst;

	public AnnotationSourceCodeFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, AnnotationAst annotationAst) {
		super(typeNameRenderer, codeWriter);
		this.annotationAst = requireNonNull(annotationAst);
	}

	public void convertAstToJavaCode() {
		codeWriter
				.print("@")
				.print(typeName(annotationAst.getAnnotationType()));

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

					new ExpressionSourceCodeFormatter(typeNameRenderer, codeWriter)
							.convertAstToJavaCode(assignmentAst.getPropertyValue());

				})
				.between(() -> codeWriter.print(", "))
				.after(() -> codeWriter.print(")"))
				.write();
	}
}
