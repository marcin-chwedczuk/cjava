package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.*;

import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BaseType.INT;
import static pl.marcinchwedczuk.cjava.decompiler.signature.javatype.BaseType.VOID;

public class MethodSourceCodeFormatterTests {
	@Test
	public void canPrintMethodNameReturnAndParameterTypes() throws Exception {
		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"methodName",
				MethodSignature.basic(VOID, INT, INT));

		String sourceCode = format(methodDeclaration);

		String expected = "void methodName(int arg1, int arg2) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodGenericParameters() throws Exception {
		TypeParameter E = TypeParameter.basic("E");

		ClassType listOfE = new ClassType(
				asList("java", "util"),
				SimpleClassType.forGenericClass(
					"List",
					TypeArgument.forConcreateType(TypeVariable.forTypeParameter("E"))));

		// signature: `<T> E methodName(List<E> arg)`
		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"methodName",
				MethodSignature.builder()
						.genericParameters(E)
						.signature(TypeVariable.forTypeParameter("E"), listOfE)
						.build());

		String sourceCode = format(methodDeclaration);

		String expected = "<E extends java.lang.Object> E methodName(java.util.List<E> arg1) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodAnnotations() throws Exception {
		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"methodName", MethodSignature.basic(VOID));

		methodDeclaration.setAnnotations(asList(
				new AnnotationAst(
						ClassType.fromPackageAndClassName("mc.test", "Annotation"))
		));

		String sourceCode = format(methodDeclaration);

		String expected =
				"@mc.test.Annotation " +
				"void methodName() { }";

		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodModifiers() throws Exception {
		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"m", MethodSignature.basic(VOID));

		methodDeclaration.setVisibility(Visibility.PRIVATE);
		assertThat(format(methodDeclaration))
				.isEqualToIgnoringWhitespace("private void m() { }");
		methodDeclaration.setVisibility(Visibility.PACKAGE);

		methodDeclaration.setStatic(true);
		assertThat(format(methodDeclaration))
				.isEqualToIgnoringWhitespace("static void m() { }");
		methodDeclaration.setStatic(false);

		methodDeclaration.setAbstract(true);
		assertThat(format(methodDeclaration))
				.isEqualToIgnoringWhitespace("abstract void m();");
		methodDeclaration.setAbstract(false);

		methodDeclaration.setFinal(true);
		assertThat(format(methodDeclaration))
				.isEqualToIgnoringWhitespace("final void m() { }");
		methodDeclaration.setFinal(false);

		// Combination of modifiers
		methodDeclaration.setNative(true);
		methodDeclaration.setSynchronized(true);
		methodDeclaration.setStrictFP(true);
		assertThat(format(methodDeclaration))
				.isEqualToIgnoringWhitespace("synchronized native strictfp void m() { }");
	}

	@Test
	public void canPrintVarargMethodSignature() {
		JavaType arrayOfInt = new ArrayType(1, BaseType.INT);

		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"methodName",
				MethodSignature.basic(VOID, arrayOfInt));
		methodDeclaration.setVarargs(true);

		String sourceCode = format(methodDeclaration);

		String expected = "void methodName(int... args) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintConstructor() throws Exception {
		MethodDeclarationAst methodDeclaration = new MethodDeclarationAst(
				"MyClass",
				MethodSignature.basic(VOID));

		methodDeclaration.setConstructor(true);

		String sourceCode = format(methodDeclaration);

		String expected = "MyClass() { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	private String format(MethodDeclarationAst methodDeclaration) {
		JavaCodeWriter codeWriter = new JavaCodeWriter();
		new MethodSourceCodeFormatter(methodDeclaration, codeWriter)
				.convertAstToJavaCode();
		return codeWriter.dumpSourceCode();
	}
}
