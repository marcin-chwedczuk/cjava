package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fixture_Annotation {
	// byte byteField();

	int intField();
	String stringField();

	boolean boolFieldWithDefault() default false;

	String[] stringArrayField();

	int[] intArrayFieldWithDefault() default { 1, 2, 3 };
}
