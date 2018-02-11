package pl.marcinchwedczuk.cjava.sourcecode.formatter;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;

import java.io.IOException;

import static org.junit.Assert.*;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readExpectedDecompiledSourceCode;

public class ClassDeclarationFormatterTests {

	@Test
	public void printsPrettyClassDeclaration() throws Exception {
		String decompiled = decompile(Fixture_GenericClass.class);
		String expected = readExpectedDecompiledSourceCode(Fixture_GenericClass.class);

		assertEquals(expected, decompiled);
	}

	private static String decompile(Class<?> klass) throws IOException {
		byte[] klassBytes = TestUtils.readClassBytes(klass);

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(new JavaClassFileLoader().load(klassBytes))
						.decompile();

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		ClassDeclarationFormatter formatter =
				new ClassDeclarationFormatter(new JavaCodeWriter(), classDeclaration);
		return formatter.convertToSourceCode();
	}
}