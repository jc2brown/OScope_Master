package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import processing.core.PApplet;
import controlP5.ControlP5;

public abstract class Form<FormControllerType> {

	protected static int PADDING = 10;
	protected static int SMALL_KNOB_RADIUS = 	12;
	protected static int LARGE_KNOB_RADIUS = 	20;
	
	protected PApplet context;
	protected ControlP5 gui;
	protected FormControllerType controller;
	
	protected Point pos;
	protected Dimension dim;
	
	public Form(PApplet context, ControlP5 gui, FormControllerType controller, Point pos, Dimension dim) {
		this.context = context;
		this.gui = gui;
		this.controller = controller;
		this.pos = pos;
		this.dim = dim;
	}
	
	
	public abstract void draw();
	
	
}
