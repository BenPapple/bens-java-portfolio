package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 * Sidebar for RTree generator class.
 *
 * @author BenGe47
 */
public class SideBarRTree extends ASideBar {

	private static JLabel lblGenerations = new JLabel("Tree Depth:");
	private static SpinnerModel smGenerations = new SpinnerNumberModel(5, 0, 15, 1);
	private static JSpinner jsGenerations = new JSpinner(smGenerations);
	private static JLabel lblcbPresets = new JLabel("Color Model:");
	private static String comboBoxList[] = { "Preview All", "Monochrome", "Monochrome Noise", "RGB",
			"RGB White Noise" };
	private static JComboBox<Object> cbColorPresets = new JComboBox<Object>(comboBoxList);
	private static JLabel lblSeed = new JLabel("Random Seed:");
	private static JTextField tfSeed = new JTextField();
	private static JCheckBox cbSeed = new JCheckBox("Use above seed", false);
	// private static JButton btnLoad = new JButton("Load previous image");

	private JPanel GeneratorPnl;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarRTree(ActionListener e) {
		super(e);
	}

	/**
	 * Returns user choosen color model as int position in combobox.
	 *
	 * @return int of selected index
	 */
	public int getColorModelIndex() {
		return SideBarRTree.cbColorPresets.getSelectedIndex();
	}

	/**
	 * Returns user choosen color model as string.
	 *
	 * @return string of color calculation model
	 */
	public String getColorModelName() {
		return SideBarRTree.cbColorPresets.getSelectedItem().toString();
	}

	/**
	 * Get user input generations value.
	 *
	 * @return int of num generations
	 */
	public int getGenerations() {
		return (int) jsGenerations.getValue();
	}

	/**
	 * Get the user input seed as int value.
	 *
	 * @return int of random seed
	 */
	public int getSeed() {
		return Integer.parseInt(tfSeed.getText());
	}

	@Override
	public JPanel initGeneratorPnl() {

		GeneratorPnl = new JPanel();

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbConstraints = new GridBagConstraints();

		GeneratorPnl.setLayout(layout);

		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.lblGenerations, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.jsGenerations, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.lblcbPresets, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.cbColorPresets, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.lblSeed, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.tfSeed, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 1;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 3;
		gbConstraints.gridwidth = 2;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarRTree.cbSeed, gbConstraints);
		// //
		// gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		// gbConstraints.weightx = 1;
		// gbConstraints.gridx = 0;
		// gbConstraints.gridy = 4;
		// gbConstraints.gridwidth = 2;
		// gbConstraints.insets = new Insets(10, 10, 10, 10);
		// GeneratorPnl.add(SideBarRTree.btnLoad, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");

		GeneratorPnl.setBorder(border);

		setStdValues();

		return GeneratorPnl;
	}

	/**
	 * Make cbSeed true.
	 * 
	 */
	public void setCbSeed() {
		cbSeed.setSelected(true);
	}

	/**
	 * Set user input generations value.
	 *
	 * @return int of num generations
	 */
	public void setGenerations(int inGen) {
		jsGenerations.setValue(inGen);
	}

	/**
	 * Sets tfSeed to input string.
	 *
	 */
	public void setSeedText(String inSeed) {
		tfSeed.setText(inSeed);
	}

	@Override
	public void setStdValues() {
		super.setWidth(800);
		super.setHeight(600);
		super.setColor(Color.CYAN);
		super.setBGColor(Color.BLACK);
		super.setbtnBGColorVisible(false);
		super.setbtnColorVisible(false);
		super.setlblColorVisible(false);
		super.setLblBGColorVisible(false);
		super.setLblWidthText("Enter Width:");
		super.setLblHeightText("Enter Height");
		cbColorPresets.setSelectedIndex(3);
		super.setTaDescriptionText(
				"<b>Random-tree Art</b>"
						+ "<br><br>Creates a random binary tree. Each node has a math "
						+ "formula with one or two variables. The variables "
						+ "get filled with the result of a childrens node math "
						+ "function or if they are a leave with the xy coordinate. "
						+ "This creates a color value for every xy coordinate.");
	}

	/**
	 * Determines if calculations should use textfield seed or generate new.
	 *
	 * @return true if selected
	 */
	public Boolean usingFieldSeed() {
		return cbSeed.isSelected();
	}

}
