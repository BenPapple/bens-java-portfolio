package generator;

import data.GlobalSettings;
import data.karyTree;
import gui.MainCanvasPanel;
import gui.SideBarRTree;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements a random tree art generator by putting math formulas into a random
 * tree structure, this tree then calculates color values for xy coordinates.
 *
 * @author BenGe47
 */
public class RandomTreeArt extends AGenerator {
	private Random mySeededRandom;
	private int generatedSeed;
	private karyTree myRandomTree;

	/**
	 * 
	 */
	protected SideBarRTree guiSideBar;

	/**
	 * 
	 */
	protected Random rand;

	/**
	 * 
	 */
	protected int maxXPixel;
	/**
	 * 
	 */
	protected int maxYPixel;

	/**
	 * 
	 */
	protected List<String> FormulaOneVar = new ArrayList<>(Arrays.asList("SINX", "COSX", "SINY", "COSY"));
	/**
	 * 
	 */
	protected List<String> FormulaTwoVar = new ArrayList<>(
			Arrays.asList("SINXPLUSY", "COSXPLUSY", "XTIMESY", "SINCOSXPLUSY", "XTOTHEPOWEROFY", "XSQUAREPLUSYSQUARE", "XSQUARETIMESYSQUARE"));
	/**
	 * 
	 */
	protected List<String> FormulaAll = new ArrayList<>();

