package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 * Abstract class to create unique sidebars for each generator in gui.
 *
 * @author BenGe47
 */
public abstract class ASideBar {

	private JButton btnGenerate = new JButton("Generate");
	private JButton btnPause = new JButton("Pause");
	private JButton btnStop = new JButton("Stop");

	/**
	 *
	 */
	protected JPanel PanelSidebar;
	private Boolean paused = false;
	private Boolean stopped = false;

	/**
	 *
	 */
	protected JLabel lblWidth = new JLabel("Horizontal # Cells (4x4 px):");

	/**
	 *
	 */
	protected JLabel lblHeight = new JLabel("Vertical # Cells (4x4 px):");

	/**
	 *
	 */
	protected JLabel lblColor = new JLabel("Color Cells");

	/**
	 *
	 */
	protected JButton btnColor = new JButton("Choose Color");

	/**
	 *
	 */
	protected JLabel lblBGColor = new JLabel("BGColor");

	/**
	 *
	 */
	protected JButton btnBGColor = new JButton("Choose BG Color");

	private SpinnerModel smWidth = new SpinnerNumberModel(300, 0, 3840, 1);
	private SpinnerModel smHeight = new SpinnerNumberModel(300, 0, 2160, 1);
	private JSpinner jsWidth = new JSpinner(smWidth);
	private JSpinner jsHeight = new JSpinner(smHeight);
	private Color chosenColor = Color.GRAY;
	private Color BGColor = Color.BLACK;

	/**
	 * 
	 */
	protected JTextPane taDescription;

	/**
	 * Constructor with event pass through.
	 *
	 * @param e ActionListener event buttons
	 */
	public ASideBar(ActionListener e) {
		PanelSidebar = new JPanel();
//		PanelSidebar.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		
		createSideBar(e);

	}

	/**
	 * Get complete sidebar panel for specific generator.
	 * 
	 *
	 * @return PanelSidebar complete Sidebar Panel
	 */
	public JPanel getSideBarPnl() {
		return PanelSidebar;
	}

	/**
	 * Construct sidebar with bottom, top, center and generator specific JPanel.
	 *
	 * @param e ActionListener buttons
	 */
	protected void createSideBar(ActionListener e) {
		btnGenerate.addActionListener(e);

		PanelSidebar.setLayout(new BorderLayout());

		JPanel SideBarPanel = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		SideBarPanel.setLayout(layout);		

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarPanel.add(initDescriptionPnl(), gbConstraints);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarPanel.add(initGraphicControls(), gbConstraints);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarPanel.add(initGeneratorPnl(), gbConstraints);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarPanel.add(initButtonPnl(), gbConstraints);

		PanelSidebar.add(SideBarPanel, BorderLayout.WEST);

	}

