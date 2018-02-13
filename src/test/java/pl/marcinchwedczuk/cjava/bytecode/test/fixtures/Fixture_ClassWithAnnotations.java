package pl.marcinchwedczuk.cjava.bytecode.test.fixtures;

@Fixture_Annotation(
		// TODO: byteField = (byte)254,
		intField = 123,
		stringField = "foo",
		stringArrayField = {"foo", "bar"})
@Fixture_EmptyAnnotation
public class Fixture_ClassWithAnnotations {
}
