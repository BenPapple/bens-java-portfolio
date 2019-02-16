package generator;

import data.GlobalSettings;
import gui.MainCanvasPanel;
import gui.SideBarWolfram;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Implements cellular automaton by Stephen Wolfram with all rules.
 *
 * @author BenGe47
 */
public final class Wolfram extends AGeneratorCellular {

	private final static int MAXFIELDPIXEL = 1;
	private SideBarWolfram guiSideBar;
	private boolean[][] GridWorld;
	private int pixelGap = 0;
	private int currentRow;

	/**
	 * Constructor.
	 *
	 * @param MainCanvas Inject MainCanvasPanel
	 * @param name Name for this generator
	 */
	public Wolfram(MainCanvasPanel mainCanvas, String name) {
		this.setName(name);
		this.setMnemonicChar('W');
		this.setMainCanvas(mainCanvas);
		initSideBarPanel();
		new Random();
		this.setGenType(GlobalSettings.GeneratorType.CELLULAR);

		guiSideBar = new SideBarWolfram(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateStatus(IGenerator.Status.READY);
			}
		});

		createSideBarGUI();
		// so current rule gets calculated
		guiSideBar.clickRules();
	}

	/**
	 * Calculates next generation of values for all lines except the edge value
	 * on both sides.
	 */
	private void calculateInnerField() {
		for (int x = 1; x < GridWorld.length - 1; x++) {
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = guiSideBar.isCB0();
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = guiSideBar.isCB1();
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = guiSideBar.isCB2();
			}
			if ((GridWorld[x - 1][currentRow - 1] == true) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = guiSideBar.isCB3();
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = guiSideBar.isCB4();
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == true)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = guiSideBar.isCB5();
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == true)) {
				GridWorld[x][currentRow] = guiSideBar.isCB6();
			}
			if ((GridWorld[x - 1][currentRow - 1] == false) && (GridWorld[x][currentRow - 1] == false)
					&& (GridWorld[x + 1][currentRow - 1] == false)) {
				GridWorld[x][currentRow] = guiSideBar.isCB7();
			}
		}
		currentRow += 1;
	}

	@Override
	public void createSideBarGUI() {
		this.addToSidebar(guiSideBar.getSideBarPnl());
	}

	@Override
	public void init2DField() {

		Double rand;
		currentRow = 1;
		GridWorld = new boolean[guiSideBar.getWidth()][guiSideBar.getHeight()];
		try {
			rand = Double.parseDouble((guiSideBar.getRandomness()));

			if (((rand < 0) || ((rand > 1.0)))) {
				throw new NumberFormatException();
			}

			if (!guiSideBar.isEdgeWrapAround()) {
				for (int x = 1; x < GridWorld.length - 1; x++) {
					GridWorld[x][0] = Math.random() <= rand;
				}
			} else {
				for (int x = 0; x < GridWorld.length; x++) {
					GridWorld[x][0] = Math.random() <= rand;
				}
			}

		} catch (NumberFormatException ne) {
			updateStatus(IGenerator.Status.ERROR);
		}
	}

	@Override
	public void nextGenField() {

		if (guiSideBar.isEdgeWrapAround()) {

			// Left Edge
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB0();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB1();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB2();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == true) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB3();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB4();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == true)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB5();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == true)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB6();
			}
			if ((GridWorld[GridWorld.length - 1][currentRow - 1] == false) && (GridWorld[0][currentRow - 1] == false)
					&& (GridWorld[0 + 1][currentRow - 1] == false)) {
				GridWorld[0][currentRow] = (Boolean) guiSideBar.isCB7();
			}

			// Right Edge
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB0();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB1();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB2();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == true)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB3();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB4();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == true)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB5();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == true)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB6();
			}
			if ((GridWorld[GridWorld.length - 2][currentRow - 1] == false)
					&& (GridWorld[GridWorld.length - 1][currentRow - 1] == false)
					&& (GridWorld[0][currentRow - 1] == false)) {
				GridWorld[GridWorld.length - 1][currentRow] = guiSideBar.isCB7();
			}

			calculateInnerField();

		} else {
			calculateInnerField();

		}
	}

	@Override
	public void run() {
		try {
			// check input double range
			if (Double.parseDouble(guiSideBar.getRandomness()) >= 0.0
					&& Double.parseDouble(guiSideBar.getRandomness()) <= 1.0) {
				startCalcTime();
				updateStatus(IGenerator.Status.CALCULATING);
				guiSideBar.setButtonsCalculating();
				init2DField();

				while (!guiSideBar.isStopped()) {

					try {
						Thread.sleep(guiSideBar.getSpeed());
					} catch (Exception e) {

					}

					updateScreenPanel();

					if (currentRow < GridWorld[0].length) {
						nextGenField();
					} else {
						break;

					}

					while (guiSideBar.isPaused()) {
						updateStatus(IGenerator.Status.PAUSED);
						if (guiSideBar.isStopped()) {
							break;
						}
					}
					updateStatus(IGenerator.Status.CALCULATING);

				}

				guiSideBar.setButtonsReady();
				endCalcTime();
				updateStatus(IGenerator.Status.FINISHED);
			} else {
				showWarning("Randomness has to be in 0.0 to 1.0 range.");
			}
		} catch (Exception ne) {
			showWarning("Randomness has to be in 0.0 to 1.0 range.");
		}
	}

	@Override
	public void stopGenerator() {
		guiSideBar.setStopped();
		setGenStatus(IGenerator.Status.STOP);
	}

	/**
	 * Draw dead/alive values from array into color squares on myCanvas.
	 */
	private void updateScreenPanel() {
		BufferedImage image = new BufferedImage((guiSideBar.getWidth() * (MAXFIELDPIXEL + pixelGap)),
				(guiSideBar.getHeight() * (MAXFIELDPIXEL + pixelGap)), BufferedImage.TYPE_INT_ARGB);
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
