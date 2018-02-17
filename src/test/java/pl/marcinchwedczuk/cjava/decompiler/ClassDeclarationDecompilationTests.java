package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassExtendingNestedClasses;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ComplexClassWithoutCode;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ClassDeclarationDecompilationTests extends BaseDecompilerTests {
	@Test
	public void canDecompileClassName() throws Exception {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_ComplexClassWithoutCode.class);

		assertThat(classDeclaration.getClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ComplexClassWithoutCode");
	}

	@Test
	public void canDecompileSuperClass() throws Exception {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_ComplexClassWithoutCode.class);

		assertThat(classDeclaration.getSuperClass().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClass");
	}

	@Test
	public void canDecompileListOfImplementedInterfaces() throws Exception {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_ComplexClassWithoutCode.class);

		List<JavaType> implementedInterfaces =
				classDeclaration.getImplementedInterfaces();

		assertThat(implementedInterfaces.size())
				.isEqualTo(2);

		assertThat(implementedInterfaces.get(0).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyInterface");

		assertThat(implementedInterfaces.get(1).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyInterface2");
	}

	@Test
	public void canDecompileNestedSuperClassAndNestedInterfaces() throws IOException {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_ClassExtendingNestedClasses.class);

		assertThat(classDeclaration.getSuperClass().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedClass");

		assertThat(classDeclaration.getImplementedInterfaces().get(0).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedInterface");
	}

	@Test
	public void canDecompileGenericClassTypeParameters() throws IOException {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_GenericClass.class);

		assertThat(classDeclaration.getClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass");

		assertThat(classDeclaration.getTypeParameters())
				.hasSize(2);

		TypeParameter paramA = classDeclaration.getTypeParameters().get(0);

		assertThat(paramA.getName())
				.isEqualTo("ParamA");

		assertThat(paramA.getClassBound())
				.isNull();

		assertThat(paramA.getInterfaceBounds().get(0).asSourceCodeString())
				.isEqualTo("java.io.Serializable");

		TypeParameter paramB = classDeclaration.getTypeParameters().get(1);

		assertThat(paramB.getName())
				.isEqualTo("ParamB");

		assertThat(paramB.getClassBound().asSourceCodeString())
				.isEqualTo("java.util.ArrayList<ParamA>");

		assertThat(paramB.getInterfaceBounds())
				.isEmpty();

		assertThat(paramA.getInterfaceBounds().get(0).asSourceCodeString())
				.isEqualTo("java.io.Serializable");
	}

	@Test
	public void canDecompileGenericSuperclass() throws Exception {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_GenericClass.class);

		assertThat(classDeclaration.getSuperClass().asSourceCodeString())
				.isEqualTo("java.util.ArrayDeque<ParamA>");
	}

	@Test
	public void canDecompileImplementedGenericInterface() throws Exception {
		ClassDeclarationAst classDeclaration = decompileWithoutCode(Fixture_GenericClass.class);

		assertThat(classDeclaration.getImplementedInterfaces().get(0).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericInterface<ParamA>");
	}
}