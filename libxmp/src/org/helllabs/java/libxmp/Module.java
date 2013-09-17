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

import java.io.IOException;

public class Module {
	private final Player player;	// Xmp player
	private final long ctx;			// Xmp context
	
	public String name;		// Module title
	public String type;		// Module format
	public int pat;			// Number of patterns
	public int trk;			// Number of tracks
	public int chn;			// Tracks per pattern
	public int ins;			// Number of instruments
	public int smp;			// Number of samples
	public int spd;			// Initial speed
	public int bpm;			// Initial BPM
	public int len;			// Module length in patterns
	public int rst;			// Restart position
	public int gvl;			// Global volume

	private final Pattern xxp[];	// Patterns
	private final Track xxt[];		// Tracks
	private final Instrument xxi[];	// Instruments
	private final Sample xxs[];		// Samples
	private final Channel xxc[] = new Channel[Xmp.MAX_CHANNELS];	// Channel info
	private byte xxo[] = new byte[Xmp.MAX_MOD_LENGTH];				// Orders
	
	private OnFrameListener onFrameListener;
	
	public static class OnFrameListener {
		public void onFrame(FrameInfo fi) {
			// do nothing
		}
	}

	public Module(Player player, String path) throws IOException {
		
		this.player = player;
		this.ctx = player.getContext();
		
		final int code = Xmp.loadModule(ctx, path);
		if (code < 0) {
			throw new IOException(Xmp.errorString[-code]);
		}
		
		Xmp.getModData(ctx, this);
		this.xxp = new Pattern[this.pat];
		this.xxt = new Track[this.trk];
		this.xxi = new Instrument[this.ins];
		this.xxs = new Sample[this.smp];
		
		for (int i = 0; i < this.ins; i++) {
			this.xxi[i] = new Instrument();
			Xmp.getInstrumentData(ctx, this.xxi[i], i);
		}
	}
	
	@Override
	protected void finalize() {
		release();
	}

	public Module release() {
		Xmp.releaseModule(ctx);
		return this;
	}
	
	
	public static boolean test(String path, TestInfo info) {
		if (info == null)
			info = new TestInfo();
		final int code = Xmp.testModule(path, info);
		return code == 0;
	}
	
	public static boolean test(String path) {
		return test(path, null);
	}
	
	public Pattern getPattern(int num) {
		return num < pat ? xxp[num] : null;
	}
	
	public Track getTrack(int num) {
		return num < trk ? xxt[num] : null;
	}
	
	public Instrument getInstrument(int num) {
		return num < ins ? xxi[num] : null;
	}
	
	public Sample getSample(int num) {
		return num < smp ? xxs[num] : null;
	}
	
	public Channel getChannel(int num) {
		return num < Xmp.MAX_CHANNELS ? xxc[num] : null;
	}
	
	public int getPosition(int num) {
		return num < len ? xxo[num] : -1;
	}
	
	
	public void setOnFrameListener(OnFrameListener listener) {
		onFrameListener = listener;
	}
	
	public FrameInfo playFrame(FrameInfo fi) {		
		Xmp.playFrame(ctx);
		getFrameInfo(fi);
		
		if (onFrameListener != null)
			onFrameListener.onFrame(fi);
		
		return fi;
	}
	
	public void playBuffer() {
		
	}
	
	public void play() {
		play(false);
	}
	
	public void play(boolean loop) {
		FrameInfo fi = new FrameInfo();
		player.start();
		do {
			playFrame(fi);
		} while (loop || fi.loopCount == 0);
		player.end();
	}
	
	public FrameInfo getFrameInfo(FrameInfo info) {
		if (info == null)
			info = new FrameInfo();
		Xmp.getFrameInfo(ctx, info);
		return info;
	}
	
	public ModuleInfo getInfo(ModuleInfo info) {
		if (info == null)
			info = new ModuleInfo();
		Xmp.getModuleInfo(ctx, info);
		return info;
	}
}
