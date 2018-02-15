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

			case "u1": {
				// single operand of type unsigned byte
				int operand = Byte.toUnsignedInt(machineInstructions[current++]);
				return new SingleOperandInstruction(opcode, operand);
			}

			case "u2": {
				// single operand of type *singed* short
				int first = Byte.toUnsignedInt(machineInstructions[current++]);
				int second = Byte.toUnsignedInt(machineInstructions[current++]);
				int operand = (first << 8) | second;
				return new SingleOperandInstruction(opcode, operand);
			}

			case "s2": {
				// single operand of type *singed* short
				int first = Byte.toUnsignedInt(machineInstructions[current++]);
				int second = Byte.toUnsignedInt(machineInstructions[current++]);
				int operand = (short)((first << 8) | second);

				return new SingleOperandInstruction(opcode, operand);
			}

			case "u1s1": {
				// two operands with types u1 and s1
				int first = Byte.toUnsignedInt(machineInstructions[current++]);
				int second = machineInstructions[current++];

				return new DoubleOperandInstruction(opcode, first, second);
			}
		}

		throw new IllegalStateException("Opcode still under development: " + opcode);
	}
}
