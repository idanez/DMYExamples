package my.snippets.watermark;

/*
 * Copyright 2006 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

/**
 * An extension of <code>WatermarkPainter</code> that
 * paints the entire component with a gradient.
 *
 * @version 1.2 10/25/2006
 * @author Shannon Hickey
 */
public class GradientPainter extends WatermarkPainter {
    
    /** The first color to use in the gradient */
    private static final Color color1 = Color.WHITE;
    
    /** The second color to use in the gradient */
    private static final Color color2 = new Color(128, 128, 255);

    public GradientPainter() {}
    
    public void paint(Graphics g) {
        int width = getComponent().getWidth();
        int height = getComponent().getHeight();

        // Create the gradient paint        
        GradientPaint paint = new GradientPaint(0, 0, color1, width, height, color2, true);
        
        // we need to cast to Graphics2D for this operation
        Graphics2D g2d = (Graphics2D)g;
        
        // save the old paint
        Paint oldPaint = g2d.getPaint();
        
        // set the paint to use for this operation
        g2d.setPaint(paint);
        
        // fill the background using the paint
        g2d.fillRect(0, 0, width, height);
        
        // restore the original paint
        g2d.setPaint(oldPaint);
    }

}
