package ca.jc2brown.arduino.oscope2.model.fragment;

public enum FragmentType {
	DIGITAL_SEQUENCE(1),
	DIGITAL_VD(2),
	DIGITAL_VDF(3),
	ANALOG_SEQUENCE(4),
	ANALOG_VD(5),
	ANALOG_VDF(6);
	
	int value;
	
	FragmentType(int value) {
		this.value = value;
	}
}
