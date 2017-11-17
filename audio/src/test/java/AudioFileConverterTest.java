package com.dvsoft.libra.transcriber.audio;

import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class AudioFileConverterTest
{


	@Before
	public void setUp() throws Exception
	{

	}

	@Test
	public void testAudioFilePreparation() throws Exception
	{
//		AudioFileConverter.prepareFile(file);

	}

	@Test
	public void testFormatConversion_8k_to_16k() throws Exception
	{
		AudioFormat targetFormat = new AudioFormat((float) 16000, 16, 1, true, false);

		File inFile = new File(getClass().getClassLoader().getResource("samples\"" + OSR_uk_000_0020_8k.wav").getFile());
		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(AudioFileConverter.convertAudioFile(inFile, AudioSystem.Type.WAVE, targetFormat));
		AudioFormat format = fileFormat.getFormat();

		for (Map.Entry<String, Object> entry : format.properties().entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}

		assertEquals((float) 8000, AudioSystem.getAudioFileFormat(inFile).getFormat().getSampleRate());
		assertEquals(16, AudioSystem.getAudioFileFormat(inFile).getFormat().getSampleSizeInBits());

		assertEquals((float) 16000, format.getSampleRate());
		assertEquals(16, format.getSampleSizeInBits());
	}

	@Test
	public void testFormatConversion_44100_to_16k() throws Exception
	{
		AudioFormat targetFormat = new AudioFormat((float) 16000, 16, 1, true, false);

		File inFile = new File(getClass().getClassLoader().getResource("samples\"" +"Police_Chatter.wav").getFile());
		AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(AudioFileConverter.convertAudioFile(inFile, AudioSystem.Type.WAVE, targetFormat));
		AudioFormat format = fileFormat.getFormat();

		for (Map.Entry<String, Object> entry : format.properties().entrySet())
		{
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}

		assertEquals((float) 44100, AudioSystem.getAudioFileFormat(inFile).getFormat().getSampleRate());
		assertEquals(16, AudioSystem.getAudioFileFormat(inFile).getFormat().getSampleSizeInBits());

		assertEquals((float) 16000, format.getSampleRate());
		assertEquals(16, format.getSampleSizeInBits());
	}
}
