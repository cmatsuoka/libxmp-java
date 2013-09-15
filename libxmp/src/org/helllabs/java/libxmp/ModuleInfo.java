package org.helllabs.java.libxmp;

public class ModuleInfo {
	public byte md5[] = new byte[16];	/* MD5 message digest */
	int volBase;						/* Volume scale */
	Module mod;							/* Pointer to module data */
	String comment;						/* Comment text, if any */
	int numSequences;					/* Number of valid sequences */
	Sequence seqData[];					/* Pointer to sequence data */
}
