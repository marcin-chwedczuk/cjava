package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fixture_Annotation {
	int intField();
	String stringField();

	boolean boolFieldWithDefault() default false;

	String[] stringArrayField();

	int[] intArrayFieldWithDefault() default { 1, 2, 3 };
}
