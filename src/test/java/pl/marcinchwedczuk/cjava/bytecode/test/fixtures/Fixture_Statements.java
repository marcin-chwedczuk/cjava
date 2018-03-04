package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

public class Fixture_Statements {
	public void ifElseStatement(int a) {
		System.out.println("before");

		if (a > 0) {
			System.out.println("a > 0");
		} else {
			System.out.println("a < 0");
		}

		System.out.println("after");
	}

	public void ternaryOperator(int a) {
		print(2*a, (a<100 ? 100-a : a), a*a);
	}

	public void forLoop(int limit) {
		for (int i = 0; i < limit; i++) {
			print(i, i*i, i*i*i);
		}
	}

	public void forLoopWithNestedIf(int limit) {
		for (int i = 0; i < limit; i++) {
			if ((i % 2) == 0) {
				print(1, i, 0);
			} else {
				print(0, i, 1);
			}
		}
	}

	public void doWhileWithNestedIf(int limit) {
		do {
			print(limit, 0, 0);

			if (limit == 13) {
				print(limit, 1, 1);
				break;
			}

		} while (limit-- > 0);
	}

	public void nestedStatements(int a, int b) {
		outer:
		for (int i = 0; i < a; i++) {
			if (b > 0) {
				while (i > b) {
					b -= 2;
					if (b == 13) break outer;
				}
			} else {
				while (i*i < b*b) {
					b -= 3;
					if (b == -8) continue;
					b++;
					if (b == -6) break;
				}
			}
		}

		print(a, b, b);
	}

	private static void print(int n1, int n2, int n3) {
		System.out.printf("%d %d %d%n", n1, n2, n3);
	}
}
