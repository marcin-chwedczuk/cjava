package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithTwoMethods;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class MethodDecompilerTests extends BaseDecompilerTests {
	@Test
	public void canDecompileMethodSignatures() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompile(Fixture_ClassWithTwoMethods.class);

		assertThat(classDeclaration.getMethods())
				// +1 for default constructor
				.hasSize(2 + 1);

		MethodDeclarationAst main = findMethodByName(classDeclaration, "main");
		assertThat(main.getMethodSignature().asJavaSoucrceCode())
				.isEqualTo("void(java.lang.String[])");
		assertThat(main.isStatic())
				.isTrue();
		assertThat(main.getVisibility())
				.isEqualTo(Visibility.PUBLIC);

		MethodDeclarationAst foo = findMethodByName(classDeclaration, "foo");
		assertThat(foo.getMethodSignature().asJavaSoucrceCode())
				.isEqualTo("int()");
		assertThat(foo.getVisibility())
				.isEqualTo(Visibility.PUBLIC);

		MethodDeclarationAst constructor = findMethodByName(classDeclaration, "<init>");
		assertThat(constructor.getMethodSignature().asJavaSoucrceCode())
				.isEqualTo("void()");
		assertThat(constructor.getVisibility())
				.isEqualTo(Visibility.PUBLIC);
	}

	private MethodDeclarationAst findMethodByName(ClassDeclarationAst classDeclaration, String methodName) {
		Optional<MethodDeclarationAst> methodDeclarationOpt = classDeclaration
				.getMethods()
				.stream()
				.filter(m -> m.getMethodName().equals(methodName))
				.findFirst();

		if (!methodDeclarationOpt.isPresent()) {
			fail("Cannot find method with name: " + methodName + ".");
		}

		return methodDeclarationOpt.get();
	}
}
