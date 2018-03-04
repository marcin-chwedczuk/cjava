package pl.marcinchwedczuk.nomoregotos.condexpr;

public class True extends Term {
	public static final True instance = new True();

	private True() { }

	@Override
	public Term not() {
		return False.instance;
	}

	@Override
	public String toString() {
		return "T";
	}
}
