package pl.marcinchwedczuk.cjava.decompiler;

import org.junit.Before;
import org.junit.Test;
import pl.marcinchwedczuk.cjava.ast.ClassDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.FieldDeclarationAst;
import pl.marcinchwedczuk.cjava.ast.Visibility;
import pl.marcinchwedczuk.cjava.ast.annotation.AnnotationAst;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithThreeFields;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class FieldDecompilerTests extends BaseDecompilerTests {
	private ClassDeclarationAst classDeclAst;

	@Before
	public void setUp() throws Exception {
		classDeclAst = decompileWithoutCode(Fixture_ClassWithThreeFields.class);
	}

	@Test
	public void canDecompileStaticField() throws Exception {
		// public static String field1;

		FieldDeclarationAst field1 = findFieldByName("field1");

		assertThat(field1.getFieldType().asSourceCodeString())
			.isEqualTo("java.lang.String");

		assertThat(field1.getVisibility())
				.isEqualTo(Visibility.PUBLIC);

		assertThat(field1.isStatic()).isTrue();
		assertThat(field1.isFinal()).isFalse();
		assertThat(field1.isTransient()).isFalse();
		assertThat(field1.isVolatile()).isFalse();
	}

	@Test
	public void canDecompileFinalField() throws Exception {
		// protected final Boolean field3;
		FieldDeclarationAst field3 = findFieldByName("field3");

		assertThat(field3.getFieldType().asSourceCodeString())
				.isEqualTo("java.lang.Boolean");

		assertThat(field3.getVisibility())
				.isEqualTo(Visibility.PROTECTED);

		assertThat(field3.isStatic()).isFalse();
		assertThat(field3.isFinal()).isTrue();
		assertThat(field3.isTransient()).isFalse();
		assertThat(field3.isVolatile()).isFalse();
	}

	@Test
	public void canDecompileFieldAnnotations() throws Exception {
		// @Fixture_EmptyAnnotation
		// private int field2;

		FieldDeclarationAst field2 = findFieldByName("field2");

		assertThat(field2.getAnnotations())
				.hasSize(1);

		AnnotationAst emptyAnnotation = field2.getAnnotations().get(0);
		assertThat(emptyAnnotation.getAnnotationType().asSourceCodeString())
				.contains("Fixture_EmptyAnnotation");
	}

	private FieldDeclarationAst findFieldByName(String fieldName) {
		Optional<FieldDeclarationAst> field = classDeclAst.getFields().stream()
				.filter(ast -> ast.getFieldName().equals(fieldName))
				.findFirst();

		assertThat(field)
				.as("field with name '" + fieldName + "'")
				.isPresent();

		return field.get();
	}
}
