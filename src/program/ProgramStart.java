package program;

import generator.GenGameOfLife;
import generator.IGenerator;
import generator.GenLSystem;
import generator.GenNaSch;
import generator.GenRandomTreeArt;
import generator.TestShapes;
import generator.GenWolfram;
import gui.MainCanvasPanel;
import gui.MainWindow;
import gui.SaveMainCanvasToImage;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

/**
 * Portfolio of BenGe47. Starting point for the program. Includes cellular
 * automatons, L-systems and generative art programs in a GUI environment.
 *
 * @author BenGe47
 * @version 1.0.19.050
 */
public class ProgramStart {

	/**
	 * Sub parts of this program get initialized, collected in an ArrayList and
	 * then forwarded into the MainWindow class by its initialization.
	 *
	 * @param args command line input parameters
	 */
	public static void main(String[] args) {
		// Create ImageSaver Instance
		SaveMainCanvasToImage imageSaverHelper = new SaveMainCanvasToImage();
		// Create main panel in gui
		MainCanvasPanel centerImagePanel = new MainCanvasPanel();
		centerImagePanel.setBackground(Color.WHITE);

		// Register Generators in ArrayList
		ArrayList<IGenerator> generators = new ArrayList<>();

		generators.add(new GenNaSch(centerImagePanel, "Nagel-Schreckenberg"));
		generators.add(new GenWolfram(centerImagePanel, "Wolfram's Rule 30 "));
		generators.add(new TestShapes(centerImagePanel, "Shapes Generator"));
		generators.add(new GenRandomTreeArt(centerImagePanel, "Random-Tree Art (2-Leaf)"));
		generators.add(new GenLSystem(centerImagePanel, "Lindenmayer system"));
		generators.add(new GenGameOfLife(centerImagePanel, "Conway's Game of Life"));

		// Show GUI and construct it with ArrayList generators
		SwingUtilities.invokeLater(() -> {
			final MainWindow wnd = new MainWindow(imageSaverHelper, generators, centerImagePanel);
			wnd.setVisible(true);
		});
	}
}
