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

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Timer;

/**
 * An extension of <code>WatermarkPainter</code> that
 * animates a waving Duke in the bottom right corner
 * of the component.
 *
 * @version 1.2 10/25/2006
 * @author Shannon Hickey
 */
public class AnimPainter extends WatermarkPainter {
    
    /** A single image containing frames of Duke */
	static File file = new File("images/duke_anim.gif");
    //private static final Image animImage = getImage(AnimPainter.class.getResource("duke_anim.gif"));
	private static final Image animImage = getImage(file);
    
    /** The number of frames in the image */
    private static final int numFrames = 10;
    
    /** The width of each frame */
    private static final int animW = animImage.getWidth(null) / numFrames;
    
    /** The height of each frame */
    private static final int animH = animImage.getHeight(null);
    
    /** The amount of time to wait between each frame */
    private static final int animDelay = 100;

    /** The current frame number */
    private int currentFrame = 0;

    /**
     * Listens for events on the timer. Increments the frame
     * number and repaints the component.
     */
    private ActionListener timerListener = new ActionListener() {
	public void actionPerformed(ActionEvent ae) {
	    currentFrame = (currentFrame + 1) % numFrames;

	    int rpX = getComponent().getWidth() - animW - 5;
	    int rpY = getComponent().getHeight() - animH - 5;
	    getComponent().paintImmediately(rpX, rpY, animW, animH);
	}
    };
    
    /** The timer that controls the animation */
    private Timer timer = new Timer(animDelay, timerListener);

    public void paint(Graphics g) {
	// if the animation image exists, paint a frame of animation
	if (animImage != null) {
	    // calculate x and y positions to paint at
	    int xloc = getComponent().getWidth() - animW - 5;
	    int yloc = getComponent().getHeight() - animH - 5;

            // calculate the x position in the image where
            // the current frame starts
	    int imageX = currentFrame * animW;

            // paint the current frame by specifying the target
            // co-ordinates to paint at and the source co-ordinates
            // representing the portion of the image to paint
            // (ie. the co-ordinates representing the current frame)
            g.drawImage(animImage,
                        xloc, yloc, xloc + animW, yloc + animH,
                        imageX, 0, imageX + animW, animH,
                        getComponent());
	}
    }

    public void start() {
	if (animImage != null) {
	    timer.start();
	}
    }
    
    public void stop() {
	if (animImage != null) {
	    timer.stop();
	}
    }

}
