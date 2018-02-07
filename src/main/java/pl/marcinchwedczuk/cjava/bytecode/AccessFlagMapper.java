package pl.marcinchwedczuk.cjava.bytecode;

import java.util.EnumSet;

import static java.util.stream.Collectors.toMap;

public class AccessFlagMapper {

	public EnumSet<AccessFlag> mapBitFieldsAccessFlagsToAccessFlagEnumSet(short byteCodeConstant) {
		EnumSet<AccessFlag> accessFlags =
				EnumSet.noneOf(AccessFlag.class);

		for (AccessFlag accessFlag : AccessFlag.values()) {
			int flag = accessFlag.asByteCodeConstant();
			if ((byteCodeConstant & flag) == flag) {
				accessFlags.add(accessFlag);
			}
		}

		return accessFlags;
	}
}
