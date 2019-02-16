package generator;

import data.GlobalSettings;
import data.GlobalSettings.GeneratorType;
import gui.MainCanvasPanel;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Abstract class which defines how to integrate and control a generator with
 * GUI parts.
 *
 * @author BenGe47
 * 
 */
public abstract class AGenerator extends Observable implements IGenerator, Runnable {

	private String generatorName;
	private JPanel PanelSidebar;
	private MainCanvasPanel myCanvas;	
	private GeneratorType generatorType;
	private char myMnemonicKey;
	private Instant startCalc, endCalc;
	private long timeBetween = 0;
	private GlobalSettings.Status status;
	private String errorMsg;
	
	/**
	 * Add inPanel to PanelSideBar.
	 * 
	 * @param inPanel Panel to add
	 */
	public void addToSidebar(JPanel inPanel) {
		PanelSidebar.add(inPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Initializes the GUI from another class in gui package that implements GUI
	 * elements.
	 */
	public abstract void createSideBarGUI();

	/**
	 * Update variable endCalc with current time.
	 * 
	 */
	public void endCalcTime() {
		endCalc = Instant.now();
	}

	/**
	 * Checks if this IGenerator is equal to input IGenerator.
	 *
	 * @param g is a IGenerator
	 * @return true if both IGenerator are equal
	 */
	public boolean equals(IGenerator g) {
		return this.getName().equals(g.getName());
	}

	/**
	 * Gives the duration between two Instant variables.
	 *
	 * @return long time in seconds
	 */
	@Override
	public long getCalcTime() {
		timeBetween = ChronoUnit.MILLIS.between(startCalc, endCalc);
		return this.timeBetween;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMsg;
	}

	@Override
	public String getFilePath() {
		return ".png";
	}

	/**
	 * Gives you current status like calculating, finished etc of Generator.
	 *
	 * @return current Generator Status
	 */
	@Override
	public GlobalSettings.Status getGenStatus() {
		return this.status;
	}

	@Override
	public GlobalSettings.GeneratorType getGenType() {
		return generatorType;
	}

	@Override
	public char getKey() {
		return this.myMnemonicKey;
	}

	/**
	 * Gives you the generatorName as a string of the generator.
	 *
	 * @return String generatorName of generator
	 */
	@Override
	public String getName() {
		return this.generatorName;
	}

	@Override
	public JPanel getSideBarPanel() {
		return PanelSidebar;
	}

	/**
	 * Create new JPanel for PanelSidebar.
	 * 
	 */
	public void initSideBarPanel() {
		PanelSidebar = new JPanel();
	}

	/**
	 * Setter text on errorMsg.
	 * 
	 * @param inString the text to set
	 */
	public void setErrorMsgText(String inString) {
		this.errorMsg = inString;
	}

	/**
	 * Set status to inStatus.
	 *
	 * @param inStatus new Generator Status
	 */
	public void setGenStatus(GlobalSettings.Status inStatus) {
		this.status = inStatus;
	}

	/**
	 * Set generator type.
	 * 
	 * @param inType Type of Generator
	 */
	public void setGenType(GlobalSettings.GeneratorType inType) {
		generatorType = inType;
	}

	@Override
	public void setLoadedValues(String inPath) {
		// default, overridden when needed
	}

	/**
	 * Set myCanvas to input canvas.
	 * 
	 * @param inCanvas the canvas to set
	 */
	public void setMainCanvas(MainCanvasPanel inCanvas) {
		this.myCanvas = inCanvas;
	}

	/**
	 * Set myCanvas to input canvas.
	 * 
	 * @param inCanvas the canvas to set
	 */
	public void setMainCanvasToImage(BufferedImage inImage) {
		this.myCanvas.setImage(inImage);
	}

	/**
	 * Setter for mnemonic key.
	 * 
	 * @param inChar the char to set
	 */
	public void setMnemonicChar(char inChar) {
		this.myMnemonicKey = inChar;
	}

	/**
	 * Change generatorName of generator to input string value.
	 *
	 * @param name designation generatorName of the Generator
	 */
	public void setName(String name) {
		this.generatorName = name;
	}

	@Override
	public void setReady() {
		this.status = GlobalSettings.Status.READY;
	}

	/**
	 * Display pop-up with input error message.
	 * 
	 * @param inString error message
	 */
	public void showWarning(String inString) {
		JOptionPane.showMessageDialog(null,
				inString,
				"Warning",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Update variable startCalc with current time.
	 *
	 */
	public void startCalcTime() {
		startCalc = Instant.now();
	}

	@Override
	public abstract void stopGenerator();

	/**
	 * Updates the generator status and notifies all registered observer that
	 * the status has changed.
	 *
	 * @param newGeneratorStatusValue new status of generator
	 */
	public void updateStatus(GlobalSettings.Status newGeneratorStatusValue) {
		this.status = newGeneratorStatusValue;

		// Notify Observers
		setChanged();
		notifyObservers();
	}

}
