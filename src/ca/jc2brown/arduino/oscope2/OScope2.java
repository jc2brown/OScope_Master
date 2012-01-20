package ca.jc2brown.arduino.oscope2;

import java.awt.Point;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.Scope;
import ca.jc2brown.arduino.oscope2.gui.controller.Controller;
import ca.jc2brown.arduino.oscope2.gui.controller.FaceplateController;
import ca.jc2brown.arduino.oscope2.gui.form.Faceplate;
import ca.jc2brown.arduino.oscope2.model.Packet;
import ca.jc2brown.arduino.oscope2.service.PersistService;
import controlP5.ControlP5;

import processing.core.PApplet;
import processing.serial.Serial;


public class OScope2 extends PApplet {
	private static final long serialVersionUID = 1L;
	
	public static final boolean DEBUG = true; 
	public static final boolean INFO = false;
		
	private Serial port;
		
	
	PersistService persistService;
	
	FaceplateController faceplateController;
	Faceplate faceplate;
	ControlP5 gui;
	
	Scope scope;
	Packet packet;
	
	public static void main(String[] args) {
		PApplet.main(new String[] { "ca.jc2brown.arduino.oscope2.OScope2" });
	}
	
	
	
	public void setup() {
		debug("Setup start");

		size( Faceplate.getDimensions().width, Faceplate.getDimensions().height, P2D );
		
		// Initialize the serial port
		for ( String s : Serial.list()) {
			System.out.println(s);
		}
		
		port = new Serial(this, Serial.list()[1], 115200);
		port.clear();
				
		gui = new ControlP5(this);
		new Colours(this);
		

		// Controllers
		faceplateController = new FaceplateController();
		
		// Forms
		faceplate = new Faceplate(this, gui, faceplateController, new Point(0, 0));
		
		faceplateController.setForm(faceplate);
		Controller.setRootController(faceplateController);
		
		persistService = new PersistService(faceplateController.persistCtl);
		faceplateController.persistCtl.setPersistService(persistService);

		persistService.loadModeNames();
		persistService.loadMode(0);
		
		background(Colours.BACKGROUND_COLOUR);
		
		//scope = new Scope(this, "test", new Point(100, 100), new Dimension(640, 480));
		//packet = new Packet();
		
		//while ( port.read() != 123 );
		Controller.getRootController().controlEvent(null);
		

	}
	
	
	
	
	public void draw() {
		boolean goodPacket;
		Packet packet = new Packet();
		if ( port.available() >= 128 ) {
			while ( port.read() != 123 );
			goodPacket = packet.read(port);
			if ( goodPacket ) {
				switch ( packet.header.type ) {
					case DIGITAL_SEQUENCE:
						faceplateController.ch1Ctl.addFragment(packet.payload);
						break;
					case ANALOG_SEQUENCE:
						faceplateController.ch2Ctl.addFragment(packet.payload);
						break;
				}
				port.clear();
				////Syste,.out.println(packet);
			}
		}
		
		faceplate.draw();
	}
	

	public static void debug(Object o) {
		if ( DEBUG ) {
			//Syste,.out.println("Main:\t\t" + o);
		}
	}
	public static void info(Object o) {
		if ( INFO ) {
			//Syste,.out.println("Main:\t\t" + o);
		}
	}
}