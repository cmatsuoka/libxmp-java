package com.example.player;

import java.io.IOException;

import org.helllabs.java.libxmp.FrameInfo;
import org.helllabs.java.libxmp.Module;
import org.helllabs.java.libxmp.TestInfo;
import org.helllabs.java.libxmp.Xmp;


public class Player {
	public static void main(String[] args) {
		System.out.println("Libxmp player test");
		
		TestInfo info = new TestInfo();
		Xmp.testModule("/home/claudio/jarre.mod", info);
		System.out.println("Name=" + info.name);
		System.out.println("Type=" + info.type);
		
		Xmp xmp = new Xmp();
		Module mod;
		try {
			mod = xmp.loadModule("/home/claudio/jarre.mod");
			System.out.println(mod.name + " (" + mod.type + ")");
			System.out.println("Patterns: " + mod.pat + "  Length: " + mod.len);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FrameInfo fi = new FrameInfo();
		
		xmp.startPlayer(44100);
		while (xmp.playFrame()) {
			xmp.getFrameInfo(fi);
			if (fi.loopCount > 0)
				break;
		}
		xmp.endPlayer();
		xmp.releaseModule();
		
		System.out.println("End");
		
	}
}
