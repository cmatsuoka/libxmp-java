package org.helllabs.java.libxmp;

public class Instrument {
	public static class Map {
		byte ins;				/* Instrument number for each key */
		byte xpo;				/* Instrument transpose for each key */
	}
	
	public String name;			/* Instrument name */
	public int vol;				/* Instrument volume */
	public int nsm;				/* Number of samples */
	public int rls;				/* Release (fadeout) */
	public Envelope aei;		/* Amplitude envelope info */
	public Envelope pei;		/* Pan envelope info */
	public Envelope fei;		/* Frequency envelope info */
	
	public Map map[] = new Map[Xmp.MAX_KEYS];

	public SubInstrument[] sub;
}
