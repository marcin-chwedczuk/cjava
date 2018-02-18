package pl.marcinchwedczuk.cjava.ast.expr;

import com.google.auto.value.AutoValue;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ArrayType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

@AutoValue
public abstract class ArrayAccess extends LValueAst {

	public static ArrayAccess create(ExprAst arrayRef, ExprAst indexExpr) {
		return new AutoValue_ArrayAccess(arrayRef, indexExpr);
	}

	public abstract ExprAst getArrayRef();
	public abstract ExprAst getIndexExpr();

	@Override
	public JavaType getResultType() {
		ArrayType arrayType = (ArrayType) getArrayRef().getResultType();
		return arrayType.getElementType();
	}
}
