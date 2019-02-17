package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 * Sidebar for LSystem generator class.
 *
 * @author BenGe47
 */
public class SideBarLSystem extends ASideBar {

	private static JLabel lblStartingSequence = new JLabel("Enter Starting Sequence:");
	private static JLabel lblGenerations = new JLabel("Enter Iterations:");
	private static SpinnerModel smGenerations = new SpinnerNumberModel(7, 0, 15, 1);
	private static JSpinner jsGenerations = new JSpinner(smGenerations);
	private static JLabel lblAngle = new JLabel("Angle:");
	private static SpinnerModel smAngle = new SpinnerNumberModel(120, 1, 360, 1);
	private static JSpinner jsAngle = new JSpinner(smAngle);
	private static JTextField tfStartingSequence = new JTextField("A+A+B");
	private static JLabel lblProductionRules = new JLabel("Enter Production Rules:");
	private static JTextField tfProductionRules = new JTextField("(A,AA),(B,B+A-B-A+B)");
	private static JLabel lblcbPresets = new JLabel("Load Preset:");
	private static String comboBoxList[] = { "*** choose preset ***", "Koch edge", "Koch snowflake",
			"Sierpinski triangle", "Dragon curve", "Fuzzy weed", "Wavy seaweed", "Tall seaweed" };
	private static JComboBox<Object> cbPresets = new JComboBox<Object>(comboBoxList);
	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarLSystem(ActionListener e) {
		super(e);
	}

	/**
	 * Gets angle as int from user input.
	 *
	 * @return int value of user input angle
	 */
	public int getAngle() {
		return (int) jsAngle.getValue();
	}

	/**
	 * Gets generations as int from user input.
	 *
	 * @return int value of user input generations
	 */
	public int getGenerations() {
		return (int) jsGenerations.getValue();
	}

	/**
	 * Returns user input of LSystem rules as string.
	 *
	 * @return string of rules for LSystem
	 */
	public String getProductionRules() {
		return tfProductionRules.getText();
	}

	/**
	 * Returns user input starting sequence as string.
	 *
	 * @return string of Starting sequence
	 */
	public String getStartingSequence() {
		return tfStartingSequence.getText();
	}

	@Override
	public JPanel initGeneratorPnl() {

		GeneratorPnl = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		GeneratorPnl.setLayout(layout);

		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.lblStartingSequence, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.tfStartingSequence, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.lblProductionRules, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.tfProductionRules, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.lblAngle, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.jsAngle, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.lblGenerations, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 3;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.jsGenerations, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 4;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.lblcbPresets, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 4;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarLSystem.cbPresets, gbConstraints);
		cbPresets.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent ee) {
				loadPreset();
			}
		});

		Border border = BorderFactory.createTitledBorder("Generator settings:");

		GeneratorPnl.setBorder(border);

		setStdValues();

		return GeneratorPnl;
	}

	/**
	 * Put preset values in combobox.
	 *
	 */
	private void loadPreset() {
		String valuePreset = SideBarLSystem.cbPresets.getSelectedItem().toString();
		System.out.println("PresetString        :  " + valuePreset);

		switch (valuePreset) {
		case "*** choose preset ***":

			break;
		case "Sierpinski triangle":
			jsGenerations.setValue(6);
			tfStartingSequence.setText("A+A+B");
			tfProductionRules.setText("(A,AA),(B,B+A-B-A+B)");
			jsAngle.setValue(120);
			break;
		case "Koch snowflake":
			jsGenerations.setValue(5);
			tfStartingSequence.setText("F++F++F");
			tfProductionRules.setText("(F,F-F++F-F)");
			jsAngle.setValue(60);
			break;
		case "Koch edge":
			jsGenerations.setValue(4);
			tfStartingSequence.setText("F");
			tfProductionRules.setText("(F,F-F++F-F)");
			jsAngle.setValue(60);
			break;
		case "Dragon curve":
			jsGenerations.setValue(12);
			tfStartingSequence.setText("CD");
			tfProductionRules.setText("(E,-CD-E),(D,D+EC+)");
			jsAngle.setValue(90);
			break;
		case "Fuzzy weed":
			jsGenerations.setValue(6);
			tfStartingSequence.setText("A");
			tfProductionRules.setText("(F,FF),(A,F-[[A]+A]+F[+FA]-A)");
			jsAngle.setValue(22);
			break;
		case "Tall seaweed":
			jsGenerations.setValue(4);
			tfStartingSequence.setText("F");
			tfProductionRules.setText("(F,F[+F]F[-F]F)");
			jsAngle.setValue(25);
			break;
		case "Wavy seaweed":
			jsGenerations.setValue(5);
			tfStartingSequence.setText("F");
			tfProductionRules.setText("(F,FF-[-F+F+F]+[+F-F-F])");
			jsAngle.setValue(22);
			break;

		default:
			throw new AssertionError();
		}

	}

	@Override
	public void setStdValues() {
		super.setLblWidthText("Enter Width:");
		super.setLblHeightText("Enter Height");
		super.setWidth(1280);
		super.setHeight(720);
		super.setColor(Color.YELLOW);
		super.setBGColor(Color.BLACK);
		super.setTaDescriptionText("<b>Lindenmayer-system</b>"
				+ "<br><br>Implements a Lindenmayer-system."
				+ "<br>Allowed symbols: A B C D E F + - [ ]");
	}

}
