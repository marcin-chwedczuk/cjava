package pl.marcinchwedczuk.cjava.bytecode.utils;

public class JavaClassFileUtf8Decoder {
	private static final int FIRST_TWO_BITS_SET = (1 << 7) | (1 << 6);
	private static final int FIRST_THREE_BITS_SET = (1 << 7) | (1 << 6) | (1 << 5);

	public String toString(byte[] bytes) {
		throw new RuntimeException("not impl.");
	}

	public static String decode(byte[] bytes) {
		// see: https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.7

		StringBuilder decoded = new StringBuilder();

		for(int i = 0; i < bytes.length; ) {

			// `& 0xFF` to perform unsigned conversion
			int x = bytes[i] & 0xFF;

			if (x <= 0x007f) {
				decoded.append((char) x);
				i++;
			}
			else if ((x & FIRST_TWO_BITS_SET) == FIRST_TWO_BITS_SET) {
				int y = bytes[i+1] & 0xFF;

				char c = (char) (((x & 0x1F) << 6) + (y & 0x3F));
				decoded.append(c);

				i += 2;
			}
			else if ((x & FIRST_THREE_BITS_SET) == FIRST_THREE_BITS_SET) {
				// TODO: Add tests

				int y = bytes[i+1] & 0xFF;
				int z = bytes[i+2] & 0xFF;

				char c = (char) (((x & 0xF) << 12) + ((y & 0x3F) << 6) + (z & 0x3F));
				decoded.append(c);

				i += 3;
			}
			else {
				throw new RuntimeException(
						"Support for characters with code " +
						"points above U+FFFF is not implemented.");
			}
		}

		return decoded.toString();
	}
}
