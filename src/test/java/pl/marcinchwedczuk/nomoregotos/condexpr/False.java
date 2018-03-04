package pl.marcinchwedczuk.nomoregotos.condexpr;

public class False extends Term {
	public static final False instance = new False();

	private False() { }


	@Override
	public Term not() {
		return True.instance;
	}

	@Override
	public String toString() {
		return "F";
	}
}
