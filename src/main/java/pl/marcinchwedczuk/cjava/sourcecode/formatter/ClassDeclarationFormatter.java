package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClassDeclarationFormatter implements SourceCodeFormatter {
	private final JavaCodeWriter codeWriter;
	private final ClassDeclarationAst classDeclarationAst;

	public ClassDeclarationFormatter(
			JavaCodeWriter codeWriter, ClassDeclarationAst classDeclarationAst) {
		this.codeWriter = requireNonNull(codeWriter);
		this.classDeclarationAst = requireNonNull(classDeclarationAst);
	}

	@Override
	public void convertAstToJavaCode() {
		printClassNameWithModifiers();
		printGenericTypeParameters();
		printSuperclass();
		printImplementedInterfaces();
	}

	private void printClassNameWithModifiers() {
		codeWriter
			.printIf(classDeclarationAst.getVisibility() == Visibility.PUBLIC, "public ")
			.printIf(classDeclarationAst.isFinal(), "final ")
			.printIf(classDeclarationAst.isAbstract(), "abstract ")
			.print("class ")
			.print(classDeclarationAst.getClassName().asSourceCodeString());
	}

	private void printSuperclass() {
		codeWriter
				.printNewLine()
				.increaseIndent(2)
				.printIndent()
				.print("extends ")
				.print(classDeclarationAst.getSuperClassName().asSourceCodeString())
				.decreaseIndent(2);
	}

	private void printImplementedInterfaces() {
		if (classDeclarationAst.isImplementingInterfaces()) {
			codeWriter
					.printNewLine()
					.increaseIndent(2)
					.printIndent()
					.print("implements ");

			List<JavaType> interfaceTypes = classDeclarationAst.getImplementedInterfaces();
			boolean moreThanOne = interfaceTypes.size() > 1;

			codeWriter
					.print(interfaceTypes.get(0).asSourceCodeString())
					.printIf(moreThanOne, ",")
					.decreaseIndent(2);

			if (interfaceTypes.size() > 1) {
				codeWriter.increaseIndent(4);

				for (int i = 1; i < interfaceTypes.size(); i++) {
					boolean isLast = (i == (interfaceTypes.size()-1));

					codeWriter
							.printNewLine()
							.printIndent()
							.print(interfaceTypes.get(i).asSourceCodeString())
							.printIf(!isLast, ", ");
				}

				codeWriter.decreaseIndent(4);
			}
		}
	}

	private void printGenericTypeParameters() {
		if (classDeclarationAst.isGenericClassDeclaration()) {

			codeWriter
					.print("<")
					.increaseIndent(3);

			List<TypeParameter> typeParameters = classDeclarationAst.getTypeParameters();
			for (int i = 0; i < typeParameters.size(); i++) {
				boolean isLast = (i == (typeParameters.size()-1));

				codeWriter
						.printNewLine()
						.printIndent()
						.print(typeParameters.get(i).toJavaString())
						.printIf(!isLast, ",");
			}

			codeWriter
					.printNewLine()
					.decreaseIndent(1)
					.printIndent()
					.print(">")
					.decreaseIndent(2);
		}
	}
}
