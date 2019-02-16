package generator;

import data.GlobalSettings;
import data.GlobalSettings.GeneratorType;

import java.util.Observer;

import javax.swing.JPanel;

/**
 * Interface definition for the implementation of a generator for GUI creation
 * and access necessary in MainWindow class.
 *
 * @author BenGe47
 */
public interface IGenerator extends Runnable {

	/**
	 * Add input Observer to the list of registered observers.
	 *
	 * @param o is added to observer list
	 */
	void addObserver(Observer o);

	/**
	 * Delete input observer from list of registered observers.
	 *
	 * @param o is deleted from observer list
	 */
	void deleteObserver(Observer o);

	/**
	 * Gives the duration between two Instant variables.
	 *
	 * @return long time in seconds
	 */
	public long getCalcTime();

	/**
	 * Returns the error message of the generator.
	 *
	 * @return error message
	 */
	public String getErrorMessage();

	/**
	 * Returns the assigned key for mnemonics.
	 *
	 * @return String file name
	 */
	public String getFilePath();

	/**
	 * Gives you the current status value of the generator.
	 *
	 * @return current Generator Status
	 */
	public GlobalSettings.Status getGenStatus();

	/**
	 * Gives you the current type of the generator to sort into related menu.
	 *
	 * @return GeneratorType type of Generator
	 */
	public GeneratorType getGenType();

	/**
	 * Returns the assigned key for mnemonics.
	 *
	 * @return char mnemonic key
	 */
	public char getKey();

	/**
	 * Gives you the current name of the generator as a string value.
	 *
	 * @return name of the Generator
	 */
	public String getName();

	/**
	 * Returns JPanel of SideBar GUI.
	 *
	 * @return JPanel of Generator
	 */
	public JPanel getSideBarPanel();

	/**
	 * Method that gets called on Thread.start() to run calculations of
	 * generator.
	 *
	 */
	@Override
	public void run();

	/**
	 * Returns the assigned key for mnemonics.
	 *
	 * @return String file name
	 */
	void setLoadedValues(String inPath);

	/**
	 * Sets status to ready.
	 */
	public void setReady();

	/**
	 * Stops the current Generators calculations and returns it to ready status.
	 *
	 */
	public void stopGenerator();

}
