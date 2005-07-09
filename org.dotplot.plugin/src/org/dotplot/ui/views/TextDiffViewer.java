/*
 * Created on 01.07.2004
 */
package org.dotplot.ui.views;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * <code>TextDiffViewer</code> subclasses TextMergeViewer to provide possiblity setting files to show.
 *
 * @author Sascha Hemminger
 */
public class TextDiffViewer extends TextMergeViewer
{
   /**
    * Constructs a TextDiffViewer object.
    *
    * @param parent        parent Composite maybe a window
    * @param configuration the diff's config
    */
   public TextDiffViewer(Composite parent, CompareConfiguration configuration)
   {
      super(parent, configuration);
   }

   /**
    * sets the two texts.
    *
    * @param leftText  left text
    * @param rightText right text
    */
   public void setText(Object leftText, Object rightText)
   {
      super.updateContent(null, leftText, rightText);
   }
}
