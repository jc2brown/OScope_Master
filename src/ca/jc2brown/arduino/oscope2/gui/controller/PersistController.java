package ca.jc2brown.arduino.oscope2.gui.controller;

import java.util.Properties;

import ca.jc2brown.arduino.oscope2.OScope2;
import ca.jc2brown.arduino.oscope2.gui.form.PersistForm;
import ca.jc2brown.arduino.oscope2.service.PersistService;


public class PersistController extends Controller<PersistForm> {
	
	// Configurables
	public static final int NUM_MODES = 5;
	
	// Constants
	public static final String PROP_CURRENT_MODE = "persist.currentMode";
	private static final String PROP_MODE_NAMES = "persist.modeNames";
	private static final String PROP_SPLIT = "`";
			
	// Services
	private PersistService persistService;
	public void setPersistService(PersistService persistService) {
		this.persistService = persistService;
	}

	// Member variables
	public int currentMode;
	private int lastMode;
	private static String modeNames[];
	

	// Constructor
    public PersistController() {
    	super();
    	currentMode = 0;
    	lastMode = -1;
    	modeNames = new String[NUM_MODES];
    }
    
	public void setForm(PersistForm form) {
		this.form = form;
		for ( int i = 0; i < NUM_MODES; i++ ) {
			modeNames[i] = ">";
		}
		//updateForm();
	}
    
    
	// Update the model from from values
	public void readForm() {
		modeNames[currentMode] = form.f_modeName[currentMode].getText();
		currentMode = (int) form.r_selectMode.value();
	}	

	// Update the form based on model values
	public void updateForm() {
		
		if ( currentMode != lastMode ) {
			persistService.loadMode(currentMode);
			return;
		}
		
		for ( int i = 0; i < NUM_MODES; i++ ) {
			form.l_modeName[i].setValueLabel( modeNames[i] );
			form.f_modeName[i].setText( modeNames[i] );
			
			form.f_modeName[i].hide();
			form.l_modeName[i].show();
		}
		
		form.l_modeName[currentMode].hide();
		form.f_modeName[currentMode].show();
		
		if ( doSave ) {
			persistService.saveModeNames();
			persistService.saveCurrentMode();
			doSave = false;
		}
	}


	public void loadForm(Properties properties) {
		currentMode = Integer.parseInt(properties.getProperty(PROP_CURRENT_MODE));
		persistService.loadModeNames();
		form.f_modeName[currentMode].setText(modeNames[currentMode]);
		lastMode = currentMode;
		//updateForm();
	}


	public Properties saveForm() {
		Properties properties = new Properties();
		
		saveModeNames();
		
		//properties.setProperty(PROP_CURRENT_MODE, new Integer(currentMode).toString());

		return properties;
	}
	
	
	public Properties saveModeNames() {
		Properties properties = new Properties();
		StringBuilder sb = new StringBuilder();
		
		for ( String mn : modeNames ) {
			if ( sb.length() > 0 ) {
				sb.append(PROP_SPLIT);
			}
			sb.append(mn);
		}
		properties.setProperty(PROP_MODE_NAMES, sb.toString());
		
		return properties;
	}
	
	
	public void loadModeNames(Properties properties) {
		String mn[] = properties.getProperty(PROP_MODE_NAMES).split(PROP_SPLIT);
		for ( int i = 0; i < mn.length; i++ ) {
			modeNames[i] = mn[i];
		}
		//updateForm();
		
	}
    
	
}
