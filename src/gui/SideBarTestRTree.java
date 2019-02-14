package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 * Sidebar for TestRandomTreeArt generator class.
 *
 * @author BenGe47
 */
public class SideBarTestRTree extends SideBarRTree {

	private static JLabel lblGenerations = new JLabel("Tree Depth:");
	private static SpinnerModel smGenerations = new SpinnerNumberModel(7, 0, 15, 1);
	private static JSpinner jsGenerations = new JSpinner(smGenerations);
	private static JLabel lblcbPresets = new JLabel("Color Model:");
	private static String comboBoxList[] = { "Monochrome", "Monochrome Noise", "RGB", "RGB White Noise" };
	private static final JComboBox<Object> J_COMBO_BOX = new JComboBox<Object>(comboBoxList);
	private static JComboBox<Object> cbColorPresets = J_COMBO_BOX;
	private JPanel GeneratorPnl;

	/**
	 * Constructor
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarTestRTree(ActionListener e) {
		super(e);
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
		GeneratorPnl.add(SideBarTestRTree.lblGenerations, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 0;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarTestRTree.jsGenerations, gbConstraints);
		//
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.2;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarTestRTree.lblcbPresets, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.8;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarTestRTree.cbColorPresets, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");

		GeneratorPnl.setBorder(border);

		setStdValues();

		return GeneratorPnl;
	}

	@Override
	public void setStdValues() {
		super.setWidth(1080);
		super.setHeight(1080);
		super.setColor(Color.CYAN);
		super.setBGColor(Color.BLACK);
		super.btnBGColor.setVisible(false);
		super.btnColor.setVisible(false);
		super.lblColor.setVisible(false);
		super.lblBGColor.setVisible(false);
		super.lblWidth.setText("Enter Width:");
		super.lblHeight.setText("Enter Height");
		cbColorPresets.setSelectedIndex(2);
		super.taDescription.setText("Alternative Random-tree Art Generator");
	}

	@Override
	public int getGenerations() {
		return (int) jsGenerations.getValue();
	}

	@Override
	public String getColorModelName() {
		return SideBarTestRTree.cbColorPresets.getSelectedItem().toString();
	}

	@Override
	public int getColorModelIndex() {
		return SideBarTestRTree.cbColorPresets.getSelectedIndex();
	}

}
