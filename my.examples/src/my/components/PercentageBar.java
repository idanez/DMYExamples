package my.components;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class PercentageBar extends Canvas implements PaintListener {

	private Color backgroundColor = Display.getDefault().getSystemColor(SWT.COLOR_RED);
	private Color okPartBgColor = Display.getDefault().getSystemColor(SWT.COLOR_GREEN);
	private Color textColor = Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	private int okPercentage;
	private boolean showText;

	/**
	 * The constructor.<br>
	 * Note that the default background color is Red.<br>
	 * The Green part, or the Ok Part will be painted according to the parameter okPercentage.
	 * 
	 * @param parent
	 *            - The parent composite where this must be placed.
	 * @param style
	 *            - The style
	 */
	public PercentageBar(final Composite parent, final int style) {
		super(parent, style);
		addPaintListener(this);
	}

	/**
	 * Informs the component that it must be udpated with the values informed.
	 * 
	 * @param okPercentage
	 *            - The okPartBackground percentage
	 * @param showText
	 *            - If the percetage text must be shown inside the bar.
	 */
	public final void updateBar(final int okPercentage, final boolean showText) {
		this.okPercentage = okPercentage;
		this.showText = showText;
		this.redraw();
		this.update();
	}

	/**
	 * Draws the coverage information bar in the given canvas.
	 * 
	 * @param okPercent
	 *            - The percentage covered.
	 * @param showPercentageText
	 *            - Whether to show the percentage text inside the bar or not.
	 */
	public final void drawBar(final double okPercent, final boolean showPercentageText) {
		Rectangle rectMain = this.getClientArea();
		int totalWidth = rectMain.width;
		int widthSize = (totalWidth * (int) okPercent) / 100;
		Rectangle rectCovered = new Rectangle(rectMain.x, rectMain.y, widthSize, rectMain.height);
		GC gc = new GC(this);
		gc.setForeground(textColor);
		gc.setBackground(backgroundColor);
		gc.drawRectangle(rectMain);
		gc.fillRectangle(rectMain.x, rectMain.y, rectMain.width, rectMain.height);
		gc.setBackground(okPartBgColor);
		gc.drawRectangle(rectCovered);
		gc.fillRectangle(rectCovered.x, rectCovered.y, rectCovered.width, rectCovered.height);
		if (showPercentageText) {
			// TODO: A localização do texto na barra está fixa. Mehorar esse processo para posicionar no meio.
			gc.drawText(okPercent + "%", 10, 7);
		}
		gc.dispose();
	}

	@Override
	public final void paintControl(final PaintEvent e) {
		drawBar(okPercentage, showText);
	}

}
