package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.controller.DisplayController;

import controlP5.ControlP5;
import controlP5.Knob;
import processing.core.PApplet;

public class DisplayForm extends Form<DisplayController> {

	private static int NUM_DIVISIONS_MIN = 0;
	private static float NUM_DIVISIONS_MAX = 9.9f;
	
	private static int NUM_TICKS_PER_DIV_MIN = 0;
	private static float NUM_TICKS_PER_DIV_MAX = 9.9f;
	
	private static int TICK_LENGTH_MIN = 0;
	private static float TICK_LENGTH_MAX = 9.9f;
	
			
	// Time/Div
	public Knob     k_timeDivisions;
	
	// Time ticks/div
	public Knob		k_timeTicks;
	
	// Time tick length
	public Knob 	k_timeTickLength;
	
	// Voltage/Div
	public Knob     k_voltageDivisions;
	
	// Voltage ticks/div
	public Knob		k_voltageTicks;

	// Voltage tick length
	public Knob 	k_voltageTickLength;
	
	
	
	public DisplayForm(PApplet context, ControlP5 gui, DisplayController controller, Point pos, Dimension dim) {
		super(context, gui, controller, pos, dim);
		
		int avgh = pos.y + dim.height / 2;
		
		k_timeDivisions = gui.addKnob("TIME DIVS", NUM_DIVISIONS_MIN, NUM_DIVISIONS_MAX, pos.x + 30, avgh-LARGE_KNOB_RADIUS, 2*LARGE_KNOB_RADIUS);
		k_timeTicks = gui.addKnob("T TICKS", NUM_TICKS_PER_DIV_MIN, NUM_TICKS_PER_DIV_MAX, pos.x + 100, avgh-SMALL_KNOB_RADIUS, 2*SMALL_KNOB_RADIUS);
		k_timeTickLength = gui.addKnob("T TICK LEN", TICK_LENGTH_MIN, TICK_LENGTH_MAX, pos.x + 150, avgh-SMALL_KNOB_RADIUS, 2*SMALL_KNOB_RADIUS);
		
		k_voltageDivisions = gui.addKnob("VOLT DIVS",NUM_DIVISIONS_MIN, NUM_DIVISIONS_MAX, pos.x + 330, avgh-LARGE_KNOB_RADIUS, 2*LARGE_KNOB_RADIUS);
		k_voltageTicks = gui.addKnob("V TICKS", NUM_TICKS_PER_DIV_MIN, NUM_TICKS_PER_DIV_MAX, pos.x + 400, avgh-SMALL_KNOB_RADIUS, 2*SMALL_KNOB_RADIUS);
		k_voltageTickLength = gui.addKnob("V TICK LEN", TICK_LENGTH_MIN, TICK_LENGTH_MAX, pos.x + 450, avgh-SMALL_KNOB_RADIUS, 2*SMALL_KNOB_RADIUS);
		
		
		k_timeDivisions.addListener(controller);
		k_timeTicks.addListener(controller);
		k_timeTickLength.addListener(controller);
		
		k_voltageDivisions.addListener(controller);
		k_voltageTicks.addListener(controller);
		k_voltageTickLength.addListener(controller);
				
	}
	
	public void draw() {
		
		context.noFill();
		context.stroke(Colours.OUTLINE_COLOUR);
		context.strokeWeight(1);
		context.rect(pos.x, pos.y, dim.width, dim.height);
		
	}

	
}