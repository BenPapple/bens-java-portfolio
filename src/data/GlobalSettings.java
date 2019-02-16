package data;

/**
 * Collection of global variables.
 *
 * @author BenGe47
 */
public class GlobalSettings {

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
		CALCULATING,
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
		 * Error occurred
		 */
		ERROR
	}

	/**
	 * ENUMs which declare into which gui menu a generator can be sorted.
	 */
	public enum GeneratorType {

		/**
		 *
		 */
		TEST,
		/**
		 *
		 */
		CELLULAR,
		/**
		 *
		 */
		LSYSTEM,
		/**
		 *
		 */
		SCIENCE,
		/**
		 *
		 */
		GAME,
		/**
		 *
		 */
		RTREE
	}

}
