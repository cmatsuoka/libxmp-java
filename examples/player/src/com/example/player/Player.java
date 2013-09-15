/*
 * Example mod player using libxmp.jar.
 *  
 * This code is in the public domain.
 */

package com.example.player;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import org.helllabs.java.libxmp.*;


public class Player {
	private int oldPos = -1;

	private void showInfo(FrameInfo fi) {
		if (fi.pos != oldPos) {
			System.out.printf("Pos: %d, Pattern: %d\r", fi.pos, fi.pattern);
			oldPos = fi.pos;
		}
	}
	
	private void showHeader(Module mod) {
		System.out.println("Module name  : " + mod.name);
		System.out.println("Module type  : " + mod.type);
		System.out.println("Module length: " + mod.len + " patterns");
	}
	
	private void play(String path) throws LineUnavailableException {
		AudioPlay audio = new AudioPlay(44100);

		Xmp xmp = new Xmp();
		Module mod;
		try {
			mod = xmp.loadModule(path);
			showHeader(mod);
		} catch (IOException e) {
			e.printStackTrace();
		}

		FrameInfo fi = new FrameInfo();

		xmp.startPlayer(44100);
		while (xmp.playFrame()) {
			xmp.getFrameInfo(fi);
			if (fi.loopCount > 0)
				break;

			audio.play(fi.buffer,fi.bufferSize);
			showInfo(fi);
		}
		xmp.endPlayer();
		xmp.releaseModule();

	}

	public static void main(String[] args) {
		System.out.println("Libxmp player test");

		Player player = new Player();
		TestInfo ti = new TestInfo();

		for (String arg : args) {
			if (!Xmp.testModule(arg, ti))
				continue;

			System.out.println("Playing " + arg + "...");

			try {
				player.play(arg);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("\nEnd");

	}

}
