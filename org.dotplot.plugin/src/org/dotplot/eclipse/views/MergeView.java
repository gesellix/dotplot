package org.dotplot.eclipse.views;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.jface.text.Document;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * <code>Merge View</code> shows Diff.
 * 
 * @author Sascha Hemminger
 * @see org.eclipse.compare.contentmergeviewer.TextMergeViewer
 */
public class MergeView extends ViewPart {
	private TextDiffViewer merger;

	private CompareConfiguration config;

	public MergeView() {
		this.config = new CompareConfiguration();
		this.config.setLeftEditable(false);
		this.config.setRightEditable(false);
	}

	/**
	 * creates the control.
	 * 
	 * @param parent
	 *            the parent composite
	 * 
	 * @see ViewPart#createPartControl
	 */
	@Override
	public void createPartControl(Composite parent) {
		this.merger = new TextDiffViewer(parent, this.config);
	}

	/**
	 * empty implementation.
	 * 
	 * @see ViewPart#setFocus
	 */
	@Override
	public void setFocus() {
	}

	// TODO dont't know how good the rectangle thing works...
	/**
	 * the core of the diff, calculates which rows to show.
	 * 
	 * @param textLeft
	 *            left text to compare
	 * @param lineLeft
	 *            from which line on
	 * @param textRight
	 *            right text to compare
	 * @param lineRight
	 *            from which line on
	 * @param choice
	 *            a rectangular region of interest
	 */
	public void setText(File textLeft, int lineLeft, File textRight,
			int lineRight, Rectangle choice) {
		Document docLeft = null;
		Document docRight = null;
		BufferedReader left = null, right = null;

		try {
			left = new BufferedReader(new FileReader(textLeft));
			right = new BufferedReader(new FileReader(textRight));

			for (int i = 0; i < lineLeft; ++i) {
				left.readLine();
			}

			StringBuffer leftContent = new StringBuffer();

			for (int i = 0; i < choice.width; ++i) {
				String line = left.readLine();
				if (line == null) {
					break;
				}
				leftContent.append(line + "\n");
			}

			right = new BufferedReader(new FileReader(textRight));

			for (int i = 0; i < lineRight; ++i) {
				right.readLine();
			}

			StringBuffer rightContent = new StringBuffer();

			for (int i = 0; i < choice.height; ++i) {
				String line = right.readLine();
				if (line == null) {
					break;
				}
				rightContent.append(line + "\n");
			}

			docLeft = new Document(new String(leftContent));
			docRight = new Document(new String(rightContent));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
		}

		this.merger.setText(docLeft, docRight);
	}
}
