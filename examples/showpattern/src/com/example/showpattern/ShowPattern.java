/*
 * Example mod player using Libxmp.jar.
 *  
 * This code is in the public domain.
 */

package com.example.showpattern;

import java.io.IOException;

import org.helllabs.libxmp.Module.Event;
import org.helllabs.libxmp.Module;
import org.helllabs.libxmp.Player;
import org.helllabs.libxmp.Module.Pattern;


public class ShowPattern {

	private void showHeader(Module module) {
		System.out.println("Module name  : " + module.getName());
		System.out.println("Module type  : " + module.getType());
		System.out.println("Module length: " + module.getLength() + " patterns");
	}
	
	private String formatNote(int n) {
		final String[] note = {
			"C ", "C#", "D ", "D#", "E ", "F ",
			"F#", "G ", "G#", "A ", "A#", "B "
		};
		if (n++ == 0)
			return "---";
		else
			return String.format("%s%d", note[n % 12], n / 12);
	}
	
	private String formatNum(int n) {
		if (n == 0)
			return "--";
		else
			return String.format("%02X", n);
	}
	
	private void showEvent(Event e) {
		System.out.printf(" | %s %s %s", formatNote(e.note), formatNum(e.ins), formatNum(e.vol));
	}

	private void run(String path, int num) throws IOException {
		Player player = new Player();
		Module module = player.loadModule(path);
		
		showHeader(module);
		
		Pattern pattern = module.getPattern(num);
		Event event = new Event();
		
		System.out.println("\nPATTERN " + num);
		
		for (int row = 0; row < pattern.getRows(); row++) {
			System.out.printf("%02X", row);
			for (int chn = 0; chn < pattern.getChannels(); chn++) {
				pattern.getEvent(row, chn, event);
				showEvent(event);
			}
			System.out.println();
		}
		

	}

	public static void main(String[] args) {
		System.out.println("Libxmp show pattern test");
		
		if (args.length == 0) {
			System.out.println("Usage: showpattern <module> <num>");
			System.exit(0);
		}

		String path = args[0];
		int num = Integer.parseInt(args[1]);

		try {
			Module.test(path);
			ShowPattern app = new ShowPattern();
			app.run(path, num);
		} catch (IOException e) {
			System.out.print("\nError: " + path + ": " + e.getMessage());
		}
	}

}
