package pl.marcinchwedczuk.cjava.bytecode;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.attribute.Attribute;
import pl.marcinchwedczuk.cjava.bytecode.attribute.UnknownAttribute;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.fields.FieldInfo;
import pl.marcinchwedczuk.cjava.bytecode.fields.Fields;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodAccessFlag;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.method.Methods;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithTwoMethods;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClass;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClassImplementingTwoInterfaces;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithThreeFields;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.idx;

public class JavaClassFileReaderTests {
	private JavaClassFileLoader loader;

	private byte[] Fixture_EmptyClass_Bytes;
	private byte[] Fixture_EmptyClassImplementingTwoInterfaces;
	private byte[] Fixture_ClassWithThreeFields;
	private byte[] Fixture_ClassWithTwoMethods;

	@Before
	public void before() {
		Fixture_EmptyClass_Bytes =
				TestUtils.readClassBytes(Fixture_EmptyClass.class);

		Fixture_EmptyClassImplementingTwoInterfaces =
				TestUtils.readClassBytes(Fixture_EmptyClassImplementingTwoInterfaces.class);

		Fixture_ClassWithThreeFields =
				TestUtils.readClassBytes(Fixture_ClassWithThreeFields.class);

		Fixture_ClassWithTwoMethods =
				TestUtils.readClassBytes(Fixture_ClassWithTwoMethods.class);

		loader = new JavaClassFileLoader();
	}

	@Test
	public void canReadHeaderFields() throws Exception {
		JavaClassFile classFile = loader.load(Fixture_EmptyClass_Bytes);

		assertThat(classFile.getMagicNumber())
				.as("magic_number")
				.isEqualTo(0xCA_FE_BA_BE);

		assertThat(classFile.getMinorVersion())
				.as("minor_version")
				.isEqualTo((short) 0);

		assertThat(classFile.getMajorVersion())
				.as("major_version")
				.isEqualTo(JavaClassFileVersions.JAVA_SE_8);

		assertThat(classFile.getConstantPool().getCount())
				.as("constant_pool_count")
				// 15 constants (from #1 to #15) and the reserved constant #0
				.isEqualTo((short) (15 + 1));
	}

	@Test
	public void canReadConstantPool() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_EmptyClass_Bytes);
		ConstantPool constantPool = classFile.getConstantPool();

		assertThat(constantPool.getMethodRef(1))
				.isEqualTo(new MethodRefConstant(idx(3), idx(13)));

		assertThat(constantPool.getClass(2))
				.isEqualTo(new ClassConstant(idx(14)));

		assertThat(constantPool.getClass(3))
				.isEqualTo(new ClassConstant(idx(15)));

		assertThat(constantPool.getUtf8(4).asString())
				.isEqualTo("<init>");

		assertThat(constantPool.getUtf8(5).asString())
				.isEqualTo("()V");

		assertThat(constantPool.getUtf8(12).asString())
				.isEqualTo("Fixture_EmptyClass.java");

		assertThat(constantPool.getNameAndType(13))
				.isEqualTo(new NameAndTypeConstant(idx(4), idx(5)));

		assertThat(constantPool.getUtf8(15).asString())
				.isEqualTo("java/lang/Object");
	}

	@Test
	public void canReadAccessFlags() throws Exception {
		JavaClassFile classFile = loader.load(Fixture_EmptyClass_Bytes);

		assertThat(classFile.getAccessFlags())
				.containsExactly(AccessFlag.ACC_PUBLIC, AccessFlag.ACC_SUPER);
	}

	@Test
	public void canReadThisAndSuperClass() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_EmptyClass_Bytes);

		assertThat(classFile.getThisClass())
				.as("this_class")
				.isEqualTo(idx(2));

		assertThat(classFile.getSuperClass())
				.as("super_class")
				.isEqualTo(idx(3));
	}

	@Test
	public void canReadEmptyInterfacesList() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_EmptyClass_Bytes);

		assertThat(classFile.getInterfaces().getCount())
				.isZero();

		assertThat(classFile.getInterfaces().getClasses())
				.isEmpty();
	}

	@Test
	public void canReadInterfacesList() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_EmptyClassImplementingTwoInterfaces);

		assertThat(classFile.getInterfaces().getCount())
				.isEqualTo(2);

		assertThat(classFile.getInterfaces().getClasses())
				.containsExactly(idx(4), idx(5));
	}

	@Test
	public void canReadFields() throws Exception {
		JavaClassFile classFile = loader.load(Fixture_ClassWithThreeFields);

		Fields fields = classFile.getClassFields();

		assertThat(fields.getCount())
				.as("fields_count")
				.isEqualTo(3);

		// public static String field1;
		FieldInfo field1 = fields.get(0);

		assertThat(field1.getName())
				.isEqualTo(idx(5));

		assertThat(field1.getDescriptor())
				.isEqualTo(idx(6));

		assertThat(field1.getAccessFlags())
				.containsExactly(FieldAccessFlag.ACC_PUBLIC, FieldAccessFlag.ACC_STATIC);

		// private int field2;
		FieldInfo field2 = fields.get(1);

		assertThat(field2.getName())
				.isEqualTo(idx(7));

		assertThat(field2.getDescriptor())
				.isEqualTo(idx(8));

		assertThat(field2.getAccessFlags())
				.containsExactly(FieldAccessFlag.ACC_PRIVATE);

		// protected final Boolean field3;
		FieldInfo field3 = fields.get(2);

		assertThat(field3.getName())
				.isEqualTo(idx(9));

		assertThat(field3.getDescriptor())
				.isEqualTo(idx(10));

		assertThat(field3.getAccessFlags())
				.containsExactly(FieldAccessFlag.ACC_PROTECTED, FieldAccessFlag.ACC_FINAL);
	}

	@Test
	public void canReadMethods() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_ClassWithTwoMethods);

		Methods methods = classFile.getClassMethods();

		assertThat(methods.getCount())
				// 2 methods + default constructor
				.isEqualTo(2 + 1);

		// default constructor
		MethodInfo init = methods.get(0);

		assertThat(init.getAccessFlags())
				.containsExactly(MethodAccessFlag.ACC_PUBLIC);

		assertThat(init.getName())
				.isEqualTo(idx(4));

		assertThat(init.getDescriptor())
				.isEqualTo(idx(5));

		assertThat(init.getAttributes().getCount())
				.isEqualTo(1);

		// main
		MethodInfo main = methods.get(1);

		assertThat(main.getAccessFlags())
				.containsExactly(MethodAccessFlag.ACC_PUBLIC, MethodAccessFlag.ACC_STATIC);

		assertThat(main.getName())
				.isEqualTo(idx(11));

		assertThat(main.getDescriptor())
				.isEqualTo(idx(12));
	}

	@Test
	public void canReadAttributes() throws Exception {
		JavaClassFile classFile = loader.load(Fixture_ClassWithTwoMethods);

		assertThat(classFile.getAttributes())
				.isNotNull();

		// Class file contains source file attribute
		assertThat(classFile.getAttributes().getCount())
				.isEqualTo(1);

		UnknownAttribute sourceFile = (UnknownAttribute) classFile.getAttributes().get(0);

		assertThat(sourceFile.getName())
				.isEqualTo(idx(17));

		assertThat(sourceFile.getData())
				.containsExactly(0, 18); // index to constant pool
	}
}
