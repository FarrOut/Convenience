package farrout.convenience.file.stream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VirtualFile
{
	private static final Logger logger = LoggerFactory.getLogger(VirtualFile.class);

	private static final String PREFIX = "stream2file";
	private static final String SUFFIX = ".tmp";

	public static File stream2file(InputStream in, String directory) throws IOException
	{
		File dir = new File(directory);

		if (!dir.exists())
		{
			logger.trace("Directory '{}' does not exist; creating...", directory);
		}

		dir.mkdir();

		final File tempFile = File.createTempFile(PREFIX, SUFFIX, directory == null ? null : dir);
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile))
		{
			IOUtils.copy(in, out);
		}
		return tempFile;
	}

	public static File stream2file(InputStream in) throws IOException
	{
		return stream2file(in, null);
	}
}
