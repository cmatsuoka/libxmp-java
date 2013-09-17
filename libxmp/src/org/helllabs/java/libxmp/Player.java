/*
 * Java API for libxmp
 * Copyright (C) 2013 Claudio Matsuoka
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.helllabs.java.libxmp;


public class Player {

	private final int samplingRate;
	private final int mode;
	private final long ctx;
	private Module mod;
	
	public Player() {
		this(44100);
	}
	
	public Player(int samplingRate) {
		this(samplingRate, 0);
	}	

	public Player(int samplingRate, int mode) {
		this.samplingRate = samplingRate;
		this.mode = mode;
		ctx = Xmp.createContext();
	}		

	@Override
	protected void finalize() {
		Xmp.freeContext(ctx);
	}
	
	long getContext() {
		return ctx;
	}
	
	public void start() {
		final int code = Xmp.startPlayer(ctx, samplingRate, mode);
		switch (code) {
		case -Xmp.ERROR_INTERNAL:
			throw new RuntimeException(Xmp.errorString[-code]);
		case -Xmp.ERROR_INVALID:
			throw new IllegalArgumentException("Invalid sampling rate " + samplingRate + "Hz");
		case -Xmp.ERROR_SYSTEM:
			break;
			
		// What do do if no module loaded?
			
			
			
		}
		System.out.println("code=" + code);
	}
	
	public void end() {
		Xmp.endPlayer(ctx);
	}
	
	public boolean playFrame() {
		return Xmp.playFrame(ctx) == 0;
	}

	public FrameInfo getFrameInfo(FrameInfo info) {
		if (info == null)
			info = new FrameInfo();
		Xmp.getFrameInfo(ctx, info);
		return info;
	}
	
	public FrameInfo getFrameInfo() {
		return getFrameInfo(null);
	}

	
	public ModuleInfo getModuleInfo(ModuleInfo info) {
		if (info == null)
			info = new ModuleInfo();
		Xmp.getModuleInfo(ctx, info);
		info.mod = mod;
		return info;
	}
	
	public ModuleInfo getModuleInfo() {
		return getModuleInfo(null);
	}

}
