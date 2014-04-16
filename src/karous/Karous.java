/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karous;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import processing.core.PApplet;
import static processing.core.PConstants.P2D;
import processing.core.PVector;

/**
 *
 * @author ubu & 3dsense
 */
public class Karous extends PApplet {
    
    private final AtomicBoolean wasRunBefore = new AtomicBoolean(false);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<String> a = new ArrayList<>();
        // a.add(PApplet.ARGS_HIDE_STOP); //hide stop button
        a.add(PApplet.ARGS_LOCATION + "=0,0"); //do not center the location of sketch
        //a.add(PApplet.ARGS_FULL_SCREEN); //go into fullscreen (works for 1 monitor only)
        // a.add(PApplet.ARGS_DISPLAY + "=" + 0); //use display number X (0, 1, 2, 3)
        a.add(Karous.class.getCanonicalName());
        PApplet.main(a.toArray(new String[0]));
    }
    
    private PVector appSize = new PVector(1000,400);
    
    public Karous() {
        
    }
    
    @Override
    public void setup() {
        intializeOnce();
    }
    
    @SuppressWarnings("empty-statement")
    private void intializeOnce() {
        
        System.out.println("setup start");
        //if intializeOnce was already run, simply return
        if (wasRunBefore.getAndSet(true)) {
        }
        
    }
    
    @Override
    public void draw() {
    
    }
    
    @Override
    public int sketchWidth() {
        return (int) appSize.x;
    }

    @Override
    public int sketchHeight() {
        return (int) appSize.y;
    }

    @Override
    public String sketchRenderer() {
        return P2D;
    }

    @Override
    public void init() {
        frame.removeNotify();
        frame.setUndecorated(true);
        frame.addNotify();
        super.init(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
