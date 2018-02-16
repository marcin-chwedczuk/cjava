package pl.marcinchwedczuk.cjava.decompiler;

public class DecompilationOptions {
	public static DecompilationOptions defaultOptions() {
		return new DecompilationOptions();
	}

	public static DecompilationOptions withoutCode() {
		DecompilationOptions options = new DecompilationOptions();
		options.setCodeDecompilationEnabled(false);
		return options;
	}

	private boolean isCodeDecompilationEnabled = true;

	public boolean isCodeDecompilationEnabled() {
		return isCodeDecompilationEnabled;
	}

	public void setCodeDecompilationEnabled(boolean codeDecompilationEnabled) {
		this.isCodeDecompilationEnabled = codeDecompilationEnabled;
	}
}
