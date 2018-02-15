package pl.marcinchwedczuk.cjava.bytecode;

import org.junit.Ignore;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.instruction.Instruction;
import pl.marcinchwedczuk.cjava.bytecode.instruction.InstructionReader;
import pl.marcinchwedczuk.cjava.bytecode.instruction.OpcodeMapper;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithCode;
import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JavaClassFileReader_InstructionsTests extends BaseJavaClassFileReaderTests {

	@Ignore
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
				.isEqualTo(92);

	}
}
