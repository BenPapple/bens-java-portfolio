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
import javax.swing.border.Border;

/**
 * Sidebar for TestShapes generator class.
 *
 * @author BenGe47
 */
public class SideBarShapes extends ASideBar {

	private static JLabel lblPresets = new JLabel("Shape:");
	private JPanel GeneratorPnl;

	private static String comboBoxList[] = { "Circle", "Ellipse", "Rectangle", "RoundRectangle" };
	private static final JComboBox<Object> J_COMBO_BOX = new JComboBox<Object>(comboBoxList);
	private static JComboBox<Object> cbPresets = J_COMBO_BOX;

	/**
	 * Constructor.
	 *
	 * @param e ActionEvent passthrough
	 */
	public SideBarShapes(ActionListener e) {
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
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 0;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarShapes.lblPresets, gbConstraints);
		gbConstraints.fill = GridBagConstraints.HORIZONTAL;
		gbConstraints.weightx = 0.5;
		gbConstraints.gridx = 1;
		gbConstraints.gridy = 1;
		gbConstraints.insets = new Insets(10, 10, 10, 10);
		GeneratorPnl.add(SideBarShapes.cbPresets, gbConstraints);

		Border border = BorderFactory.createTitledBorder("Generator settings:");
		GeneratorPnl.setBorder(border);

		setStdValues();

		return GeneratorPnl;
	}

	@Override
	public void setStdValues() {
		super.lblWidth.setText("Enter Pixel Width:");
		super.lblHeight.setText("Enter Pixel Height");
		super.taDescription.setText("Conway's Game of Life Generator");
		super.taDescription.setText(
				"<b>Simple shape generator</b>"
						+ "<br><br>" + "Most simple generator to easily understand how classes "
						+ "(generator and gui) interact to create generators.");

		super.setWidth(300);
		super.setHeight(200);
		super.setColor(Color.decode("#CCF0CC"));
		super.setBGColor(Color.BLACK);
	}

	/**
	 * Gets the name of a shape as string.
	 *
	 * @return string name of shape
	 */
	public String getShape() {
		return SideBarShapes.cbPresets.getSelectedItem().toString();
	}

}
