package pl.marcinchwedczuk.cjava.integrationtest;

import org.junit.Ignore;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.CJava;
import pl.marcinchwedczuk.cjava.DecompilationOptions;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.*;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.optimizer.imports.FullQualifiedNameJavaTypeNameRenderer;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.ClassFormatter;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaCodeWriter;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pl.marcinchwedczuk.cjava.DecompilationOptions.defaultOptions;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readExpectedDecompiledSourceCode;

public class CJavaIntegrationTests {

	@Test
	public void canDecompileGenericClassDeclaration() throws Exception {
		String decompiled = decompileWithoutCode(Fixture_GenericClass.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_GenericClass.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassAnnotations() throws Exception {
		String decompiled = decompileWithoutCode(Fixture_ClassWithAnnotations.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithAnnotations.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassFields() throws Exception {
		String decompiled = decompileWithoutCode(Fixture_ClassWithThreeFields.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithThreeFields.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassMethods() throws Exception {
		String decompiled = decompileWithoutCode(Fixture_ClassWithTwoMethods.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithTwoMethods.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileGenericClassMethods() throws Exception {
		String decompiled = decompileWithoutCode(Fixture_ClassWithGenericMethodSignatures.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithGenericMethodSignatures.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileHelloWorld() throws Exception {
		String decompiled = decompile(Fixture_HelloWorld.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_HelloWorld.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassWithSimpleCode() throws Exception {
		String decompiled = decompile(Fixture_ClassWithSimpleCode.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithSimpleCode.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	@Ignore
	public void justToSee() throws Exception {
		String decompiled = decompile(Fixture_ClassWithReferencesToOtherTypes.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace("");
	}

	private static String decompileWithoutCode(Class<?> klass) throws IOException {
		return decompile(klass, DecompilationOptions.withoutCode());
	}

	private static String decompile(Class<?> klass) throws IOException {
		return decompile(klass, defaultOptions());
	}

	private static String decompile(Class<?> klass, DecompilationOptions options) throws IOException {
		byte[] klassBytes = TestUtils.readClassBytes(klass);
		String sourceCode = CJava.decompile(klassBytes, options);
		return sourceCode;
	}
}