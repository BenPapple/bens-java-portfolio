package generator;

import data.GlobalSettings;
import data.LSystem;
import gui.MainCanvasPanel;
import gui.SideBarLSystem;
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

/**
 * Lindenmayer system
 *
 * @author BenGe47
 */
public class GenLSystem extends AGenerator {

	private SideBarLSystem guiSideBar;
	private int pixelGap = 15;
	private String formatedString;
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
	private final List<String> ALPHABETLIST = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
	private LSystem myLSystem;

	/**
	 * Constructor for an L-system.
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of this generator
	 */
	public GenLSystem(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('L');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		new Random();
		this.setGenType(GlobalSettings.GeneratorType.LSYSTEM);

		guiSideBar = new SideBarLSystem(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(GlobalSettings.Status.READY);
			}
		});
		createSideBarGUI();
	}

	@Override
	public void createSideBarGUI() {
		this.addToSidebar(guiSideBar.getSideBarPnl());
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
				updateStatus(GlobalSettings.Status.PAUSED);
				if (guiSideBar.isStopped()) {
					break;
				}
			}
			updateStatus(GlobalSettings.Status.CALCULATING);
			oldCurrentY = currentY;
			oldCurrentX = currentX;
			if (Character.isLetter(formatedString.charAt(i))) {
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
	 * Check all the texfields if they are correctly formatted and return true
	 * if they are.
	 * 
	 * @return true when input is ok
	 */
	private boolean isInputCorrect() {

		Boolean abcCorrect = false;
		Boolean rulesCorrect = false;
		Boolean rulesBrackets = true;

		// Proof even numbers and correct order of [ and ]
		String[] splitRules = guiSideBar.getProductionRules().split("\\),");
		for (int i = 0; i < splitRules.length; i++) {
			int splitOpen = splitRules[i].split("\\[", -1).length - 1;
			int splitClose = splitRules[i].split("\\]", -1).length - 1;
			if (splitOpen != splitClose) {
				rulesBrackets = false;
				showWarning("Production rules wrong bracket number. Must be even.");
				break;
			}
			int count = 0;
			for (int j = 0; j < splitRules[i].length(); j++) {

				if (splitRules[i].charAt(j) == '[') {
					count += 1;
				}
				if (splitRules[i].charAt(j) == ']') {
					count -= 1;
				}
				if (count < 0) {
					rulesBrackets = false;
					showWarning("Production rules wrong bracket order.");
					break;
				}

			}

		}

		// check patterns
		String inTfCompare = guiSideBar.getStartingSequence();
		String regexPattern = "[A-Z+-]*";
		boolean matchesRegex = inTfCompare.matches(regexPattern);
		if (matchesRegex) {
			abcCorrect = true;
		} else {
			showWarning("Starting Sequence wrong."
					+ "\nA-Z,+,- allowed.");
		}

		inTfCompare = guiSideBar.getProductionRules();
		regexPattern = "(\\([A-Z],[A-Z\\+\\-\\[\\]]*\\),{0,1})*";
		matchesRegex = inTfCompare.matches(regexPattern);
		if (matchesRegex) {
			rulesCorrect = true;
		} else {
			showWarning("Production rules wrong."
					+ "\n(A,AAA+-[]),(B,CDF+-[]) allowed.");
		}

		// proof every letter has only one rule
		for (String itemInList : ALPHABETLIST) {
			String compareLetter = "\\(";
			compareLetter += itemInList;
			compareLetter += ",";
			int splitLetter = guiSideBar.getProductionRules().split(compareLetter, -1).length - 1;
			if (splitLetter > 1) {
				rulesCorrect = false;
				showWarning("Production rules wrong."
						+ "\nA letter can't have more than 1 rule.");
				break;
			}
		}

		if (abcCorrect & rulesCorrect & rulesBrackets) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void run() {
		startCalcTime();
		updateStatus(GlobalSettings.Status.CALCULATING);
		guiSideBar.setButtonsCalculating();

		formatedString = "";
		try {

			if (isInputCorrect()) {
				myLSystem = new LSystem(guiSideBar.getStartingSequence(), guiSideBar.getProductionRules(),
						guiSideBar.getGenerations());
				formatedString = myLSystem.getTurtleData();
				while (!guiSideBar.isStopped()) {

					updateScreenPanel();

					guiSideBar.setStopped();

					while (guiSideBar.isPaused()) {
						updateStatus(GlobalSettings.Status.PAUSED);
						if (guiSideBar.isStopped()) {
							break;
						}
					}
					updateStatus(GlobalSettings.Status.CALCULATING);
				}
			}

			endCalcTime();
			guiSideBar.setButtonsReady();
			updateStatus(GlobalSettings.Status.FINISHED);

		} catch (OutOfMemoryError e) {
			setErrorMsgText("OutOfMemory");
			updateStatus(GlobalSettings.Status.ERROR);
			guiSideBar.setButtonsReady();
		}

	}

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		setGenStatus(GlobalSettings.Status.STOP);
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
		this.setMainCanvasToImage(image);
	}

}
