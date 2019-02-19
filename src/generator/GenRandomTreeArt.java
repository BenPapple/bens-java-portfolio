package generator;

import data.GlobalSettings;
import data.MathRTreeArt;
import gui.MainCanvasPanel;
import gui.SideBarRTree;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
public class GenRandomTreeArt extends AGenerator {
	private Random mySeededRandom;
	private int generatedSeed;
	private SideBarRTree guiSideBar;
	private int maxXPixel;
	private int maxYPixel;
	private MathRTreeArt myMathRTreeArt;

	/**
	 * Constructor for a random tree art generator.
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of this generator
	 */
	public GenRandomTreeArt(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('R');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		this.setGenType(GlobalSettings.GeneratorType.RTREE);

		guiSideBar = new SideBarRTree(new ActionListener() {
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

				double colorDouble = myMathRTreeArt.startRecCalc(xDouble, yDouble);

				colorDouble = Math.abs(colorDouble);
				if (colorDouble > 1.0) {
					colorDouble = 1.0;

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

	@Override
	public void run() {
		startCalcTime();
		updateStatus(GlobalSettings.Status.CALCULATING);
		guiSideBar.setButtonsCalculating();

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

		try {
			Thread.sleep(50);

			maxXPixel = guiSideBar.getWidth();
			maxYPixel = guiSideBar.getHeight();

			myMathRTreeArt = new MathRTreeArt(mySeededRandom, guiSideBar.getGenerations(), maxXPixel, maxYPixel);

			updateScreenPanel();

		} catch (OutOfMemoryError e) {
			setErrorMsgText("OutOfMemory");
			updateStatus(GlobalSettings.Status.ERROR);
			guiSideBar.setButtonsReady();
		} catch (InterruptedException ex) {
			Logger.getLogger(GenRandomTreeArt.class.getName()).log(Level.SEVERE, null, ex);
		}

		endCalcTime();
		guiSideBar.setButtonsReady();
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
