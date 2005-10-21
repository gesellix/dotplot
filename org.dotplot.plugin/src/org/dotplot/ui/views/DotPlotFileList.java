package org.dotplot.ui.views;

import org.dotplot.tokenizer.DefaultFileList;

/**
 * <code>DotPlotFileList</code> IFileList used with the plugin.
 *
 * @author Sascha Hemminger
 * @author Christian Gerhardt
 * @see org.dotplot.tokenizer.DefaultFileList
 */
public class DotPlotFileList extends DefaultFileList
{
   /**
    * for being Serializable
    */
   private static final long serialVersionUID = 5243269658205956283L;

   /**
    * <code>swapPositions</code> is a support for the drag&drop to change filepositions in a plot.
    *
    * @param file1 first file to swap
    * @param file2 second file to swap
    *
    * @see java.util.Vector
    */
   //TODO change to File again!
   public void swapPositions(String file1, String file2)
   {
      int index = super.indexOf(file2);
      super.setElementAt(file2, super.indexOf(file1));
      super.setElementAt(file1, index);
   }
}
