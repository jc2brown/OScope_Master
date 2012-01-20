package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.controller.ChannelController;

import controlP5.ControlP5;
import controlP5.Knob;
import controlP5.Textlabel;
import controlP5.Toggle;
import processing.core.PApplet;

public class ChannelForm extends Form<ChannelController> {
		
	private static int USEC_PER_DIV_MIN = 0;
	private static float USEC_PER_DIV_MAX = ChannelController.divisionTimes.length - .01f;
	
	private static int USEC_OFFSET_MIN = 0;
	private static int USEC_OFFSET_MAX = 2;

	private static int MVOLT_PER_DIV_MIN = 0;
	private static float MVOLT_PER_DIV_MAX = ChannelController.divisionVolts.length - .01f;
	
	private static int MVOLT_OFFSET_MIN = 0;
	private static int MVOLT_OFFSET_MAX = 2;
	
	private static int BRIGHTNESS_MIN = 0;
	private static float BRIGHTNESS_MAX = Colours.NUM_BRIGHTNESS_LEVELS - .01f;
	
	private static int INTENSITY_MIN = 0;
	private static int INTENSITY_MAX = 5;
	
	
	
	
	
	private Textlabel l_name;

	public Knob k_uSecsPerDivision;
	public Knob k_uSecOffset;

	public Knob k_mVoltsPerDivision;
	public Knob k_mVoltOffset;
	
	public Knob k_brightness;
	public Knob k_intensity;
	
	public Toggle t_pause;
	
	
	public ChannelForm(PApplet context, ControlP5 gui, ChannelController controller, String name, Point pos, Dimension dim) {
		super(context, gui, controller, pos, dim);
		
		int avgw = (int) (pos.x + .5 * dim.width);
		
		l_name = 			 	gui.addTextlabel(	"", 			name, 				avgw, 				20);
		
		k_mVoltsPerDivision = 	gui.addKnob( "VOLTS/DIV", 	MVOLT_PER_DIV_MIN, 	MVOLT_PER_DIV_MAX, 	avgw-LARGE_KNOB_RADIUS, 	pos.y + 40, 	2*LARGE_KNOB_RADIUS);
		k_mVoltOffset = 		gui.addKnob( "- V +", 	MVOLT_OFFSET_MIN, 	MVOLT_OFFSET_MAX, 	avgw-SMALL_KNOB_RADIUS, 	pos.y + 100, 	2*SMALL_KNOB_RADIUS);
		
	    k_uSecsPerDivision = 	gui.addKnob( "TIME/DIV", 	USEC_PER_DIV_MIN, 	USEC_PER_DIV_MAX, 	avgw-LARGE_KNOB_RADIUS, 	pos.y + 170, 	2*LARGE_KNOB_RADIUS);
		k_uSecOffset = 		 	gui.addKnob( "- T +", 		USEC_OFFSET_MIN, 	USEC_OFFSET_MAX, 	avgw-SMALL_KNOB_RADIUS, 	pos.y + 230, 	2*SMALL_KNOB_RADIUS);
		
		k_brightness = 			gui.addKnob( "SAT.", 	BRIGHTNESS_MIN, 	BRIGHTNESS_MAX, 	avgw-SMALL_KNOB_RADIUS, 	pos.y + 300, 	2*SMALL_KNOB_RADIUS);
		k_intensity = 			gui.addKnob( "LUM.", 	INTENSITY_MIN, 	    INTENSITY_MAX, 		avgw-SMALL_KNOB_RADIUS, 	pos.y + 350, 	2*SMALL_KNOB_RADIUS);
		
		t_pause = 				gui.addToggle("PAUSE", 		false,				avgw-SMALL_KNOB_RADIUS,				430,						20, 			20);
		
		l_name.addListener(					controller );
		k_uSecsPerDivision.addListener(		controller );
		k_uSecOffset.addListener(			controller );
		k_mVoltsPerDivision.addListener(	controller );
		k_mVoltOffset.addListener(			controller );
		k_brightness.addListener(			controller );
		k_intensity.addListener(			controller );	
		t_pause.addListener(				controller );		
	}
			
	
	public void draw() {
		
		context.noFill();
		context.stroke(Colours.OUTLINE_COLOUR);
		context.rect(pos.x, pos.y, dim.width, dim.height);
		
	}
}

