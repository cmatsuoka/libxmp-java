package com.example.player;

import java.io.IOException;

import org.helllabs.java.libxmp.FrameInfo;
import org.helllabs.java.libxmp.Module;
import org.helllabs.java.libxmp.TestInfo;
import org.helllabs.java.libxmp.Xmp;


public class Player {
	int oldPos = -1;
	
	public void play(String path) {
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
			showInfo(fi);
		}
		xmp.endPlayer();
		xmp.releaseModule();

	}
	
	
	private void showInfo(final FrameInfo fi) {
		if (fi.pos != oldPos) {
			System.out.println("Pos: " + fi.pos + ", Pattern: " + fi.pattern);
			oldPos = fi.pos;
		}
	}
	
	public static void main(String[] args) {
		System.out.println("Libxmp player test");
		
		TestInfo info = new TestInfo();
		Xmp.testModule("/home/claudio/jarre.mod", info);
		System.out.println("Name=" + info.name);
		System.out.println("Type=" + info.type);
		
		Player player = new Player();
		player.play("/home/claudio/jarre.mod");
		
		System.out.println("End");
		
	}

}
