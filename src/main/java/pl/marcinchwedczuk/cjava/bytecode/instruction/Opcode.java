package pl.marcinchwedczuk.cjava.bytecode.instruction;

public enum Opcode {
	/**
	 * Load reference from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	aaload(0x32),

	/**
	 * Store into reference array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	aastore(0x53, ""),

	/**
	 * Push null.
	 * <p>
	 * {@code STACK ... -> ..., null }
	 */
	aconst_null(0x1),
	/**
	 * Load reference from local variable.
	 * <p>
	 * {@code STACK ... -> ..., objectref }
	 */
	aload(0x19),

	/**
	 * Load reference from local variable.
	 * <p>
	 * {@code STACK ... -> ..., objectref }
	 */
	aload_0(0x2a, ""),
	aload_1(0x2b, ""),
	aload_2(0x2c, ""),
	aload_3(0x2d, ""),

	/**
	 * Create new array of reference.
	 * <p>
	 * {@code STACK ..., count -> ..., arrayref }
	 */
	anewarray(0xbd, "u2"),

	/**
	 * Return reference from method.
	 * <p>
	 * {@code STACK ..., objectref -> [empty] }
	 */
	areturn(0xb0, ""),

	/**
	 * Get length of array.
	 * <p>
	 * {@code STACK ..., arrayref -> ..., length }
	 */
	arraylength(0xbe, ""),

	/**
	 * Store reference into local variable.
	 * <p>
	 * {@code STACK ..., objectref -> ... }
	 */
	astore(0x3a, "u1"),

	/**
	 * Store reference into local variable.
	 * <p>
	 * {@code STACK ..., objectref -> ... }
	 */
	astore_0(0x4b, ""),
	astore_1(0x4c, ""),
	astore_2(0x4d, ""),
	astore_3(0x4e, ""),

	/**
	 * Throw exception or error.
	 * <p>
	 * {@code STACK ..., objectref -> objectref }
	 */
	athrow(0xbf, ""),

	/**
	 * Load byte or boolean from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	baload(0x33),
	/**
	 * Store into byte or boolean array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	bastore(0x54),

	/**
	 * Push byte.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	bipush(0x10, "s1"),

	/**
	 * Load char from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	caload(0x34),
	/**
	 * Store into char array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	castore(0x55),

	/**
	 * Check whether object is of given type.
	 * <p>
	 * {@code STACK ..., objectref -> ..., objectref }
	 */
	checkcast(0xc0, "u2"),

	/**
	 * Convert double to float.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	d2f(0x90),
	/**
	 * Convert double to int.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	d2i(0x8e),
	/**
	 * Convert double to long.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	d2l(0x8f),
	/**
	 * Add double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	dadd(0x63, ""),

	/**
	 * Load double from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	daload(0x31),
	/**
	 * Store into double array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	dastore(0x52),
	/**
	 * Compare double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	dcmpg(0x98),

	/**
	 * Push double 0.0 onto stack.
	 * <p>
	 * {@code STACK ... -> ..., <d> }
	 */
	dconst_0(0xe, ""),

	/**
	 * Divide double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ddiv(0x6f),
	/**
	 * Load double from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	dload(0x18),

	/**
	 * Load double from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	dload_0(0x26, ""),
	dload_1(0x27, ""),
	dload_2(0x28, ""),
	dload_3(0x29, ""),

	/**
	 * Multiply double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	dmul(0x6b),
	/**
	 * Negate double.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	dneg(0x77),
	/**
	 * Remainder double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	drem(0x73),
	/**
	 * Return double from method.
	 * <p>
	 * {@code STACK ..., value -> [empty] }
	 */
	dreturn(0xaf),
	/**
	 * Store double into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	dstore(0x39),

	/**
	 * Store double into local variable with index 0.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	dstore_0(0x47, ""),
	dstore_1(0x48, ""),
	dstore_2(0x49, ""),
	dstore_3(0x4a, ""),

	/**
	 * Subtract double.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	dsub(0x67),

	/**
	 * Duplicate the top operand stack value.
	 * <p>
	 * {@code STACK ..., value -> ..., value, value }
	 */
	dup(0x59, ""),

