package pl.marcinchwedczuk.cjava.bytecode.instruction;

import pl.marcinchwedczuk.cjava.decompiler.ConstantPoolHelper;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class InstructionReader {
	private final OpcodeMapper opcodeMapper;

	private final byte[] machineInstructions;
	private final ConstantPoolHelper cp;

	private int current;

	public InstructionReader(byte[] machineInstructions, ConstantPoolHelper cp, OpcodeMapper opcodeMapper) {
		this.machineInstructions = requireNonNull(machineInstructions);
		this.cp = requireNonNull(cp);
		this.opcodeMapper = requireNonNull(opcodeMapper);

		this.current = 0;
	}

	public List<Instruction> readInstructions() {
		List<Instruction> instructions = new ArrayList<>();

		while (current < machineInstructions.length) {
			Instruction instruction = readInstruction();
			instructions.add(instruction);
		}

		return instructions;
	}

	private Instruction readInstruction() {
		int machineCode = Byte.toUnsignedInt(machineInstructions[current]);
		current++;

		Opcode opcode = opcodeMapper.toOpcode(machineCode);
		switch (opcode.operands()) {
			case "":
				// no operands to read
				return new BasicInstruction(opcode);
		}

		throw new IllegalStateException("Opcode still under development: " + opcode);
	}
}
