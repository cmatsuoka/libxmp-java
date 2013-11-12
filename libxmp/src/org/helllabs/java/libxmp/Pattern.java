package org.helllabs.java.libxmp;

public class Pattern {
	private Module mod;
	private int rows;
	private int[] index;
	
	public Pattern(Module mod) {
		this.mod = mod;
		this.index = new int[mod.chn];
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getChannels() {
		return mod.chn;
	}
	
	public int getIndex(int num) {
		return index[num];
	}
	
	public Track getTrack(int num) {
		return mod.getTrack(index[num]);
	}
	
	public Event getEvent(int row, int chn) {
		return getEvent(row, chn, null);
	}
	
	public Event getEvent(int row, int chn, Event event) {
		final Track track = mod.getTrack(index[chn]);
		return track.getEvent(row, event);
	}
}
