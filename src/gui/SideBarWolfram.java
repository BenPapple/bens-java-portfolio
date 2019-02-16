package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Sidebar for Wolfram generator class.
 *
 * @author BenGe47
 */
public class SideBarWolfram extends ASideBar implements ActionListener {

	private static JLabel lblRandomness1 = new JLabel("Enter Randomness:");
	private static JTextField tfRandomness1 = new JTextField("0.33");
	private static JLabel lblSpeed1 = new JLabel("Speed(in ms wait):");
	private static JSlider sliderSpeed1 = new JSlider();
	private static JCheckBox cbEdgeDead1 = new JCheckBox("Dead Cells Edge", true);
	private static JCheckBox cbEdgeWrapAround1 = new JCheckBox("Wraparound Edge");
	private static JCheckBox cb0 = new JCheckBox("", true);
	private static JCheckBox cb1 = new JCheckBox("", false);
	private static JCheckBox cb2 = new JCheckBox("", false);
	private static JCheckBox cb3 = new JCheckBox("", true);
	private static JCheckBox cb4 = new JCheckBox("", true);
	private static JCheckBox cb5 = new JCheckBox("", true);
	private static JCheckBox cb6 = new JCheckBox("", true);
	private static JCheckBox cb7 = new JCheckBox("", false);
	private static JLabel lblRule = new JLabel("");
	private int rule;
	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarWolfram(ActionListener e) {
		super(e);
	}

	@Override
	public JPanel initGeneratorPnl() {

		GeneratorPnl = new JPanel();

		ButtonGroup bgEdge = new ButtonGroup();
		bgEdge.add(cbEdgeWrapAround1);
		bgEdge.add(cbEdgeDead1);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		GeneratorPnl.setLayout(layout);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.lblRandomness1, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.tfRandomness1, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.lblSpeed1, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.sliderSpeed1, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.cbEdgeWrapAround1, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarWolfram.cbEdgeDead1, gbConstraints);

		JPanel pnlCbox = new JPanel();
		pnlCbox.add(cb0);
		pnlCbox.add(cb1);
		pnlCbox.add(cb2);
		pnlCbox.add(cb3);
		pnlCbox.add(cb4);
		pnlCbox.add(cb5);
		pnlCbox.add(cb6);
		pnlCbox.add(cb7);
		cb0.addActionListener(this);
		cb1.addActionListener(this);
		cb2.addActionListener(this);
		cb3.addActionListener(this);
		cb4.addActionListener(this);
		cb5.addActionListener(this);
		cb6.addActionListener(this);
		cb7.addActionListener(this);

		JPanel pnlWolframRules = new JPanel(new GridLayout(2, 8));
		Border border2 = BorderFactory.createTitledBorder("Wolfram Rules:");
		pnlWolframRules.setBorder(border2);
		pnlWolframRules.add(pnlCbox);
		pnlWolframRules.add(lblRule);
		lblRule.setHorizontalAlignment(JLabel.CENTER);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(pnlWolframRules, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");
		GeneratorPnl.setBorder(border);

		setStdValues();

		return this.GeneratorPnl;

	}

	@Override
	public void setStdValues() {
		super.setLblWidthText("Enter Pixel Width:");
		super.setLblHeightText("Enter Pixel Height");
		lblRandomness1.setText("Initial randomness:");
		lblRule.setText("Rule " + rule);
		cbEdgeWrapAround1.setSelected(true);
		sliderSpeed1.setMinimum(0);
		sliderSpeed1.setMaximum(500);
		sliderSpeed1.setMajorTickSpacing(100);
		sliderSpeed1.setMinorTickSpacing(50);
		sliderSpeed1.createStandardLabels(50);
		sliderSpeed1.setPaintTicks(true);
		sliderSpeed1.setPaintLabels(true);
		sliderSpeed1.setValue(0);
		sliderSpeed1.setPreferredSize(new Dimension(150, 80));
		// taDescription.setPreferredSize(new Dimension(310, 150));
		super.setTaDescriptionText("<b>Wolfram Cellular Automaton</b>"
				+ "<br><br>"
				+ "Implements Stephen Wolfram's cellular automaton<br> with all 256 rules." );

		super.setWidth(850);
		super.setHeight(450);
		super.setColor(Color.decode("#9999FF"));
		super.setBGColor(Color.BLACK);
	}

	/**
	 * Returns user input speed value(pause between screen updates) as int.
	 *
	 * @return int of speed value
	 */
	public int getSpeed() {
		return (int) sliderSpeed1.getValue();
	}

	/**
	 * True if cellular automaton calculations should calculate edge cases.
	 *
	 * @return true if selected
	 */
	public Boolean isEdgeWrapAround() {
		return cbEdgeWrapAround1.isSelected();
	}

	/**
	 * Gets randomness as string from user input.
	 *
	 * @return string of randomness user input
	 */
	public String getRandomness() {
		return tfRandomness1.getText();
	}

	/**
	 * Determines when neighbors are 111 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB0() {
		return cb0.isSelected();
	}

	/**
	 * Determines when neighbors are 110 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB1() {
		return cb1.isSelected();
	}

	/**
	 * Determines when neighbors are 101 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB2() {
		return cb2.isSelected();
	}

	/**
	 * Determines when neighbors are 100 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB3() {
		return cb3.isSelected();
	}

	/**
	 * Determines when neighbors are 011 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB4() {
		return cb4.isSelected();
	}

	/**
	 * Determines when neighbors are 010 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB5() {
		return cb5.isSelected();
	}

	/**
	 * Determines when neighbors are 001 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB6() {
		return cb6.isSelected();
	}

	/**
	 * Determines when neighbors are 000 if it means dead/false or alive/true.
	 *
	 * @return true if selected
	 */
	public Boolean isCB7() {
		return cb7.isSelected();
	}

	/**
	 * Perform click on button to calculate Wolfram rule on generator creation.
	 */
	public void clickRules() {
		cb0.doClick();
	}

	/**
	 * Calculate Wolfram rule from 8 checkboxes representing 8bits.
	 */
	public void actionPerformed(ActionEvent e) {
		rule = 0;
		if (cb7.isSelected()) {
			rule += 1 * 1;
		}
		if (cb6.isSelected()) {
			rule += 1 * 2;
		}
		if (cb5.isSelected()) {
			rule += 1 * 4;
		}
		if (cb4.isSelected()) {
			rule += 1 * 8;
		}
		if (cb3.isSelected()) {
			rule += 1 * 16;
		}
		if (cb2.isSelected()) {
			rule += 1 * 32;
		}
		if (cb1.isSelected()) {
			rule += 1 * 64;
		}
		if (cb0.isSelected()) {
			rule += 1 * 128;
		}
		lblRule.setText("Rule " + rule);
	}

}
