package ca.jc2brown.arduino.oscope2.gui;

import processing.core.PApplet;

public class Colours {
	
	PApplet context;

	public static int BLACK;
	public static int GREY;
	public static int WHITE;
	public static int DK_CYAN;
	public static int LT_CYAN;
	
	public static int BACKGROUND_COLOUR;
	public static int OUTLINE_COLOUR;
	public static int SCREEN_DIVISION_COLOUR;
	public static int SCREEN_OUTLINE_COLOUR;
	
	public static int NUM_BRIGHTNESS_LEVELS = 10;
	public static int[] CH1_DATA_COLOURS;
	public static int[] CH2_DATA_COLOURS;
	
	
	public Colours(PApplet context) {
		this.context = context;		
		
		BLACK = context.color(0, 0, 0);
		GREY = context.color(100, 100, 100);
		WHITE = context.color(255, 255, 255);
		DK_CYAN = context.color(0, 100, 100);
		LT_CYAN = context.color(0, 200, 200);
		
		BACKGROUND_COLOUR = BLACK;
		OUTLINE_COLOUR = GREY;
		SCREEN_DIVISION_COLOUR = GREY;
		SCREEN_OUTLINE_COLOUR = GREY;
		
		CH1_DATA_COLOURS = new int[NUM_BRIGHTNESS_LEVELS];
		CH2_DATA_COLOURS = new int[NUM_BRIGHTNESS_LEVELS];
		
		for ( int i = 0; i < NUM_BRIGHTNESS_LEVELS; i++ ) {
			CH1_DATA_COLOURS[i] = context.color((i*255)/NUM_BRIGHTNESS_LEVELS, 0, 0);
			CH2_DATA_COLOURS[i] = context.color(0, (i*255)/NUM_BRIGHTNESS_LEVELS, 0);
		}		
	}	
	
}