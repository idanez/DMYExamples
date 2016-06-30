package tutogef.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;

public class ServiceFigure extends Figure {
	private Label labelName = new Label();
	private Label labelEtage = new Label();

	public static final int SERVICE_FIGURE_DEFWIDTH = 250;
	public static final int SERVICE_FIGURE_DEFHEIGHT = 150;

	public ServiceFigure() {
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		labelName.setForegroundColor(ColorConstants.darkGray);
		add(labelName, ToolbarLayout.ALIGN_CENTER);
		setConstraint(labelName, new Rectangle(5, 17, -1, -1));

		labelEtage.setForegroundColor(ColorConstants.black);
		add(labelEtage, ToolbarLayout.ALIGN_CENTER);
		setConstraint(labelEtage, new Rectangle(5, 5, -1, -1));

		/** Just for Fun :) **/
		setForegroundColor(ColorConstants.black);
		setBorder(new LineBorder(1));
		setOpaque(true);
	}

	public void setName(final String text) {
		labelName.setText(text);
	}

	public void setEtage(final int etage) {
		labelEtage.setText("Etage:" + etage);
	}

	public void setLayout(final Rectangle rect) {
		getParent().setConstraint(this, rect);
	}

}