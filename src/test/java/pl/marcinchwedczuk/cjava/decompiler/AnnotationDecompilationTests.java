package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationDecompilationTests extends BaseDecompilerTests {
	@Test
	public void canDecompileAnnotationsPutOnTopLevelClass() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompile(Fixture_ClassWithAnnotations.class);

		assertThat(classDeclaration.getAnnotations())
				.hasSize(1);
	}
}
