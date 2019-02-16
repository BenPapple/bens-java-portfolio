package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Creates a String with LSystem rules from input which can then be used by a
 * drawing turtle.
 * 
 * @author BenGe47
 *
 */
public class LSystem {
	
	private HashMap<String, String> formulaMap = new HashMap<String, String>();
	private String tempStartingSeq;
	private int tempGenerations;
	private String tempProductionRules;
	private String outTurtleLSystem;
	private final List<String> ALPHABETLIST = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));

	/**
	 * Constructor.
	 * 
	 * @param inStartingSeq LSystem start sequence
	 * @param inProdRules LSystem production rules
	 * @param inGen LSystem generations
	 */
	public LSystem(String inStartingSeq, String inProdRules, int inGen) {
		tempStartingSeq = inStartingSeq;
		tempGenerations = inGen;
		tempProductionRules = inProdRules;

		fillFormulaStrings();
		outTurtleLSystem = buildString();
	}

	/**
	 * Returns calculated turtle string of this class for use with drawing
	 * turtle.
	 * 
	 * @return String with turle data
	 */
	public String getTurtleData() {
		return outTurtleLSystem;
	}

	/**
	 * Helper Method to build the long string for the drawing turtle method.
	 *
	 * @return String for the drawing turtle
	 */
	private String buildString() {
		// tempStartingSeq = guiSideBar.getStartingSequence();
		tempStartingSeq = tempStartingSeq.replace(",", "");
		// tempGenerations = guiSideBar.getGenerations();
		StringBuilder b = new StringBuilder();
		b.append(tempStartingSeq);

		// Split ProductionRules into corresponding variable forumula A to Z
		fillFormulaStrings();

		// create string usable by drawing turtle
		for (int i = 0; i < tempGenerations; i++) {
			tempStartingSeq = b.toString();
			b = new StringBuilder();
			for (int j = 0; j < tempStartingSeq.length(); j++) {

				for (String itemInList : ALPHABETLIST) {
					if (tempStartingSeq.charAt(j) == itemInList.charAt(0)) {
						b.append(formulaMap.get(itemInList));
					}
				}
				if (tempStartingSeq.charAt(j) == '+') {
					b.append("+");
				}
				if (tempStartingSeq.charAt(j) == '-') {
					b.append("-");
				}
				if (tempStartingSeq.charAt(j) == '[') {
					b.append("[");
				}
				if (tempStartingSeq.charAt(j) == ']') {
					b.append("]");
				}
			}
		}
		return b.toString();
	}

	/**
	 * Helper Method to parse formulas into hashmap with the corresponding
	 * values from the textfield tfProductionRules.
	 */
	private void fillFormulaStrings() {
		Integer indexStart;
		Integer indexEnd = 0;
		Integer indexCount = 1;
		// String tempProductionRules = guiSideBar.getProductionRules();
		// String tempStartingSequence = guiSideBar.getStartingSequence();

		// Search for strings in productionrules that indicate a rule for a
		// letter A to Z and put them into hashmap.
		for (String itemInList : ALPHABETLIST) {
			String compare = "(";
			compare += itemInList;
			compare += ",";
			if (tempProductionRules.contains(itemInList)) {
				indexStart = tempProductionRules.indexOf(compare);
				indexCount += 1;
				for (int i = indexStart; i < tempProductionRules.length(); i++) {
					indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf(compare) + indexCount);
				}
				formulaMap.put(itemInList, tempProductionRules.substring(indexStart + 3, indexEnd));
			}
		}

		// in case a letter only occurs in the starting sequence and not in the
		// production rules. So it can replace itself with itself.
		for (String itemInList : ALPHABETLIST) {
			String compare = "(";
			compare += itemInList;
			if (tempStartingSeq.contains(itemInList) && (!tempProductionRules.contains(compare))) {
				formulaMap.put(itemInList, itemInList);
			}
		}

	}

}
