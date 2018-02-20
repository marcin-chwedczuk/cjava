package pl.marcinchwedczuk.cjava.optimizer.imports;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithReferencesToOtherTypes;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.decompiler.DecompilationOptions;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.ClassFormatter;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaCodeWriter;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.DecompilationOptions.defaultOptions;

public class JavaTypeCollectorTests {

	@Test
	public void canCreateTypeHistogram() throws Exception {
		CompilationUnitAst ast =
				decompile(Fixture_ClassWithReferencesToOtherTypes.class);

		JavaTypeHistogram histogram = new JavaTypeHistogram();
		ast.astMap(new JavaTypeCollector(histogram));

		assertThat(histogram.getNumberOfUsages(ClassType.of(String.class)))
				.isEqualTo(2);
	}

	private static CompilationUnitAst decompile(Class<?> klass) throws IOException {
		byte[] klassBytes = TestUtils.readClassBytes(klass);

		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(
						new JavaClassFileLoader().load(klassBytes),
						defaultOptions())
							.decompile();

		return compilationUnit;
	}
}