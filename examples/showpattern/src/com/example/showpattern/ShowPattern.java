/*
 * Example mod player using libxmp.jar.
 *  
 * This code is in the public domain.
 */

package com.example.showpattern;

import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import org.helllabs.java.libxmp.*;


public class ShowPattern {

	private void showHeader(Module mod) {
		System.out.println("Module name  : " + mod.name);
		System.out.println("Module type  : " + mod.type);
		System.out.println("Module length: " + mod.len + " patterns");
	}


	private void run(String path, int num) throws IOException {
		Module mod = new Module(path);
		
		Pattern pattern = mod.getPattern(num);
		Event event = new Event();
		
		for (int row = 0; row < pattern.getRows(); row++) {
			for (int chn = 0; chn < pattern.getChannels(); chn++) {
				pattern.getEvent(row, chn, event);
			}
		}
		

	}

	public static void main(String[] args) {
		System.out.println("Libxmp player test");

		String path = args[0];
		int num = Integer.parseInt(args[1]);

		ShowPattern app = new ShowPattern();

		try {
			if (Module.test(path)) {
				app.run(path, num);
			} else {
				System.out.println("Unknown format");
			}
		} catch (IOException e) {
			System.out.println(path + e);
		}
	}

}
