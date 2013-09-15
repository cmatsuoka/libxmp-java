/*
 * Example mod player using libxmp.jar.
 *  
 * This code is in the public domain.
 */

package com.example.player;

import java.nio.ByteBuffer;

import javax.sound.sampled.*;

import org.helllabs.java.libxmp.Xmp;


public class AudioPlay {
	SourceDataLine line;
	byte[] myBuffer;

	public AudioPlay(int freq) throws LineUnavailableException {
		AudioFormat format = new AudioFormat(freq, 16, 2, true, false);
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		if (!AudioSystem.isLineSupported(info)){
			System.out.println("Line matching " + info + " is not supported.");
			throw new LineUnavailableException();
		}

		line = (SourceDataLine)AudioSystem.getLine(info);
		line.open(format);  
		line.start();
		
		myBuffer = new byte[Xmp.MAX_FRAMESIZE];
	}

	@Override
	protected void finalize() {
		close();
	}
	
	public void play(ByteBuffer buffer, int bufferSize) {
		
		buffer.clear();
		buffer.get(myBuffer, 0, bufferSize);
		line.write(myBuffer, 0, bufferSize);
	}
	
	public void close() {
		line.drain();                                         
		line.close();
	}
}
