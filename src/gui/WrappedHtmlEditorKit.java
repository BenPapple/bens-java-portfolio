package gui;

import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

/**
 * This class from below link allows word wrap with html in JTextPane when this
 * editor is chosen.
 * 
 * @see <a
 *      href="https://stackoverflow.com/questions/7811666/enabling-word-wrap-in-a-jtextpane-with-htmldocument>link</a>
 * 
 * @author StanislavL
 * @author vallismortis
 *
 */
public class WrappedHtmlEditorKit extends HTMLEditorKit {
	private class WrappedHtmlFactory extends HTMLEditorKit.HTMLFactory {
		private class WrapLabelView extends LabelView {
			public WrapLabelView(Element elem) {
				super(elem);
				return;
			}

			@Override
			public float getMinimumSpan(int axis) {
				switch (axis) {
				case View.X_AXIS: {
					return 0;
				}
				case View.Y_AXIS: {
					return super.getMinimumSpan(axis);
				}
				default: {
					throw new IllegalArgumentException("Invalid axis: " + axis);
				}
				}
			}
		}

		@Override
		public View create(Element elem) {
			View v = super.create(elem);

			if (v instanceof LabelView) {
				Object o = elem.getAttributes().getAttribute(StyleConstants.NameAttribute);

				if ((o instanceof HTML.Tag) && (o == HTML.Tag.BR)) {
					return v;
				}

				return new WrapLabelView(elem);
			}

			return v;
		}
	}

	private static final long serialVersionUID = 1L;

	private ViewFactory viewFactory = null;

	public WrappedHtmlEditorKit() {
		super();
		this.viewFactory = new WrappedHtmlFactory();
		return;
	}

	@Override
	public ViewFactory getViewFactory() {
		return this.viewFactory;
	}
}
