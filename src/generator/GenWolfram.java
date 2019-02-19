package generator;

import data.GlobalSettings;
import data.Wolfram;
import gui.MainCanvasPanel;
import gui.SideBarWolfram;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.BitSet;
import java.util.Random;

/**
 * Implements cellular automaton by Stephen Wolfram with all rules.
 *
 * @author BenGe47
 */
public final class GenWolfram extends AGenerator {

	private final static int MAXFIELDPIXEL = 1;
	private SideBarWolfram guiSideBar;
	private boolean[][] GridWorld;
	private int pixelGap = 0;
	private BitSet myWolframBits = new BitSet(8);
	private Wolfram myWolfram;

	/**
	 * Constructor.
	 *
	 * @param MainCanvas Inject MainCanvasPanel
	 * @param name Name for this generator
	 */
	public GenWolfram(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('W');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		new Random();
		this.setGenType(GlobalSettings.GeneratorType.CELLULAR);

		guiSideBar = new SideBarWolfram(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(GlobalSettings.Status.READY);
			}
		});

		createSideBarGUI();
		// so current rule gets calculated
		guiSideBar.clickRules();
	}

	@Override
	public void createSideBarGUI() {
		this.addToSidebar(guiSideBar.getSideBarPnl());
	}

	@Override
	public void run() {
		startCalcTime();
		updateStatus(GlobalSettings.Status.CALCULATING);
		guiSideBar.setButtonsCalculating();

		try {
			// check input double range
			if (Double.parseDouble(guiSideBar.getRandomness()) >= 0.0
					&& Double.parseDouble(guiSideBar.getRandomness()) <= 1.0) {

				setWolframBits();
				myWolfram = new Wolfram(guiSideBar.getWidth(), guiSideBar.getHeight(),
						Double.parseDouble(guiSideBar.getRandomness()), guiSideBar.isEdgeWrapAround(), myWolframBits);

				GridWorld = myWolfram.getGridWorld();

				while (!guiSideBar.isStopped()) {

					try {
						Thread.sleep(guiSideBar.getSpeed());
					} catch (Exception e) {

					}

					updateScreenPanel();
					setWolframBits();

					if (myWolfram.getCurrentRow() < GridWorld[0].length) {
						myWolfram.calcNextGenField();
					} else {
						break;

					}

					while (guiSideBar.isPaused()) {
						updateStatus(GlobalSettings.Status.PAUSED);
						if (guiSideBar.isStopped()) {
							break;
						}
					}
					updateStatus(GlobalSettings.Status.CALCULATING);

				}

				endCalcTime();
				guiSideBar.setButtonsReady();
				updateStatus(GlobalSettings.Status.FINISHED);
			} else {
				showWarning("Randomness has to be in 0.0 to 1.0 range.");
				guiSideBar.setButtonsReady();
			}
		} catch (Exception ne) {
			showWarning("Randomness has to be in 0.0 to 1.0 range.");
			guiSideBar.setButtonsReady();
		}
	}

	/**
	 * Convert 8 checkboxes for Wolfram rules into a bitset.
	 * 
	 */
	private void setWolframBits() {
		myWolframBits.set(0, guiSideBar.isCB0());
		myWolframBits.set(1, guiSideBar.isCB1());
		myWolframBits.set(2, guiSideBar.isCB2());
		myWolframBits.set(3, guiSideBar.isCB3());
		myWolframBits.set(4, guiSideBar.isCB4());
		myWolframBits.set(5, guiSideBar.isCB5());
		myWolframBits.set(6, guiSideBar.isCB6());
		myWolframBits.set(7, guiSideBar.isCB7());
	}

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		setGenStatus(GlobalSettings.Status.STOP);
	}

	/**
	 * Draw dead/alive values from array into color squares on myCanvas.
	 */
	private void updateScreenPanel() {
		BufferedImage image = new BufferedImage((GridWorld.length * (MAXFIELDPIXEL + pixelGap)),
				(GridWorld[0].length * (MAXFIELDPIXEL + pixelGap)), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(guiSideBar.getColor());
		for (int y = 0; y < GridWorld[0].length; y++) {
			for (int x = 0; x < GridWorld.length; x++) {
				if (GridWorld[x][y] == true) {
					g2d.setColor(guiSideBar.getColor());
					g2d.fillRect((MAXFIELDPIXEL + pixelGap) * x, (MAXFIELDPIXEL + pixelGap) * y, MAXFIELDPIXEL,
							MAXFIELDPIXEL);
				} else {
					g2d.setColor(guiSideBar.getBGColor());
					g2d.fillRect((MAXFIELDPIXEL + pixelGap) * x, (MAXFIELDPIXEL + pixelGap) * y, MAXFIELDPIXEL,
							MAXFIELDPIXEL);
				}
			}
		}
		g2d.dispose();
		this.setMainCanvasToImage(image);
	}

}
