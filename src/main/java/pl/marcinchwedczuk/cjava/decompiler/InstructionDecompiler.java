package pl.marcinchwedczuk.cjava.decompiler;

import com.google.common.base.Preconditions;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.auxiliary.NewMemoryAst;
import pl.marcinchwedczuk.cjava.ast.expr.*;
import pl.marcinchwedczuk.cjava.ast.expr.literal.DoubleLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.IntegerLiteral;
import pl.marcinchwedczuk.cjava.ast.expr.literal.StringLiteral;
import pl.marcinchwedczuk.cjava.ast.statement.*;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute;
import pl.marcinchwedczuk.cjava.bytecode.constantpool.*;
import pl.marcinchwedczuk.cjava.bytecode.instruction.*;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodSignature;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.ClassType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static pl.marcinchwedczuk.cjava.decompiler.LocalVariablesTracker.SlotType.PARAMETER;
import static pl.marcinchwedczuk.cjava.decompiler.LocalVariablesTracker.SlotType.VARIABLE;

public class InstructionDecompiler {
	private final CodeAttribute codeAttribute;
	private final MethodDeclarationAst methodDeclaration;
	private final ConstantPoolHelper cp;

	private List<StatementAst> alreadyDecompiled;
	private Stack<ExprAst> stack;
	private List<Instruction> instructions;
	private int current;
	private LocalVariablesTracker localVariablesTracker;

	// Set of *READ-ONLY* local variables introducted (e.g. by dup instruction)
	// to hold  some value.
	// Values of these variables can be safetly reused.
	private Set<LocalVariable> syntheticLocalVariables;
	private List<LocalVariable> localVariables;

