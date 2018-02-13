package pl.marcinchwedczuk.cjava.decompiler;

import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationPropertyAssignmentAst;
import pl.marcinchwedczuk.cjava.ast.expr.ExprAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.ArrayLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.LiteralAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute.ElementValue;
import pl.marcinchwedczuk.cjava.bytecode.attribute.RuntimeVisibleAnnotationsAttribute.ElementValuePair;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.ConstantPool;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

public class AnnotationDecompiler {

	private final ConstantPoolHelper cp;
	private final RuntimeVisibleAnnotationsAttribute annotationsAttribute;

	public AnnotationDecompiler(
			RuntimeVisibleAnnotationsAttribute annotationsAttribute,
			ConstantPool constantPool) {
		this.annotationsAttribute = requireNonNull(annotationsAttribute);
		this.cp = new ConstantPoolHelper(constantPool);
	}

	public List<AnnotationAst> decompile() {
		return annotationsAttribute.getAnnotations().stream()
				.map(this::decompileAnnotation)
				.collect(toList());
	}

	private AnnotationAst decompileAnnotation(RuntimeVisibleAnnotationsAttribute.Annotation bytecodeAnnotation) {
		JavaType annotationType = cp.getFieldDescriptor(bytecodeAnnotation.getType());

		List<AnnotationPropertyAssignmentAst> elementValuePairs = bytecodeAnnotation.getElementValuePairs()
				.stream()
				.map(this::decompileElementValuePair)
				.collect(toList());

		AnnotationAst ast = new AnnotationAst(annotationType, elementValuePairs);
		return ast;
	}

	private AnnotationPropertyAssignmentAst decompileElementValuePair(ElementValuePair elementValuePair) {
		String propertyName = cp.getString(elementValuePair.getElementName());

		ExprAst propertyValue =
				decompileElementValue(elementValuePair.getValue());

		return new AnnotationPropertyAssignmentAst(propertyName, propertyValue);
	}

	private ExprAst decompileElementValue(ElementValue value) {
		switch (value.getTag()) {
			case BYTE:
				break;
			case CHAR:
				break;
			case DOUBLE:
				break;
			case FLOAT:
				break;

			case INT: {
				int integerConstant = cp.getInteger(value.getConstantValue());
				return new IntegerLiteral(integerConstant);
			}

			case LONG:
				break;
			case SHORT:
				break;
			case BOOLEAN:
				break;
			case STRING: {
				String stringConstant = cp.getString(value.getConstantValue());
				return new StringLiteral(stringConstant);
			}

			case ENUM:
				break;
			case CLASS:
				break;
			case ANNOTATION:
				break;

			case ARRAY: {
				List<ExprAst> arrayElements = value.getArrayValue()
						.getValues()
						.stream()
						.map(this::decompileElementValue)
						.collect(toList());

				return new ArrayLiteral(arrayElements);
			}
		}

		throw new AssertionError("Attribute property=value value tag: " +
				value.getTag() + " is not supported yet!");
	}
}
