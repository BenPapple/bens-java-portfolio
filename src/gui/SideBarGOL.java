package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
 * Sidebar for GameofLife generator class.
 *
 * @author BenGe47
 */
public class SideBarGOL extends ASideBar {

	private static JLabel lblRandomness = new JLabel("Enter Randomness:");
	private static JTextField tfRandomness = new JTextField("0.33");
	private static JLabel lblSpeed = new JLabel("Speed(in ms wait):");
	private static JSlider sliderSpeed = new JSlider();
	private static JCheckBox cbEdgeDead = new JCheckBox("Dead Cells Edge", true);
	private static JCheckBox cbEdgeWrapAround = new JCheckBox("Wraparound Edge");
	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarGOL(ActionListener e) {
		super(e);
	}

	@Override
	public JPanel initGeneratorPnl() {

		GeneratorPnl = new JPanel();

		ButtonGroup bgEdge = new ButtonGroup();
		bgEdge.add(cbEdgeWrapAround);
		bgEdge.add(cbEdgeDead);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		GeneratorPnl.setLayout(layout);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.lblRandomness, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.tfRandomness, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.lblSpeed, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.sliderSpeed, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.cbEdgeWrapAround, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarGOL.cbEdgeDead, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");
		GeneratorPnl.setBorder(border);

		setStdValues();

		return GeneratorPnl;
	}

	@Override
	public void setStdValues() {
		cbEdgeWrapAround.setSelected(true);
		sliderSpeed.setMinimum(0);
		sliderSpeed.setMaximum(500);
		sliderSpeed.setMajorTickSpacing(100);
		sliderSpeed.setMinorTickSpacing(50);
		sliderSpeed.createStandardLabels(50);
		sliderSpeed.setPaintTicks(true);
		sliderSpeed.setPaintLabels(true);
		sliderSpeed.setValue(100);
		sliderSpeed.setPreferredSize(new Dimension(150, 80));
		super.taDescription.setText(
				"<b>Conway's Game of Life</b>"
						+ "<br><br>Implements Conway's Game of Life cellular automaton.");
		super.setWidth(150);
		super.setHeight(150);
		super.setColor(Color.decode("#CC00CC"));
		super.setBGColor(Color.BLACK);
	}

	/**
	 * Get wait time in ms for pausing between generations.
	 *
	 * @return int wait time in ms
	 */
	public int getSpeed() {
		return (int) sliderSpeed.getValue();
	}

	/**
	 * Is true if selected and signals edge of array wraps around to opposite
	 * edge.
	 *
	 * @return bool true if selected
	 */
	public Boolean isEdgeWrapAround() {
		return cbEdgeWrapAround.isSelected();
	}

	/**
	 * Return randomness as string.
	 *
	 * @return string user input text
	 */
	public String getRandomness() {
		return tfRandomness.getText();
	}

}
