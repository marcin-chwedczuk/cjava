package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Fixture_ClassWithCode {
	public int methodContainingCode(int a, int b) {
		double sum = 0;

		for (int i = a; i < b; i++) {
			sum += Math.sin(i);
		}

		try {
			if (Boolean.TRUE) {
				throw new RuntimeException("foo");
			}
		} catch(Exception e) {
			return -1;
		}

		try {
			return Files.readAllBytes(Paths.get("file-" + sum + ".txt")).length;
		} catch(IOException | RuntimeException e) {
			return -1;
		}
	}
}
