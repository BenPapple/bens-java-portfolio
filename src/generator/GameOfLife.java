package generator;

import data.GlobalSettings;
import gui.MainCanvasPanel;
import gui.SideBarGOL;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Implementation of Conway's Game of Life cellular automata.
 *
 * @author BenGe47
 */
public class GameOfLife extends AGeneratorCellular {

	private final static int MAXFIELDPIXEL = 4;
	private SideBarGOL guiSideBar;

	private boolean[][] GridWorld;
	private boolean[][] CalcGridWorld;
	private int pixelGap = 1;

	/**
	 * Constructor
	 *
	 * @param mainCanvas Inject MainCanvasPanel
	 * @param name Name of generator
	 */
	public GameOfLife(MainCanvasPanel mainCanvas, String name) {
		this.generatorName = name;
		this.myMnemonicKey = 'C';
		this.myCanvas = mainCanvas;
		this.PanelSidebar = new JPanel();
		this.generatorDescr = "Game of Live";
		this.generatorType = GlobalSettings.GeneratorType.CELLULAR;

		guiSideBar = new SideBarGOL(new ActionListener() {
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
		init2DField();

		while (!guiSideBar.isStopped()) {

			try {
				Thread.sleep(guiSideBar.getSpeed());
			} catch (Exception e) {

			}

			updateScreenPanel();
			nextGenField();

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
		this.myCanvas.setImage(image);
	}

	@Override
	public void init2DField() {

		Double rand;
		GridWorld = new boolean[guiSideBar.getWidth()][guiSideBar.getHeight()];
		try {
			rand = Double.parseDouble(guiSideBar.getRandomness());

			if (((rand < 0) || ((rand >= 1.0)))) {

				throw new NumberFormatException();
			}

			if (!guiSideBar.isEdgeWrapAround()) {

				for (int y = 1; y < GridWorld[0].length - 1; y++) {
					for (int x = 1; x < GridWorld.length - 1; x++) {
						GridWorld[x][y] = Math.random() <= rand;
					}
				}
			} else {
				for (int i = 0; i < GridWorld[0].length; i++) {
					for (boolean[] GridWesen1 : GridWorld) {
						GridWesen1[i] = Math.random() <= rand;
					}
				}
			}

		} catch (NumberFormatException ne) {
			updateStatus(IGenerator.Status.ERROR);
		}
	}

	@Override
	public void nextGenField() {
		int counter;
		CalcGridWorld = new boolean[GridWorld.length][GridWorld[0].length];

		if (guiSideBar.isEdgeWrapAround()) {

			// Calculate Edge Cases
			// corners right top
			counter = 0;
			CalcGridWorld[GridWorld.length - 1][0] = false;
			if (CalcGridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][0]) {
				counter++;
			}
			setNewCellStatus(counter, GridWorld.length - 1, 0);

			// corners right bottom
			counter = 0;
			CalcGridWorld[GridWorld.length - 1][GridWorld[0].length - 1] = false;
			if (CalcGridWorld[0][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 2][0]) {
				counter++;
			}
			setNewCellStatus(counter, GridWorld.length - 1, GridWorld[0].length - 1);

			// corners left bottom
			counter = 0;
			CalcGridWorld[0][GridWorld[0].length - 1] = false;
			if (CalcGridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 2]) {
				counter++;
			}
			if (GridWorld[0][1]) {
				counter++;
			}
			if (GridWorld[0][2]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			setNewCellStatus(counter, 0, GridWorld[0].length - 1);

			// corners left top
			counter = 0;
			CalcGridWorld[0][0] = false;
			if (CalcGridWorld[GridWorld.length - 1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][1]) {
				counter++;
			}
			if (GridWorld[0][1]) {
				counter++;
			}
			if (GridWorld[1][0]) {
				counter++;
			}
			if (GridWorld[0][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[1][GridWorld[0].length - 1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][1]) {
				counter++;
			}
			if (GridWorld[GridWorld.length - 1][0]) {
				counter++;
			}
			setNewCellStatus(counter, 0, 0);

			// top edge
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][0] = false;
				if (GridWorld[x - 1][1]) {
					counter++;
				}
				if (GridWorld[x][1]) {
					counter++;
				}
				if (GridWorld[x + 1][1]) {
					counter++;
				}
				if (GridWorld[x - 1][0]) {
					counter++;
				}
				if (GridWorld[x + 1][0]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 1]) {
					counter++;
				}
				setNewCellStatus(counter, x, 0);
			}

			// bottom edge
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][GridWorld[0].length - 1] = false;
				if (GridWorld[x - 1][0]) {
					counter++;
				}
				if (GridWorld[x][0]) {
					counter++;
				}
				if (GridWorld[x + 1][0]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 1]) {
					counter++;
				}
				if (GridWorld[x - 1][GridWorld[0].length - 2]) {
					counter++;
				}
				if (GridWorld[x][GridWorld[0].length - 2]) {
					counter++;
				}
				if (GridWorld[x + 1][GridWorld[0].length - 2]) {
					counter++;
				}
				setNewCellStatus(counter, x, GridWorld[0].length - 1);
			}

			// right side
			for (int y = 1; y < GridWorld[0].length - 1; y++) {
				counter = 0;
				CalcGridWorld[GridWorld.length - 1][y] = false;
				if (GridWorld[0][y - 1]) {
					counter++;
				}
				if (GridWorld[0][y]) {
					counter++;
				}
				if (GridWorld[0][y + 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 2][y + 1]) {
					counter++;
				}
				setNewCellStatus(counter, GridWorld.length - 1, y);
			}

			// left side
			for (int y = 1; y < GridWorld[0].length - 1; y++) {
				counter = 0;
				CalcGridWorld[0][y] = false;
				if (GridWorld[GridWorld.length - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y]) {
					counter++;
				}
				if (GridWorld[GridWorld.length - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[0][y - 1]) {
					counter++;
				}
				if (GridWorld[0][y + 1]) {
					counter++;
				}
				if (GridWorld[0 + 1][y - 1]) {
					counter++;
				}
				if (GridWorld[0 + 1][y]) {
					counter++;
				}
				if (GridWorld[0 + 1][y + 1]) {
					counter++;
				}
				setNewCellStatus(counter, 0, y);
			}

		}

		// Calculate Inner Field
		for (int y = 1; y < GridWorld[0].length - 1; y++) {
			for (int x = 1; x < GridWorld.length - 1; x++) {
				counter = 0;
				CalcGridWorld[x][y] = false;
				if (GridWorld[x - 1][y - 1]) {
					counter++;
				}
				if (GridWorld[x - 1][y]) {
					counter++;
				}
				if (GridWorld[x - 1][y + 1]) {
					counter++;
				}
				if (GridWorld[x][y - 1]) {
					counter++;
				}
				if (GridWorld[x][y + 1]) {
					counter++;
				}
				if (GridWorld[x + 1][y - 1]) {
					counter++;
				}
				if (GridWorld[x + 1][y]) {
					counter++;
				}
				if (GridWorld[x + 1][y + 1]) {
					counter++;
				}

				setNewCellStatus(counter, x, y);
			}
		}

		copyGridworld();
	}

	/**
	 * Copies array CalcGridWorld with new results into old array Gridworld.
	 *
	 */
	private void copyGridworld() {
		for (int y = 0; y < GridWorld[0].length; y++) {
			for (int x = 0; x < GridWorld.length; x++) {
				GridWorld[x][y] = CalcGridWorld[x][y];
			}
		}
	}

	/**
	 * Calculates dead/alive by comparing living neighbours with target cell
	 * status.
	 *
	 * @param counter Number of living neighbours
	 * @param inX inX coordinate pixel
	 * @param inY inY coordinate pixel
	 */
	private void setNewCellStatus(int counter, int inX, int inY) {
		// Tote und lebende Zellen für die neue Generation bestimmen, anhand
		// der Anzahl lebender Nachbarn.
		if (counter > 3 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = false;
		}
		if (counter == 2 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
		}
		if (counter == 3 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
		}
		if (counter < 2 && (GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = false;
		}
		if (counter == 3 && !(GridWorld[inX][inY])) {
			CalcGridWorld[inX][inY] = true;
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
