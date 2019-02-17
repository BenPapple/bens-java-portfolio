package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Creates a k-ary tree with randomly filled math formulas and calculates a tree traversal using those formulas.
 * 
 * @author BenGe47
 *
 */
public class MathRTreeArt {

	private int maxXPixel;
	private int maxYPixel;
//	private List<String> FormulaOneVar = new ArrayList<>(Arrays.asList("SINX", "COSX", "ABSCOSX", "ABSSINX", "SQUARE"));
//	private List<String> FormulaTwoVar = new ArrayList<>(
//			Arrays.asList("SINXPLUSY", "COSXPLUSY", "XTIMESY", "SINCOSXPLUSY", "XTOTHEPOWEROFY", "XSQUAREPLUSYSQUARE",
//					"XSQUARETIMESYSQUARE", "XPLUSYPLUSDMOD1", "MAXXY", "MINXY", "MAXXPLUSY" , "AVGXY"));
	private List<String> FormulaOneVar = new ArrayList<>(Arrays.asList(  "SINX", "SQUARE"));
	private List<String> FormulaTwoVar = new ArrayList<>(
			Arrays.asList("XPLUSYPLUSDMOD1", "MAXXY", "MINXY", "MAXXPLUSY" , "AVGXY", "XTIMESYTIMESD"));
	
	
	
	private List<String> FormulaAll = new ArrayList<>();
	private KaryTree myRandomTree;
	private Random mySeededRandom;
	private int treeDepth;

	/**
	 * Constructor.
	 * 
	 * @param inRandom random seeded
	 * @param inGenerations tree depth
	 * @param inMaxXPixel max picture size x
	 * @param inMaxYPixel max picture size y
	 */
	public MathRTreeArt(Random inRandom, int inGenerations, int inMaxXPixel, int inMaxYPixel) {
		mySeededRandom = inRandom;
		maxXPixel = inMaxXPixel;
		maxYPixel = inMaxYPixel;
		FormulaAll.addAll(FormulaOneVar);
		FormulaAll.addAll(FormulaTwoVar);

		createRandomKaryTree(inGenerations);
	}

	/**
	 * Create kary tree with random formula in tree root, then call method to
	 * fill tree.
	 * 
	 * @param inGenerations tree depth
	 */
	private void createRandomKaryTree(int inGenerations) {
		treeDepth = inGenerations;
		String randomFormula = receiveRandomString(FormulaAll);
		myRandomTree = new KaryTree(randomFormula, null, null);
		fillRandomTree(myRandomTree, treeDepth - 1);
	}

	/**
	 * Fill kary tree nodes with math formulas or make leave according to
	 * root/node formula and input tree depth.
	 *
	 * @param inRoot Node which will be added to or becomes leave
	 * @param inDepth tree depth from user input
	 */
	private void fillRandomTree(KaryTree inRoot, int inDepth) {

		KaryTree child;
		KaryTree child2;

		if (inDepth >= 0) {
			String wahlFormel;

			if (FormulaOneVar.contains(inRoot.getFormula()) && inDepth > 0) {
				wahlFormel = receiveRandomString(FormulaAll);
				child = new KaryTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, null);

				fillRandomTree(child, inDepth - 1);
			} else if (FormulaOneVar.contains(inRoot.getFormula()) && inDepth == 0) {
				wahlFormel = receiveRandomString(FormulaOneVar);
				child = new KaryTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, null);

				fillRandomTree(child, inDepth - 1);
			}

			if (FormulaTwoVar.contains(inRoot.getFormula()) && inDepth > 0) {
				wahlFormel = receiveRandomString(FormulaAll);
				child = new KaryTree(wahlFormel, null, null);

				wahlFormel = receiveRandomString(FormulaAll);
				child2 = new KaryTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, child2);

