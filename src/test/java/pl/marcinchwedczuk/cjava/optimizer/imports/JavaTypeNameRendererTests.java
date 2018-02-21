package pl.marcinchwedczuk.cjava.optimizer.imports;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PackageName;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.decompiler.fixture.AstBuilder.string;

public class JavaTypeNameRendererTests {
	// TODO: Add support for nested types

	@Test
	public void canProduceImportsForRawTypes() throws Exception {
		JavaTypeHistogram histogram = new JavaTypeHistogram();

		histogram
				.addUsage(string())
				.addUsage(string())
				.addUsage(string())
				.addUsage(ClassType.of(ArrayList.class))
				.addUsage(ClassType.of(java.util.ArrayList.class))
				.addUsage(ClassType.of(java.util.ArrayList.class));

		PackageName currentPackage = PackageName.fromString("mc.foo");

		// should import only
		// java.util.ArrayList - the most frequent occurrence
		// of ArrayList class name.
		// java.lang.String is imported by default.

		JavaTypeNameRenderer javaTypeNameRenderer =
				new JavaTypeNameRenderer(currentPackage, histogram);

		assertThat(javaTypeNameRenderer.getImports())
				.hasSize(1);

		assertThat(javaTypeNameRenderer.getImports().get(0).getTypeToImport())
				.isEqualTo(ClassType.of(java.util.ArrayList.class));
	}

	public static class ArrayList { }
}