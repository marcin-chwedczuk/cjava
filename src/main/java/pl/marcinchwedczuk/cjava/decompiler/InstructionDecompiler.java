package pl.marcinchwedczuk.cjava.decompiler;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.ast.Ast;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.FieldAccessAst;
import pl.marcinchwedczuk.cjava.ast.expr.MethodCallAst;
import pl.marcinchwedczuk.cjava.ast.expr.ThisValueAst;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.ExprStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.ReturnStatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementAst;
import pl.marcinchwedczuk.cjava.ast.statement.StatementBlockAst;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.bytecode.instruction.*;
import pl.marcinchwedczuk.cjava.decompiler.descriptor.method.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.signature.javatype.JavaType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static java.util.Objects.requireNonNull;

public class InstructionDecompiler {
	private final CodeAttribute codeAttribute;
	private final MethodDeclarationAst methodDeclaration;
	private final ConstantPoolHelper cp;

	private List<StatementAst> alreadyDecompiled;
	private Stack<Ast> stack;
	private List<Instruction> instructions;
	private int current;

	public InstructionDecompiler(CodeAttribute codeAttribute, MethodDeclarationAst methodDeclaration, ConstantPoolHelper cp) {
		this.codeAttribute = requireNonNull(codeAttribute);
		this.methodDeclaration = methodDeclaration;
		this.cp = requireNonNull(cp);
	}

	public StatementBlockAst decompile() {
		loadInstructions(codeAttribute.getCode());

		while (current < instructions.size()) {
			Instruction instruction = instructions.get(current);
			current++;

			switch (instruction.getOpcode()) {
				case getstatic:
					decompileGetStatic((SingleOperandInstruction)instruction);
					break;

				case aload_0:
					decompileALoad(0);
					break;

				case invokespecial:
				case invokevirtual:
					decompileInvokeX((SingleOperandInstruction)instruction);
					break;

				case return_:
					decompileReturn();
					break;

				case ldc:
					decompileLdc((SingleOperandInstruction)instruction);
					break;

				default:
					throw new RuntimeException("decompilation of " +
							instruction.getOpcode() + " is not implemented yet!");
			}
		}

		Preconditions.checkState(stack.isEmpty(), "Stack must be empty at method end.");
		return new StatementBlockAst(alreadyDecompiled);
	}

	private void decompileLdc(SingleOperandInstruction ldc) {
		Constant constant = cp.getAny(ldc.getOperand());

		if (constant instanceof StringConstant) {
			StringConstant stringConstant = (StringConstant)constant;
			String literalValue = cp.getString(stringConstant.getUtf8());
			stack.push(new StringLiteral(literalValue));
		} else {
			throw new RuntimeException("LDC for constant: " +
					constant.getClass().getSimpleName() +
					" is not implmeneted. Please add it!");
		}
	}

	private void decompileReturn() {
		Preconditions.checkState(stack.isEmpty(), "Stack should be empty before return.");
		alreadyDecompiled.add(new ReturnStatementAst());
	}

	private void decompileInvokeX(SingleOperandInstruction invokeSpecial) {
		MethodRefConstant calledMethod = cp.getMethodRef(invokeSpecial.getOperand());

		ClassType classContainingMethod = cp.getClassName(calledMethod.getKlass());

		NameAndTypeConstant nameAndType = cp.getNameAndType(calledMethod.getNameAndType());
		String methodName = cp.getString(nameAndType.getName());
		MethodSignature methodSignature = cp.getMethodDescriptor(nameAndType.getDescriptor());

		int numberOfArguments = methodSignature.getNumberOfParameters();

		List<Ast> methodArguments = takeFromStack(numberOfArguments);
		Ast thisArgument = stack.pop();

		MethodCallAst methodCall = new MethodCallAst(
				classContainingMethod, methodName, methodSignature,
				thisArgument, methodArguments);

		if (methodSignature.hasVoidReturnType()) {
			// wrap as statement
			alreadyDecompiled.add(new ExprStatementAst(methodCall));
		} else {
			stack.push(methodCall);
		}
	}

	private void decompileALoad(int localVarIndex) {
		// locals array:
		// This | Parameters | Local variables

		if ((localVarIndex == 0) && !methodDeclaration.isStatic()) {
			stack.push(new ThisValueAst());
			return;
		}


		// TODO: return parameter and local vars access

		throw new RuntimeException("aload_xxx - not impllemented");
	}

	private void decompileGetStatic(SingleOperandInstruction getStatic) {
		FieldRefConstant fieldRef = cp.getFieldRef(getStatic.getOperand());

		ClassType classContainingField = cp.getClassName(fieldRef.getKlass());

		NameAndTypeConstant fieldNameAndType = cp.getNameAndType(fieldRef.getNameAndType());
		String fieldName = cp.getString(fieldNameAndType.getName());
		JavaType fieldType = cp.getFieldDescriptor(fieldNameAndType.getDescriptor());

		FieldAccessAst fieldAccess = new FieldAccessAst(classContainingField, fieldName, fieldType);
		stack.push(fieldAccess);
	}

	private void loadInstructions(CodeAttribute.Code code) {
		this.instructions =
				new InstructionReader(code.getBytes(), cp, new OpcodeMapper())
					.readInstructions();

		this.current = 0;
		this.stack = new Stack<>();
		this.alreadyDecompiled = new ArrayList<>();
	}

	private List<Ast> takeFromStack(int number) {
		List<Ast> values = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			values.add(stack.pop());
		}

		Collections.reverse(values);

		return values;
	}
}
