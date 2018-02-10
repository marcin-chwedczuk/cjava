package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.ast.TypeName;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.attribute.SignatureAttribute;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ComplexClassWithoutCode;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_GenericClass;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readClassBytes;

public class BytecodeDecompilerTests {

	private BytecodeDecompiler decompiler;

	private JavaClassFile complexClassWithoutCode;
	private JavaClassFile genericClassWithoutCode;

	@Before
	public void setUp() throws Exception {
		decompiler = new BytecodeDecompiler();

		JavaClassFileLoader loader = new JavaClassFileLoader();

		complexClassWithoutCode =
				loader.load(readClassBytes(Fixture_ComplexClassWithoutCode.class));

		genericClassWithoutCode =
				loader.load(readClassBytes(Fixture_GenericClass.class));

	}

	@Test
	public void canDecompileClassName() throws Exception {
		CompilationUnitAst compilationUnit = decompiler.decompile(complexClassWithoutCode);

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		assertThat(classDeclaration.getClassName().getTypeName())
				.isEqualTo("Fixture_ComplexClassWithoutCode");

		assertThat(classDeclaration.getClassName().getPackageName())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures");
	}

	@Test
	public void canDecompileSuperClass() throws Exception {
		CompilationUnitAst compilationUnit = decompiler.decompile(complexClassWithoutCode);

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		assertThat(classDeclaration.getSuperClassName().getTypeName())
				.isEqualTo("Fixture_EmptyClass");

		assertThat(classDeclaration.getSuperClassName().getPackageName())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures");
	}

	@Test
	public void canDecompileListOfImplementedInterfaces() throws Exception {
		CompilationUnitAst compilationUnit = decompiler.decompile(complexClassWithoutCode);

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

		List<TypeName> implementedIntefaces =
				classDeclaration.getImplementedInterfaces();

		assertThat(implementedIntefaces.size())
				.isEqualTo(2);

		assertThat(implementedIntefaces.get(0).getTypeName())
				.isEqualTo("Fixture_EmptyInterface");

		assertThat(implementedIntefaces.get(0).getPackageName())
				.isEqualTo("pl.marcinchwedczuk.cjava.bytecode.test.fixtures");

		assertThat(implementedIntefaces.get(1).getTypeName())
				.isEqualTo("Fixture_EmptyInterface2");
	}

	@Test
	public void canDecompileGenericClassTypeParameters() {
		CompilationUnitAst compilationUnit = decompiler.decompile(genericClassWithoutCode);

		ClassDeclarationAst classDeclaration =
				(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);


		SignatureAttribute signatureAttribute = genericClassWithoutCode
				.getAttributes()
				.findSignatureAttribute()
				.get();

		ConstantPoolHelper cp = new ConstantPoolHelper(genericClassWithoutCode.getConstantPool());
		String signature = cp.getString(signatureAttribute.getUtf8SignatureString());

		assertThat(signature).isEqualTo("foo");
	}
}