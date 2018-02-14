package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.List;

public class Fixture_ClassWithGenericMethodSignatures<T> {
	public <T extends Exception> void throwsSometing(T arg) throws T { }

	public void process(T element) {
		if (element == null)
			throw new RuntimeException("some message");
	}

	public <E> List<E> listOf(E e1, E e2) {
		return Lists.newArrayList(e1, e2);
	}
}
