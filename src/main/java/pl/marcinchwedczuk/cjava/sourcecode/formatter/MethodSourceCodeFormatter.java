package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter.ElementPosition.LAST;
import static pl.marcinchwedczuk.cjava.sourcecode.formatter.ListWriter.writeList;

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

		printMethodTypeParameters(methodSignature.getTypeParameters());
		printMethodSignature(methodSignature);

		printThrowsDeclaration(methodSignature);

		printMethodBody();
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

		writeList(genericTypeParameters)
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
		boolean isConstructor = methodDeclaration.isConstructor();

		codeWriter
				.printIf(!isConstructor, returnType.asSourceCodeString())
				.printIf(!isConstructor, " ")
				.print(methodDeclaration.getMethodName());

		AtomicLong paramCounter = new AtomicLong(1);

		writeList(methodSignature.getParametersTypes())
				.before(codeWriter.printAction("("))
				.element((paramType, pos) -> {
					if (pos == LAST && isVarargParameter(paramType)) {
						codeWriter
								.print(computeVarargElementType(paramType).asSourceCodeString())
								.print("... args");
					}
					else {
						codeWriter
								.print(paramType.asSourceCodeString())
								.print(" arg")
								.print(paramCounter.getAndIncrement());
					}
				})
				.between(codeWriter.printAction(", "))
				.after(codeWriter.printAction(")"))
				.write();
	}

	private boolean isVarargParameter(JavaType parameterType) {
		if (!methodDeclaration.isVarargs()) {
			return false;
		}

		if (!(parameterType instanceof ArrayType)) {
			return false;
		}

		return true;
	}

	private JavaType computeVarargElementType(JavaType varargType) {
		// TODO: This should be computed during AST processing
		// TODO: Introduce new AST for vararg argument with all necessary info.

		ArrayType arrayType = (ArrayType) varargType;

		if (arrayType.getDimensions() == 1) {
			return arrayType.getElementType();
		}
		else {
			return ArrayType.create(
					arrayType.getDimensions()-1,
					arrayType.getElementType());
		}
	}

	private void printThrowsDeclaration(MethodSignature methodSignature) {
		if (methodSignature.getCheckedExceptions().isEmpty()) {
			return;
		}

		writeList(methodSignature.getCheckedExceptions())
				.beforeNonEmpty(codeWriter.printAction(" throws "))
				.element((exceptionType, pos) -> {
					codeWriter.print(exceptionType.asSourceCodeString());
				})
				.between(codeWriter.printAction(", "))
				.write();
	}

	private void printMethodBody() {
		if (methodDeclaration.isAbstract() || methodDeclaration.isNative()) {
			codeWriter.print(";");
			return;
		}

		codeWriter
				.print("{")
				.printNewLine()
				.increaseIndent(1);

		for (StatementAst statement : methodDeclaration.getMethodBody().getStatements()) {
			new StatementSourceCodeFormatter(statement, codeWriter)
					.convertAstToJavaCode();

			codeWriter.printNewLine().printIndent();
		}

		codeWriter
				.decreaseIndent(1)
				.printNewLine()
				.printIndent()
				.print("}")
				.printNewLine();
	}

}