	/**
	 * Duplicate the top operand stack value and insert two values down.
	 * <p>
	 * {@code STACK ..., value2, value1 -> ..., value1, value2, value1 }
	 */
	dup_x1(0x5a),
	/**
	 * Duplicate the top operand stack value and insert two or three values down.
	 * <p>
	 * {@code STACK Form 1: ..., value3, value2, value1 -> ..., value1, value3, value2, value1 where value1, value2, and value3 are all values of a category 1 computational type (§2.11.1). Form 2: ..., value2, value1 -> ..., value1, value2, value1 where value1 is a value of a category 1 computational type and value2 is a value of a category 2 computational type (§2.11.1). }
	 */
	dup_x2(0x5b),
	/**
	 * Duplicate the top one or two operand stack values.
	 * <p>
	 * {@code STACK Form 1: ..., value2, value1 -> ..., value2, value1, value2, value1 where both value1 and value2 are values of a category 1 computational type (§2.11.1). Form 2: ..., value -> ..., value, value where value is a value of a category 2 computational type (§2.11.1). }
	 */
	dup2(0x5c),
	/**
	 * Duplicate the top one or two operand stack values and insert two or three values down.
	 * <p>
	 * {@code STACK Form 1: ..., value3, value2, value1 -> ..., value2, value1, value3, value2, value1 where value1, value2, and value3 are all values of a category 1 computational type (§2.11.1). Form 2: ..., value2, value1 -> ..., value1, value2, value1 where value1 is a value of a category 2 computational type and value2 is a value of a category 1 computational type (§2.11.1). }
	 */
	dup2_x1(0x5d),
	/**
	 * Duplicate the top one or two operand stack values and insert two, three, or four values down.
	 * <p>
	 * {@code STACK Form 1: ..., value4, value3, value2, value1 -> ..., value2, value1, value4, value3, value2, value1 where value1, value2, value3, and value4 are all values of a category 1 computational type (§2.11.1). Form 2: ..., value3, value2, value1 -> ..., value1, value3, value2, value1 where value1 is a value of a category 2 computational type and value2 and value3 are both values of a category 1 computational type (§2.11.1). Form 3: ..., value3, value2, value1 -> ..., value2, value1, value3, value2, value1 where value1 and value2 are both values of a category 1 computational type and value3 is a value of a category 2 computational type (§2.11.1). Form 4: ..., value2, value1 -> ..., value1, value2, value1 where value1 and value2 are both values of a category 2 computational type (§2.11.1). }
	 */
	dup2_x2(0x5e),
	/**
	 * Convert float to double.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	f2d(0x8d),
	/**
	 * Convert float to int.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	f2i(0x8b),
	/**
	 * Convert float to long.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	f2l(0x8c),
	/**
	 * Add float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	fadd(0x62),
	/**
	 * Load float from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	faload(0x30),
	/**
	 * Store into float array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	fastore(0x51),
	/**
	 * Compare float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	fcmpg(0x96),
	/**
	 * Push float.
	 * <p>
	 * {@code STACK ... -> ..., <f> }
	 */
	fconst_0(0xb),
	/**
	 * Divide float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	fdiv(0x6e),
	/**
	 * Load float from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	fload(0x17),
	/**
	 * Load float from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	fload_0(0x22),
	/**
	 * Multiply float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	fmul(0x6a),
	/**
	 * Negate float.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	fneg(0x76),
	/**
	 * Remainder float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	frem(0x72),
	/**
	 * Return float from method.
	 * <p>
	 * {@code STACK ..., value -> [empty] }
	 */
	freturn(0xae),
	/**
	 * Store float into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	fstore(0x38),
	/**
	 * Store float into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	fstore_0(0x43),
	/**
	 * Subtract float.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	fsub(0x66),
	/**
	 * Fetch field from object.
	 * <p>
	 * {@code STACK ..., objectref -> ..., value }
	 */
	getfield(0xb4),

	/**
	 * Get static field from class.
	 * <p>
	 * {@code STACK ..., -> ..., value }
	 */
	getstatic(0xb2, "u2"),

	/**
	 * Branch always.
	 * <p>
	 * {@code STACK No change }
	 */
	goto_(0xa7, "s2"),

	/**
	 * Branch always (wide index).
	 * <p>
	 * {@code STACK No change }
	 */
	goto_w(0xc8),
	/**
	 * Convert int to byte.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2b(0x91),
	/**
	 * Convert int to char.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2c(0x92),

	/**
	 * Convert int to double.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2d(0x87, ""),

	/**
	 * Convert int to float.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2f(0x86),
	/**
	 * Convert int to long.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2l(0x85),
	/**
	 * Convert int to short.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	i2s(0x93),
	/**
	 * Add int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	iadd(0x60),
	/**
	 * Load int from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	iaload(0x2e),
	/**
	 * Boolean AND int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	iand(0x7e),
	/**
	 * Store into int array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	iastore(0x4f),

	/**
	 * Push int constant.
	 * <p>
	 * {@code STACK ... -> ..., <i> }
	 */
	iconst_m1(0x2, ""),
	iconst_0(0x3, ""),
	iconst_1(0x4, ""),
	iconst_2(0x5, ""),
	iconst_3(0x6, ""),
	iconst_4(0x7, ""),
	iconst_5(0x8, ""),

