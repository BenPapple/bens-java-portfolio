package generator;

import data.GlobalSettings;
import data.GlobalSettings.GeneratorType;
import gui.MainCanvasPanel;

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

	String generatorName;
	JPanel PanelSidebar;
	MainCanvasPanel myCanvas;
	GeneratorType generatorType;
	char myMnemonicKey;
	private Instant startCalc, endCalc;
	private long timeBetween = 0;

	String generatorDescr;
	IGenerator.Status status;
	String errorMsg;

	/**
	 * Updates the generator status and notifies all registered observer that
	 * the status has changed.
	 *
	 * @param newGeneratorStatusValue new status of generator
	 */
	public void updateStatus(IGenerator.Status newGeneratorStatusValue) {
		this.status = newGeneratorStatusValue;

		// Notify Observers
		setChanged();
		notifyObservers();
	}

	/**
	 * Update variable startCalc with current time.
	 *
	 */
	public void startCalcTime() {
		startCalc = Instant.now();
	}

	/**
	 * Update variable endCalc with current time.
	 * 
	 */
	public void endCalcTime() {
		endCalc = Instant.now();
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

	/**
	 * Gives you current status like calculating, finished etc of Generator.
	 *
	 * @return current Generator Status
	 */
	@Override
	public IGenerator.Status getGenStatus() {
		return this.status;
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
	 * Change generatorName of generator to input string value.
	 *
	 * @param name designation generatorName of the Generator
	 */
	public void setName(String name) {
		this.generatorName = name;
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
	public GlobalSettings.GeneratorType getGenType() {
		return generatorType;
	}

	@Override
	public JPanel getSideBarPanel() {
		return PanelSidebar;
	}

	@Override
	public abstract void stopGenerator();

	@Override
	public void setReady() {
		this.status = IGenerator.Status.READY;
	}

	@Override
	public String getErrorMessage() {
		return this.errorMsg;
	}

	@Override
	public char getKey() {
		return this.myMnemonicKey;
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
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Initializes the GUI from another class in gui package that implements GUI
	 * elements.
	 */
	public abstract void createSideBarGUI();

	@Override
	public String getFilePath() {
		return ".png";
	}

	@Override
	public void setLoadedValues(String inPath) {
		// default, overridden when needed
	}

}
