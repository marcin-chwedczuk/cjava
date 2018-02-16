package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithGenericMethodSignatures;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithTwoMethods;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class MethodDecompilerTests extends BaseDecompilerTests {
	@Test
	public void canDecompileMethodSignatures() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompileWithoutCode(Fixture_ClassWithTwoMethods.class);

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

		MethodDeclarationAst constructor = findMethodByName(classDeclaration, "Fixture_ClassWithTwoMethods");
		assertThat(constructor.getMethodSignature().asJavaSoucrceCode())
				.isEqualTo("void()");
		assertThat(constructor.getVisibility())
				.isEqualTo(Visibility.PUBLIC);
	}

	@Test
	public void canDecompileGenericMethodSignature() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompileWithoutCode(Fixture_ClassWithGenericMethodSignatures.class);

		MethodDeclarationAst listOf = findMethodByName(classDeclaration, "listOf");
		MethodSignature listOfSignature = listOf.getMethodSignature();

		assertThat(listOfSignature.asJavaSoucrceCode())
				.isEqualTo("<E extends java.lang.Object> java.util.List<E>(E, E)");

		assertThat(listOfSignature.getGenericTypeParameters())
				.hasSize(1);
		assertThat(listOfSignature.getGenericTypeParameters().get(0).getName())
				.isEqualTo("E");

		assertThat(listOfSignature.getThrowsExceptions())
				.isEmpty();
	}

	@Test
	public void canDecompileMethodWithGenericThrowsSignature() throws Exception {
		ClassDeclarationAst classDeclaration =
				decompileWithoutCode(Fixture_ClassWithGenericMethodSignatures.class);

		MethodDeclarationAst throwsSometing = findMethodByName(classDeclaration, "throwsSometing");
		MethodSignature throwsSometingSignature = throwsSometing.getMethodSignature();

		assertThat(throwsSometingSignature.getThrowsExceptions())
				.hasSize(1);

		assertThat(throwsSometingSignature.getThrowsExceptions().get(0).asSourceCodeString())
				.isEqualTo("T");
	}
}
