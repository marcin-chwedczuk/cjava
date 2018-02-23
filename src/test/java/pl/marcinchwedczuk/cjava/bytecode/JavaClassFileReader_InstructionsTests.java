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

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static pl.marcinchwedczuk.cjava.util.ListUtils.firstElement;
import static pl.marcinchwedczuk.cjava.util.ListUtils.lastElement;

public class JavaClassFileReader_InstructionsTests extends BaseJavaClassFileReaderTests {

	@Test
	public void canReadCodeAttribute() throws Exception {
		int methodContainingCodeIndex = 1;

		List<Instruction> instructions =
				readMethodInstructions(Fixture_ClassWithCode.class, methodContainingCodeIndex);

		assertThat(instructions.size())
				.isEqualTo(46);

		Instruction goto_ = instructions.get(14);
		Instruction gotoTarget = instructions.get(4);

		assertInstruction(instructions.get(14),
				23, Opcode.goto_, -18);

		assertThat(gotoTarget.getPC().asInteger() - goto_.getPC().asInteger())
				.as("goto offset")
				.isEqualTo(-18);

		// Check last instruction
		assertInstruction(lastElement(instructions),
			91, Opcode.ireturn);
	}

	@Test
	public void canReadCodeAttribute2_moreOOPCode() throws Exception {
		int methodContainingMoreOOPCodeIndex = 2;

		List<Instruction> instructions =
				readMethodInstructions(Fixture_ClassWithCode.class, methodContainingMoreOOPCodeIndex);

		assertThat(instructions).hasSize(47);

		// Check first instruction
		assertInstruction(firstElement(instructions),
				0, Opcode.new_, 9);

		// Check last instruction
		assertInstruction(lastElement(instructions),
				95, Opcode.areturn);
	}

	private static List<Instruction> readMethodInstructions(Class<?> klass, int methodIndex) throws IOException {
		JavaClassFile classWithCode = load(klass);

		MethodInfo method = classWithCode
				.getClassMethods()
				.get(methodIndex);

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

		return reader.readInstructions();
	}

	private static void assertInstruction(Instruction instruction,
										  int pc, Opcode opcode, int... operands) {
		assertThat(instruction.getPC().asInteger()).isEqualTo(pc);
		assertThat(instruction.getOpcode()).isEqualTo(opcode);

		switch (operands.length) {
			case 0:
				assertThat(instruction).isInstanceOf(BasicInstruction.class);
				break;

			case 1:
				assertThat(instruction).isInstanceOf(SingleOperandInstruction.class);
				assertThat(((SingleOperandInstruction)instruction).getOperand())
						.as("operand1")
						.isEqualTo(operands[0]);
				break;

			case 2:
				assertThat(instruction).isInstanceOf(DoubleOperandInstruction.class);
				assertThat(((DoubleOperandInstruction)instruction).getOperand1())
						.as("operand1")
						.isEqualTo(operands[0]);
				assertThat(((DoubleOperandInstruction)instruction).getOperand2())
						.as("operand2")
						.isEqualTo(operands[1]);
				break;

			default:
				fail("Please add checks for 3 and 4 operand instructions.");
				break;
		}
	}
}
