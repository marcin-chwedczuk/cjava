package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.*;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.typeargs.TypeArgument;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.TypeVariable;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.INT;
import static pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType.VOID;

public class MethodSourceCodeFormatterTests {
	@Test
	public void canPrintMethodNameReturnAndParameterTypes() throws Exception {
		MethodDeclarationAst methodDeclaration = MethodDeclarationAst
				.builder("methodName", MethodSignature.basic(VOID, INT, INT))
				.build();

		String sourceCode = format(methodDeclaration);

		String expected = "void methodName(int arg1, int arg2) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodGenericParameters() throws Exception {
		TypeParameter E = TypeParameter.basic("E");

		ClassType listOfE = ClassType.create(
				asList("java", "util"),
				SimpleClassType.forGenericClass(
					"List",
					TypeArgument.forConcreateType(TypeVariable.fromTypeParameterName("E"))));

		// signature: `<T> E methodName(List<E> arg)`
		MethodDeclarationAst methodDeclaration = MethodDeclarationAst
				.builder("methodName",
					MethodSignature.builder()
							.typeParameters(E)
							.signature(TypeVariable.fromTypeParameterName("E"), listOfE)
							.build())
				.build();

		String sourceCode = format(methodDeclaration);

		String expected = "<E extends java.lang.Object> E methodName(java.util.List<E> arg1) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodAnnotations() throws Exception {
		MethodDeclarationAst methodDeclaration =MethodDeclarationAst
				.builder("methodName", MethodSignature.basic(VOID))
				.setAnnotations(
					AnnotationAst.create(
						ClassType.fromPackageAndClassName("mc.test", "Annotation")))
				.build();

		String sourceCode = format(methodDeclaration);

		String expected =
				"@mc.test.Annotation " +
				"void methodName() { }";

		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintMethodModifiers() throws Exception {
		MethodDeclarationAst.Builder methodDeclaration = MethodDeclarationAst
				.builder("m", MethodSignature.basic(VOID));

		methodDeclaration.setVisibility(Visibility.PRIVATE);
		assertThat(format(methodDeclaration.build()))
				.isEqualToIgnoringWhitespace("private void m() { }");
		methodDeclaration.setVisibility(Visibility.PACKAGE);

		methodDeclaration.setStatic(true);
		assertThat(format(methodDeclaration.build()))
				.isEqualToIgnoringWhitespace("static void m() { }");
		methodDeclaration.setStatic(false);

		methodDeclaration.setAbstract(true);
		assertThat(format(methodDeclaration.build()))
				.isEqualToIgnoringWhitespace("abstract void m();");
		methodDeclaration.setAbstract(false);

		methodDeclaration.setFinal(true);
		assertThat(format(methodDeclaration.build()))
				.isEqualToIgnoringWhitespace("final void m() { }");
		methodDeclaration.setFinal(false);

		// Combination of modifiers
		methodDeclaration.setNative(true);
		methodDeclaration.setSynchronized(true);
		methodDeclaration.setStrictFP(true);
		assertThat(format(methodDeclaration.build()))
				.isEqualToIgnoringWhitespace("synchronized native strictfp void m() { }");
	}

	@Test
	public void canPrintVarargMethodSignature() {
		JavaType arrayOfInt = ArrayType.create(1, PrimitiveType.INT);

		MethodDeclarationAst methodDeclaration = MethodDeclarationAst
				.builder("methodName", MethodSignature.basic(VOID, arrayOfInt))
				.setVarargs(true)
				.build();

		String sourceCode = format(methodDeclaration);

		String expected = "void methodName(int... args) { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintConstructor() throws Exception {
		MethodDeclarationAst methodDeclaration = MethodDeclarationAst
				.builder("MyClass", MethodSignature.basic(VOID))
				.setConstructor(true)
				.build();

		String sourceCode = format(methodDeclaration);

		String expected = "MyClass() { }";
		assertThat(sourceCode)
				.isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canPrintThrowsDeclaration() throws Exception {
		MethodDeclarationAst methodDeclaration = MethodDeclarationAst
				.builder("throwEx",
					MethodSignature.builder()
						.signature(PrimitiveType.VOID)
						.checkedExceptions(ClassType.fromPackageAndClassName("java.lang", "Exception"))
						.build())
				.build();

		String sourceCode = format(methodDeclaration);

		String expected = "void throwEx() throws java.lang.Exception { }";
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
