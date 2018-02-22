package pl.marcinchwedczuk.cjava.optimizer.imports;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithReferencesToOtherTypes;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;

import java.io.IOException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.DecompilationOptions.defaultOptions;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.*;

public class JavaTypeHistogramCollectorTests {

	@Test
	public void canCreateTypeHistogram() throws Exception {
		CompilationUnitAst ast =
				decompile(Fixture_ClassWithReferencesToOtherTypes.class);

		JavaTypeHistogram histogram = new JavaTypeHistogram();
		ast.astMap(new JavaTypeHistogramCollector(histogram));

		assertThat(histogram.getNumberOfUsages(string()))
				.as("number of String's")
				.isEqualTo(3);

		assertThat(histogram.getNumberOfUsages(rawArrayList()))
				.as("number of ArrayList's")
				.isEqualTo(3);

		assertThat(histogram.getNumberOfUsages(listInterface()))
				.as("number of List's")
				.isEqualTo(3);

		assertThat(histogram.getNumberOfUsages(mapInterface()))
				.as("number of Map's")
				.isEqualTo(1);
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