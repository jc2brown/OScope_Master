package ca.jc2brown.arduino.oscope2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import ca.jc2brown.arduino.oscope2.OScope2;
import ca.jc2brown.arduino.oscope2.gui.controller.Controller;
import ca.jc2brown.arduino.oscope2.gui.controller.PersistController;

public class PersistService {
	
	private static final String DEFAULT_PROPERTIES_FILE = "properties.default";
	private static final String PROPERTIES_FILE = "properties.cfg"; 
	
	private PersistController persistController;
	
	
	public PersistService(PersistController persistController) {
		this.persistController = persistController;
	}
	

	public void saveCurrentMode() {
		Properties properties = Controller.getRootController().saveForm();
		FileOutputStream os = null;
		
		try {
			
			os = new FileOutputStream(new File(PROPERTIES_FILE + persistController.currentMode));
			properties.store(os, "");
			OScope2.debug("Mode saved");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void loadMode(int mode) {
		Properties properties = new Properties();
		FileInputStream is = null;
		File file;
		
		try {
			file = new File(PROPERTIES_FILE + mode);
			if ( ! file.exists() ) {
				file = new File(DEFAULT_PROPERTIES_FILE);
			}
			
			is = new FileInputStream(file);
			properties.load(is);
			properties.put(PersistController.PROP_CURRENT_MODE, new Integer(mode).toString());
			Controller.getRootController().loadForm(properties);
			OScope2.debug("Mode loaded");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
				
	}
	
	
	
	public void saveModeNames() {
		Properties properties = persistController.saveModeNames();
		FileOutputStream os = null;
		try {
			
			os = new FileOutputStream(new File(PROPERTIES_FILE));
			properties.store(os, "");
			OScope2.debug("Mode names saved");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public void loadModeNames() {
		Properties properties = new Properties();
		FileInputStream is = null;
		try {
			
			is = new FileInputStream(new File(PROPERTIES_FILE));
			properties.load(is);
			persistController.loadModeNames(properties);
			OScope2.debug("Mode names loaded");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
}
