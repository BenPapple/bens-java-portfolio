package generator;

import data.GlobalSettings;
import data.NaSch;
import gui.MainCanvasPanel;
import gui.SideBarNaSch;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Implements cellular automaton by Stephen Wolfram with all 256 rules.
 *
 * @author BenGe47
 */
public final class GenNaSch extends AGenerator {

	private static int MAXFIELDPIXEL = 1;
	private SideBarNaSch guiSideBar;
	private int[][] GridWorld;
	private int pixelGap = 0;
	private NaSch myNaSch;

	/**
	 * Constructor.
	 *
	 * @param MainCanvas Inject MainCanvasPanel
	 * @param name Name for this generator
	 */
	public GenNaSch(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('N');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		this.setGenType(GlobalSettings.GeneratorType.CELLULAR);

		guiSideBar = new SideBarNaSch(new ActionListener() {
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

		// check input double range
		try {
			if (Double.parseDouble(guiSideBar.getRandomness()) >= 0.0
					&& Double.parseDouble(guiSideBar.getRandomness()) <= 1.0
					&& Double.parseDouble(guiSideBar.getBrakeRandomness()) >= 0.0
					&& Double.parseDouble(guiSideBar.getBrakeRandomness()) <= 1.0) {

				myNaSch = new NaSch(guiSideBar.getWidth(), guiSideBar.getHeight(),
						Double.parseDouble(guiSideBar.getRandomness()), Double.parseDouble(guiSideBar.getBrakeRandomness()));
				
				GridWorld = myNaSch.getGridWorld();
				MAXFIELDPIXEL = guiSideBar.getZoomFactor();

				while (!guiSideBar.isStopped()) {

					try {
						
					} catch (Exception e) {

					}

					updateScreenPanel();

					if (myNaSch.getCurrentRow() < GridWorld[0].length) {
						myNaSch.calcNextGenField();
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

				switch (GridWorld[x][y]) {

				case 0:
					g2d.setColor(Color.RED);
					break;
				case 1:
					g2d.setColor(Color.ORANGE);
					break;
				case 2:
					g2d.setColor(Color.YELLOW);
					break;
				case 3:
					g2d.setColor(Color.GREEN);
					break;
				case 4:
					g2d.setColor(Color.BLUE);
					break;
				case 5:
					g2d.setColor(new Color(102, 0, 153)); // Purple
					break;
				case 6:
					g2d.setColor(guiSideBar.getBGColor());
					break;
				}

				g2d.fillRect((MAXFIELDPIXEL + pixelGap) * x, (MAXFIELDPIXEL + pixelGap) * y, MAXFIELDPIXEL,
						MAXFIELDPIXEL);

			}
		}
		g2d.dispose();
		this.setMainCanvasToImage(image);
	}

}
