package ca.jc2brown.arduino.oscope2.gui.controller;

import java.util.Properties;

import controlP5.ControlEvent;
import controlP5.ControlListener;


public abstract class Controller<FormType> implements ControlListener {
	
	private static final long UPDATE_PERIOD = 10;
	
	private static Controller<?> rootController;
	protected FormType form;
	private boolean locked;
	private long lastUpdateTime;
	protected boolean doSave;
	
	public Controller() {
		locked = false;
		doSave = false;
	}
	
	public static void setRootController(Controller<?> rootController) {
		Controller.rootController = rootController;
	}
	
	public static Controller<?> getRootController() {
		return Controller.rootController;
	}
	
	
	public void controlEvent(ControlEvent event) {
		
		if ( event != null ) {
			//Syste,.out.println("null");
		}
		
		if ( event != null && "SAVE".equals(event.name()) ) {
			doSave = true;
		}
		
		if ( ! locked && System.currentTimeMillis() - lastUpdateTime > UPDATE_PERIOD ) {
			locked = true;
			rootController.readForm();
			rootController.updateForm();
			locked = false;
			lastUpdateTime = System.currentTimeMillis();
		}		
	}
	

	public abstract void setForm(FormType form);
	
	protected abstract void readForm();
	
	abstract void updateForm();
	
	public abstract void loadForm(Properties properties);
	
	public abstract Properties saveForm();
    
}
