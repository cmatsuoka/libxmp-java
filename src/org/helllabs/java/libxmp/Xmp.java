package org.helllabs.java.libxmp;

import java.io.IOException;

public class Xmp {
	
	// constants
	public final static int ERROR_INTERNAL = 2;
	public final static int ERROR_SYSTEM = 6;
	public final static int ERROR_INVALID = 7;

	private long ctx;

	private String[] error = {
		"No error",
		"End of module",
		"Internal error",
		"Unknown module format",
		"Can't load module",
		"Can't decompress module",
		"System error",
		"Invalid parameter"
	};

	// native methods
	protected native long xmpCreateContext();
	protected native void xmpFreeContext(long ctx);
	protected native int xmpLoadModule(long ctx, String path);
	protected native static int xmpTestModule(String path, TestInfo info);
	protected native int xmpReleaseModule(long ctx);
	protected native int xmpStartPlayer(long ctx, int freq, int mode);
	protected native int xmpPlayFrame(long ctx);
	protected native void xmpGetFrameInfo(long ctx, FrameInfo info);
	protected native void xmpEndPlayer(long ctx);
	protected native int getErrno();


	public Xmp() {
		ctx = xmpCreateContext();
	}
	
	@Override
	protected void finalize() {
		xmpFreeContext(ctx);
	}

	public Module xmpLoadModule(String path) throws IOException {
		final int code = xmpLoadModule(ctx, path);
		if (code < 0) {
			throw new IOException(error[-code]);
		}
		
		return new Module();
	}
	
	public static boolean testModule(String path, TestInfo info) {
		if (info == null)
			info = new TestInfo();
		final int code = xmpTestModule(path, info);
		return code == 0;
	}
	
	public static boolean testModule(String path) {
		return testModule(path, null);
	}

	public void releaseModule() {
		xmpReleaseModule(ctx);
	}
	
	public void startPlayer(int freq, int mode) {
		final int code = xmpStartPlayer(ctx, freq, mode);
		switch (code) {
		case -ERROR_INTERNAL:
			throw new RuntimeException(error[-code]);
		case -ERROR_INVALID:
			throw new IllegalArgumentException("Invalid sampling rate " + freq + "Hz");
		case -ERROR_SYSTEM:
			break;
			
			
			
		}
	} 
	
	public void startPlayer(int freq) {
		startPlayer(freq, 0);
	}
	
	public boolean playFrame() {
		return xmpPlayFrame(ctx) == 0;
	}

	public FrameInfo getFrameInfo(FrameInfo info) {
		if (info == null)
			info = new FrameInfo();
		xmpGetFrameInfo(ctx, info);
		return info;
	}
	
	public FrameInfo getFrameInfo() {
		return getFrameInfo(null);
	}
	
	public void endPlayer() {
		xmpEndPlayer(ctx);
	}

	static {
		System.loadLibrary("xmp");
	}


}