	/**
	 * Divide int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	idiv(0x6c),
	/**
	 * Branch if reference comparison succeeds.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ... }
	 */
	if_acmpeq(0xa5),

	/**
	 * Branch if int comparison succeeds.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ... }
	 */
	if_icmpeq(0x9f, "s2"),
	if_icmpne(0xa0, "s2"),
	if_icmplt(0xa1, "s2"),
	if_icmpge(0xa2, "s2"),
	if_icmpgt(0xa3, "s2"),
	if_icmple(0xa4, "s2"),

	/**
	 * Branch if int comparison with zero succeeds.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	ifeq(0x99, "s2"),
	ifne(0x9a, "s2"),
	iflt(0x9b, "s2"),
	ifge(0x9c, "s2"),
	ifgt(0x9d, "s2"),
	ifle(0x9e, "s2"),

	/**
	 * Branch if reference not null.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	ifnonnull(0xc7),
	/**
	 * Branch if reference is null.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	ifnull(0xc6),
	/**
	 * Increment local variable by constant.
	 * <p>
	 * {@code STACK No change }
	 */
	iinc(0x84, "u1s1"),

	/**
	 * Load int from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	iload(0x15, "u1"),

	/**
	 * Load int from local variable with index 0.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	iload_0(0x1a, ""),
	iload_1(0x1b, ""),
	iload_2(0x1c, ""),
	iload_3(0x1d, ""),

	/**
	 * Multiply int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	imul(0x68),
	/**
	 * Negate int.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	ineg(0x74),
	/**
	 * Determine if object is of given type.
	 * <p>
	 * {@code STACK ..., objectref -> ..., result }
	 */
	instanceof_(0xc1),
	/**
	 * Invoke dynamic method.
	 * <p>
	 * {@code STACK ..., [arg1, [arg2 ...]] -> ... }
	 */
	invokedynamic(0xba),

	/**
	 * Invoke interface method.
	 * <p>
	 * {@code STACK ..., objectref, [arg1, [arg2 ...]] -> ... }
	 */
	invokeinterface(0xb9, "u2u1u1"),

	/**
	 * Invoke instance method; special handling for superclass, private, and instance initialization method invocations.
	 * <p>
	 * {@code STACK ..., objectref, [arg1, [arg2 ...]] -> ... }
	 */
	invokespecial(0xb7, "u2"),

	/**
	 * Invoke a class (static) method.
	 * <p>
	 * {@code STACK ..., [arg1, [arg2 ...]] -> ... }
	 */
	invokestatic(0xb8, "u2"),

	/**
	 * Invoke instance method; dispatch based on class.
	 * <p>
	 * {@code STACK ..., objectref, [arg1, [arg2 ...]] -> ... }
	 */
	invokevirtual(0xb6, "u2"),

	/**
	 * Boolean OR int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ior(0x80),
	/**
	 * Remainder int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	irem(0x70),

	/**
	 * Return int from method.
	 * <p>
	 * {@code STACK ..., value -> [empty] }
	 */
	ireturn(0xac, ""),

	/**
	 * Shift left int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ishl(0x78),
	/**
	 * Arithmetic shift right int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ishr(0x7a),

	/**
	 * Store int into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	istore(0x36, "u1"),

	/**
	 * Store int into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	istore_0(0x3b),
	/**
	 * Subtract int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	isub(0x64),
	/**
	 * Logical shift right int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	iushr(0x7c),
	/**
	 * Boolean XOR int.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ixor(0x82),
	/**
	 * Jump subroutine.
	 * <p>
	 * {@code STACK ... -> ..., address }
	 */
	jsr(0xa8),
	/**
	 * Jump subroutine (wide index).
	 * <p>
	 * {@code STACK ... -> ..., address }
	 */
	jsr_w(0xc9),
	/**
	 * Convert long to double.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	l2d(0x8a),
	/**
	 * Convert long to float.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	l2f(0x89),
	/**
	 * Convert long to int.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	l2i(0x88),
	/**
	 * Add long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ladd(0x61),
	/**
	 * Load long from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	laload(0x2f),
	/**
	 * Boolean AND long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	land(0x7f),
	/**
	 * Store into long array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	lastore(0x50),
	/**
	 * Compare long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lcmp(0x94),
	/**
	 * Push long constant.
	 * <p>
	 * {@code STACK ... -> ..., <l> }
	 */
	lconst_0(0x9),

