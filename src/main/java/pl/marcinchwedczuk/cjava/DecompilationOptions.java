package pl.marcinchwedczuk.cjava;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class DecompilationOptions {
	public static DecompilationOptions defaultOptions() {
		return builder()
				.decompileCode(true)
				.optimizeImports(true)
				.build();
	}

	public static DecompilationOptions withoutCode()  {
		return builder()
				.decompileCode(false)
				.optimizeImports(true)
				.build();
	}

	public abstract boolean decompileCode();
	public abstract boolean optimizeImports();

	public static Builder builder() {
		return new AutoValue_DecompilationOptions.Builder();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder decompileCode(boolean decompileCode);
		public abstract Builder optimizeImports(boolean optimizeImports);

		public abstract DecompilationOptions build();
	}
}
