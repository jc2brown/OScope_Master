package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.controller.ChannelController;
import ca.jc2brown.arduino.oscope2.gui.controller.DisplayController;
import ca.jc2brown.arduino.oscope2.model.fragment.Fragment;


import controlP5.ControlP5;
import controlP5.Textlabel;

import processing.core.PApplet;

public class Screen extends Form<DisplayController> {
	
	
	public int timeDivisions;
	public int timeTicksPerDiv;
	public int timeTickLength;
    
	public int voltageDivisions;
	public int voltageTicksPerDiv;
	public int voltageTickLength;
	
	public Textlabel l_ch1USecsPerDiv;
	public Textlabel l_ch1MVoltsPerDiv;
	public Textlabel l_ch2USecsPerDiv;
	public Textlabel l_ch2MVoltsPerDiv;
	
    
	public Screen(PApplet context, ControlP5 gui, DisplayController controller, Point pos, Dimension dim) {
		super(context, gui, controller, pos, dim);
		
		l_ch1MVoltsPerDiv = gui.addTextlabel("", "", pos.x + PADDING, pos.y + PADDING);
		l_ch2MVoltsPerDiv = gui.addTextlabel("", "", pos.x + PADDING, pos.y + 15 + PADDING);

		l_ch1USecsPerDiv = gui.addTextlabel("", "", pos.x + PADDING, pos.y + dim.height - 15 - PADDING);
		l_ch2USecsPerDiv = gui.addTextlabel("", "", pos.x + PADDING, pos.y + dim.height - PADDING);
	}
	
	
	public void draw() {
		
		int scope_w = context.getSize().width;
		int scope_h = context.getSize().height;
		
		// Grid lines
		drawDivisions();
		
		// Data
		drawData(controller.ch1Ctl);
		drawData(controller.ch2Ctl);
		
		// Mask
		context.noStroke();
		context.fill(Colours.BACKGROUND_COLOUR);
		context.rect(0, 0, scope_w, PADDING);
		context.rect(0, 0, PADDING, scope_h);
		context.rect(0, dim.height + PADDING, scope_w, scope_h - dim.height - PADDING);
		context.rect(dim.width + PADDING, 0, scope_w - dim.width - PADDING, scope_h);

		// Screen outline
		context.stroke(Colours.SCREEN_OUTLINE_COLOUR);
		context.strokeWeight(2);
		context.noFill();
		context.rect(pos.x, pos.y, dim.width, dim.height);

	}
	
	
	
	private void drawData(ChannelController chCtl) {
		
		Fragment fragment = chCtl.getOutputFragment();
		
		if ( fragment == null || fragment.isEmpty() ) {
			return;
		}
		
		int x1 = -1;
		int y1 = -1;
		int x2 = -1;
		int y2 = -1;
		
		
		//Syste,.out.println(timeDivisions);
		double hPixelsPerUSec = dim.width / (chCtl.uSecsPerDivision * timeDivisions);
		double hPixelsPerSample = hPixelsPerUSec * fragment.uSecsPerSample();
				
		double mVoltsPerMax = ChannelController.INPUT_MVOLTS_MAX;
		double vPixelsPerMVolt = dim.height / (chCtl.mVoltsPerDivision * voltageDivisions); 
		double vPixelsPerMax = vPixelsPerMVolt * mVoltsPerMax;
		
		int hPixelOffset = (int) (chCtl.timeOffset * dim.width);
		int vPixelOffset = (int) (chCtl.voltageOffset * chCtl.mVoltsPerDivision*1000);
		
		int numLines = (int) (dim.width / hPixelsPerSample) + 1;
		if ( numLines > fragment.size() ) {
			numLines = fragment.size();
		}
		
		//Syste,.out.println("\t" +hPixelsPerSample);
		
		context.stroke(chCtl.dataColour);
		context.strokeWeight(chCtl.intensity);
		for ( int i = 1; i < numLines; i++ ) {
			x1 = x2;
			y1 = y2;
		  	x2 = pos.x + hPixelOffset + (int)( i * hPixelsPerSample );
			y2 = pos.y + dim.height - vPixelOffset - (int)(fragment.get(i) * vPixelsPerMax);
			if ( x1 != -1 ) {
				context.line(x1, y1, x2, y2);
			}
		}
		
	}
	
	
	private void drawDivisions() {
		int r;
		
		context.stroke(Colours.SCREEN_DIVISION_COLOUR);
		context.strokeWeight(1);
		
		for ( double t = 0; t <= timeDivisions; t += 1 ) {
			r = (int)( (t * dim.width) / timeDivisions );
			context.line( pos.x + r, pos.y, pos.x + r, pos.y + dim.height );			
		}
		for ( double t = 0; t < timeDivisions * timeTicksPerDiv; t += 1 ) {
			r = (int)( (t * dim.width) / (timeDivisions * timeTicksPerDiv) );
			context.line( pos.x + r, pos.y - timeTickLength + dim.height / 2, pos.x + r, pos.y + timeTickLength + dim.height / 2 );			
		}
		
		for ( double v = 0; v <= voltageDivisions; v += 1 ) {
			r = (int)( (v * dim.height) / voltageDivisions );
			context.line( pos.x, pos.y + r, pos.x + dim.width, pos.y + r );			
		}
		for ( double v = 0; v < voltageDivisions * voltageTicksPerDiv; v += 1 ) {
			r = (int)( (v * dim.height) / (voltageDivisions * voltageTicksPerDiv) );
			context.line( pos.x - voltageTickLength + dim.width / 2, pos.y + r, pos.x + voltageTickLength + dim.width / 2, pos.y + r );			
		}
	}
	
	
	
}

