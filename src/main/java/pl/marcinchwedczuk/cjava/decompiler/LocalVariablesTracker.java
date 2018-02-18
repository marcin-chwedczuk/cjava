package pl.marcinchwedczuk.cjava.decompiler;

import com.google.common.collect.ImmutableList;
import pl.marcinchwedczuk.cjava.ast.MethodDeclarationAst;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.JavaType;
import pl.marcinchwedczuk.cjava.decompiler.typesystem.PrimitiveType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Tracks this, method parameters and local variables
 */
public class LocalVariablesTracker {
	private final AtomicInteger variableOrdinals = new AtomicInteger(0);
	private final Map<Integer, Slot> variableMap = new HashMap<>();

	public LocalVariablesTracker(MethodDeclarationAst methodDeclarationAst) {
		initializeVariableMap(methodDeclarationAst);
	}

	public void store(int varIndex, JavaType type) {
		Slot slot = variableMap.get(varIndex);
		if (slot != null) {
			if (slot.slotType.equals(type)) {
				// The same type nothing to fix
				return;
			}

			if (slot.size == 2) {
				// Remove second part of the wide slot
				Slot below = variableMap.get(varIndex - 1);
				if (below == slot) {
					variableMap.remove(varIndex - 1);
				}

				Slot above = variableMap.get(varIndex + 1);
				if (above == slot) {
					variableMap.remove(varIndex + 1);
				}
			}
		}

		// create or overwrite existing slot
		int slotSize = isWideType(type) ? 2 : 1;

		Slot parameterSlot = new Slot(
				SlotType.VARIABLE, type,
				variableOrdinals.getAndIncrement(), slotSize);

		for (int i = 0; i < slotSize; i++) {
			variableMap.put(varIndex + i, parameterSlot);
		}
	}

	public Slot getSlot(int varIndex) {
		Slot slot = variableMap.get(varIndex);
		if (slot == null) {
			throw new RuntimeException("Cannot find slot for index: " + varIndex);
		}

		return slot;
	}

	private Map<Integer, Slot> initializeVariableMap(MethodDeclarationAst methodDeclarationAst) {
		int currentParameterVarIndex = 0;

		// register this slow if necessary
		if (!methodDeclarationAst.isStatic()) {
			Slot thisSlot = new Slot(SlotType.THIS, null, -1, 1);

			variableMap.put(0, thisSlot);
			currentParameterVarIndex = 1;
		}

		// register method parameters
		ImmutableList<JavaType> parametersTypes =
				methodDeclarationAst.getMethodSignature().getParametersTypes();

		int parameterOrdinal = 0;
		for (JavaType parametersType : parametersTypes) {
			int slotSize = isWideType(parametersType) ? 2 : 1;

			Slot parameterSlot = new Slot(
					SlotType.PARAMETER, parametersType,
					parameterOrdinal++, slotSize);

			for (int i = 0; i < slotSize; i++) {
				variableMap.put(currentParameterVarIndex + i, parameterSlot);
			}
			currentParameterVarIndex += slotSize;
		}

		return variableMap;
	}

	private boolean isWideType(JavaType type) {
		return (type.equals(PrimitiveType.LONG) || type.equals(PrimitiveType.DOUBLE));
	}

	public static class Slot {
		public final SlotType slotType;
		public final JavaType valueType;
		public final int ordinal;
		public final int size;

		public Slot(SlotType slotType, JavaType valueType, int ordinal, int size) {
			this.slotType = slotType;
			this.valueType = valueType;
			this.ordinal = ordinal;
			this.size = size;
		}
	}

	public enum SlotType {
		THIS, PARAMETER, VARIABLE
	}
}
