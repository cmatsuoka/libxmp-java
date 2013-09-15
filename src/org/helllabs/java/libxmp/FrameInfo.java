package org.helllabs.java.libxmp;

public class FrameInfo {
	public int pos;				/* Current position */
	public int pattern;			/* Current pattern */
	public int row;				/* Current row in pattern */
	public int numRows;			/* Number of rows in current pattern */
	public int frame;			/* Current frame */
	public int speed;			/* Current replay speed */
	public int bpm;				/* Current bpm */
	public int time;			/* Current module time in ms */
	public int totalTime;		/* Estimated replay time in ms*/
	public int frameTime;		/* Frame replay time in us */
	public int bufferSize;		/* Used buffer size */
	public int totalSize;		/* Total buffer size */
	public int volume;			/* Current master volume */
	public int loopCount;		/* Loop counter */
	public int virtChannels;	/* Number of virtual channels */
	public int virtUsed;		/* Used virtual channels */
	public int sequence;		/* Current sequence */
}
