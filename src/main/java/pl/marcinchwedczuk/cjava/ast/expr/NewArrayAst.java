package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class NewArrayAst extends ExprAst {
	public static NewArrayAst create(JavaType elementType, ExprAst sizeExpr) {
		return new AutoValue_NewArrayAst(elementType, sizeExpr);
	}

	public abstract JavaType getElementType();
	public abstract ExprAst getSizeExpr();

	@Override
	public JavaType getResultType() {
		return ArrayType.create(1, getElementType());
	}
}