	/**
	 * Push item from run-time constant pool.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	ldc(0x12, "u1"),

	/**
	 * Push item from run-time constant pool (wide index).
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	ldc_w(0x13),
	/**
	 * Push long or double from run-time constant pool (wide index).
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	ldc2_w(0x14),
	/**
	 * Divide long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	ldiv(0x6d),
	/**
	 * Load long from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	lload(0x16),
	/**
	 * Load long from local variable.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	lload_0(0x1e),
	/**
	 * Multiply long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lmul(0x69),
	/**
	 * Negate long.
	 * <p>
	 * {@code STACK ..., value -> ..., result }
	 */
	lneg(0x75),
	/**
	 * Access jump table by key match and jump.
	 * <p>
	 * {@code STACK ..., key -> ... }
	 */
	lookupswitch(0xab),
	/**
	 * Boolean OR long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lor(0x81),
	/**
	 * Remainder long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lrem(0x71),
	/**
	 * Return long from method.
	 * <p>
	 * {@code STACK ..., value -> [empty] }
	 */
	lreturn(0xad),
	/**
	 * Shift left long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lshl(0x79),
	/**
	 * Arithmetic shift right long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lshr(0x7b),
	/**
	 * Store long into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	lstore(0x37),
	/**
	 * Store long into local variable.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	lstore_0(0x3f),
	/**
	 * Subtract long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lsub(0x65),
	/**
	 * Logical shift right long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lushr(0x7d),
	/**
	 * Boolean XOR long.
	 * <p>
	 * {@code STACK ..., value1, value2 -> ..., result }
	 */
	lxor(0x83),
	/**
	 * Enter monitor for object.
	 * <p>
	 * {@code STACK ..., objectref -> ... }
	 */
	monitorenter(0xc2),
	/**
	 * Exit monitor for object.
	 * <p>
	 * {@code STACK ..., objectref -> ... }
	 */
	monitorexit(0xc3),
	/**
	 * Create new multidimensional array.
	 * <p>
	 * {@code STACK ..., count1, [count2, ...] -> ..., arrayref }
	 */
	multianewarray(0xc5),

	/**
	 * Create new object.
	 * <p>
	 * {@code STACK ... -> ..., objectref }
	 */
	new_(0xbb, "u2"),

	/**
	 * Create new array.
	 * <p>
	 * {@code STACK ..., count -> ..., arrayref }
	 */
	newarray(0xbc),
	/**
	 * Do nothing.
	 * <p>
	 * {@code STACK No change }
	 */
	nop(0x0),
	/**
	 * Pop the top operand stack value.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	pop(0x57, ""),
	/**
	 * Pop the top one or two operand stack values.
	 * <p>
	 * {@code STACK Form 1: ..., value2, value1 -> ... where each of value1 and value2 is a value of a category 1 computational type (§2.11.1). Form 2: ..., value -> ... where value is a value of a category 2 computational type (§2.11.1). }
	 */
	pop2(0x58),
	/**
	 * Set field in object.
	 * <p>
	 * {@code STACK ..., objectref, value -> ... }
	 */
	putfield(0xb5),
	/**
	 * Set static field in class.
	 * <p>
	 * {@code STACK ..., value -> ... }
	 */
	putstatic(0xb3),
	/**
	 * Return from subroutine.
	 * <p>
	 * {@code STACK No change }
	 */
	ret(0xa9),
	/**
	 * Return void from method.
	 * <p>
	 * {@code STACK ... -> [empty] }
	 */
	return_(0xb1),
	/**
	 * Load short from array.
	 * <p>
	 * {@code STACK ..., arrayref, index -> ..., value }
	 */
	saload(0x35),
	/**
	 * Store into short array.
	 * <p>
	 * {@code STACK ..., arrayref, index, value -> ... }
	 */
	sastore(0x56),
	/**
	 * Push short.
	 * <p>
	 * {@code STACK ... -> ..., value }
	 */
	sipush(0x11),
	/**
	 * Swap the top two operand stack values.
	 * <p>
	 * {@code STACK ..., value2, value1 -> ..., value1, value2 }
	 */
	swap(0x5f),
	/**
	 * Access jump table by index and jump.
	 * <p>
	 * {@code STACK ..., index -> ... }
	 */
	tableswitch(0xaa),
	/**
	 * Extend local variable index by additional bytes.
	 * <p>
	 * {@code STACK Same as modified instruction }
	 */
	wide(0xc4);

	private final int opcode;
	private final String format;

	private Opcode(int opcode) {
		// After you check this instruction please use other constructor
		this(opcode, "NOT-TESTED-YET");
	}

	private Opcode(int opcode, String format) {
		this.opcode = opcode;
		this.format = format;
	}

	public int asMachineCode() {
		return opcode;
	}

	public String operands() {
		return format;
	}
}
