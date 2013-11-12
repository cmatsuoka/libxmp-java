package org.helllabs.java.libxmp;

import java.nio.ByteBuffer;

public class Track {
	public int rows;
	public ByteBuffer data;
	
	public Event getEvent(int row, Event event) {
		int index = row * 8;
		
		if (event == null)
			event = new Event();
		
		event.note = data.get(index++);
		event.ins = data.get(index++);
		event.vol = data.get(index++);
		event.fxt = data.get(index++);
		event.fxp = data.get(index++);
		event.f2t = data.get(index++);
		event.f2p = data.get(index++);
		event._flag = data.get(index);
		
		return event;
	}
}