	/**
	 * Constructor for a random tree art generator.
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of this generator
	 */
	public RandomTreeArt(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('R');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		this.rand = new Random();
		this.setGenType(GlobalSettings.GeneratorType.RTREE);

		guiSideBar = new SideBarRTree(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(GlobalSettings.Status.READY);
			}
		});

		FormulaAll.addAll(FormulaOneVar);
		FormulaAll.addAll(FormulaTwoVar);

		createSideBarGUI();
	}

	/**
	 * Create kary tree with random formula in tree root, then call method to
	 * fill tree.
	 *
	 */
	private void createRandomKaryTree() {
		int treeDepth = guiSideBar.getGenerations();
		String randomFormula = receiveRandomString(FormulaAll);
		myRandomTree = new karyTree(randomFormula, null, null);
		fillRandomTree(myRandomTree, treeDepth - 1);
	}

	@Override
	public void createSideBarGUI() {
		this.addToSidebar(guiSideBar.getSideBarPnl());
	}

	/**
	 * Calculate color value for each xy coordinate with math formulas in kary
	 * tree.
	 *
	 * @param gc graphics context 2D
	 */
	private void drawPicture(Graphics2D gc) {

		double rVal, gVal, bVal = 0;
		int averageRGB;
		int rgbVal;

		for (int x = 0; x < maxXPixel; x++) {
			// Stop and pause
			if (guiSideBar.isStopped()) {
				break;
			}
			while (guiSideBar.isPaused()) {
				updateStatus(GlobalSettings.Status.PAUSED);
				if (guiSideBar.isStopped()) {
					break;
				}
			}
			updateStatus(GlobalSettings.Status.CALCULATING);

			for (int y = 0; y < maxYPixel; y++) {
				double xDouble = x;
				double yDouble = y;
				Color rgbCalc;

				double colorDouble = recursiveColorValCalc(xDouble, yDouble, myRandomTree.getFormula(),
						guiSideBar.getGenerations(), myRandomTree);

				if (colorDouble > 1.0) {
					colorDouble = 1.0;
					System.out.println("is Bigger 1");
					
				}
				if (colorDouble < 0) {
					colorDouble = 0.0;
					System.out.println("is Negative");
				}

				rgbVal = (int) (colorDouble * 16777215.0);
				rgbCalc = new Color(rgbVal);

				switch (guiSideBar.getColorModelName()) {
				case "Preview All":
					while (x <= (guiSideBar.getWidth() * 0.25)) {
						averageRGB = (rgbCalc.getRed() + rgbCalc.getGreen() + rgbCalc.getBlue()) / 3;
						rgbCalc = new Color(averageRGB, averageRGB, averageRGB);
						break;
					}
					while (x > (guiSideBar.getWidth() * 0.25) && x <= (guiSideBar.getWidth() * 0.5)) {
						double randomNoise = Math.random();
						averageRGB = (rgbCalc.getRed() + rgbCalc.getGreen() + rgbCalc.getBlue()) / 3;
						double newAverageRGB = averageRGB * randomNoise;
						averageRGB = (int) newAverageRGB;
						rgbCalc = new Color(averageRGB, averageRGB, averageRGB);
						break;
					}
					while (x > (guiSideBar.getWidth() * 0.5) && x <= (guiSideBar.getWidth() * 0.75)) {
						break;
					}
					while (x > (guiSideBar.getWidth() * 0.75) && x <= (guiSideBar.getWidth())) {
						double randomNoise2 = Math.random();
						rVal = rgbCalc.getRed() * randomNoise2;
						gVal = rgbCalc.getGreen() * randomNoise2;
						bVal = rgbCalc.getBlue() * randomNoise2;
						rgbCalc = new Color((int) rVal, (int) gVal, (int) bVal);
						break;
					}

					break;
				case "Monochrome":
					averageRGB = (rgbCalc.getRed() + rgbCalc.getGreen() + rgbCalc.getBlue()) / 3;
					rgbCalc = new Color(averageRGB, averageRGB, averageRGB);
					break;
				case "Monochrome Noise":
					double randomNoise = Math.random();
					averageRGB = (rgbCalc.getRed() + rgbCalc.getGreen() + rgbCalc.getBlue()) / 3;
					double newAverageRGB = averageRGB * randomNoise;
					averageRGB = (int) newAverageRGB;
					rgbCalc = new Color(averageRGB, averageRGB, averageRGB);
					break;
				case "RGB White Noise":
					double randomNoise2 = Math.random();
					rVal = rgbCalc.getRed() * randomNoise2;
					gVal = rgbCalc.getGreen() * randomNoise2;
					bVal = rgbCalc.getBlue() * randomNoise2;
					rgbCalc = new Color((int) rVal, (int) gVal, (int) bVal);
					break;
				case "RGB":
					// default RGB calculations
					break;
				default:
					throw new AssertionError();
				}

				gc.setColor(rgbCalc);
				gc.drawLine(x, y, x, y);
			}
		}

	}

	/**
	 * Fill kary tree nodes with math formulas or make leave according to
	 * root/node formula and input tree depth.
	 *
	 * @param inRoot Node which will be added to or becomes leave
	 * @param inDepth tree depth from user input
	 */
	private void fillRandomTree(karyTree inRoot, int inDepth) {

		karyTree child;
		karyTree child2;

		if (inDepth >= 0) {
			String wahlFormel;

			if (FormulaOneVar.contains(inRoot.getFormula()) && inDepth > 0) {
				wahlFormel = receiveRandomString(FormulaAll);
				child = new karyTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, null);

				fillRandomTree(child, inDepth - 1);
			} else if (FormulaOneVar.contains(inRoot.getFormula()) && inDepth == 0) {
				wahlFormel = receiveRandomString(FormulaOneVar);
				child = new karyTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, null);

				fillRandomTree(child, inDepth - 1);
			}

			if (FormulaTwoVar.contains(inRoot.getFormula()) && inDepth > 0) {
				wahlFormel = receiveRandomString(FormulaAll);
				child = new karyTree(wahlFormel, null, null);

				wahlFormel = receiveRandomString(FormulaAll);
				child2 = new karyTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, child2);

				fillRandomTree(child, inDepth - 1);
				fillRandomTree(child2, inDepth - 1);
			} else if (FormulaTwoVar.contains(inRoot.getFormula()) && inDepth == 0) {
				wahlFormel = receiveRandomString(FormulaOneVar);

				child = new karyTree(wahlFormel, null, null);
				wahlFormel = receiveRandomString(FormulaOneVar);

				child2 = new karyTree(wahlFormel, null, null);
				inRoot.setBothChilds(child, child2);
			}
		}
	}

	@Override
	public String getFilePath() {
		// generate name for image with generator values to save into name
		String seperator = "_";
		StringBuilder outPath = new StringBuilder();
		outPath.append("RTree_");
		outPath.append("W");
		outPath.append(guiSideBar.getWidth());
		outPath.append(seperator);
		outPath.append("H");
		outPath.append(guiSideBar.getHeight());
		outPath.append(seperator);
		outPath.append("S");
		outPath.append(guiSideBar.getSeed());
		outPath.append(seperator);
		outPath.append("G");
		outPath.append(guiSideBar.getGenerations());
		// outPath.append("]");
		outPath.append(seperator);
		outPath.append(".png");
		return outPath.toString();
	}

	/**
	 * Choose a random entry in a list and return.
	 *
	 * @param inList
	 * @return String with one random formula
	 */
	private String receiveRandomString(List<String> inList) {
		int randomNum;
		randomNum = mySeededRandom.nextInt(inList.size());
		String randomFormula = inList.get(randomNum);
		return randomFormula;
	}

	/**
	 * Calculation of pixel color by recursive RTree traversal.
	 *
	 * @param inX x-pixel coordinate
	 * @param inY y-pixel coordinate
	 * @param inFormula math formula to process
	 * @param TreeDepth current tree depth
	 * @param myKaryTree k-ary tree for calculations
	 * @return Double color value
	 */
	public double recursiveColorValCalc(double inX, double inY, String inFormula, Integer TreeDepth,
			karyTree myKaryTree) {

		int TreeDepthNew = TreeDepth;

		if ((myKaryTree.getLeftChild() != null) && (FormulaOneVar.contains(inFormula))) {

			switch (inFormula) {
			// Einwert
			case "SINX":
				return Math.sin(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "SINY":
				return Math.sin(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "COSX":
				return Math.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "COSY":
				return Math.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));

			default:
				throw new AssertionError();
			}
		} else {
			if ((myKaryTree.getLeftChild() != null) && (myKaryTree.getRightChild() != null)) {

				switch (inFormula) {
				// One value formula
				case "SINX":
					return Math
							.sin(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()));
				case "SINY":
					return Math
							.sin(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()));
				case "COSX":
					return Math
							.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()));
				case "COSY":
					return Math
							.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()));

				// Two value formula
				case "XTIMESY":
					return (recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild())
							* recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild()));
				case "COSXPLUSY":
					return Math
							.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild())
									+ recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(),
											TreeDepth,
											myKaryTree.getRightChild()) / 2);
				case "SINXPLUSY":
					return Math
							.sin(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild())
									+ recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(),
											TreeDepth,
											myKaryTree.getRightChild()) / 2);
				case "SINCOSXPLUSY":
					return Math.sin(Math.cos(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(),
							TreeDepthNew, myKaryTree.getLeftChild())
							+ recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild())));
				case "XTOTHEPOWEROFY":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()),
									recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(),
											TreeDepthNew, myKaryTree.getRightChild())));
				case "XSQUAREPLUSYSQUARE":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()), 2)
							+ Math.pow(recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(),
									TreeDepthNew, myKaryTree.getRightChild()), 2));
				case "XSQUARETIMESYSQUARE":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
									myKaryTree.getLeftChild()), 2)
							* Math.pow(recursiveColorValCalc(inX, inY, myKaryTree.getRightChild().getFormula(),
									TreeDepthNew, myKaryTree.getRightChild()), 2));

				default:
					throw new AssertionError();
				}
			}
		}

		if ((myKaryTree.getLeftChild() == null) && (myKaryTree.getRightChild() == null)) {
			if (inFormula.equals("SINX") || inFormula.equals("COSX")) {
				return (inX / maxXPixel);
			} else {
				return (inY / maxYPixel);
			}
		} else {

			return 1.0;
		}

	}

	@Override
	public void run() {
		startCalcTime();
		updateStatus(GlobalSettings.Status.CALCULATING);

		// use user input seed
		if (guiSideBar.usingFieldSeed()) {
			// check if user input is integer range
			try {
				mySeededRandom = new Random(guiSideBar.getSeed());
			} catch (Exception ne) {
				showWarning("Randomness has to be in integer range. Now using random seed.");
				// use random seed instead of user input one
				generatedSeed = ThreadLocalRandom.current().nextInt(0, 2147483647);
				guiSideBar.setSeedText(Integer.toString(generatedSeed));
				mySeededRandom = new Random(generatedSeed);
			}

		} else {// use newly generated random seed
			generatedSeed = ThreadLocalRandom.current().nextInt(0, 2147483647);
			guiSideBar.setSeedText(Integer.toString(generatedSeed));
			mySeededRandom = new Random(generatedSeed);
		}

		guiSideBar.setButtonsCalculating();

		try {
			Thread.sleep(50);

			maxXPixel = guiSideBar.getWidth();
			maxYPixel = guiSideBar.getHeight();

			createRandomKaryTree();
			updateScreenPanel();

		} catch (OutOfMemoryError e) {
			setErrorMsgText("OutOfMemory");
			updateStatus(GlobalSettings.Status.ERROR);
			guiSideBar.setButtonsReady();
		} catch (InterruptedException ex) {
			Logger.getLogger(RandomTreeArt.class.getName()).log(Level.SEVERE, null, ex);
		}

		guiSideBar.setButtonsReady();
		endCalcTime();
		updateStatus(GlobalSettings.Status.FINISHED);

	}

	@Override
	public void setLoadedValues(String inPath) {
		// match filename to allowed regex pattern
		// Casts are covered with NumberFormatException too
		try {
			// example regex RTree_W640_H480_S1142650635_G7_.png
			String pattern = "\\p{Alpha}*_W\\d*_H\\d*_S\\d*_G\\d*_.png";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(inPath);
			if (!m.find()) {
				throw new IllegalArgumentException();
			}
			String[] splitIn = inPath.split("_");
			System.out.println(Arrays.toString(splitIn));
			String width = splitIn[1];
			guiSideBar.setWidth(Integer.parseInt(width.substring(1)));
			String height = splitIn[2];
			guiSideBar.setHeight(Integer.parseInt(height.substring(1)));
			String generations = splitIn[4];
			guiSideBar.setGenerations(Integer.parseInt(generations.substring(1)));
			String seed = splitIn[3];
			guiSideBar.setSeedText(Integer.toString((Integer.parseInt(seed.substring(1)))));
			guiSideBar.setCbSeed();
		} catch (IllegalArgumentException exception) {
			System.out.println(exception);
			showWarning("  File name not correctly formatted."
					+ "\n"
					+ "\n  Correct Example:"
					+ "\n"
					+ "\n  RTree_W640_H480_S1142650635_G7_.png  "
					+ "\n ");
		}

	}

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		setGenStatus(GlobalSettings.Status.STOP);
	}

	/**
	 * Prepares and updates mainCanvas with calculated color value for each xy
	 * coordinate.
	 *
	 */
	private void updateScreenPanel() {

		BufferedImage image = new BufferedImage(guiSideBar.getWidth(), guiSideBar.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(0, 0, guiSideBar.getWidth(), guiSideBar.getHeight());
		g2d.setColor(guiSideBar.getColor());

		drawPicture(g2d);

		g2d.dispose();
		this.setMainCanvasToImage(image);
	}

}
