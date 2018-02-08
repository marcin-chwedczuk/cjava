package pl.marcinchwedczuk.cjava.bytecode;

import java.util.EnumSet;

import static java.util.stream.Collectors.toMap;

public class FlagsEnumMapper {

	public <E extends Enum<E> & FlagsEnum<E>> EnumSet<E>
		mapToFlags(short byteCodeConstant, Class<E> enumClass)
	{
		EnumSet<E> setFlags = EnumSet.noneOf(enumClass);

		for (E flag : enumClass.getEnumConstants()) {
			int bitMask = flag.getBitMask();

			if ((byteCodeConstant & bitMask) == bitMask) {
				setFlags.add(flag);
			}
		}

		return setFlags;
	}
}
