package pl.marcinchwedczuk.cjava.bytecode;

public interface FlagsEnum<E extends Enum<E>> {
	int getBitMask();
}
