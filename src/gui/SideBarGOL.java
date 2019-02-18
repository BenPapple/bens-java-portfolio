package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	private static JLabel lblNumGens = new JLabel("0");
	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarGOL(ActionListener e) {
		super(e);
	}

	/**
	 * Return randomness as string.
	 *
	 * @return string user input text
	 */
	public String getRandomness() {
		return tfRandomness.getText();
	}

	/**
	 * Get wait time in ms for pausing between generations.
	 *
	 * @return int wait time in ms
	 */
	public int getSpeed() {
		return (int) sliderSpeed.getValue();
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
		//
		JPanel pnlWolframRules = new JPanel(new GridLayout(2, 8));
		Border border2 = BorderFactory.createTitledBorder("Generation Counter");
		pnlWolframRules.setBorder(border2);
		pnlWolframRules.add(lblNumGens);
		lblNumGens.setHorizontalAlignment(JLabel.CENTER);
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

		return GeneratorPnl;
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
	 * Set number of generations into label.
	 * 
	 * @param inString generation number
	 */
	public void setGenCounter(String inString) {
		lblNumGens.setText(inString);
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
		super.setTaDescriptionText(
				"<b>Conway's Game of Life</b>"
						+ "<br><br>Implements  John Horton Conway's Game of Life cellular automaton."
						+ "<br><i>Randomness</i> determines how many <b>cells</b> are <b>alive</b> in the first generation,"
						+ " it can be set in the 0.0 to 1.0 range(equal to 0 to 100%)."
						+ "<br>The amount of living and dead cells from it's <b>8 neighbours</b> determines if a cell dies or "
						+ "stays alive in a <b>new generation</b>:"
						+ "<ul><li>A dead cell with 3 living neighbors becomes alive.</li>"
						+ "<li>Alive cells with less than two living neighbouring cells die.</li>"
						+ "<li>Alive cells with two or three living neighbouring cells stays alive.</li>"
						+ "<li>An alive cell with more than 3 living neighbors dies.</li>"
						+ "</ul>");
		super.setWidth(150);
		super.setHeight(150);
		super.setColor(Color.decode("#CC00CC"));
		super.setBGColor(Color.BLACK);
	}

}