				fillRandomTree(child, inDepth - 1);
				fillRandomTree(child2, inDepth - 1);
			} else if (FormulaTwoVar.contains(inRoot.getFormula()) && inDepth == 0) {
				wahlFormel = receiveRandomString(FormulaOneVar);

				child = new KaryTree(wahlFormel, null, null);
				wahlFormel = receiveRandomString(FormulaOneVar);

				child2 = new KaryTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, child2);
			}
		}
	}

	/**
	 * Choose a random entry in a list and return.
	 *
	 * @param inList list with strings
	 * 
	 * @return String with one random formula
	 */
	private String receiveRandomString(List<String> inList) {
		int randomNum;
		randomNum = mySeededRandom.nextInt(inList.size());
		String randomFormula = inList.get(randomNum);
		return randomFormula;
	}

	/**
	 * Start recursive calculation recursiveColorValCalc with xy coordinate
	 * input.
	 * 
	 * @param inX x coordinate
	 * @param inY y coordinate
	 * 
	 * @return double for color value
	 */
	public double startRecCalc(double inX, double inY) {
		return recursiveColorValCalc(inX, inY, myRandomTree.getFormula(), treeDepth, myRandomTree);
	}

	/**
	 * Calculation of pixel color by recursive RTree traversal.
	 *
	 * @param inX x-pixel coordinate
	 * @param inY y-pixel coordinate
	 * @param inFormula math formula to process
	 * @param TreeDepth current tree depth
	 * @param inKaryTree k-ary tree for calculations
	 * 
	 * @return Double color value
	 */
	public double recursiveColorValCalc(double inX, double inY, String inFormula, Integer TreeDepth,
			KaryTree inKaryTree) {

		int TreeDepthNew = TreeDepth;

		if ((inKaryTree.getLeftChild() != null) && (FormulaOneVar.contains(inFormula))) {			
			switch (inFormula) {			
			// Einwert
			case "SINX":
				return Math.sin(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						inKaryTree.getLeftChild()));
			case "ABSCOSX":
				return Math.abs(Math.cos(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						inKaryTree.getLeftChild())));
			case "COSX":
				return Math.cos(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						inKaryTree.getLeftChild()));
			case "ABSSINX":
				return Math.abs(Math.sin(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						inKaryTree.getLeftChild())));
			case "SQUARE":
				return Math.pow((recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						inKaryTree.getLeftChild())), 2);

			default:
				throw new AssertionError();
			}
		} else {
			if ((inKaryTree.getLeftChild() != null) && (inKaryTree.getRightChild() != null)) {

				switch (inFormula) {
				// Two value formulas
				case "XTIMESY":
					return (recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							inKaryTree.getLeftChild())
							* recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(), TreeDepth,
									inKaryTree.getRightChild()));
				case "COSXPLUSY":
					return Math
							.cos(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild())
									+ recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
											TreeDepth,
											inKaryTree.getRightChild()) / 2);
				case "SINXPLUSY":
					return Math
							.sin(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild())
									+ recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
											TreeDepth,
											inKaryTree.getRightChild()) / 2);
				case "SINCOSXPLUSY":
					return Math.sin(Math.cos(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(),
							TreeDepthNew, inKaryTree.getLeftChild())
							+ recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(), TreeDepth,
									inKaryTree.getRightChild())));
				case "XTOTHEPOWEROFY":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()),
									recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
											TreeDepthNew, inKaryTree.getRightChild())));
				case "XSQUAREPLUSYSQUARE":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()), 2)
							+ Math.pow(recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild()), 2));
				case "XSQUARETIMESYSQUARE":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()), 2)
							* Math.pow(recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild()), 2));
				case "XPLUSYPLUSDMOD1":
					return (recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()) / recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
											inKaryTree.getRightChild()) / treeDepth ) % 1;
				case "MAXXY":
					return (Math
							.max(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()), recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild())));
				case "MINXY":
					return (Math
							.min(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()), recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild())));
				case "MAXXPLUSY":
					return (Math
							.max(recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()) + recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild()), 1));
				case "AVGXY":
					return ((recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									inKaryTree.getLeftChild()) + recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(),
									TreeDepthNew, inKaryTree.getRightChild()))) / 2;
				case "XTIMESYTIMESD":
					return recursiveColorValCalc(inX, inY, inKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							inKaryTree.getLeftChild())
							* recursiveColorValCalc(inX, inY, inKaryTree.getRightChild().getFormula(), TreeDepth,
									inKaryTree.getRightChild()) * treeDepth;

				default:
					throw new AssertionError();
				}
			}
		}

		if ((inKaryTree.getLeftChild() == null) && (inKaryTree.getRightChild() == null)) {
			if (inFormula.equals("SINX") || inFormula.equals("COSX")) {
				return (inX / maxXPixel);
			} else {
				return (inY / maxYPixel);
			}
		} else {

			return 1.0;
		}

	}

}
