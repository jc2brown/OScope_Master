package ca.jc2brown.arduino.oscope2.model;


import ca.jc2brown.arduino.oscope2.model.fragment.AnalogSequenceFragment;
import ca.jc2brown.arduino.oscope2.model.fragment.DigitalSequenceFragment;
import ca.jc2brown.arduino.oscope2.model.fragment.Fragment;
import processing.serial.Serial;

public class Packet {
	
	public Header header;
    public Fragment payload;
    
    public Packet() {
    	header = new Header();
    }
    
    public boolean read(Serial port) {
    	boolean goodHeader = header.read(port);
    	if ( ! goodHeader ) {
    		return false;
    	}
    	switch ( header.type ) {
			case DIGITAL_SEQUENCE:
				payload = new DigitalSequenceFragment();
				break;
			case DIGITAL_VD:
				//payload = new DigitalVDFragment();
				break;
			case DIGITAL_VDF:
				//payload = new DigitalVDFFragment();
				break;
			case ANALOG_SEQUENCE:
				payload = new AnalogSequenceFragment();
				break;
			case ANALOG_VD:
				//payload = new AnalogVDFragment();
				break;
			case ANALOG_VDF:
				//payload = new AnalogVDFFragment();
				break;
			default:
				return false;
    	}
    	payload.read(port, header.numDataBytes);
    	payload.duration = - header.endMicros + header.startMicros;
    	//.out.println(payload.duration);
    	return true;
    }
    
/*
    public OutputFragment getOutputFragment() {
    	OutputFragment outputFragment = new OutputFragment(payload);
    	outputFragment.startMicros = header.startMicros;    	
    	outputFragment.endMicros = header.endMicros;
    	return outputFragment;
    }
    */
    public String toString() {
    	int i = 0;
    	StringBuilder sb = new StringBuilder();
    	sb.append(header);
    	for (Double b : payload) {
    		sb.append(b);
    		i += 1;
    		if ( i == 4 ) {
    			sb.append("\n");
    			i = 0;
    		}
    	}
    	return sb.toString();
    }
    
}
