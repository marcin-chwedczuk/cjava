package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fixture_EmptyAnnotation { }
