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
		int pc = current;
		int machineCode = Byte.toUnsignedInt(machineInstructions[current]);
		current++;

		Opcode opcode = opcodeMapper.toOpcode(machineCode);
		switch (opcode.operands()) {
			case "":
				return new BasicInstruction(pc, opcode);

			case "u1": {
				int operand = readU1();
				return new SingleOperandInstruction(pc, opcode, operand);
			}

			case "s1": {
				int operand = readS1();
				return new SingleOperandInstruction(pc, opcode, operand);
			}

			case "u2": {
				int operand = readU2();
				return new SingleOperandInstruction(pc, opcode, operand);
			}

			case "s2": {
				int operand = readS2();
				return new SingleOperandInstruction(pc, opcode, operand);
			}

			case "u1s1": {
				int first = readU1();
				int second = readS1();
				return new DoubleOperandInstruction(pc, opcode, first, second);
			}

			case "u2u1u1": {
				int first = readU2();
				int second = readU1();
				int third = readU1();
				return new TripleOperandInstruction(pc, opcode, first, second, third);
			}
		}

		throw new IllegalStateException("Opcode still under development: " + opcode);
	}

	private int readU1() {
		int operand = Byte.toUnsignedInt(machineInstructions[current++]);
		return operand;
	}

	private int readS1() {
		return machineInstructions[current++];
	}

	/**
	 * Read unsigned short.
	 */
	private int readU2() {
		int firstByte = Byte.toUnsignedInt(machineInstructions[current++]);
		int secondByte = Byte.toUnsignedInt(machineInstructions[current++]);

		int operand = (firstByte << 8) | secondByte;
		return operand;
	}

	/**
	 * Read singed short.
	 */
	private int readS2() {
		// force conversion to singed number
		int operand = (short)readU2();
		return operand;
	}
}
