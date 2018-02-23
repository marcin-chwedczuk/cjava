package pl.marcinchwedczuk.cjava.decompiler.controlflow;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFile;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.bytecode.TestUtils;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute;
import pl.marcinchwedczuk.cjava.bytecode.instruction.Instruction;
import pl.marcinchwedczuk.cjava.bytecode.instruction.InstructionReader;
import pl.marcinchwedczuk.cjava.bytecode.instruction.OpcodeMapper;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_Statements;
import pl.marcinchwedczuk.cjava.decompiler.BaseDecompilerTests;
import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.in;
import static pl.marcinchwedczuk.cjava.bytecode.TestUtils.readClassBytes;

public class FlowGraphBuilderTests extends BaseDecompilerTests {

	@Test
	public void canBuildGraphForSimpleIfElse() throws Exception {
		List<Instruction> instructions = readMethodInstructions("ifElseStatement");

		FlowGraph flowGraph =
				new FlowGraphBuilder(instructions).buildGraph();

		assertThat(flowGraph)
				.isNotNull();

		// cat /home/mc/tmp/flowGraph1.dot | xclip -sel clip
		new FlowGraphToDotConverter(flowGraph, "/home/mc/tmp/flowGraph1.dot")
				.saveToDotFile();

	}

	private static List<Instruction> readMethodInstructions(String methodName) throws IOException {
		JavaClassFile classFile = new JavaClassFileLoader().load(
				readClassBytes(Fixture_Statements.class));

		MethodInfo methodInfo = classFile.getClassMethods().getMethods().stream()
				.filter(m -> classFile
								.getConstantPool()
								.getUtf8(m.getName())
								.asString().equals(methodName))
				.findAny()
				.orElseThrow(() -> new RuntimeException("Cannot find method with name: '" + methodName + "'."));

		CodeAttribute codeAttribute = methodInfo.getAttributes()
				.findCodeAttribute()
				.orElseThrow(() ->
					new RuntimeException("Cannot find CODE attribute for method: '" + methodName + "'."));

		InstructionReader instructionReader = new InstructionReader(
				codeAttribute.getCode().getBytes(),
				new ConstantPoolHelper(classFile.getConstantPool()),
				new OpcodeMapper());

		List<Instruction> instructions = instructionReader.readInstructions();
		return instructions;
	}
}