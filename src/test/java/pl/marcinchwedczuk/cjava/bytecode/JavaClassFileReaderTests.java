package pl.marcinchwedczuk.cjava.bytecode;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClass;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_EmptyClassImplementingTwoInterfaces;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.idx;

public class JavaClassFileReaderTests {
	private JavaClassFileLoader loader;
	private byte[] Fixture_EmptyClass_Bytes;
	private byte[] Fixture_EmptyClassImplementingTwoInterfaces;

	@Before
	public void before() {
		Fixture_EmptyClass_Bytes =
				TestUtils.readClassBytes(Fixture_EmptyClass.class);

		Fixture_EmptyClassImplementingTwoInterfaces =
				TestUtils.readClassBytes(Fixture_EmptyClassImplementingTwoInterfaces.class);

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

		assertThat(constantPool.getUtf8(4).getString())
				.isEqualTo("<init>");

		assertThat(constantPool.getUtf8(5).getString())
				.isEqualTo("()V");

		assertThat(constantPool.getUtf8(12).getString())
				.isEqualTo("Fixture_EmptyClass.java");

		assertThat(constantPool.getNameAndType(13))
				.isEqualTo(new NameAndTypeConstant(idx(4), idx(5)));

		assertThat(constantPool.getUtf8(15).getString())
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

		assertThat(classFile.getInterfaces().getList())
				.isEmpty();
	}

	@Test
	public void canReadInterfacesList() throws IOException {
		JavaClassFile classFile = loader.load(Fixture_EmptyClassImplementingTwoInterfaces);

		assertThat(classFile.getInterfaces().getCount())
				.isEqualTo(2);

		assertThat(classFile.getInterfaces().getList())
				.containsExactly(idx(4), idx(5));
	}
}
