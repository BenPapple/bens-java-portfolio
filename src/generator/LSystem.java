package generator;

import data.GlobalSettings;
import gui.MainCanvasPanel;
import gui.SideBarLSystem;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javax.swing.JPanel;

/**
 * Lindenmayer system
 *
 * @author BenGe47
 */
public class LSystem extends AGenerator {

	private SideBarLSystem guiSideBar;
	private int pixelGap = 15;
	private String formatedString;
	private String forumulaA;
	private String forumulaB;
	private String forumulaC;
	private String forumulaD;
	private String forumulaE;
	private String forumulaF;
	private final int STEP = 10;
	private Stack<Double> drawingSymbolStack = new Stack<Double>();
	private double currentAngle = -90;
	private double currentX = 0;
	private double currentY = 0;
	private double oldCurrentX;
	private double oldCurrentY;
	private double minX = 0;
	private double minY = 0;
	private double maxX = 0;
	private double maxY = 0;
	private double scaleX;
	private double scaleY;
	private final List<String> ALPHABETLIST = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F"));

	/**
	 * Constructor for an L-system.
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of this generator
	 */
	public LSystem(MainCanvasPanel mainCanvas, String name) {
		this.generatorName = name;
		this.myMnemonicKey = 'L';
		this.myCanvas = mainCanvas;
		this.PanelSidebar = new JPanel();
		this.generatorDescr = "Lindenmayer system";
		new Random();
		this.generatorType = GlobalSettings.GeneratorType.LSYSTEM;

		guiSideBar = new SideBarLSystem(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(IGenerator.Status.READY);
			}
		});
		createSideBarGUI();
	}

	@Override
	public void run() {
		startCalcTime();
		updateStatus(IGenerator.Status.CALCULATING);
		guiSideBar.setButtonsCalculating();

		formatedString = "";
		try {

			if (isInputCorrect()) {
				formatedString = buildString();
				while (!guiSideBar.isStopped()) {

					updateScreenPanel();

					guiSideBar.setStopped();

					while (guiSideBar.isPaused()) {
						updateStatus(IGenerator.Status.PAUSED);
						if (guiSideBar.isStopped()) {
							break;
						}
					}
					updateStatus(IGenerator.Status.CALCULATING);
				}
			}

			guiSideBar.setButtonsReady();
			endCalcTime();
			updateStatus(IGenerator.Status.FINISHED);

		} catch (OutOfMemoryError e) {
			errorMsg = "OutOfMemory";
			updateStatus(IGenerator.Status.ERROR);
			guiSideBar.setButtonsReady();
		}

	}

	/**
	 * Check all the texfields if they are correctly formatted and return true
	 * if they are.
	 * 
	 * @return true when input is ok
	 */
	private boolean isInputCorrect() {

		Boolean abcCorrect = false;
		Boolean rulesCorrect = false;

		String inPath = guiSideBar.getStartingSequence();
		String pattern = "[A-Z+-]*";
		boolean matches = inPath.matches(pattern);
		if (matches) {
			abcCorrect = true;
		} else {
			showWarning("Starting Sequence wrong."
					+ "\nA-Z,+,- allowed.");
		}

		inPath = guiSideBar.getProductionRules();
		pattern = "(\\([A-Z],[A-Z\\+\\-\\[\\]]*\\),{0,1})*";
		matches = inPath.matches(pattern);
		if (matches) {
			rulesCorrect = true;
		} else {
			showWarning("Production rules wrong."
					+ "\n(A,AAA+-[]),(B,CDF+-[]) allowed.");
		}

		if (abcCorrect & rulesCorrect) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Draws a L-system onto mainCanvas with a turtle drawer.
	 *
	 */
	private void updateScreenPanel() {

		BufferedImage image = new BufferedImage(guiSideBar.getWidth(), guiSideBar.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(guiSideBar.getBGColor());
		g2d.fillRect(0, 0, guiSideBar.getWidth(), guiSideBar.getHeight());
		g2d.setColor(guiSideBar.getColor());

		drawLinesFromProductionString(g2d, true);

		drawLinesFromProductionString(g2d, false);

		g2d.dispose();
		this.myCanvas.setImage(image);
	}

	/**
	 * Can calculate an L-system with scale factor while virtual or draws on
	 * mainCanvas while not virtual.
	 *
	 * @param gc graphics 2D
	 * @param virtual determines if drawing while calculating
	 */
	private void drawLinesFromProductionString(Graphics2D gc, Boolean virtual) {

		if (virtual) {
			minX = 0;
			minY = 0;
			maxX = 0;
			maxY = 0;
			currentAngle = -90;
			currentX = 0;
			currentY = 0;
			scaleX = 1;
			scaleY = 1;
		} else {
			currentAngle = -90;
			currentX = (Math.abs(minX) * scaleX) + pixelGap;
			currentY = (Math.abs(minY) * scaleY) + pixelGap;
		}

		for (int i = 0; i < formatedString.length(); i++) {
			// Stop and pause
			if (guiSideBar.isStopped()) {
				break;
			}
			while (guiSideBar.isPaused()) {
				updateStatus(IGenerator.Status.PAUSED);
				if (guiSideBar.isStopped()) {
					break;
				}
			}
			updateStatus(IGenerator.Status.CALCULATING);
			oldCurrentY = currentY;
			oldCurrentX = currentX;
			if ((formatedString.charAt(i) == 'A') || (formatedString.charAt(i) == 'B')
					|| (formatedString.charAt(i) == 'C') || (formatedString.charAt(i) == 'D')
					|| (formatedString.charAt(i) == 'E') || (formatedString.charAt(i) == 'F')) {
				currentX += cos(Math.toRadians(currentAngle)) * (STEP * scaleX);
				currentY += sin(Math.toRadians(currentAngle)) * (STEP * scaleY);
				if (virtual) {
					if (minX > currentX) {
						minX = (int) currentX;
					}
					if (minY > currentY) {
						minY = (int) currentY;
					}
					if (maxX < currentX) {
						maxX = (int) currentX;
					}
					if (maxY < currentY) {
						maxY = (int) currentY;
					}
				} else {
					drawSingleLine(gc);
				}
			}
			if ((formatedString.charAt(i) == '+')) {
				currentAngle += guiSideBar.getAngle();
			}
			if ((formatedString.charAt(i) == '-')) {
				currentAngle -= guiSideBar.getAngle();
			}
			if ((formatedString.charAt(i) == '[')) {
				drawingSymbolStack.push(currentX);
				drawingSymbolStack.push(currentY);
				drawingSymbolStack.push(currentAngle);
			}
			if ((formatedString.charAt(i) == ']')) {
				currentAngle = (double) drawingSymbolStack.pop();
				currentY = (double) drawingSymbolStack.pop();
				currentX = (double) drawingSymbolStack.pop();
			}
		}

		if (virtual) {
			scaleY = (double) (guiSideBar.getHeight() - (pixelGap * 2)) / (Math.abs(minY) + maxY);
			scaleX = (double) (guiSideBar.getWidth() - (pixelGap * 2)) / (Math.abs(minX) + maxX);
			if (scaleX <= scaleY) {
				scaleY = scaleX;
			} else {
				scaleX = scaleY;
			}
		}

	}

	/**
	 * Draws a line on mainCanvas.
	 *
	 * @param gc graphics context 2D
	 */
	private void drawSingleLine(Graphics2D gc) {
		gc.drawLine((int) currentX, (int) currentY, (int) oldCurrentX, (int) oldCurrentY);
	}

	/**
	 * Helper Method to build the long string for the drawing turtle method.
	 *
	 * @return String for the drawing turtle
	 */
	private String buildString() {
		String temp = guiSideBar.getStartingSequence();
		temp = temp.replace(",", "");
		int tempGenerations = guiSideBar.getGenerations();
		StringBuilder b = new StringBuilder();
		b.append(temp);

		// Split ProductionRules into corresponding variable forumulaA to F
		fillFormulaStrings();

		// with stringbuilder replace original string with user input
		// generations
		// and productionrules * startingsequence
		for (int i = 0; i < tempGenerations; i++) {
			temp = b.toString();
			b = new StringBuilder();
			for (int j = 0; j < temp.length(); j++) {
				if (temp.charAt(j) == 'A') {
					b.append(forumulaA);
				}
				if (temp.charAt(j) == 'B') {
					b.append(forumulaB);
				}
				if (temp.charAt(j) == 'C') {
					b.append(forumulaC);
				}
				if (temp.charAt(j) == 'D') {
					b.append(forumulaD);
				}
				if (temp.charAt(j) == 'E') {
					b.append(forumulaE);
				}
				if (temp.charAt(j) == 'F') {
					b.append(forumulaF);
				}
				if (temp.charAt(j) == '+') {
					b.append("+");
				}
				if (temp.charAt(j) == '-') {
					b.append("-");
				}
				if (temp.charAt(j) == '[') {
					b.append("[");
				}
				if (temp.charAt(j) == ']') {
					b.append("]");
				}
			}
		}
		return b.toString();
	}

	/**
	 * Helper Method to parse variables forumulaA to forumulaF with the
	 * corresponding values from the textfield tfProductionRules.
	 */
	private void fillFormulaStrings() {
		Integer indexStart;
		Integer indexEnd = 0;
		Integer indexCount = 1;
		forumulaA = "";
		forumulaB = "";
		forumulaC = "";
		forumulaD = "";
		forumulaE = "";
		forumulaF = "";
		String tempProductionRules = guiSideBar.getProductionRules();
		String tempStartingSequence = guiSideBar.getStartingSequence();

		// Search for strings in productionrules that indicate a rule for a
		// letter A to F and put them in a seperate variable.
		if (tempProductionRules.contains("(A,")) {
			indexStart = tempProductionRules.indexOf("(A,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(A,") + indexCount);
			}
			forumulaA = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		if (tempProductionRules.contains("(B,")) {
			indexStart = tempProductionRules.indexOf("(B,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(B,") + indexCount);
			}
			forumulaB = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		if (tempProductionRules.contains("(C,")) {
			indexStart = tempProductionRules.indexOf("(C,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(C,") + indexCount);
			}
			forumulaC = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		if (tempProductionRules.contains("(D,")) {
			indexStart = tempProductionRules.indexOf("(D,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(D,") + indexCount);
			}
			forumulaD = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		if (tempProductionRules.contains("(E,")) {
			indexStart = tempProductionRules.indexOf("(E,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(E,") + indexCount);
			}
			forumulaE = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		if (tempProductionRules.contains("(F,")) {
			indexStart = tempProductionRules.indexOf("(F,");
			indexCount += 1;
			for (int i = indexStart; i < tempProductionRules.length(); i++) {
				indexEnd = tempProductionRules.indexOf(")", tempProductionRules.indexOf("(F,") + indexCount);
			}
			forumulaF = guiSideBar.getProductionRules().substring(indexStart + 3, indexEnd);
		}

		// in case a letter only occurs in the production rules and not in the
		// starting sequence. So it can replace itself with itself.
		for (String itemInList : ALPHABETLIST) {
			String compare = "(";
			compare += itemInList;
			if (tempStartingSequence.contains(itemInList) && (!tempProductionRules.contains(compare))) {
				switch (itemInList) {
				case "A":
					forumulaA = "A";
					break;
				case "B":
					forumulaB = "B";
					break;
				case "C":
					forumulaC = "C";
					break;
				case "D":
					forumulaD = "D";
					break;
				case "E":
					forumulaE = "E";
					break;
				case "F":
					forumulaF = "F";
					break;
				default:
					throw new AssertionError();
				}
			}
		}

	}

	@Override
	public void createSideBarGUI() {
		PanelSidebar.add(guiSideBar.getSideBarPnl(), BorderLayout.CENTER);
	}

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		this.status = IGenerator.Status.STOP;
	}

}
