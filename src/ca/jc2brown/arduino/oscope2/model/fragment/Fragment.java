package ca.jc2brown.arduino.oscope2.model.fragment;
import java.util.LinkedList;

import processing.serial.Serial;


public abstract class Fragment extends LinkedList<Double> {
	private static final long serialVersionUID = 1L;
	
	public long duration;
	protected double maxInputValue;
	
	public Fragment() {
		super();	
	}
	
	public double uSecsPerSample() {
		if ( isEmpty() ) {
			return 0;
		}
		return duration / size();
	}
	
	
	public abstract void read(Serial port, int numBytes);
	
	
}
