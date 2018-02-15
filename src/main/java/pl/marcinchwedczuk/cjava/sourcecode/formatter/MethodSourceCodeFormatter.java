package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;

public class MethodSourceCodeFormatter extends MemberSourceCodeFormatter {
	private final MethodDeclarationAst methodDeclaration;

	public MethodSourceCodeFormatter(MethodDeclarationAst methodDeclaration, JavaCodeWriter codeWriter) {
		super(codeWriter);
		this.methodDeclaration = requireNonNull(methodDeclaration);
	}

	@Override
	public void convertAstToJavaCode() {
		MethodSignature methodSignature = methodDeclaration.getMethodSignature();

		printAnnotations(methodDeclaration.getAnnotations());
		printVisibility(methodDeclaration.getVisibility());
		printModifiers();

		printMethodTypeParameters(methodSignature.getGenericTypeParameters());
		printMethodSignature(methodSignature);

		codeWriter.print("{ }");
	}

	private void printModifiers() {
		if (methodDeclaration.isStatic()) {
			codeWriter.print("static ");
		}

		if (methodDeclaration.isAbstract()) {
			codeWriter.print("abstract ");
		}

		if (methodDeclaration.isFinal()) {
			codeWriter.print("final ");
		}

		if (methodDeclaration.isSynchronized()) {
			codeWriter.print("synchronized ");
		}

		if (methodDeclaration.isNative()) {
			codeWriter.print("native ");
		}

		if (methodDeclaration.isStrictFP()) {
			codeWriter.print("strictfp ");
		}
	}

	private void printMethodTypeParameters(List<TypeParameter> genericTypeParameters) {
		if (genericTypeParameters.isEmpty()) {
			return;
		}

		ListWriter.writeList(genericTypeParameters)
				.before(codeWriter.printAction("<"))
				.element((typeParameter, pos) -> {
					codeWriter.print(typeParameter.toJavaString());
				})
				.between(codeWriter.printAction(", "))
				.after(codeWriter.printAction("> "))
				.write();
	}

	private void printMethodSignature(MethodSignature methodSignature) {
		JavaType returnType = methodSignature.getReturnType();

		codeWriter
				.print(returnType.asSourceCodeString())
				.print(" ")
				.print(methodDeclaration.getMethodName());

		AtomicLong paramCounter = new AtomicLong(1);

		ListWriter.writeList(methodSignature.getParameterTypes())
				.before(codeWriter.printAction("("))
				.element((paramType, pos) -> {
					codeWriter
						.print(paramType.asSourceCodeString())
						.print(" ")
						.print("arg")
						.print(paramCounter.getAndIncrement());
				})
				.between(codeWriter.printAction(", "))
				.after(codeWriter.printAction(")"))
				.write();
	}
}
