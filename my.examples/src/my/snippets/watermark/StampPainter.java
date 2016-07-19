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

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * An extension of <code>WatermarkPainter</code> that
 * paints a translucent image in the bottom right
 * corner of the component.
 *
 * @version 1.2 10/25/2006
 * @author Shannon Hickey
 */
public class StampPainter extends WatermarkPainter {

    /** The image to paint in the foreground */
    private Image fgImage;
   
    public StampPainter() {
    	
    	File file = new File("images/duke_wave.png");
    	
        // fgImage = getImage(getClass().getResource("duke_wave.png"));
    	fgImage = getImage(file);
    	
       
    }
    
    public String[] getCommands() {
        return new String[] {"images/duke_wave.png",
                             "images/duke_hips.png"};
    }
    
    public void paint(Graphics g) {
        // if a foreground image exists, paint it
        if (fgImage != null) {
            int imageW = fgImage.getWidth(null);
            int imageH = fgImage.getHeight(null);
            
            // we need to cast to Graphics2D for this operation
            Graphics2D g2d = (Graphics2D)g;
            
            // create the composite to use for the translucency
            AlphaComposite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            
            // save the old composite
            Composite oldComp = g2d.getComposite();

            // set the translucent composite
            g2d.setComposite(comp);
            
            // calculate the x and y positions to paint at
            int xloc = getComponent().getWidth() - imageW - 5;
            int yloc = getComponent().getHeight() - imageH - 5;

            // paint the image using the new composite
            g2d.drawImage(fgImage, xloc, yloc, getComponent());

            // restore the original composite
            g2d.setComposite(oldComp);
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	File file = new File(ae.getActionCommand());
    	
        // fgImage = getImage(getClass().getResource(ae.getActionCommand()));
    	fgImage = getImage(file);
    	
        
        getComponent().repaint();
    }

}
