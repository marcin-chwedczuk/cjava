package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.io.Serializable;
import java.util.List;

public class Fixture_GenericClass<
			ParamA extends Serializable,
			ParamB extends List<ParamA>
		>
		implements Fixture_GenericInterface<ParamA> {

	private ParamA key;
	private ParamB values;

	public ParamA getKey() {
		return key;
	}

	public void setKey(ParamA key) {
		this.key = key;
	}

	public ParamB getValues() {
		return values;
	}

	public void setValues(ParamB values) {
		this.values = values;
	}
}
