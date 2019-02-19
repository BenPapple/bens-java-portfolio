package generator;

import data.GameOfLife;
import data.GlobalSettings;
import gui.MainCanvasPanel;
import gui.SideBarGOL;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Implementation of Conway's Game of Life cellular automata.
 *
 * @author BenGe47
 */
public class GenGameOfLife extends AGeneratorCellular {

	private final static int MAXFIELDPIXEL = 4;
	private SideBarGOL guiSideBar;
	private boolean[][] GridWorld;
	private int pixelGap = 1;
	private int genCounter;
	private GameOfLife myGOL;

	/**
	 * Constructor
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of generator
	 */
	public GenGameOfLife(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('C');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		this.setGenType(GlobalSettings.GeneratorType.CELLULAR);

		guiSideBar = new SideBarGOL(new ActionListener() {
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

	@Override
	public void run() {
		startCalcTime();
		updateStatus(GlobalSettings.Status.CALCULATING);
		guiSideBar.setButtonsCalculating();

		try {
			// check input double range
			if (Double.parseDouble(guiSideBar.getRandomness()) >= 0.0
					&& Double.parseDouble(guiSideBar.getRandomness()) <= 1.0) {

				// init2DField();
				myGOL = new GameOfLife(guiSideBar.getWidth(), guiSideBar.getHeight(),
						Double.parseDouble(guiSideBar.getRandomness()), guiSideBar.isEdgeWrapAround());
				GridWorld = myGOL.getGridWorld();

				genCounter = 0;

				while (!guiSideBar.isStopped()) {

					try {
						Thread.sleep(guiSideBar.getSpeed());
					} catch (Exception e) {

					}

					updateScreenPanel();
					genCounter += 1;
					guiSideBar.setGenCounter(String.valueOf(genCounter));

					myGOL.calcNextGenField();

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

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		setGenStatus(GlobalSettings.Status.STOP);
	}

	/**
	 * Draw dead/alive values from array into color squares on myCanvas.
	 *
	 */
	private void updateScreenPanel() {
		BufferedImage image = new BufferedImage((guiSideBar.getWidth() * (MAXFIELDPIXEL + pixelGap)),
				(guiSideBar.getHeight() * (MAXFIELDPIXEL + pixelGap)), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(guiSideBar.getBGColor());
		g2d.fillRect(0, 0, guiSideBar.getWidth() * (MAXFIELDPIXEL + pixelGap),
				guiSideBar.getHeight() * (MAXFIELDPIXEL + pixelGap));
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
