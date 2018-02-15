package pl.marcinchwedczuk.cjava.bytecode;

import org.junit.Test;
import pl.marcinchwedczuk.cjava.bytecode.attribute.CodeAttribute;
import pl.marcinchwedczuk.cjava.bytecode.method.MethodInfo;
import pl.marcinchwedczuk.cjava.bytecode.test.fixtures.Fixture_ClassWithCode;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class JavaClassFileReader_CodeAttributeTests extends BaseJavaClassFileReaderTests {
	@Test
	public void canReadCodeAttribute() throws Exception {
		JavaClassFile classWithCode = load(Fixture_ClassWithCode.class);

		MethodInfo method = classWithCode
				.getClassMethods()
				.get(1);

		CodeAttribute codeAttribute = method
				.getAttributes()
				.findCodeAttribute()
				.get();

		System.out.println(Arrays.toString(codeAttribute.getCode().getBytes()));

		// stack=4, locals=6, args_size=3
		assertThat(codeAttribute.getCode().getMaxStack())
				.isEqualTo(4);

		assertThat(codeAttribute.getCode().getMaxLocals())
				.isEqualTo(6);

		assertThat(codeAttribute.getCode().getBytes())
				.hasSize(92);

		/*Exception table:
         from    to  target type
            26    45    48   Class java/lang/Exception
            52    87    88   Class java/io/IOException
            52    87    88   Class java/lang/RuntimeException*/
		assertThat(codeAttribute.getExceptionTable().size())
				.isEqualTo(3);

		CodeAttribute.ExceptionTableEntry entry =
				codeAttribute.getExceptionTable().get(0);
		assertThat(entry.getStartPC()).isEqualTo(26);
		assertThat(entry.getEndPC()).isEqualTo(45);
		assertThat(entry.getHandlerPC()).isEqualTo(48);
		assertThat(entry.getExceptionClassIndexOptional().asInteger())
				.isNotEqualTo(0);

		assertThat(codeAttribute.getAttributes().getCount())
				.isEqualTo(3);
	}
}
