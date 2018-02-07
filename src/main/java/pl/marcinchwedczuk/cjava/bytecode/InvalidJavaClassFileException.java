package pl.marcinchwedczuk.cjava.bytecode;

public class InvalidJavaClassFileException extends RuntimeException {
	public InvalidJavaClassFileException() {
	}

	public InvalidJavaClassFileException(String message) {
		super(message);
	}

	public InvalidJavaClassFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidJavaClassFileException(Throwable cause) {
		super(cause);
	}
}
