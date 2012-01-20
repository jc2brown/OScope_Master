package ca.jc2brown.arduino.oscope2.gui.controller;

import java.util.Properties;

import ca.jc2brown.arduino.oscope2.gui.Colours;
import ca.jc2brown.arduino.oscope2.gui.form.Faceplate;

public class FaceplateController extends Controller<Faceplate> {
			
	public DisplayController displayCtl;
	public ChannelController ch1Ctl;
	public ChannelController ch2Ctl;
	public PersistController persistCtl;
	
    
    public FaceplateController() {    	    	
    	ch1Ctl = new ChannelController(1, Colours.CH1_DATA_COLOURS);
    	ch2Ctl = new ChannelController(2, Colours.CH2_DATA_COLOURS);
    	
    	displayCtl = new DisplayController();
    	displayCtl.ch1Ctl = ch1Ctl;
    	displayCtl.ch2Ctl = ch2Ctl;
    	
    	persistCtl = new PersistController();
    }
    
    
	public void setForm(Faceplate form) {
		this.form = form;
		
    	displayCtl.setForm(form.displayForm);
    	displayCtl.setScreen(form.screen);
    	
    	ch1Ctl.setForm(form.ch1Form);
    	ch2Ctl.setForm(form.ch2Form);
    	persistCtl.setForm(form.persistForm);
	}
	
    
	protected void readForm() {
		displayCtl.readForm();
		ch1Ctl.readForm();
		ch2Ctl.readForm();
		persistCtl.readForm();
	}

	void updateForm() {
		displayCtl.updateForm();
		displayCtl.updateScreen();
		ch1Ctl.updateForm();
		ch2Ctl.updateForm();
		persistCtl.updateForm();
	}


	
	public void loadForm(Properties properties) {
		displayCtl.loadForm(properties);
		persistCtl.loadForm(properties);
		ch1Ctl.loadForm(properties);
		ch2Ctl.loadForm(properties);	
		
		updateForm();
	}
	

	public Properties saveForm() {
		Properties properties = new Properties();
		
		readForm();
		
		
		properties.putAll( displayCtl.saveForm() );
		properties.putAll( persistCtl.saveForm() );
		properties.putAll( ch1Ctl.saveForm() );
		properties.putAll( ch2Ctl.saveForm() );
		
		return properties;
	}

}