	/**
	 * Create center panel in sidebar with graphic controls for mainCanvas.
	 * 
	 * @return SideBarControlPnl central graphics panel in sidebar
	 */
	private JPanel initGraphicControls() {
		lblColor.setBounds(0, 0, 0, 10);
		lblColor.setForeground(chosenColor);
		lblColor.setBackground(chosenColor);
		lblColor.setOpaque(true);

		lblBGColor.setBounds(0, 0, 0, 10);
		lblBGColor.setForeground(BGColor);
		lblBGColor.setBackground(BGColor);
		lblBGColor.setOpaque(true);

		JPanel SideBarControlPnl = new JPanel();
		GridBagLayout layout = new GridBagLayout();

		SideBarControlPnl.setLayout(layout);

		GridBagConstraints gbConstraints = new GridBagConstraints();

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(lblWidth, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(jsWidth, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(lblHeight, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(jsHeight, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.gridwidth = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(btnColor, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.gridwidth = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(lblColor, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(btnBGColor, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		SideBarControlPnl.add(lblBGColor, gbConstraints);

		btnColor.addActionListener((ActionEvent e) -> {
			chosenColor = JColorChooser.showDialog(null, "Farbauswahl", null);
			lblColor.setForeground(chosenColor);
			lblColor.setBackground(chosenColor);
		});
		btnBGColor.addActionListener((ActionEvent e) -> {
			BGColor = JColorChooser.showDialog(null, "Farbauswahl", null);
			lblBGColor.setForeground(BGColor);
			lblBGColor.setBackground(BGColor);
		});

		Border border = BorderFactory.createTitledBorder("Graphic settings:");
		SideBarControlPnl.setBorder(border);

		return SideBarControlPnl;
	}

	/**
	 * Create top panel in sidebar with description of generator.
	 * 
	 * @return SideBarPanelNorth Topmost panel
	 */
	private JPanel initDescriptionPnl() {
		taDescription = new JTextPane();
		taDescription.setContentType("text/html");
		taDescription.setEditorKit(new WrappedHtmlEditorKit());
		taDescription.setOpaque(false);
		taDescription.setEditable(false);
		
		taDescription.setText("");
		
		JScrollPane spDescription = new JScrollPane(taDescription);
		//height of description panel
		spDescription.setPreferredSize(new Dimension(310, 120));
		JPanel SideBarPanelNorth = new JPanel();

		Border border = BorderFactory.createTitledBorder("Description:");
		SideBarPanelNorth.setBorder(border);

		BorderLayout layout = new BorderLayout();
		SideBarPanelNorth.setLayout(layout);

		SideBarPanelNorth.add(spDescription);
		return SideBarPanelNorth;
	}

	/**
	 * Create bottom panel in sidebar with buttons.
	 * 
	 * @return SideBarPanelSouth bottom panel
	 */
	private JPanel initButtonPnl() {

		// IGenerator observableGenerator;

		btnPause.addActionListener((ActionEvent e) -> {
			if (paused) {
				paused = false;
				btnPause.setText("Pause");
			} else {
				paused = true;
				btnPause.setText("Play");
			}

		});
		btnStop.addActionListener((ActionEvent e) -> {
			stopped = true;
		});

		btnPause.setEnabled(false);
		btnStop.setEnabled(false);

		JPanel SideBarPanelSouth = new JPanel();
		SideBarPanelSouth.add(btnGenerate);
		SideBarPanelSouth.add(btnPause);
		SideBarPanelSouth.add(btnStop);
		return SideBarPanelSouth;
	}

	/**
	 * Get current width value.
	 *
	 * @return int new width value from user input
	 */
	public int getWidth() {
		return (int) jsWidth.getValue();
	}

	/**
	 * Set width to new value.
	 *
	 * @param x new width value
	 */
	public void setWidth(int x) {
		jsWidth.setValue(x);
	}

	/**
	 * Get current height value.
	 *
	 * @return int height value user input
	 */
	public int getHeight() {
		return (int) jsHeight.getValue();
	}

	/**
	 * Set height to new value.
	 *
	 * @param x new height value
	 */
	public void setHeight(int x) {
		jsHeight.setValue(x);
	}

	/**
	 * Get color for backround in mainCanvas.
	 *
	 * @return BGColor current color for background
	 */
	public Color getBGColor() {
		return BGColor;
	}

	/**
	 * Set color for background in color examples.
	 *
	 * @param x new color value
	 */
	public void setBGColor(Color x) {
		BGColor = x;
		lblBGColor.setForeground(BGColor);
		lblBGColor.setBackground(BGColor);
	}

	/**
	 * Get color for foreground elements in gui.
	 *
	 * @return chosenColor Color of foreground elements.
	 */
	public Color getColor() {
		return chosenColor;
	}

	/**
	 * Returns true if generator is paused.
	 *
	 * @return paused is true when paused
	 */
	public Boolean isPaused() {
		return paused;
	}

	/**
	 * Returns true if generator is stopped.
	 *
	 * @return stopped true if stopped
	 */
	public Boolean isStopped() {
		return stopped;
	}

	/**
	 * Generator calculations will be stopped.
	 *
	 */
	public void setStopped() {
		stopped = true;
	}

	/**
	 * Sets label to color chosen from colorchooser.
	 *
	 * @param x color to set
	 */
	public void setColor(Color x) {
		chosenColor = x;
		lblColor.setForeground(chosenColor);
		lblColor.setBackground(chosenColor);
	}

	/**
	 * Set buttons to only pause and stop button active.
	 *
	 */
	public void setButtonsCalculating() {
		paused = false;
		stopped = false;
		btnGenerate.setEnabled(false);
		btnPause.setEnabled(true);
		btnStop.setEnabled(true);
		btnPause.setText("Pause");
	}

	/**
	 * Set buttons to only button generate is active.
	 *
	 */
	public void setButtonsReady() {
		btnGenerate.setEnabled(true);
		btnPause.setText("Pause");
		btnPause.setEnabled(false);
		btnStop.setEnabled(false);
	}

	/**
	 * Init all gui elements with standard values for this generator.
	 *
	 */
	public abstract void setStdValues();

	/**
	 * Create generator specific JPanel with generator specific gui controls.
	 *
	 * @return JPanel Panel of generator specific gui elements in sidebar. *
	 */
	public abstract JPanel initGeneratorPnl();

}
