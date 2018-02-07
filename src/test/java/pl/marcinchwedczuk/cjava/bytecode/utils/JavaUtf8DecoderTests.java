package pl.marcinchwedczuk.cjava.bytecode.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaUtf8DecoderTests {
	@Test
	public void codepoints_in_range_u0001_to_u007F_are_represented_by_single_byte() {
		byte[] bytes = { 0x01, 0x44, 0x66, 0x7f };

		String string = JavaClassFileUtf8Decoder.decode(bytes);

		assertThat(string)
				.isEqualTo("\u0001\u0044\u0066\u007f");
	}

	@Test
	public void zero_byte_is_endcoded_using_two_bytes() {
		byte[] bytes = { 0x55, (byte)0xC0, (byte)0x80, 0x77 };

		String string = JavaClassFileUtf8Decoder.decode(bytes);

		assertThat(string)
				.isEqualTo("\u0055\u0000\u0077");
	}

	@Test
	public void codepoints_in_range_u0080_to_u07FF_are_represented_by_two_bytes() {
		// bin 10000 001001 == dec 1033 == hex409

		byte[] bytes = { 0x41, (byte)208, (byte)137,  0x43 };

		String string = JavaClassFileUtf8Decoder.decode(bytes);

		assertThat(string)
				.isEqualTo("\u0041\u0409\u0043");
	}

}
