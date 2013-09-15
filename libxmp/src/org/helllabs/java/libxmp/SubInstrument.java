package org.helllabs.java.libxmp;

public class SubInstrument {
	public static final int NNA_CUT = 0;
	public static final int NNA_CONT = 1;
	public static final int NNA_OFF = 2;
	public static final int NNA_FADE = 3;
	
	public static final int DCT_OFF = 0;
	public static final int DCT_NOTE = 1;
	public static final int DCT_SMP = 2;
	public static final int DCT_INST = 3;
	
	public static final int DCA_CUT = NNA_CUT;
	public static final int DCA_OFF = NNA_OFF;
	public static final int DCA_FADE = NNA_FADE;
	
	public int vol;			/* Default volume */
	public int gvl;			/* Global volume */
	public int pan;			/* Pan */
	public int xpo;			/* Transpose */
	public int fin;			/* Finetune */
	public int vwf;			/* Vibrato waveform */
	public int vde;			/* Vibrato depth */
	public int vra;			/* Vibrato rate */
	public int vsw;			/* Vibrato sweep */
	public int rvv;			/* Random volume variation (IT) */
	public int sid;			/* Sample number */
	public int nna;			/* New note action */
	public int dct;			/* Duplicate check type */
	public int dca;			/* Duplicate check action */
	public int ifc;			/* Initial filter cutoff */
	public int ifr;			/* Initial filter resonance */
}
