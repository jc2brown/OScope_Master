package ca.jc2brown.arduino.oscope2.gui.controller;

import java.util.Properties;

import ca.jc2brown.arduino.oscope2.gui.form.DisplayForm;
import ca.jc2brown.arduino.oscope2.gui.form.Screen;


public class DisplayController extends Controller<DisplayForm> {
	
	private static final String PROP_TIME_DIVS = 			 "display.time.divs";
	private static final String PROP_TIME_TICKS_PER_DIV =	 "display.time.ticksPerDiv";
	private static final String PROP_TIME_TICK_LENGTH =		 "display.time.tickLength";
	private static final String PROP_VOLTAGE_DIVS =			 "display.voltage.divs";
	private static final String PROP_VOLTAGE_TICKS_PER_DIV = "display.voltage.ticksPerDiv";	
	private static final String PROP_VOLTAGE_TICK_LENGTH =	 "display.voltage.tickLength";
	
	
	public ChannelController ch1Ctl;
	public ChannelController ch2Ctl;
	
	
	private Screen screen;
	
    int timeDivisions;
    int timeTicksPerDiv;
    int timeTickLength;
    
    int voltageDivisions;
    int voltageTicksPerDiv;
    int voltageTickLength;
		
    
    public DisplayController() {
    	super();
    }
    
    
	public void setForm(DisplayForm form) {
		this.form = form;	
	}
	
    public void setScreen(Screen screen) {
    	this.screen = screen;
    }
    
    
	public void readForm() { 
		timeDivisions = 	 1 + (int) form.k_timeDivisions.value();
		timeTicksPerDiv = 	 1 + (int) form.k_timeTicks.value();
		timeTickLength = 	     (int) form.k_timeTickLength.value();
		voltageDivisions = 	 1 + (int) form.k_voltageDivisions.value();
		voltageTicksPerDiv = 1 + (int) form.k_voltageTicks.value();
		voltageTickLength =  	 (int) form.k_voltageTickLength.value();
	}
	
	public void updateForm() {
		form.k_timeDivisions. setValue(    	timeDivisions - 1 	 	);
		form.k_timeTicks.setValue( 			timeTicksPerDiv - 1	 	);
		form.k_timeTickLength.setValue( 	timeTickLength		 	);
		form.k_voltageDivisions.setValue(  	voltageDivisions - 1 	);
		form.k_voltageTicks.setValue( 		voltageTicksPerDiv - 1	);
		form.k_voltageTickLength.setValue( 	voltageTickLength		);
		
		updateScreen();
	}
	
	
	public void updateScreen() {
		screen.timeDivisions = timeDivisions;
		screen.timeTicksPerDiv = timeTicksPerDiv;
		screen.timeTickLength = timeTickLength;
		screen.voltageDivisions = voltageDivisions;
		screen.voltageTicksPerDiv = voltageTicksPerDiv;
		screen.voltageTickLength = voltageTickLength;
		
		screen.l_ch1USecsPerDiv.setValue( "CH1: " + unifyU(ch1Ctl.uSecsPerDivision) );
		screen.l_ch2USecsPerDiv.setValue( "CH2: " + unifyU(ch2Ctl.uSecsPerDivision) );
		
		screen.l_ch1MVoltsPerDiv.setValue( "CH1: " + unifyM(ch1Ctl.mVoltsPerDivision) + "V/DIV + " + unifyM(ch1Ctl.voltageOffset * ch1Ctl.mVoltsPerDivision) + "V" );		
		screen.l_ch2MVoltsPerDiv.setValue( "CH2: " + unifyM(ch2Ctl.mVoltsPerDivision) + "V/DIV + " + unifyM(ch2Ctl.voltageOffset * ch2Ctl.mVoltsPerDivision) + "V" );
		
	}

	private String unifyM(double millis) {
		return unifyU(millis * 1e3);
	}
		
		
	private String unifyU(double micros) {
		int sign = ( micros < 0 ? -1 : 1 );
		double base = Math.abs(micros);
		String units = " ";
		if ( micros < 1e3 ) {
			units = " u";
		} else if ( micros < 1e6 ) {
			base /= 1e3;
			units = " m";
		} else {
			base /= 1e6;
		} 
		base = Math.round(base * 100) / 100;
		return sign * base + units;
	}
		

	public void loadForm(Properties properties) {
		
		
		timeDivisions = 	 new Integer(properties.getProperty(PROP_TIME_DIVS));
		timeTicksPerDiv = 	 new Integer(properties.getProperty(PROP_TIME_TICKS_PER_DIV));
		timeTickLength = 	 new Integer(properties.getProperty(PROP_TIME_TICK_LENGTH));
		voltageDivisions = 	 new Integer(properties.getProperty(PROP_VOLTAGE_DIVS));
		voltageTicksPerDiv = new Integer(properties.getProperty(PROP_VOLTAGE_TICKS_PER_DIV));
		voltageTickLength =  new Integer(properties.getProperty(PROP_VOLTAGE_TICK_LENGTH));
		
		updateForm();
		
	}


	public Properties saveForm() {
		Properties properties = new Properties();
		
		readForm();
		
		properties.put(PROP_TIME_DIVS, 				new Integer(timeDivisions).toString());
		properties.put(PROP_TIME_TICKS_PER_DIV, 	new Integer(timeTicksPerDiv).toString());
		properties.put(PROP_TIME_TICK_LENGTH, 		new Integer(timeTickLength).toString());
		properties.put(PROP_VOLTAGE_DIVS, 			new Integer(voltageDivisions).toString());
		properties.put(PROP_VOLTAGE_TICKS_PER_DIV, 	new Integer(voltageTicksPerDiv).toString());
		properties.put(PROP_VOLTAGE_TICK_LENGTH, 	new Integer(voltageTickLength).toString());
		return properties;
	}
    
}

