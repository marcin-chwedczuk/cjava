package pl.marcinchwedczuk.cjava.commandline;

import pl.marcinchwedczuk.cjava.CJava;
import pl.marcinchwedczuk.cjava.DecompilationOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static pl.marcinchwedczuk.cjava.DecompilationOptions.defaultOptions;

public class Main {

	public static void main(String[] args) throws IOException {
		/*if (args.length != 1) {
			System.out.println("usage: cjava directory/to/decompile");
			return;
		}*/

		// QUICK AND DIRTY TESTING
		String directory =
				System.getProperty("user.home") +
				"/devel/java/cjava/out/test/classes/pl/marcinchwedczuk/cjava/bytecode/test/fixtures/";

		Files.list(Paths.get(directory))
				.filter(Files::isRegularFile)
				.filter(p -> p.getFileName().toString().endsWith(".class"))
				.forEach(Main::tryDecompile);
	}

	private static void tryDecompile(Path classFilePath) {
		System.out.println("decompiling " + classFilePath + "...");

		String sourceCodePath =
				classFilePath.toString().replace(".class", ".decompiled.java");

		try {
			byte[] klassBytes = Files.readAllBytes(classFilePath);

			String sourceCode = CJava.decompile(klassBytes, defaultOptions());

			Files.write(Paths.get(sourceCodePath), sourceCode.getBytes("UTF-8"));
		}
		catch (Exception e) {
			System.err.println("UNHANDLED EXCEPTION WHILE DECOMPILING " + classFilePath.toString());

			try {
				Files.write(Paths.get(sourceCodePath), e.toString().getBytes());
			} catch (Exception ignored) { }
		}
	}

}
