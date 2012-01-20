package ca.jc2brown.arduino.oscope2.gui;

import java.awt.Dimension;
import java.awt.Point;

import controlP5.ControlP5;
import controlP5.Knob;
import controlP5.Textlabel;

import ca.jc2brown.arduino.oscope2.gui.controller.ChannelController;
import ca.jc2brown.arduino.oscope2.model.Packet;
import ca.jc2brown.arduino.oscope2.model.fragment.Fragment;

import processing.core.PApplet;
import processing.core.PConstants;


public class Scope implements PConstants {

	PApplet context;
	String name;	
	Point pos;
	Dimension dim;
	
	ControlP5 gui;
	Textlabel uSecsPerDivisionLabel;
	Knob uSecsPerDivisionKnob;

	int dataColour;
	int divisionColour;
	
	
	// Horizontal time axis
	int    timeDivisions;
	long   uSecsPerDivison;		// in microseconds	
	double timeOffset;				// in divisions
	
	// Vertical voltage axis
	int    voltageDivisions;
	long   voltageUnitsPerDivision; // in millivolts
	double voltageOffset; 			// in divisions
	
	
	double triggerThreshold;
	double lastSample;
	double avgFreq;
	int freqc;
	

	public Scope(PApplet context, String name, Point pos, Dimension dim) {
		this.context = context;
		this.name = name;
		this.pos = pos;
	    this.dim = dim;
	    
	    gui = new ControlP5(context);
	    uSecsPerDivisionLabel = gui.addTextlabel("0", "1", 100, pos.y + dim.height + 5 );
	    uSecsPerDivisionKnob = gui.addKnob("TIME/DIV", 0, 17, 100, pos.y + dim.height + 30, 40);
	    	    
	    dataColour = context.color(100, 255, 100);
	    divisionColour = context.color(150, 150, 150);
		
		timeDivisions = 4;
		uSecsPerDivison = 2500;
		timeOffset = 0;
		
		voltageDivisions = 4;
		voltageUnitsPerDivision = 100;
		voltageOffset = 0; 	
		
		triggerThreshold = .5;
		lastSample = 0;
		avgFreq = 0;
		freqc = 0;
	}
	
	
	
	
	public void draw(Packet packet) {
		update();
		if ( ! packet.payload.isEmpty() ) {
			drawData(packet);
		}
		drawDivisions();
		gui.draw();
	}

	

	
	
	private void update() {
		float knobValue = uSecsPerDivisionKnob.value();
		String uSecLabel = uSecsPerDivisionLabel.stringValue();
		uSecsPerDivison = (long) ChannelController.divisionTimes[(int) knobValue];
		uSecsPerDivisionLabel.setValue( uSecLabel );
	}




	private void drawData(Packet packet){
		int x1 = pos.x;
		int y1 = pos.y;
		int x2 = pos.x;
		int y2 = pos.y;
		int sample = (int) triggerThreshold + 1;

		Fragment payload = packet.payload;
		
		long payloadDuration = packet.header.endMicros - packet.header.startMicros;
		
		double uSecsPerPixel = uSecsPerDivison * timeDivisions / dim.width; 
		double uSecsPerSample = payloadDuration /  payload.size();
		double pixelsPerSample = uSecsPerSample / uSecsPerPixel;
		
		int numLines = (int) (dim.width / pixelsPerSample) + 1;
		if ( numLines > payload.size() ) {
			numLines = payload.size();
		}
		
		context.stroke(dataColour);
		context.strokeWeight(2);
		for ( int i = 1; i < numLines; i++ ) {
			lastSample = sample;
			sample = payload.get(i).intValue();
			if ( sample >= triggerThreshold && lastSample < triggerThreshold ) {
				freqc += 1;
			}
			
			x1 = x2;
			y1 = y2;
		  	x2 = pos.x + (int)( i * pixelsPerSample );
			y2 = pos.y + dim.height - getY( sample );
			context.line(x1, y1, x2, y2);
		}
		
	}
	
	
	private int getY(int val){
		return (int)(val / 1 * dim.height) - 1;
	}

	

	
	// Draw divisions
	private void drawDivisions() {
		context.stroke(divisionColour);
		context.strokeWeight(1);
		
		// Time divisions
		for ( int t = 0; t <= dim.width; t += dim.width / timeDivisions ) {
			context.line( pos.x + t, pos.y, pos.x + t, pos.y + dim.height);
		}
		
		// Voltage divisions
		for ( int v = 0; v <= dim.height; v += dim.height / voltageDivisions ) {
			context.line( pos.x, pos.y + v, pos.x + dim.width, pos.y + v);
		}
	}
}


