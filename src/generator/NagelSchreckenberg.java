package generator;

import data.GlobalSettings;
import gui.MainCanvasPanel;
import gui.SideBarNaSch;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

/**
 * Implements cellular automaton by Stephen Wolfram with all 256 rules.
 *
 * @author BenGe47
 */
public final class NagelSchreckenberg extends AGeneratorCellular {

	private static int MAXFIELDPIXEL = 1;
	private SideBarNaSch guiSideBar;
	private int[][] GridWorld;
	private int pixelGap = 0;
	private int currentRow;

	/**
	 * Constructor.
	 *
	 * @param MainCanvas Inject MainCanvasPanel
	 * @param name Name for this generator
	 */
	public NagelSchreckenberg(MainCanvasPanel MainCanvas, String name) {
		this.generatorName = name;
		this.myMnemonicKey = 'N';
		this.myCanvas = MainCanvas;
		this.PanelSidebar = new JPanel();
		this.generatorDescr = "Nagel-Schreckenberg";
		this.generatorType = GlobalSettings.GeneratorType.CELLULAR;

		guiSideBar = new SideBarNaSch(new ActionListener() {
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

		MAXFIELDPIXEL = guiSideBar.getSpeed();

		while (!guiSideBar.isStopped()) {

			try {
				// Thread.sleep(guiSideBar.getSpeed());
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
		this.myCanvas.setImage(image);
	}

	@Override
	public void init2DField() {

		Double rand;
		currentRow = 1;
		GridWorld = new int[guiSideBar.getWidth()][guiSideBar.getHeight()];
		try {
			rand = Double.parseDouble((guiSideBar.getRandomness()));

			if (((rand < 0) || ((rand > 1.0)))) {
				stopGenerator();
				throw new NumberFormatException();
			}

			// because ThreadLocalRandom does not return 1.0 make line all cars
			if (rand == 1.0) {

				for (int x = 0; x < GridWorld.length; x++) {
					int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);
					GridWorld[x][0] = randomNum;
				}
				// init first row with random number of cars and speed
			} else {
				for (int x = 0; x < GridWorld.length; x++) {

					int randomNum = ThreadLocalRandom.current().nextInt(0, 5 + 1);

					if (Math.random() <= rand) {
						GridWorld[x][0] = randomNum;
					} else {
						GridWorld[x][0] = 6;
					}
				}
			}

			// init rest of field with street
			for (int y = 1; y < GridWorld[0].length; y++) {
				for (int x = 0; x < GridWorld.length; x++) {

					GridWorld[x][y] = 6;
				}
			}

		} catch (NumberFormatException ne) {
			errorMsg = "InputError";
			updateStatus(IGenerator.Status.ERROR);
			// guiSideBar.setButtonsReady();

		}
	}

	@Override
	public void nextGenField() {
		int newSpeed;
		int nextCarDistance;
		Double rand2;

		for (int x = 0; x < GridWorld.length; x++) {
			// for all cars
			if (GridWorld[x][currentRow - 1] <= 5) {
				newSpeed = GridWorld[x][currentRow - 1];
				// add 1 to speed if not already at speed 5
				if (GridWorld[x][currentRow - 1] < 5) {
					newSpeed = GridWorld[x][currentRow - 1] + 1;
				}

				nextCarDistance = 6;

				// calculate distance to next car for next 5 fields
				for (int i = 1; i < 6; i++) {
					// consider world wrap around
					int newX = ((x + i) % (GridWorld.length));

					if (GridWorld[newX][currentRow - 1] < 6) {
						nextCarDistance = i - 1;
						break;
					}

				}

				// final speed of new car
				newSpeed = (newSpeed >= nextCarDistance) ? nextCarDistance : newSpeed;

				// randomly brake or not brake each car
				rand2 = Double.parseDouble((guiSideBar.getBrakeRandomness()));
				if ((Math.random() <= rand2) && (newSpeed > 0)) {
					newSpeed -= 1;
				}
				// set speed of car in new row
				GridWorld[Math.abs((x + newSpeed) % (GridWorld.length))][currentRow] = newSpeed;

			}
		}

		currentRow += 1;

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
