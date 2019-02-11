package generator;

import data.GlobalSettings;
import data.karyTree;
import gui.MainCanvasPanel;
import gui.SideBarRTree;
import java.awt.BorderLayout;
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
import javax.swing.JPanel;

/**
 * Implements a random tree art generator by putting math formulas into a random
 * tree structure, this tree then calculates color values for xy coordinates.
 *
 * @author BenGe47
 */
public class RandomTreeArt extends AGenerator {

	/**
	 * 
	 */
	protected SideBarRTree guiSideBar;

	/**
	 * 
	 */
	protected Random rand;
	private karyTree myRandomTree;

	/**
	 * 
	 */
	protected String[] modelNames;
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
			Arrays.asList("SINXPLUSY", "COSXPLUSY", "XMALY", "SINCOSXPLUSY"));
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
		this.generatorName = name;
		this.myMnemonicKey = 'R';
		this.myCanvas = mainCanvas;
		this.PanelSidebar = new JPanel();
		this.generatorDescr = "Lindenmayer system";
		this.rand = new Random();
		this.generatorType = GlobalSettings.GeneratorType.RTREE;

		guiSideBar = new SideBarRTree(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(IGenerator.Status.READY);
			}
		});

		int numColorCalc = 4;
		int k = -1;

		FormulaAll.addAll(FormulaOneVar);
		FormulaAll.addAll(FormulaTwoVar);

		modelNames = new String[numColorCalc];

		k++;
		modelNames[k] = "BlackWhite";

		k++;
		modelNames[k] = "RGB";

		k++;
		modelNames[k] = "White-Noise-RGB (+ 3 Randoms a color)";

		k++;
		modelNames[k] = "Atari-colorful";

		createSideBarGUI();
	}

	@Override
	public void run() {
		startCalcTime();
		updateStatus(IGenerator.Status.CALCULATING);

		guiSideBar.setButtonsCalculating();

		try {
			Thread.sleep(50);

			maxXPixel = guiSideBar.getWidth();
			maxYPixel = guiSideBar.getHeight();

			createRandomKaryTree();
			System.out.println("randomTree finished");
			updateScreenPanel();

		} catch (OutOfMemoryError e) {
			errorMsg = "OutOfMemory";
			updateStatus(IGenerator.Status.ERROR);
			guiSideBar.setButtonsReady();
		} catch (InterruptedException ex) {
			Logger.getLogger(RandomTreeArt.class.getName()).log(Level.SEVERE, null, ex);
		}

		guiSideBar.setButtonsReady();
		endCalcTime();
		updateStatus(IGenerator.Status.FINISHED);

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
		this.myCanvas.setImage(image);
	}

	/**
	 * Calculate color value for each xy coordinate with math formulas in kary
	 * tree.
	 *
	 * @param gc graphics context 2D
	 */
	private void drawPicture(Graphics2D gc) {

		double rVal, gVal, bVal = 0;
		int rgbVal;

		for (int x = 1; x <= maxXPixel; x++) {
			for (int y = 1; y <= maxYPixel; y++) {
				double xDouble = x;
				double yDouble = y;
				Color rgbCalc;

				double colorDouble = recursiveColorValCalc(xDouble, yDouble, myRandomTree.getFormula(),
						guiSideBar.getGenerations(), myRandomTree);
				switch (guiSideBar.getColorModelIndex()) {
				case 0:
					rVal = colorDouble;
					gVal = colorDouble;
					bVal = colorDouble;
					break;
				case 1:
					rVal = colorDouble * 0.75;
					gVal = colorDouble * 0.5;
					bVal = colorDouble * 0.25;
					break;
				case 2:
					rVal = colorDouble * colorDouble * Math.random() + Math.random() * colorDouble + colorDouble;
					gVal = colorDouble * colorDouble * Math.random() + Math.random() * colorDouble + colorDouble;
					bVal = colorDouble * colorDouble * Math.random() + Math.random() * colorDouble + colorDouble;
					break;
				case 3:
					rVal = colorDouble * 0.75;
					gVal = colorDouble * 0.5;
					bVal = colorDouble * 0.25;
					if (colorDouble >= 1) {
						colorDouble = 0.99;
					}
					rgbVal = (int) (colorDouble * 16777215.0);
					break;
				default:
					throw new AssertionError();
				}

				if (guiSideBar.getColorModelIndex() == 3) {
					if (colorDouble > 1.0) {
						colorDouble = 1.0;
					}
					if (colorDouble < 0) {
						colorDouble = 0.0;
					}
					rgbVal = (int) (colorDouble * 16777215.0);

					rgbCalc = new Color(rgbVal);
				} else {
					if (rVal > 1) {
						rVal = 1;
					}
					if (gVal > 1) {
						gVal = 1;
					}
					if (bVal > 1) {
						bVal = 1;
					}
					if (rVal < 0) {
						rVal = 0.0;
					}
					if (gVal < 0) {
						gVal = 0.0;
					}
					if (bVal < 0) {
						bVal = 0.0;
					}
//					System.out.println("RGB: " + rVal + "  " + gVal + "  " + bVal);
					rgbCalc = new Color((float) rVal, (float) gVal, (float) bVal);
				}
				gc.setColor(rgbCalc);
				gc.drawLine(x, y, x, y);
			}
		}

	}

	/**
	 * Calculation of pixel color by recursive RTree traversal.
	 *
	 * @param x x-pixel coordinate
	 * @param y y-pixel coordinate
	 * @param inFormula math formula to process
	 * @param TreeDepth current tree depth
	 * @param myKaryTree k-ary tree for calculations
	 * @return Double color value
	 */
	public double recursiveColorValCalc(double x, double y, String inFormula, Integer TreeDepth, karyTree myKaryTree) {

		int TreeDepthNew = TreeDepth;

		if ((myKaryTree.getLeftChild() != null) && (FormulaOneVar.contains(inFormula))) {

			switch (inFormula) {
			// Einwert
			case "SINX":
				return Math.sin(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "SINY":
				return Math.sin(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "COSX":
				return Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));
			case "COSY":
				return Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
						myKaryTree.getLeftChild()));

			default:
				throw new AssertionError();
			}
		} else {
			if ((myKaryTree.getLeftChild() != null) && (myKaryTree.getRightChild() != null)) {

				switch (inFormula) {
				// One value formula
				case "SINX":
					return Math.sin(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild()));
				case "SINY":
					return Math.sin(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild()));
				case "COSX":
					return Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild()));
				case "COSY":
					return Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild()));

				// Two value formula
				case "XMALY":
					return (recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild())
							* recursiveColorValCalc(x, y, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild()));
				case "COSXPLUSY":
					return Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild())
							+ recursiveColorValCalc(x, y, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild()) / 2);
				case "SINXPLUSY":
					return Math.sin(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(), TreeDepthNew,
							myKaryTree.getLeftChild())
							+ recursiveColorValCalc(x, y, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild()) / 2);
				case "SINCOSXPLUSY":
					return Math.sin(Math.cos(recursiveColorValCalc(x, y, myKaryTree.getLeftChild().getFormula(),
							TreeDepthNew, myKaryTree.getLeftChild())
							+ recursiveColorValCalc(x, y, myKaryTree.getRightChild().getFormula(), TreeDepth,
									myKaryTree.getRightChild())));

				default:
					throw new AssertionError();
				}
			}
		}

		if ((myKaryTree.getLeftChild() == null) && (myKaryTree.getRightChild() == null)) {
			if (inFormula.equals("SINX") || inFormula.equals("COSX")) {
				return (x / maxXPixel);
			} else {
				return (y / maxYPixel);
			}
		} else {

			return 1.0;
		}

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

	/**
	 * Choose a random entry in a list and return.
	 *
	 * @param inList
	 * @return String with one random formula
	 */
	private String receiveRandomString(List<String> inList) {
		int randomNum = ThreadLocalRandom.current().nextInt(0, inList.size());
		String randomFormula = inList.get(randomNum);
		return randomFormula;
	}

	@Override
	public void createSideBarGUI() {
		PanelSidebar.add(guiSideBar.getSideBarPnl(), BorderLayout.CENTER);
	}

	@Override
	public void stopGenerator() {
		this.status = IGenerator.Status.STOP;
	}

}
