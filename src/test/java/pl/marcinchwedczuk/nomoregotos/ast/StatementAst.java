package pl.marcinchwedczuk.nomoregotos.ast;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public abstract class StatementAst {

	protected static String addIndent(String s) {
		if (s == null) return null;
		return "\t" + Joiner.on("\n\t").join(s.split("\\n"));
	}
}
