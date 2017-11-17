package farrout.convenenience.audio;

import com.dvsoft.libra.transcriber.stream.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioFileConverter
{
	private static final Logger logger = LoggerFactory.getLogger(AudioFileConverter.class);

	/**
	 * Converts an audio file to a desired format
	 *
	 * @param inFile       File to be converted
	 * @param targetFormat Desired AudioFormat
	 * @return Converted audio file
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws AudioConversionException
	 * @see 'https://docs.oracle.com/javase/tutorial/sound/converters.html'
	 */
	public static File convertAudioFile(File inFile, AudioFileFormat.Type targetType, AudioFormat targetFormat) throws IOException,
			UnsupportedAudioFileException, AudioConversionException
	{
		AudioFileFormat inFileFormat;

		AudioInputStream inFile =
				AudioSystem.getAudioInputStream(inFile);

		AudioFormat sourceFormat = inFile.getFormat();

		// query file type
		inFileFormat = AudioSystem.getAudioFileFormat(inFile);
		if (!sourceFormat.matches(targetFormat))
		{
			// inFile is not the correct format, so let's try to convert it.

			if (inFile.markSupported())
			{
				inFile.reset(); // rewind
			}

			if (AudioSystem.isFileTypeSupported(targetType, inFile) && 
			AudioSystem.isConversionSupported(targetFormat,	sourceFormat))
			{
				AudioInputStream convertedStream = AudioSystem.getAudioInputStream(targetFormat, inFile);

				//Create temporary file
				File outFile = StreamUtil.stream2file(convertedStream);

				// Write the AudioInputStream to the output file.
				AudioSystem.write(convertedStream,
						targetType, outFile);

				logger.debug("Successfully made WAVE file, "
						+ outFile.getPath() + ", from "
						+ inFileFormat.getType() + " file, " +
						inFile.getPath() + ".");

				inFile.close();
				convertedStream.close();
				return outFile; // All done now
			}
			else
			{
				throw new AudioConversionException("Conversion of " + inFile
						.getPath() + " is not currently supported by AudioSystem.");
			}
		}
		else
		{
			logger.debug("Input file {} is WAVE. Conversion is unnecessary.", inFile.getPath());
			return inFile;
		}
	}

	public static class AudioConversionException extends Exception
	{
		public AudioConversionException(String message)
		{
			super(message);
		}
	}
}
