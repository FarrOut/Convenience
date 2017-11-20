
package farrout.convenience.file.stream;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class StreamTest
{
	@Before
	public void setUp() throws Exception
	{

	}

	@Test
	public void testCreateTemporaryFile() throws Exception
	{
		InputStream stream = Mockito.mock(InputStream.class);
		File file = StreamUtil.stream2file(stream);

		assertEquals("tempFile", file.getName());
	}
}
