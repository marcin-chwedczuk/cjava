package pl.marcinchwedczuk.cjava.bytecode;

import com.google.common.primitives.UnsignedBytes;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedInts;
import org.junit.Ignore;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.instruction.*;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithCode;
import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JavaClassFileReader_InstructionsTests extends BaseJavaClassFileReaderTests {

	@Test
	public void canReadCodeAttribute() throws Exception {
		JavaClassFile classWithCode = load(Fixture_ClassWithCode.class);

		MethodInfo method = classWithCode
				.getClassMethods()
				.get(1);

		byte[] machineInstructions = method
				.getAttributes()
				.findCodeAttribute()
				.get()
				.getCode()
				.getBytes();

		InstructionReader reader = new InstructionReader(
				machineInstructions,
				new ConstantPoolHelper(classWithCode.getConstantPool()),
				new OpcodeMapper());

		List<Instruction> instructions = reader.readInstructions();

		assertThat(instructions.size())
				.isEqualTo(46);

		// Check that signed offsets are properly read
		SingleOperandInstruction goto5 = (SingleOperandInstruction) instructions.get(14);
		assertThat(goto5.getOperand())
				.isEqualTo(-18);

	}

	@Test
	public void demoHowJavaBinaryOperatorsWork() throws Exception {
		byte b = UnsignedBytes.parseUnsignedByte("11100010", 2);
		byte b2 = UnsignedBytes.parseUnsignedByte("10000111", 2);

		int i = Byte.toUnsignedInt(b);
		int i2 = Byte.toUnsignedInt(b2);
		int result = (i << 8) | i2;

		assertThat(Integer.toString(result, 2))
				.isEqualTo("1110001010000111");
	}

	@Test
	public void demoHowJavaBinaryOperatorsWork2() throws Exception {
		byte b = UnsignedBytes.parseUnsignedByte("11100010", 2);
		byte b2 = UnsignedBytes.parseUnsignedByte("10000111", 2);

		int i = Byte.toUnsignedInt(b);
		int i2 = Byte.toUnsignedInt(b2);
		int result = (i << 8) | i2;

		assertThat(Integer.toString(result, 2))
				.isEqualTo("1110001010000111");

		short s = (short)result;
		assertThat(UnsignedInts.toString(Short.toUnsignedInt(s), 2))
				.isEqualTo("1110001010000111");

		assertThat(s).isLessThan((short)0);
	}
}
