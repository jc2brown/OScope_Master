package ca.jc2brown.arduino.oscope2.model.fragment;

import processing.serial.Serial;


public class DigitalSequenceFragment extends Fragment {
	private static final long serialVersionUID = 1L;
	
	
	public DigitalSequenceFragment() {
		super();	
		maxInputValue = 1;
	}

	
	public void read(Serial port, int numBytes) {
		int bite;
		int bit;
		
    	clear();
    	for ( int i = 0; i < numBytes; i ++ ) {
    		bite = port.read();
    		for ( int j = 0; j < 8; j++ ) {
    			bit = (bite & 0x80) >> 7;
    			addLast( bit / maxInputValue );
    			bite <<= 1;
    		}
    	}
	}
	
	
}
