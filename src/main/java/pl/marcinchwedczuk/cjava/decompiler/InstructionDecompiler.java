package pl.marcinchwedczuk.cjava.decompiler;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.*;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.bytecode.instruction.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;

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
	private Stack<ExprAst> stack;
	private List<Instruction> instructions;
	private int current;
	private LocalVariablesTracker localVariablesTracker;

	public InstructionDecompiler(CodeAttribute codeAttribute, MethodDeclarationAst methodDeclaration, ConstantPoolHelper cp) {
		this.codeAttribute = requireNonNull(codeAttribute);
		this.methodDeclaration = methodDeclaration;
		this.cp = requireNonNull(cp);

		this.localVariablesTracker = new LocalVariablesTracker(methodDeclaration);
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
				case iload_0:
				case dload_0:
					decompileXLoad(0);
					break;

				case aload_1:
				case iload_1:
				case dload_1:
					decompileXLoad(1);
					break;

				case aload_2:
				case iload_2:
				case dload_2:
					decompileXLoad(2);
					break;

				case invokespecial:
				case invokevirtual:
					decompileInvokeX((SingleOperandInstruction)instruction, false);
					break;

				case invokestatic:
					decompileInvokeX((SingleOperandInstruction)instruction, true);
					break;

				case return_:
					decompileReturn();
					break;

				case ireturn:
				case dreturn:
					decompileReturnValue();
					break;

				case ldc:
					decompileLdc((SingleOperandInstruction)instruction);
					break;

				case iadd:
				case dadd:
					decompileBinaryOperator(BinaryOperator.ADD);
					break;

				case imul:
				case dmul:
					decompileBinaryOperator(BinaryOperator.MULTIPLY);
					break;

				case isub:
				case dsub:
					decompileBinaryOperator(BinaryOperator.SUBTRACT);
					break;

				case idiv:
				case ddiv:
					decompileBinaryOperator(BinaryOperator.DIVIDE);
					break;

				case new_:
					decompileNew((SingleOperandInstruction)instruction);
					break;

				default:
					throw new RuntimeException("decompilation of " +
							instruction.getOpcode() + " is not implemented yet!");
			}
		}

		Preconditions.checkState(stack.isEmpty(), "Stack must be empty at method end.");
		return StatementBlockAst.fromStatements(alreadyDecompiled);
	}



	private void decompileNew(SingleOperandInstruction newInstruction) {
		// New instructions is used with following pattern:
		// new           class some/valueType/of/Class
		// dup
		// invokespecial some/valueType/of/Class."<init>":()V

		ClassType className = cp.getClassName(newInstruction.getOperand());

		Preconditions.checkState(
				instructions.get(current).getOpcode() == Opcode.dup,
				"Expecting new/dup pattern.");

		Instruction invokeSpecial = instructions.get(current+1);
		Preconditions.checkState(
				invokeSpecial.getOpcode() == Opcode.invokespecial,
				"Expecting new/dup pattern");

		current += 2;

		// TODO: Add constructor parameters

		// TODO: Check if invoke special acutally points to the constructor

		stack.push(NewOpAst.create(className));
	}

	private void decompileReturnValue() {
		ExprAst returnedValue = stack.pop();
		alreadyDecompiled.add(
			ReturnValueStatementAst.create(returnedValue));

		Preconditions.checkState(stack.isEmpty(), "Stack should be empty after return.");
	}

	private void decompileBinaryOperator(BinaryOperator operator) {
		Preconditions.checkState(stack.size() >= 2,
				"Expecting at least two values on stack.");

		ExprAst right = stack.pop();
		ExprAst left = stack.pop();

		stack.push(BinaryOpAst.create(operator, left, right));
	}

	private void decompileLdc(SingleOperandInstruction ldc) {
		Constant constant = cp.getAny(ldc.getOperand());

		if (constant instanceof StringConstant) {
			StringConstant stringConstant = (StringConstant)constant;
			String literalValue = cp.getString(stringConstant.getUtf8());
			stack.push(StringLiteral.of(literalValue));
		} else {
			throw new RuntimeException("LDC for constant: " +
					constant.getClass().getSimpleName() +
					" is not implmeneted. Please add it!");
		}
	}

	private void decompileReturn() {
		Preconditions.checkState(stack.isEmpty(), "Stack should be empty before return.");
		alreadyDecompiled.add(ReturnStatementAst.create());
	}

	private void decompileInvokeX(SingleOperandInstruction invokeSpecial, boolean staticCall) {
		MethodRefConstant calledMethod = cp.getMethodRef(invokeSpecial.getOperand());

		ClassType classContainingMethod = cp.getClassName(calledMethod.getKlass());

		NameAndTypeConstant nameAndType = cp.getNameAndType(calledMethod.getNameAndType());
		String methodName = cp.getString(nameAndType.getName());
		MethodSignature methodSignature = cp.getMethodDescriptor(nameAndType.getDescriptor());

		int numberOfArguments = methodSignature.getArity();

		List<ExprAst> methodArguments = takeFromStack(numberOfArguments);
		ExprAst thisArgument = staticCall ? null : stack.pop();

		MethodCallAst methodCall = MethodCallAst.create(
				classContainingMethod, methodName, methodSignature,
				thisArgument, methodArguments);

		if (methodSignature.hasVoidReturnType()) {
			// wrap as statement
			alreadyDecompiled.add(ExprStatementAst.fromExpr(methodCall));
		} else {
			stack.push(methodCall);
		}
	}

	private void decompileXLoad(int varIndex) {
		// locals array:
		// This | Parameters | Local variables

		LocalVariablesTracker.Slot slot = localVariablesTracker.getSlot(varIndex);

		switch (slot.slotType) {
			case THIS:
				stack.push(ThisValueAst.create());
				return;

			case PARAMETER:
				stack.push(ParameterValueAst.forParameter("arg" + slot.ordinal));
				return;

			case VARIABLE:
				stack.push(VariableValueAst.forVariable("var" + slot.ordinal));
				return;
		}

		throw new RuntimeException("aload_xxx - not impllemented");
	}

	private void decompileGetStatic(SingleOperandInstruction getStatic) {
		FieldRefConstant fieldRef = cp.getFieldRef(getStatic.getOperand());

		ClassType classContainingField = cp.getClassName(fieldRef.getKlass());

		NameAndTypeConstant fieldNameAndType = cp.getNameAndType(fieldRef.getNameAndType());
		String fieldName = cp.getString(fieldNameAndType.getName());
		JavaType fieldType = cp.getFieldDescriptor(fieldNameAndType.getDescriptor());

		FieldAccessAst fieldAccess = FieldAccessAst.create(
				classContainingField, fieldName, fieldType);

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

	private List<ExprAst> takeFromStack(int number) {
		List<ExprAst> values = new ArrayList<>();

		for (int i = 0; i < number; i++) {
			values.add(stack.pop());
		}

		Collections.reverse(values);

		return values;
	}
}
