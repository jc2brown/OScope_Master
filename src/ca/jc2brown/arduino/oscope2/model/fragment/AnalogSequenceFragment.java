package ca.jc2brown.arduino.oscope2.model.fragment;

import processing.serial.Serial;


public class AnalogSequenceFragment extends Fragment {
	private static final long serialVersionUID = 1L;
	
	
	public AnalogSequenceFragment() {
		super();	
		maxInputValue = 255;
	}

	
	public void read(Serial port, int numBytes) {
		int bite;
		
    	clear();
    	for ( int i = 0; i < numBytes; i ++ ) {
    		bite = port.read();
    		addFirst( bite / maxInputValue );
    	}
	}
	
	
}
