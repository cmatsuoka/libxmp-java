package org.helllabs.java.libxmp;

public class Module {
	public String name;			/* Module title */
	public String type;			/* Module format */
	public int pat;				/* Number of patterns */
	public int trk;				/* Number of tracks */
	public int chn;				/* Tracks per pattern */
	public int ins;				/* Number of instruments */
	public int smp;				/* Number of samples */
	public int spd;				/* Initial speed */
	public int bpm;				/* Initial BPM */
	public int len;				/* Module length in patterns */
	public int rst;				/* Restart position */
	public int gvl;				/* Global volume */

	public Pattern xxp[];		/* Patterns */
	public Track xxt[];			/* Tracks */
	public Instrument xxi[];	/* Instruments */
	public Sample xxs[];		/* Samples */
	public Channel xxc[] = new Channel[64];				/* Channel info */
	public byte xxo[] = new byte[Xmp.MAX_MOD_LENGTH];	/* Orders */


}
