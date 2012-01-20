package ca.jc2brown.arduino.oscope2.gui.form;

import java.awt.Dimension;
import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.controller.PersistController;

import controlP5.Button;
import controlP5.ControlP5;
import controlP5.Radio;
import controlP5.Textfield;
import controlP5.Textlabel;
import processing.core.PApplet;

public class PersistForm extends Form<PersistController> {
	
	public Button b_saveCurrent;
	public Radio r_selectMode;
	public Textlabel l_modeName[];
	public Textfield f_modeName[];
	
	
	public PersistForm(PApplet context, ControlP5 gui, PersistController controller, Point pos, Dimension dim) {
		super(context, gui, controller, pos, dim);
				
		b_saveCurrent = gui.addButton("SAVE", 1, pos.x + 130 + PADDING, pos.y + PADDING, 60, 20);
		r_selectMode = gui.addRadio("MODE", pos.x + PADDING, pos.y + PADDING);
		l_modeName = new Textlabel[PersistController.NUM_MODES];
		f_modeName = new Textfield[PersistController.NUM_MODES];
		for ( int i = 0; i < PersistController.NUM_MODES; i++ ) {
			r_selectMode.addItem("", i);
			l_modeName[i] = gui.addTextlabel("", "", pos.x + 20 + PADDING, pos.y + 12 + 14 * i);
			//l_modeName[i].hide();
			f_modeName[i] = gui.addTextfield("", pos.x + 20 + PADDING, pos.y + 7 + 14 * i, 80, 16);
			f_modeName[i].addListener(controller);
			f_modeName[i].hide();
		}
		
		
		b_saveCurrent.addListener(controller);
		r_selectMode.addListener(controller);
	}
	
	public void draw() {
		
		context.noFill();
		context.stroke(Colours.OUTLINE_COLOUR);
		context.rect(pos.x, pos.y, dim.width, dim.height);
		
	}
	
}