package pl.marcinchwedczuk.cjava.integrationtest;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithAnnotations;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithThreeFields;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithTwoMethods;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.ClassFormatter;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaCodeWriter;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readExpectedDecompiledSourceCode;

public class CJavaIntegrationTests {

	@Test
	public void canDecompileGenericClassDeclaration() throws Exception {
		String decompiled = decompile(Fixture_GenericClass.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_GenericClass.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassAnnotations() throws Exception {
		String decompiled = decompile(Fixture_ClassWithAnnotations.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithAnnotations.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassFields() throws Exception {
		String decompiled = decompile(Fixture_ClassWithThreeFields.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithThreeFields.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	@Test
	public void canDecompileClassMethods() throws Exception {
		String decompiled = decompile(Fixture_ClassWithTwoMethods.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_ClassWithTwoMethods.class);

		assertThat(decompiled).isEqualToIgnoringWhitespace(expected);
	}

	private static String decompile(Class<?> klass) throws IOException {
		byte[] klassBytes = TestUtils.readClassBytes(klass);

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(new JavaClassFileLoader().load(klassBytes))
						.decompile();

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		JavaCodeWriter codeWriter = new JavaCodeWriter();

		new ClassFormatter(codeWriter, classDeclaration)
			.convertAstToJavaCode();

		return codeWriter.dumpSourceCode();
	}
}