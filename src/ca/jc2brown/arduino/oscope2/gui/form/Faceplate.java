package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.controller.FaceplateController;
import ca.jc2brown.arduino.oscope2.gui.form.Form;

import processing.core.PApplet;

import controlP5.ControlP5;

public class Faceplate extends Form<FaceplateController> {
	
	// Change these
	
	private static int SCREEN_WIDTH = 			640;
	private static int SCREEN_CTL_HEIGHT = 		80;
	private static int SCREEN_HEIGHT = 			480;
	private static int CHANNEL_WIDTH = 			100;

	
	// Don't change these
	private static int SCREEN_X = 		PADDING;
	private static int SCREEN_Y = 		PADDING;
	private static int SCREEN_W = 		SCREEN_WIDTH;
	private static int SCREEN_H = 		SCREEN_HEIGHT;
	
	private static int SCREEN_CTL_X = 	PADDING;
	private static int SCREEN_CTL_Y = 	SCREEN_Y + SCREEN_H + PADDING;
	private static int SCREEN_CTL_W = 	SCREEN_W;
	private static int SCREEN_CTL_H = 	SCREEN_CTL_HEIGHT;
	
	private static int CH1_X = 			SCREEN_X + SCREEN_W + PADDING;
	private static int CH1_Y = 			PADDING;
	private static int CH1_W = 			CHANNEL_WIDTH;
	private static int CH1_H = 			SCREEN_H;
	
	private static int CH2_X = 			CH1_X + CH1_W + PADDING;
	private static int CH2_Y = 			PADDING;
	private static int CH2_W = 			CH1_W;
	private static int CH2_H = 			CH1_H;
	
	private static int PERSIST_X = 		CH1_X;
	private static int PERSIST_Y = 		SCREEN_CTL_Y;
	private static int PERSIST_W = 		CH1_W + CH2_W + PADDING;
	private static int PERSIST_H = 		SCREEN_CTL_HEIGHT;
	
	
	public Screen screen;
	public DisplayForm displayForm;
	
	public ChannelForm ch1Form;
	public ChannelForm ch2Form;
	
	public PersistForm persistForm;
	
	
	public Faceplate(PApplet context, ControlP5 gui, FaceplateController controller, Point pos) {
		super(context, gui, controller, pos, getDimensions());
				
		screen = new Screen(context, gui, controller.displayCtl, new Point(SCREEN_X, SCREEN_Y), new Dimension(SCREEN_W, SCREEN_H));
		displayForm = new DisplayForm(context, gui, controller.displayCtl, new Point(SCREEN_CTL_X, SCREEN_CTL_Y), new Dimension(SCREEN_CTL_W, SCREEN_CTL_H));
		
		ch1Form = new ChannelForm(context, gui, controller.ch1Ctl, "CH1", new Point(CH1_X, CH1_Y), new Dimension(CH1_W, CH1_H));
		ch2Form = new ChannelForm(context, gui, controller.ch2Ctl, "CH2", new Point(CH2_X, CH2_Y), new Dimension(CH2_W, CH2_H));
		
		persistForm = new PersistForm(context, gui, controller.persistCtl, new Point(PERSIST_X, PERSIST_Y), new Dimension(PERSIST_W, PERSIST_H));
	}
	
	
	public void draw() {
		context.background( Colours.BACKGROUND_COLOUR );
		
		screen.draw();
		displayForm.draw();
		ch1Form.draw();
		ch2Form.draw();		
		persistForm.draw();

		gui.draw();
	}
	
	
	public static Dimension getDimensions() {
		Dimension dim = new Dimension();
		dim.width = 	4 * PADDING + 	SCREEN_W + 2 * CHANNEL_WIDTH;	
		dim.height = 	3 * PADDING + 	SCREEN_H + SCREEN_CTL_HEIGHT;	
		return dim;
	}
}


