package ca.jc2brown.arduino.oscope2.model;

import ca.jc2brown.arduino.oscope2.model.fragment.FragmentType;
import processing.serial.Serial;

public class Header {
	public long magic;
	public long pin;
	public FragmentType type;
	public long numUsedBits;
	public int numDataBytes;
	public long numSamplesPerValue;
	public long startMicros;
	public long endMicros;
    
    
    public Header() {
    	numDataBytes = 0;
    }
    
    public boolean read(Serial port) {
    	int t;
    	magic = 123;//port.read();
    	if ( magic != 123 ) {
    		System.err.println("Something went wrong!! magic=" + magic);
    	}
    	pin = port.read();
    	if ( pin == 123 ) {
    		pin = port.read();
    	}
    	t = port.read();
    	switch ( t ) {
    		case 1: 
    			type = FragmentType.DIGITAL_SEQUENCE;
    			break;
    		case 2: 
    			type = FragmentType.DIGITAL_VD;
    			break;
    		case 3: 
    			type = FragmentType.DIGITAL_VDF;
    			break;
    		case 4: 
    			type = FragmentType.ANALOG_SEQUENCE;
    			break;
    		case 5: 
    			type = FragmentType.ANALOG_VD;
    			break;
    		case 6: 
    			type = FragmentType.ANALOG_VDF;
    			break;
    		default:
    			return false;
    	}
    	
    	numUsedBits = port.read();
    	
    	numDataBytes = port.read();
    	numDataBytes |= port.read() << 8;
    	
    	numSamplesPerValue = port.read();
    	numSamplesPerValue |= port.read() << 8;
    	
    	endMicros = 0;
    	endMicros |= port.read();
    	endMicros |= port.read() << 8;
    	endMicros |= port.read() << 16;
    	endMicros |= port.read() << 24;
    	
    	startMicros = 0;
    	startMicros |= port.read();
    	startMicros |= port.read() << 8;
    	startMicros |= port.read() << 16;
    	startMicros |= port.read() << 24;
    	
    	return true;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(magic);
    	sb.append("\t");
    	sb.append(pin);
    	sb.append("\t");
    	sb.append(type);
    	sb.append("\t");
    	sb.append(numUsedBits);
    	sb.append("\n");

    	sb.append("    ");
    	sb.append(numDataBytes);
    	sb.append("\t");
    	sb.append("    ");
    	sb.append(numSamplesPerValue);
    	sb.append("\n");
    	
    	sb.append("\t");
    	sb.append("    ");
    	sb.append(startMicros);
    	sb.append("\n");

    	sb.append("\t");
    	sb.append("    ");
    	sb.append(endMicros);
    	sb.append("\n");
    	
    	return sb.toString();
    }
    
}
