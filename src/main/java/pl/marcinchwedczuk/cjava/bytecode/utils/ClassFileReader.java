package pl.marcinchwedczuk.cjava.bytecode.utils;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import pl.marcinchwedczuk.cjava.bytecode.InvalidJavaClassFileException;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassFileReader implements AutoCloseable {
	private final DataInputStream inner;

	public ClassFileReader(InputStream classFileInputStream) {
		Preconditions.checkNotNull(classFileInputStream);

		inner = new DataInputStream(classFileInputStream);
	}

	public ClassFileReader(byte[] classFileBytes) {
		this(new ByteArrayInputStream(classFileBytes));
	}

	public byte readByte() throws IOException {
		return inner.readByte();
	}

	public short readShort() throws IOException {
		return inner.readShort();
	}

	public int readUnsignedShort() throws IOException {
		return Short.toUnsignedInt(inner.readShort());
	}

	public int readInt() throws IOException {
		return inner.readInt();
	}

	public float readFloat() throws IOException {
		return inner.readFloat();
	}

	public byte[] readBytes(int count) throws IOException {
		byte[] buffer = new byte[count];

		if (ByteStreams.read(inner, buffer, 0, buffer.length) != count) {
			throw new InvalidJavaClassFileException(
					"Unexpected end of class file.");
		}

		return buffer;
	}

	@Override
	public void close() throws IOException {
		inner.close();
	}
}
