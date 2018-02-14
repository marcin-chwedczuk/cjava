package pl.marcinchwedczuk.cjava;

import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.CompilationUnitAst;
import pl.marcinchwedczuk.cjava.bytecode.JavaClassFileLoader;
import pl.marcinchwedczuk.cjava.decompiler.BytecodeDecompiler;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.ClassDeclarationFormatter;
import pl.marcinchwedczuk.cjava.sourcecode.formatter.JavaCodeWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

			CompilationUnitAst compilationUnit =
					new BytecodeDecompiler(new JavaClassFileLoader().load(klassBytes))
							.decompile();

			ClassDeclarationAst classDeclaration =
					(ClassDeclarationAst) compilationUnit.getDeclaredTypes().get(0);

			JavaCodeWriter codeWriter = new JavaCodeWriter();
			ClassDeclarationFormatter formatter =
					new ClassDeclarationFormatter(codeWriter, classDeclaration);

			String sourceCode = codeWriter.dumpSourceCode();

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
