package ca.jc2brown.arduino.oscope2.gui.controller;

import java.util.Properties;

import ca.jc2brown.arduino.oscope2.gui.form.ChannelForm;
import ca.jc2brown.arduino.oscope2.model.fragment.Fragment;


public class ChannelController extends Controller<ChannelForm> {
	
	public static final int INPUT_MVOLTS_MAX = 5000;
	

	private static final String PROP_USEC_PER_DIV_INDEX = 	"channel.uSecPerDivIndex";
	private static final String PROP_USEC_PER_DIV = 		"channel.uSecPerDiv";
	private static final String PROP_TIME_OFFSET = 			"channel.timeOffset";
	private static final String PROP_MVOLT_PER_DIV_INDEX = 	"channel.mVoltPerDivIndex";
	private static final String PROP_MVOLT_PER_DIV = 		"channel.mVoltPerDiv";
	private static final String PROP_VOLTAGE_OFFSET = 		"channel.voltageOffset";
	private static final String PROP_INTENSITY = 			"channel.intensity";
	private static final String PROP_BRIGHTNESS = 			"channel.brightness";
	private static final String PROP_DATA_COLOURS = 		"channel.dataColours";
	private static final String PROP_PAUSE = 				"channel.pause";
	
	private static final String PROP_SPLIT = 				"`";
	
		
	// In microseconds
	public static double divisionTimes[] = {
		1e0, 2.5e0, 5e0,
		1e1, 2.5e1, 5e1,
		1e2, 2.5e2, 5e2,
		1e3, 2.5e3, 5e3,
		1e4, 2.5e4, 5e4,
		1e5, 2.5e5, 5e5
	};
	
	// In millivolts
	public static double divisionVolts[] = {
		1e0, 2.5e0, 5e0,
		1e1, 2.5e1, 5e1,
		1e2, 2.5e2, 5e2,
		1e3, 2.5e3, 5e3,
		1e4, 2.5e4, 5e4
	};
	
		
	public int uSecsPerDivisionIndex;
	public double uSecsPerDivision;
	public float timeOffset;

	public int mVoltsPerDivisionIndex;
	public double mVoltsPerDivision;
	public float voltageOffset;

	public int intensity;
	public int brightness;
	public int dataColour;
	public int dataColours[];
	
	public boolean pause;
	
	private int channelIndex;
	

	
	private Fragment outputFragment;
	
	public void addFragment(Fragment inputFragment) {
		if ( ! pause ) {
			outputFragment = inputFragment;
		}
	}
	
	public Fragment getOutputFragment() {
		return outputFragment;
	}
	
		
    public ChannelController(int channelIndex, int dataColours[]) {
    	super();
    	this.channelIndex = channelIndex;
    	this.dataColours = dataColours;
    }
    
	public void setForm(ChannelForm form) {
		this.form = form;
	}
    	
	public void readForm() {
		// Read form values
		uSecsPerDivisionIndex =  (int) form.k_uSecsPerDivision.value();
		timeOffset = form.k_uSecOffset.value() - 1;
		mVoltsPerDivisionIndex = (int) form.k_mVoltsPerDivision.value();
		voltageOffset = form.k_mVoltOffset.value() - 1;
		brightness = (int) form.k_brightness.value();
		intensity = (int) form.k_intensity.value();
		pause = (form.t_pause.value()>0?true:false);
	}	

	public void updateForm() {

		// Do calculations with new values
		uSecsPerDivision = divisionTimes[ uSecsPerDivisionIndex ];
		mVoltsPerDivision = divisionVolts[ mVoltsPerDivisionIndex ];
		dataColour = dataColours[brightness];
		
		form.k_uSecsPerDivision.setValue(  uSecsPerDivisionIndex );
		form.k_uSecOffset.setValue( 1 + timeOffset );
		form.k_mVoltsPerDivision.setValue( mVoltsPerDivisionIndex );
		form.k_mVoltOffset.setValue( 1 + voltageOffset );
		form.k_brightness.setValue( brightness );
		form.k_intensity.setValue( intensity );
		form.t_pause.setValue( new Float((pause?1:0)) );
	}


	public void loadForm(Properties properties) {
		uSecsPerDivisionIndex = 	Integer.parseInt(properties.getProperty(channelIndex + PROP_USEC_PER_DIV_INDEX));
		uSecsPerDivision = 			Double.parseDouble(properties.getProperty(channelIndex + PROP_USEC_PER_DIV));
		timeOffset = 				Float.parseFloat(properties.getProperty(channelIndex + PROP_TIME_OFFSET));
		mVoltsPerDivisionIndex = 	Integer.parseInt(properties.getProperty(channelIndex + PROP_MVOLT_PER_DIV_INDEX));
		mVoltsPerDivision = 		Double.parseDouble(properties.getProperty(channelIndex + PROP_MVOLT_PER_DIV));
		voltageOffset =				Float.parseFloat(properties.getProperty(channelIndex + PROP_VOLTAGE_OFFSET));
		intensity = 				Integer.parseInt(properties.getProperty(channelIndex + PROP_INTENSITY));
		brightness = 				Integer.parseInt(properties.getProperty(channelIndex + PROP_BRIGHTNESS));
		String dcs[] = properties.getProperty(channelIndex + PROP_DATA_COLOURS).split(PROP_SPLIT);
		for ( int i = 0; i < dcs.length; i++ ) {
			dataColours[i] = Integer.parseInt(dcs[i]);
		}
		pause = 					Boolean.parseBoolean(properties.getProperty(channelIndex + PROP_PAUSE));
	}


	public Properties saveForm() {
		StringBuilder sb = new StringBuilder();
		
		Properties properties = new Properties();
		
		properties.setProperty(channelIndex + PROP_USEC_PER_DIV_INDEX, 	new Integer(uSecsPerDivisionIndex).toString());
		properties.setProperty(channelIndex + PROP_USEC_PER_DIV, 		new Double(uSecsPerDivision).toString());
		properties.setProperty(channelIndex + PROP_TIME_OFFSET, 		new Float(timeOffset).toString());
		properties.setProperty(channelIndex + PROP_MVOLT_PER_DIV_INDEX, new Integer(mVoltsPerDivisionIndex).toString());
		properties.setProperty(channelIndex + PROP_MVOLT_PER_DIV, 		new Double(mVoltsPerDivision).toString());
		properties.setProperty(channelIndex + PROP_VOLTAGE_OFFSET, 		new Float(voltageOffset).toString());
		properties.setProperty(channelIndex + PROP_INTENSITY, 			new Integer(intensity).toString());
		properties.setProperty(channelIndex + PROP_BRIGHTNESS, 			new Integer(brightness).toString());
		for ( int dc : dataColours ) {
			if ( sb.length() > 0 ) {
				sb.append(PROP_SPLIT);
			}
			sb.append(dc);
		}
		properties.setProperty(channelIndex + PROP_DATA_COLOURS, sb.toString());
		properties.setProperty(channelIndex + PROP_PAUSE, new Boolean(pause).toString());
		
		return properties;
	}
	

	
    
}
