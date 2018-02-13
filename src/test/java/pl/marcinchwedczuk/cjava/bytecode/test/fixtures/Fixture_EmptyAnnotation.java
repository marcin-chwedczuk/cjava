package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Fixture_EmptyAnnotation { }
