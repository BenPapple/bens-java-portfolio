package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Sidebar for NaSch generator class.
 *
 * @author BenGe47
 */
public class SideBarNaSch extends ASideBar {

	private static JLabel lblSpawnRand = new JLabel("Spawn Randomness:");
	private static JTextField tfSpawnRand = new JTextField("0.5");
	private static JLabel lblBrakeRandomness = new JLabel("Brake Randomness:");
	private static JTextField tfBrakeRandomness = new JTextField("0.5");

	private static JLabel lblSpeed1 = new JLabel("Zoom:");
	private static JSlider sliderSpeed1 = new JSlider();
	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent pass through
	 */
	public SideBarNaSch(ActionListener e) {
		super(e);
	}

	/**
	 * Gets brake randomness as string from user input.
	 *
	 * @return string of randomness user input
	 */
	public String getBrakeRandomness() {
		return tfBrakeRandomness.getText();
	}

	/**
	 * Gets spawn randomness as string from user input.
	 *
	 * @return string of randomness user input
	 */
	public String getRandomness() {
		return tfSpawnRand.getText();
	}

	/**
	 * Returns user input speed value(pause between screen updates) as int.
	 *
	 * @return int of speed value
	 */
	public int getZoomFactor() {
		return (int) sliderSpeed1.getValue();
	}

	@Override
	public JPanel initGeneratorPnl() {

		GeneratorPnl = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		GeneratorPnl.setLayout(layout);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.lblSpawnRand, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.tfSpawnRand, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.lblBrakeRandomness, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.tfBrakeRandomness, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.lblSpeed1, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarNaSch.sliderSpeed1, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");
		GeneratorPnl.setBorder(border);

		setStdValues();

		return this.GeneratorPnl;

	}

	@Override
	public void setStdValues() {
		super.setLblWidthText("Enter Width:");
		super.setLblHeightText("Enter Height");
		sliderSpeed1.setMinimum(1);
		sliderSpeed1.setMaximum(4);
		sliderSpeed1.setMajorTickSpacing(1);
		sliderSpeed1.createStandardLabels(1);
		sliderSpeed1.setPaintTicks(true);
		sliderSpeed1.setPaintLabels(true);
		sliderSpeed1.setValue(2);
		sliderSpeed1.setPreferredSize(new Dimension(150, 80));
		super.setTaDescriptionText("<b>Nagel-Schreckenberg Cellular Automaton</b>"
				+ "<br><br>"
				+ "This CA <b>simulates traffic</b> on a ring street(<b>pixel row</b>). "
				+ "The car speed is shown with following colors:"
				+ "<br>"
				+ "<font color=\"red\">Car speed 0</font>"
				+ "<br>"
				+ "<font color=\"orange\">Car speed 1</font>"
				+ "<br>"
				+ "<font color=\"yellow\">Car speed 2</font>"
				+ "<br>"
				+ "<font color=\"green\">Car speed 3</font>"
				+ "<br>"
				+ "<font color=\"blue\">Car speed 4</font>"
				+ "<br>"
				+ "<font color=\"purple\">Car speed 5</font>"
				+ "<br>"
				+ "Every new row:<ul> <li>Every car gets accelerated by 1 to a max speed of 5.</li> <li>If the new speed is "
				+ "higher than the distance to the next car the speed is set to be equal to the distance.</li> <li>Last "
				+ "every car can be decelerated by 1, this is determined by user input <i>Brake Randomness</i> chance.</li></ul>");

		super.setWidth(500);
		super.setHeight(300);
		super.setbtnColorVisible(false);
		super.setlblColorVisible(false);
		super.setColor(Color.decode("#9999FF"));
		super.setBGColor(Color.BLACK);
	}

}
