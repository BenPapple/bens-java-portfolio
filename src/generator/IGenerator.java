package generator;

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
	 * Returns JPanel of SideBar GUI.
	 *
	 * @return JPanel of Generator
	 */
	public JPanel getSideBarPanel();

	/**
	 * Gives you the current name of the generator as a string value.
	 *
	 * @return name of the Generator
	 */
	public String getName();

	/**
	 * Gives you the current type of the generator to sort into related menu.
	 *
	 * @return type of Generator
	 */
	public GeneratorType getGenType();

	/**
	 * Gives you the current status value of the generator.
	 *
	 * @return current Generator Status
	 */
	public Status getGenStatus();

	/**
	 * Stops the current Generators calculations and returns it to ready status.
	 *
	 */
	public void stopGenerator();

	/**
	 * Sets status to ready.
	 */
	public void setReady();

	/**
	 * Method that gets called on Thread.start() to run calculations of
	 * generator.
	 *
	 */
	@Override
	public void run();

	/**
	 * Returns the error message of the generator.
	 *
	 * @return error message
	 */
	public String getErrorMessage();

	/**
	 * Collection of generator status values.
	 */
	public enum Status {

		/**
		 * Generator is ready
		 */
		READY,
		/**
		 * Generator is currently calculating
		 */
		CALCULATE,
		/**
		 * Generator is paused
		 */
		PAUSED,
		/**
		 * Generator is finished with calculating the image
		 */
		FINISHED,
		/**
		 * Generator stopped
		 */
		STOP,
		/**
		 * Error occured
		 */
		ERROR
	}
}
