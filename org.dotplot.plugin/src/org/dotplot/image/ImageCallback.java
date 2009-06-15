/*
 * Created on 01.12.2004
 */
package org.dotplot.image;

import java.awt.Dimension;

/**
 * <code>ImageCallback</code> links the image calculation engine(s) to the
 * target image. This way it is possible to exchange the format (class type) of
 * the target without changing the underlying algorithm.
 * 
 * @author Tobias Gesellchen
 */
interface ImageCallback {
    /**
     * Link to the image to be filled.
     * 
     * @param x
     *            X-coordinate in the target image
     * @param y
     *            Y-coordinate in the target image
     */
    int getPixel(int x, int y);

    Dimension getSize();

    void invertLine(int index);

    /**
     * Link to the image to be filled.
     * 
     * @param x
     *            X-coordinate in the target image
     * @param y
     *            Y-coordinate in the target image
     * @param rgb
     *            Pixel value (Color)
     */
    void setPixel(int x, int y, int rgb);

    /**
     * Update the progress bar (if existing) with given values.
     * 
     * @param diff
     *            Progress (in percent) since last update
     * @param curStep
     *            Step index, with one of org.dotplot.IDotplot.STEPS
     * @param msg
     *            Log message, can be null
     * 
     * @see org.dotplot.core.IDotplot#STEPS
     */
    void updateProgress(int diff, int curStep, String msg);
}
