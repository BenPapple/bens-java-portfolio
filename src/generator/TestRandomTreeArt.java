package generator;

import data.GlobalSettings;
import data.karyTree;
import gui.MainCanvasPanel;
import gui.SideBarTestRTree;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Implements a random tree art generator by putting math formulas into a random
 * tree structure, this tree then calculates color values for xy coordinates.
 *
 * @author BenGe47
 */
public class TestRandomTreeArt extends RandomTreeArt {

	/**
	 * Constructor.
	 *
	 * @param mainCanvas
	 * @param name
	 */
	public TestRandomTreeArt(MainCanvasPanel mainCanvas, String name) {
		super(mainCanvas, name);
		setGenType(GlobalSettings.GeneratorType.TEST);
		FormulaOneVar = new ArrayList<>(Arrays.asList("FUNKTIONEINS1", "FUNKTIONEINS2"));
		FormulaTwoVar = new ArrayList<>(Arrays.asList("FUNKTIONZWEI1", "FUNKTIONZWEI2"));

		this.setName(name);
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		this.rand = new Random();
		guiSideBar = new SideBarTestRTree(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(IGenerator.Status.READY);
			}
		});

		FormulaAll.addAll(FormulaOneVar);
		FormulaAll.addAll(FormulaTwoVar);

		createSideBarGUI();

		FormulaAll.clear();
		FormulaAll.addAll(FormulaOneVar);
		FormulaAll.addAll(FormulaTwoVar);

	}

	/**
	 * Pixel color calculation by recursive k-ary tree traversal.
	 *
	 * @param inX x coordinate pixel
	 * @param inY y coordinate pixel
	 * @param inFormula current formula from tree node
	 * @param inTreeDepth current tree depth
	 * @param inRandomTree complete k-ary tree
	 * @return Double
	 */
	@Override
	public double recursiveColorValCalc(double inX, double inY, String inFormula, Integer inTreeDepth,
			karyTree inRandomTree) {

		int TreeDepthNew = inTreeDepth - 1;

		if ((inRandomTree.getLeftChild() != null) && (inRandomTree.getRightChild() == null)
				&& (FormulaOneVar.contains(inFormula))) {

			switch (inFormula) {
			// formula with one variable
			case "FUNKTIONEINS1":
				return Math.sin(recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), TreeDepthNew,
						inRandomTree.getLeftChild()));
			case "FUNKTIONEINS2":
				return Math.sin(recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), TreeDepthNew,
						inRandomTree.getLeftChild()));

			// formula with two variables
			case "FUNKTIONZWEI1":
				return (recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), TreeDepthNew,
						inRandomTree.getLeftChild())
						* recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), inTreeDepth,
								inRandomTree.getRightChild()));
			case "FUNKTIONZWEI2":
				return Math.cos(recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), TreeDepthNew,
						inRandomTree.getLeftChild())
						+ recursiveColorValCalc(inX, inY, inRandomTree.getFormula(), inTreeDepth,
								inRandomTree.getRightChild()) / 2);

			default:
				throw new AssertionError();
			}
		} else {
			if ((inRandomTree.getLeftChild() != null) && (inRandomTree.getRightChild() != null)) {

				switch (inFormula) {
				// Einwert
				case "FUNKTIONEINS1":
					return Math.pow(recursiveColorValCalc(inX, inY, inRandomTree.getLeftChild().getFormula(),
							TreeDepthNew, inRandomTree.getLeftChild()), 2);
				case "FUNKTIONEINS2":
					return Math.pow(recursiveColorValCalc(inX, inY, inRandomTree.getLeftChild().getFormula(),
							TreeDepthNew, inRandomTree.getLeftChild()), 2);

				// Zweiwerte
				case "FUNKTIONZWEI1":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, inRandomTree.getLeftChild().getFormula(), TreeDepthNew,
									inRandomTree.getLeftChild()), 2)
							+ Math.pow(recursiveColorValCalc(inX, inY, inRandomTree.getRightChild().getFormula(),
									inTreeDepth, inRandomTree.getRightChild()), 2));
				case "FUNKTIONZWEI2":
					return (Math
							.pow(recursiveColorValCalc(inX, inY, inRandomTree.getLeftChild().getFormula(), TreeDepthNew,
									inRandomTree.getLeftChild()), 2)
							+ Math.pow(recursiveColorValCalc(inX, inY, inRandomTree.getRightChild().getFormula(),
									inTreeDepth, inRandomTree.getRightChild()), 2));

				default:
					throw new AssertionError();
				}
			}
		}

		if (inFormula.equals("FUNKTIONEINS1")) {
			return (inX / maxXPixel);
		} else {
			return (inY / maxYPixel);
		}
	}

}
