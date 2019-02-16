package gui;

import generator.IGenerator;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.*;

import data.GlobalSettings;

/**
 * Constructs the GUI with menu bars, menu items and panels. Also updates status
 * of the generators in the status bar.
 * 
 * @author BenGe47
 *
 */
public class MainWindow extends JFrame implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menubar;
	private JPanel statusbarPanel;
	private MainCanvasPanel centerImagePanel;
	private JLabel statusLabel;
	private IGenerator observableGenerator;
	private JMenuItem menuItemLoad;

	private final SaveMainCanvasToImage imageSaverHelper;
	private final ArrayList<IGenerator> generators;
	private CardLayout card = new CardLayout();

	/**
	 * Constructor.
	 *
	 * @param imageSaver Helper to write displayed images to disk
	 * @param generators List of generators to build the menu items
	 * @param centerImagePanel Main panel to display generated pictures
	 */
	public MainWindow(SaveMainCanvasToImage imageSaver, ArrayList<IGenerator> generators,
			MainCanvasPanel centerImagePanel) {
		this.imageSaverHelper = imageSaver;
		this.centerImagePanel = centerImagePanel;
		this.generators = generators;
		initUI();
		registerGenerators();
	}

	/**
	 * Stops all generators.
	 */
	private void generalStop() {
		generators.forEach((generator) -> {
			generator.stopGenerator();
		});
	}

	/**
	 * Returns the center panel as an image. Helper method for
	 * saveMenuItemClicked to save an image to a drive.
	 *
	 * @return a buffered image of the center panel
	 */
	private BufferedImage GetBufferedImageFromCenterPanel() {
		return centerImagePanel.getImage();
	}

	/**
	 * Initialize GUI with menu bar, panels status and center.
	 *
	 */
	private void initUI() {
		setTitle("Ben´s Portfolio");
		setSize(1024, 768);
		setExtendedState(this.getExtendedState() | Frame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new BorderLayout());

		// Add Statusbar as JPanel
		statusbarPanel = new JPanel();
		statusbarPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		this.add(statusbarPanel, BorderLayout.SOUTH);
		statusbarPanel.setPreferredSize(new Dimension(this.getWidth(), 32));
		statusbarPanel.setLayout(new BoxLayout(statusbarPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel("");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusbarPanel.add(statusLabel);

		JMenu menuFile;

		JMenu menuTest;
		JMenu menuCellular;
		JMenu menuLSystem;
		JMenu menuRTree;
		JMenu menuGames;
		JMenu menuHelp;
		JMenuItem menuItem;
		menubar = new JMenuBar();

		// MenuBar File Entry
		menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menubar.add(menuFile);

		menuItem = new JMenuItem("Save Image as PNG");
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.addActionListener((ActionEvent ae) -> {
			saveMenuItemClicked();
		});
		menuFile.add(menuItem);

		menuItemLoad = new JMenuItem("Load Image from PNG (Random-Tree)");
		menuItemLoad.setMnemonic(KeyEvent.VK_L);
		menuItemLoad.addActionListener((ActionEvent ae) -> {
			generators.forEach((generator) -> {
				// Only load with rtree generators
				if (generator.getGenType() == GlobalSettings.GeneratorType.RTREE) {
					generator.setLoadedValues(loadImageRTree());
				}
			});
		});
		menuItemLoad.setEnabled(false);
		menuFile.add(menuItemLoad);

		menuFile.add(new JSeparator());

		menuItem = new JMenuItem("Quit");
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.addActionListener((ActionEvent event) -> {
			System.exit(0);
		});
		menuFile.add(menuItem);

		// MenuBar Generators
		menuTest = new JMenu("Test");
		menuTest.setMnemonic(KeyEvent.VK_T);
		menubar.add(menuTest);
		menuCellular = new JMenu("Cellular Automatons");
		menuCellular.setMnemonic(KeyEvent.VK_C);
		menubar.add(menuCellular);
		menuLSystem = new JMenu("L-Systems");
		menuLSystem.setMnemonic(KeyEvent.VK_L);
		menubar.add(menuLSystem);
		menuRTree = new JMenu("Random-Tree");
		menuRTree.setMnemonic(KeyEvent.VK_R);
		menubar.add(menuRTree);
		menuGames = new JMenu("Games");
		menuGames.setMnemonic(KeyEvent.VK_G);
		menubar.add(menuGames);
		menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menubar.add(menuHelp);

		menuItem = new JMenuItem("About");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.addActionListener((ActionEvent event) -> {
			showDialogAbout();
		});
		menuHelp.add(menuItem);

		this.setJMenuBar(menubar);

		// Add Left Control Panel
		JPanel cardContainer = new JPanel();
		cardContainer.setLayout(card);
		// siddebar width
		cardContainer.setPreferredSize(new Dimension(350, this.getHeight()));
		cardContainer.setBorder(new BevelBorder(BevelBorder.LOWERED));

		generators.forEach((generator) -> {
			cardContainer.add(generator.getSideBarPanel(), generator.getName());
		});

		// make sidebar scrollable
		JScrollPane spSideBar = new JScrollPane(cardContainer);
		spSideBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		spSideBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(spSideBar, BorderLayout.WEST);

		// Add Generator Entries from ArrayList
		for (IGenerator generator : generators) {
			menuItem = new JMenuItem(generator.getName());
			menuItem.setMnemonic(generator.getKey());
			menuItem.addActionListener((ActionEvent ae) -> {

				generalStop();
				card.show(cardContainer, generator.getName());
				generator.setReady();
				statusLabel.setText(generator.getName() + " Status: " + generator.getGenStatus());

				// Enable load menu entry only for RTree generators
				if (generator.getGenType() == GlobalSettings.GeneratorType.RTREE) {
					menuItemLoad.setEnabled(true);
				} else {
					menuItemLoad.setEnabled(false);
				}

			});

			switch (generator.getGenType()) {
			case TEST:
				menuTest.add(menuItem);

				break;
			case CELLULAR:
				menuCellular.add(menuItem);
				break;
			case LSYSTEM:
				menuLSystem.add(menuItem);
				break;
			case RTREE:
				menuRTree.add(menuItem);
				break;
			default:
				break;
			}
		}

		// Add Center Panel
		JScrollPane spCenter = new JScrollPane(centerImagePanel);
		centerImagePanel.setLayout(new BoxLayout(centerImagePanel, BoxLayout.PAGE_AXIS));
		spCenter.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		spCenter.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getContentPane().add(spCenter, BorderLayout.CENTER);
	}

	/**
	 * Returns file path from filechooser.
	 * 
	 * @return String path of file
	 */
	private String loadImageRTree() {
		String path = "";
		String userhome = System.getProperty("user.home");
		JFileChooser openFileChooser = new JFileChooser(userhome + "\\Downloads");
		int returnVal = openFileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = openFileChooser.getSelectedFile().getAbsolutePath();

		}
		return path;
	}

	/**
	 * Register generators with observer so the status in the status bar can be
	 * automatically updated.
	 *
	 */
	private void registerGenerators() {
		generators.forEach((generator) -> {
			generator.addObserver(this);
		});
	}

	/**
	 * React to menu entry click with saving image to disk.
	 *
	 * @param aeMenuSaveClick Click event on menu entry
	 */
	private void saveMenuItemClicked() {

		if (observableGenerator != null) {
			String userhome = System.getProperty("user.home");
			JFileChooser saveFileChooser = new JFileChooser(userhome + "\\Downloads");
			saveFileChooser.setSelectedFile(new File(observableGenerator.getFilePath()));

			if (saveFileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				String path = saveFileChooser.getSelectedFile().getAbsolutePath();
				BufferedImage image = GetBufferedImageFromCenterPanel();
				imageSaverHelper.writeBufferedImgToDisk(path, image);
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"No image to save.",
					"Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Show credits/help pop-up window.
	 */
	private void showDialogAbout() {

		JDialog meinJDialog = new JDialog();
		meinJDialog.setLayout(new GridBagLayout());
		meinJDialog.setTitle("About Ben's Portfolio");
		meinJDialog.setSize(400, 250);
		meinJDialog.setModal(true);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);

		JTextPane taAbout = new JTextPane();
		taAbout.setContentType("text/html");
		taAbout.setText("\n <b>Version 19.037</b> " + "<br><br>Author: BenGe47" + "<br>E-Mail: @googlemail.com"
				+ "<br><br>Copyright 2019");
		taAbout.setOpaque(false);
		taAbout.setEditable(false);
		meinJDialog.add(taAbout, gbc);

		JButton btnClose = new JButton("OK");
		btnClose.setPreferredSize(new Dimension(60, 25));

		btnClose.addActionListener((ActionEvent e) -> {
			meinJDialog.dispose();

		});
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 1;
		gbc.gridy = 1;
		meinJDialog.add(btnClose, gbc);

		meinJDialog.setLocationRelativeTo(null);
		meinJDialog.setVisible(true);
	}

	/**
	 * Update the status of the active generator in the status bar. Stops other
	 * generators. Also starts new thread for status ready generators.
	 *
	 * @param obsGenerator active generator
	 * @param o1 Pass through to the method notifyObservers
	 */
	@Override
	public void update(Observable obsGenerator, Object o1) {

		observableGenerator = (IGenerator) obsGenerator;

		// Update status bar with error message if status is error
		if (observableGenerator.getGenStatus() == IGenerator.Status.ERROR) {
			statusLabel.setText(observableGenerator.getName() + " Status: " + observableGenerator.getGenStatus() + ": "
					+ observableGenerator.getErrorMessage());
			return;
		}

		// Stop all generators, but not active obsGenerator
		generators.stream().map((gen) -> {
			if (!gen.equals(obsGenerator)) {
				boolean bool = gen.getGenStatus() == IGenerator.Status.CALCULATING;
				gen.stopGenerator();

				if (bool) {
					statusLabel
							.setText(observableGenerator.getName() + " Status: " + observableGenerator.getGenStatus());
				}

			}
			return gen;
		}).forEachOrdered((gen) -> {

		});

		// Update status bar with status of observableGenerator
		// if finished display duration and status
		if (observableGenerator.getGenStatus() == IGenerator.Status.FINISHED) {
			int seconds = (int) observableGenerator.getCalcTime() / 1000;
			int millis = (int) (observableGenerator.getCalcTime() % 1000);
			statusLabel.setText(observableGenerator.getName() + "'s" + " calculations have "
					+ observableGenerator.getGenStatus() + " in " + seconds + "." + millis + " seconds");
		} else {
			// display status
			statusLabel
					.setText(observableGenerator.getName() + "'s" + " Status: " + observableGenerator.getGenStatus());
		}
		// In ready status start thread to keep the buttons active
		if (observableGenerator.getGenStatus() == IGenerator.Status.READY) {
			Thread thread = new Thread(observableGenerator);
			thread.start();
		}

	}
}
