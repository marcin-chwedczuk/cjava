package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.optimizer.imports.JavaTypeNameRenderer;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class ClassDeclarationFormatter extends BaseSourceCodeFormatter {
	private final ClassDeclarationAst classDeclarationAst;

	public ClassDeclarationFormatter(JavaTypeNameRenderer typeNameRenderer, JavaCodeWriter codeWriter, ClassDeclarationAst classDeclarationAst) {
		super(typeNameRenderer, codeWriter);
		this.classDeclarationAst = requireNonNull(classDeclarationAst);
	}

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
			.print(classDeclarationAst.getClassName().computeSimpleClassName());
	}

	private void printSuperclass() {
		codeWriter
				.printNewLine()
				.increaseIndent(2)
				.printIndent()
				.print("extends ")
				.print(typeName(classDeclarationAst.getSuperClass()))
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
					.print(typeName(interfaceTypes.get(0)))
					.printIf(moreThanOne, ",")
					.decreaseIndent(2);

			if (interfaceTypes.size() > 1) {
				codeWriter.increaseIndent(4);

				for (int i = 1; i < interfaceTypes.size(); i++) {
					boolean isLast = (i == (interfaceTypes.size()-1));

					codeWriter
							.printNewLine()
							.printIndent()
							.print(typeName(interfaceTypes.get(i)))
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
						// TODO: Use typeNameRenderer
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
