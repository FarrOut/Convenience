
package farrout.convenience.file.stream;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VirtualFileTest
{
	@Test
	public void testCreateTemporaryFile() throws Exception
	{
		final String filename = "Police_Chatter.wav";
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("samples/" + filename);

		String dir = ".\\temp";

		File file = StreamUtil.stream2file(stream, dir);

		stream.close();

		assertNotNull(file);
		assertTrue(file.exists());
	}
}
