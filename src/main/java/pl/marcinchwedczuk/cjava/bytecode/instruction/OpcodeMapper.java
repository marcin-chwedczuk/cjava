package pl.marcinchwedczuk.cjava.bytecode.instruction;

import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;

public class OpcodeMapper {
	private static final int MAX_UNSIGNED_BYTE = 255;

	private final Opcode[] mapping;

	public OpcodeMapper() {
		this.mapping = createMapping();
	}

	public Opcode toOpcode(int machineCode) {
		Opcode opcode = mapping[machineCode];
		if (opcode == null) {
			throw new InvalidJavaClassFileException(
					"Unknown machine instruction: 0x" +
					Integer.toHexString(machineCode) + ".");
		}

		return opcode;
	}

	private static Opcode[] createMapping() {
		Opcode[] mapping = new Opcode[MAX_UNSIGNED_BYTE];

		for (Opcode opcode : Opcode.values()) {
			mapping[opcode.asMachineCode()] = opcode;
		}

		return mapping;
	}
}
