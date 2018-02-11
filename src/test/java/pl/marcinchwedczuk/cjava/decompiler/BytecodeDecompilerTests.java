package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassExtendingNestedClasses;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ComplexClassWithoutCode;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass;
import pl.marcinchwedczuk.cjava.decompiler.signature.TypeParameter;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readClassBytes;

public class BytecodeDecompilerTests {
	private JavaClassFile FIXTURE_COMPLEX_CLASS_WITHOUT_CODE;
	private JavaClassFile FIXTURE_GENERIC_CLASS;
	private JavaClassFile FXITURE_CLASS_EXTENDING_NESTED_CLASSES;

	@Before
	public void setUp() throws Exception {
		JavaClassFileLoader loader = new JavaClassFileLoader();

		FIXTURE_COMPLEX_CLASS_WITHOUT_CODE =
				loader.load(readClassBytes(Fixture_ComplexClassWithoutCode.class));

		FIXTURE_GENERIC_CLASS =
				loader.load(readClassBytes(Fixture_GenericClass.class));

		FXITURE_CLASS_EXTENDING_NESTED_CLASSES =
			loader.load(readClassBytes(Fixture_ClassExtendingNestedClasses.class));
	}

	@Test
	public void canDecompileClassName() throws Exception {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_COMPLEX_CLASS_WITHOUT_CODE);

		assertThat(classDeclaration.getClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ComplexClassWithoutCode");
	}

	@Test
	public void canDecompileSuperClass() throws Exception {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_COMPLEX_CLASS_WITHOUT_CODE);

		assertThat(classDeclaration.getSuperClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClass");
	}

	@Test
	public void canDecompileListOfImplementedInterfaces() throws Exception {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_COMPLEX_CLASS_WITHOUT_CODE);

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
	public void canDecompileNestedSuperClassAndNestedInterfaces() {
		ClassDeclarationAst classDeclaration = decompile(FXITURE_CLASS_EXTENDING_NESTED_CLASSES);

		assertThat(classDeclaration.getSuperClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedClass");

		assertThat(classDeclaration.getImplementedInterfaces().get(0).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithNestedClasses.NestedInterface");
	}

	@Test
	public void canDecompileGenericClassTypeParameters() {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_GENERIC_CLASS);

		assertThat(classDeclaration.getClassName().asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass");

		assertThat(classDeclaration.getTypeParameters())
				.hasSize(2);

		TypeParameter paramA = classDeclaration.getTypeParameters().get(0);

		assertThat(paramA.getName())
				.isEqualTo("ParamA");

		assertThat(paramA.getClassBound())
				.isEmpty();

		assertThat(paramA.getInterfaceBounds().get(0).asSourceCodeString())
				.isEqualTo("java.io.Serializable");

		TypeParameter paramB = classDeclaration.getTypeParameters().get(1);

		assertThat(paramB.getName())
				.isEqualTo("ParamB");

		assertThat(paramB.getClassBound().get().asSourceCodeString())
				.isEqualTo("java.util.ArrayList<ParamA>");

		assertThat(paramB.getInterfaceBounds())
				.isEmpty();

		assertThat(paramA.getInterfaceBounds().get(0).asSourceCodeString())
				.isEqualTo("java.io.Serializable");
	}

	@Test
	public void canDecompileGenericSuperclass() throws Exception {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_GENERIC_CLASS);

		assertThat(classDeclaration.getSuperClassName().asSourceCodeString())
				.isEqualTo("java.util.ArrayDeque<ParamA>");
	}

	@Test
	public void canDecompileImplementedGenericInterface() throws Exception {
		ClassDeclarationAst classDeclaration = decompile(FIXTURE_GENERIC_CLASS);

		assertThat(classDeclaration.getImplementedInterfaces().get(0).asSourceCodeString())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericInterface<ParamA>");
	}

	private static ClassDeclarationAst decompile(JavaClassFile classFile) {
		CompilationUnitAst compilationUnit =
				new BytecodeDecompiler(classFile).decompile();

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		return classDeclaration;
	}
}