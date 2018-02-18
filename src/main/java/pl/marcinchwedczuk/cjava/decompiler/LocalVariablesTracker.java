package pl.marcinchwedczuk.cjava.decompiler;

import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.decompiler.signature.LocalVariable;
import pl.marcinchwedczuk.cjava.decompiler.signature.MethodParameter;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks this, method parameters and local variables
 */
public class LocalVariablesTracker {
	private final Map<Integer, Slot> localVariables = new HashMap<>();

	public LocalVariablesTracker(MethodDeclarationAst methodDeclarationAst) {
		initializeVariableMap(methodDeclarationAst);
	}

	public Slot tryStore(int varIndex, JavaType type) {
		Slot slot = localVariables.get(varIndex);

		if ((slot != null) && slot.slotType.equals(type)) {
			return slot;
		}

		return null;
	}

	public Slot declareVariable(int varIndex, LocalVariable variable) {
		Slot slot = localVariables.get(varIndex);

		if ((slot != null) && slot.slotType.equals(variable.getType())) {
			throw new IllegalArgumentException(
					"Variable is already declared and can be reused!");
		}

		// Remove old slot
		if (slot != null) {
			if (isWideType(slot.valueType)) {
				// Remove second part of the wide slot
				Slot below = localVariables.get(varIndex - 1);
				if (below == slot) {
					localVariables.remove(varIndex - 1);
				}

				Slot above = localVariables.get(varIndex + 1);
				if (above == slot) {
					localVariables.remove(varIndex + 1);
				}
			}

			// Remove slot
			localVariables.remove(varIndex);
		}

		// create new slot
		int slotSize = isWideType(variable.getType()) ? 2 : 1;

		Slot parameterSlot = new Slot(
				SlotType.VARIABLE, variable.getType(), null, variable);

		for (int i = 0; i < slotSize; i++) {
			localVariables.put(varIndex + i, parameterSlot);
		}

		return parameterSlot;
	}

	public Slot load(int varIndex) {
		Slot slot = localVariables.get(varIndex);
		if (slot == null) {
			throw new RuntimeException("Cannot find slot for index: " + varIndex);
		}

		return slot;
	}

	private Map<Integer, Slot> initializeVariableMap(MethodDeclarationAst declarationAst) {
		int currentParameterVarIndex = 0;

		// register this slow if necessary
		if (!declarationAst.isStatic()) {
			Slot thisSlot = new Slot(SlotType.THIS, declarationAst.getThisParameterType(), null, null);
			localVariables.put(0, thisSlot);
			currentParameterVarIndex = 1;
		}

		// register method parameters
		ImmutableList<MethodParameter> parameters =
				declarationAst.getMethodSignature().getParameters();

		for (MethodParameter parameter : parameters) {
			Slot parameterSlot = new Slot(
					SlotType.PARAMETER, parameter.getType(), parameter, null);

			int slotSize = isWideType(parameter.getType()) ? 2 : 1;
			for (int i = 0; i < slotSize; i++) {
				localVariables.put(currentParameterVarIndex + i, parameterSlot);
			}

			currentParameterVarIndex += slotSize;
		}

		return localVariables;
	}

	private boolean isWideType(JavaType type) {
		return (type.equals(PrimitiveType.LONG) || type.equals(PrimitiveType.DOUBLE));
	}

	public static class Slot {
		public final SlotType slotType;
		public final JavaType valueType;
		public final MethodParameter methodParameter;
		public final LocalVariable localVariable;

		public Slot(SlotType slotType, JavaType valueType, MethodParameter parameter, LocalVariable localVariable) {
			this.slotType = slotType;
			this.valueType = valueType;
			this.methodParameter = parameter;
			this.localVariable = localVariable;
		}
	}

	public enum SlotType {
		THIS, PARAMETER, VARIABLE
	}
}