	public InstructionDecompiler(CodeAttribute codeAttribute,
								 MethodDeclarationAst methodDeclaration,
								 ConstantPoolHelper cp) {
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

				case iload_3:
					decompileXLoad(3);
					break;

				case iload:
					decompileXLoad(((SingleOperandInstruction)instruction).getOperand());
					break;

				case dstore_0:
					decompileXStore(0);
					break;

				case astore_1:
					decompileXStore(1);
					break;

				case istore_2:
					decompileXStore(2);
					break;

				case istore_3:
					decompileXStore(3);
					break;

				case istore:
					decompileXStore(((SingleOperandInstruction)instruction).getOperand());
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

				case areturn:
				case ireturn:
				case dreturn:
					decompileReturnValue();
					break;

				case ldc:
				case ldc2_w:
					decompileLdc((SingleOperandInstruction)instruction);
					break;

				case iconst_0:
					stack.push(IntegerLiteral.of(0));
					break;

				case iconst_1:
					stack.push(IntegerLiteral.of(1));
					break;

				case iconst_2:
					stack.push(IntegerLiteral.of(2));
					break;

				case iconst_3:
					stack.push(IntegerLiteral.of(3));
					break;

				case iadd:
				case dadd:
					decompileBinaryOperator(JavaOperator.ADDITION);
					break;

				case imul:
				case dmul:
					decompileBinaryOperator(JavaOperator.MULTIPLICATION);
					break;

				case isub:
				case dsub:
					decompileBinaryOperator(JavaOperator.SUBTRACTION);
					break;

				case idiv:
				case ddiv:
					decompileBinaryOperator(JavaOperator.DIVISION);
					break;

				case new_:
					decompileNew((SingleOperandInstruction)instruction);
					break;

				case anewarray:
					decompileNewArray((SingleOperandInstruction)instruction);
					break;

				case i2d:
					decompileCast(PrimitiveType.DOUBLE);
					break;

				case d2i:
					decompileCast(PrimitiveType.INT);
					break;

				case dup:
					decompileDup();
					break;

				case pop:
					decompilePop();
					break;

				case aastore:
					decompileArrayStore();
					break;

				case athrow:
					decompileThrow();
					break;

				default:
					throw new RuntimeException("decompilation of " +
							instruction.getOpcode() + " is not implemented yet!");
			}
		}

		Preconditions.checkState(stack.isEmpty(), "Stack must be empty at method end.");
		return StatementBlockAst.fromStatements(alreadyDecompiled);
	}

	private void decompileThrow() {
		ExprAst exception = stack.pop();
		alreadyDecompiled.add(ThrowStatementAst.create(exception));
	}

	private void decompilePop() {
		ExprAst value = stack.pop();
		alreadyDecompiled.add(ExprStatementAst.fromExpr(value));
	}

	private void decompileArrayStore() {
		ExprAst value = stack.pop();
		ExprAst indexExpr = stack.pop();
		ExprAst arrayExpr = stack.pop();

		ArrayAccess arrayAccess = ArrayAccess.create(arrayExpr, indexExpr);
		AssignmentOpAst assignmentAst = AssignmentOpAst.create(arrayAccess, value);

		alreadyDecompiled.add(ExprStatementAst.fromExpr(assignmentAst));
	}

	private void decompileDup() {
		// This is naive implementation - we
		// introduce a new local variable to hold
		// dup'ed value.

		ExprAst exprToDuplicate = stack.pop();

		// We already have *synthetic* local variable on stack
		if ((exprToDuplicate instanceof LocalVariableValueAst) &&
				syntheticLocalVariables.contains(((LocalVariableValueAst)exprToDuplicate).getVariable())) {

			// Since synthetic variables are read-only we can reuse them
			stack.push(exprToDuplicate);
			stack.push(exprToDuplicate);

			return;
		}

		LocalVariable newLocalVariable =
				createLocalVariable(exprToDuplicate.getResultType());

		syntheticLocalVariables.add(newLocalVariable);

		alreadyDecompiled.add(
				VariableDeclarationStatementAst.create(newLocalVariable, exprToDuplicate));

		// Push two copies of variable onto the stack
		stack.push(LocalVariableValueAst.forVariable(newLocalVariable));
		stack.push(LocalVariableValueAst.forVariable(newLocalVariable));
	}

	private void decompileNewArray(SingleOperandInstruction newArray) {
		ClassType elementType = cp.getClassName(newArray.getOperand());
		ExprAst numberOfElements = stack.pop();

		NewArrayAst newArrayExpr = NewArrayAst.create(elementType, numberOfElements);
		stack.push(newArrayExpr);
	}

	private void decompileCast(JavaType targetType) {
		ExprAst expr = stack.pop();
		CastAst cast = CastAst.create(targetType, expr);
		stack.push(cast);
	}

	private void decompileNew(SingleOperandInstruction newInstruction) {
		// New instructions is used with following pattern:
		// new           class some/valueType/of/Class
		// dup
		// [constructor arguments here]
		// invokespecial some/valueType/of/Class."<init>":()V

		ClassType className = cp.getClassName(newInstruction.getOperand());

		Preconditions.checkState(
				instructions.get(current).getOpcode() == Opcode.dup,
				"Expecting new/dup pattern.");

		current += 1;

		// Push auxiliary AST that will be replaced
		// by NewInstanceAST when invokespecial is processed.
		stack.push(NewMemoryAst.create(className));
	}

	private void decompileReturnValue() {
		ExprAst returnedValue = stack.pop();
		alreadyDecompiled.add(
			ReturnValueStatementAst.create(returnedValue));

		Preconditions.checkState(stack.isEmpty(), "Stack should be empty after return.");
	}

	private void decompileBinaryOperator(JavaOperator operator) {
		Preconditions.checkState(stack.size() >= 2,
				"Expecting at least two values on stack.");

		ExprAst right = stack.pop();
		ExprAst left = stack.pop();

		stack.push(BinaryOpAst.create(operator, left, right));
	}

	private void decompileLdc(SingleOperandInstruction ldc) {
		Constant constant = cp.getAny(ldc.getOperand());

		switch (constant.getTag()) {
			case STRING: {
				StringConstant stringConstant = (StringConstant) constant;
				String literalValue = cp.getString(stringConstant.getUtf8());
				stack.push(StringLiteral.of(literalValue));
				break;
			}

			case DOUBLE: {
				DoubleConstant doubleConstant = (DoubleConstant) constant;
				double value = doubleConstant.getValue();
				stack.push(DoubleLiteral.of(value));
				break;
			}

			default:
				throw new RuntimeException("LDC for constant: " +
						constant.getClass().getSimpleName() +
						" is not implmeneted. Please add it!");
		}

		if (constant instanceof StringConstant) {
		} else {
		}
	}

	private void decompileReturn() {
		Preconditions.checkState(stack.isEmpty(), "Stack should be empty before return.");
		alreadyDecompiled.add(ReturnStatementAst.create());
	}

	private void decompileInvokeX(SingleOperandInstruction invokeX, boolean staticCall) {
		MethodRefConstant calledMethod = cp.getMethodRef(invokeX.getOperand());

		ClassType classContainingMethod = cp.getClassName(calledMethod.getKlass());

		NameAndTypeConstant nameAndType = cp.getNameAndType(calledMethod.getNameAndType());
		String methodName = cp.getString(nameAndType.getName());
		MethodSignature methodSignature = cp.getMethodDescriptor(nameAndType.getDescriptor());

		int numberOfArguments = methodSignature.getArity();

		List<ExprAst> methodArguments = takeFromStack(numberOfArguments);
		ExprAst thisArgument = staticCall ? null : stack.pop();

		// TODO: super()
		if (isConstructorCall(methodName, invokeX, thisArgument)) {
			NewMemoryAst notInitializedInstance = (NewMemoryAst) thisArgument;

			NewInstanceAst newInstanceAst = NewInstanceAst.create(
					notInitializedInstance.getType(),
					classContainingMethod, methodName,
					methodSignature, methodArguments);
			stack.push(newInstanceAst);

		} else {
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
	}

	private boolean isConstructorCall(String methodName, SingleOperandInstruction invokeSpecial, ExprAst thisArgument) {
		if (invokeSpecial.getOpcode() != Opcode.invokespecial) {
			return false;
		}

		if (!JvmConstants.isConstructorName(methodName)) {
			return false;
		}

		if (!(thisArgument instanceof NewMemoryAst)) {
			return false;
		}

		return true;
	}

	private void decompileXStore(int varIndex) {
		ExprAst valueToStore = stack.pop();

		LocalVariablesTracker.Slot destination =
				localVariablesTracker.tryStore(varIndex, valueToStore.getResultType());

		if (destination == null) {
			// declare new local variable
			LocalVariable newVariable = createLocalVariable(valueToStore.getResultType());
			localVariablesTracker.declareVariable(varIndex, newVariable);

			alreadyDecompiled.add(VariableDeclarationStatementAst.create(newVariable, valueToStore));
		} else {
			// generate assignment expression
			LValueAst destinationAst = null;

			if (destination.slotType == VARIABLE) {
				destinationAst = LocalVariableValueAst.forVariable(destination.localVariable);
			} else if (destination.slotType == PARAMETER) {
				destinationAst = ParameterValueAst.forParameter(destination.methodParameter);
			} else {
				Preconditions.checkState(false, "Cannot reassign this.");
			}

			stack.push(AssignmentOpAst.create(destinationAst, valueToStore));
		}
	}

	private LocalVariable createLocalVariable(JavaType type) {
		LocalVariable newVariable =
				LocalVariable.create(type, "var" + localVariables.size());

		localVariables.add(newVariable);
		return newVariable;
	}

	private void decompileXLoad(int varIndex) {
		// locals array:
		// This | Parameters | Local variables

		LocalVariablesTracker.Slot slot = localVariablesTracker.load(varIndex);

		switch (slot.slotType) {
			case THIS:
				stack.push(ThisValueAst.create());
				return;

			case PARAMETER:
				stack.push(ParameterValueAst.forParameter(slot.methodParameter));
				return;

			case VARIABLE:
				stack.push(LocalVariableValueAst.forVariable(slot.localVariable));
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

		this.localVariables = new ArrayList<>();
		this.syntheticLocalVariables = new HashSet<>();
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
