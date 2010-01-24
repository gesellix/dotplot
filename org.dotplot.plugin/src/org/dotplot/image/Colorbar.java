/*
 * Created on 28.06.2004
 */
package org.dotplot.image;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * A output widget used to display a colormap, derived from the
 * org.eclipse.swt.widgets.Canvas, and can be used in any context that calls for
 * a Canvas.
 * 
 * @author Dennis Sigel for Swing, changes for SWT by Tobias Gesellchen
 */
public final class Colorbar extends Canvas {
	/*
	 * some helpful links:
	 * http://www.eclipse.org/articles/Article-Writing%20Your
	 * %20Own%20Widget/Writing%20Your%20Own%20Widget.htm
	 * http://www.cs.umanitoba.ca/~eclipse/9-Custom.pdf
	 * http://help.eclipse.org/help21
	 * /index.jsp?topic=/org.eclipse.platform.doc.isv
	 * /guide/swt_widgets_custom.htm
	 * 
	 * 
	 * 
	 * 
	 * http://www.eclipse.org/articles/Article-SWT-images/graphics-resources.html
	 * http://www.eclipse.org/articles/Article-Image-Viewer/Image_viewer.html
	 */
	protected int componentWidth;

	protected int componentHeight;

	protected int[][] lut;

	/**
	 * Canvas/Control to show a lookup table.
	 * 
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param style
	 *            the style of control to construct
	 * 
	 * @see org.eclipse.swt.widgets.Canvas
	 */
	public Colorbar(Composite parent, int style) {
		super(parent, style);

		lut = LUTs.gray();

		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Colorbar.this.paintControl(e);
			}
		});
	}

	/**
	 * Get the "background" of the displayed LUT.
	 * 
	 * @return the current background color
	 * 
	 * @see #setLUTBackground(RGB)
	 */
	public RGB getLUTBackground() {
		return new RGB(lut[0][0], lut[1][0], lut[2][0]);
	}

	/**
	 * Get the "foreground" of the displayed LUT.
	 * 
	 * @return the current foreground color
	 * 
	 * @see #setLUTForeground(RGB)
	 */
	public RGB getLUTForeground() {
		return new RGB(lut[0][lut[0].length - 1], lut[1][lut[1].length - 1],
				lut[2][lut[2].length - 1]);
	}

	/**
	 * paints the currently selected lookup table.
	 */
	protected void paintControl(PaintEvent e) {

		if (lut == null) {
			return;
		}

		GC gc = e.gc;

		gc.setBackground(getBackground());
		gc.fillRectangle(0, 0, componentWidth, componentHeight);

		float slope = componentWidth / 256.0F;

		for (int n = 0; n < lut[0].length; n++) {
			int w = componentWidth - (int) (n * slope);
			int v = lut[0].length - n - 1;
			int red = lut[0][v] & 0xFF;
			int green = lut[1][v] & 0xFF;
			int blue = lut[2][v] & 0xFF;
			gc.setBackground(new Color(null, red, green, blue));
			gc.fillRectangle(0, 0, w, componentHeight);
		}
	}

	/**
	 * Records a new size. Called by the AWT/SWT.
	 * 
	 * @param x
	 *            the new x coordinate for the receiver
	 * @param y
	 *            the new y coordinate for the receiver
	 * @param width
	 *            the new width for the receiver
	 * @param height
	 *            the new height for the receiver
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		componentWidth = width;
		componentHeight = height;
		super.setBounds(x, y, width, height);
	}

	/**
	 * Records a new size. Called by the AWT/SWT.
	 * 
	 * @param rect
	 *            the new bounds for the receiver
	 */
	@Override
	public void setBounds(Rectangle rect) {
		setBounds(rect.x, rect.y, rect.width, rect.height);
	}

	/**
	 * Changes the contents of the lookup table.
	 * 
	 * @param newlut
	 *            the LUT to display
	 */
	public synchronized void setLut(int[][] newlut) {
		if (newlut == null) {
			return;
		}

		for (int i = 0; i < newlut[0].length; i++) {
			lut[0][i] = newlut[0][i];
			lut[1][i] = newlut[1][i];
			lut[2][i] = newlut[2][i];
		}

		redraw(); // show new LUT
	}

	/**
	 * Sets the "background" of the displayed LUT.
	 * 
	 * @param color
	 *            a valid RGB object
	 * 
	 * @see #getLUTBackground()
	 */
	public void setLUTBackground(RGB color) {
		if (color != null) {
			lut[0][0] = color.red;
			lut[1][0] = color.green;
			lut[2][0] = color.blue;
		}

		redraw(); // show new LUT
	}

	/**
	 * Sets the "foreground" of the displayed LUT.
	 * 
	 * @param color
	 *            a valid RGB object
	 * 
	 * @see #getLUTForeground()
	 */
	public void setLUTForeground(RGB color) {
		if (color != null) {
			lut[0][lut[0].length - 1] = color.red;
			lut[1][lut[1].length - 1] = color.green;
			lut[2][lut[2].length - 1] = color.blue;
		}

		redraw(); // show new LUT
	}

	/**
	 * Records a new size.
	 * 
	 * @param w
	 *            the new width for the receiver
	 * @param h
	 *            the new height for the receiver
	 */
	@Override
	public void setSize(int w, int h) {
		setBounds(getBounds().x, getBounds().y, w, h);
	}
}
