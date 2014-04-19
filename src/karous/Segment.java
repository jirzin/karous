/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karous;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author ubu
 */
public class Segment {
    
    private int id;
    
    private PVector position; 
    private PVector size;
    
    // referencni obrazek
    private PImage image;
    
    // vlastnosti pulzovani
    private float pulseValue;
    private int maxPulseValue;
    private int minPulseValue;
    private float pulseFreq;
    
    // maska a image
    private PGraphics mask;
    private PGraphics content;
        
    PApplet app;        
    
    Segment(PApplet app, PVector position, PVector size) {
    
        this.app = app;
        this.position = position;
        this.size = size;
        this.mask = app.createGraphics((int)size.x, (int)size.y);
        this.content = app.createGraphics((int)size.x, (int)size.y);
        this.pulseValue = 255;
    
    }
    Segment(PApplet app, float x, float y, float width, float height) {
    
        this.app = app;
        
        this.position = new PVector(x,y);
        this.size = new PVector(width,height);
        
        this.mask = app.createGraphics((int)size.x, (int)size.y);
        this.content = app.createGraphics((int)size.x, (int)size.y);
        this.pulseValue = 255;
    
    }
    
    /**
     * vykresluje debug info a debug grafiku
     */
    public void debugDraw(){
        
        app.noFill();
        app.stroke(255,0,0);        
        app.strokeWeight(4);
        app.rect(position.x, position.y, size.x, size.y);
        
    }
    
    /**
     * vykresluje obrazek segmentu do urceneho obdelniku na plose aplikace
     */    
    public void draw(){
        
        drawMask();
        drawContent(mask);
        
        app.image(content,position.x,position.y,size.x,size.y);
        app.fill(255,255,0);
        app.rect(position.x, position.y, size.x, size.y);
        
    }
    
    /**
     * vykresluje masku 
     * parametry masky ovlivnuji viditelnost obrazku atp
     */
    public void drawMask(){
        
        mask.beginDraw();        
        mask.background(0);        
        mask.fill(255,pulseValue);
        mask.noStroke();        
        mask.rect(0,0,size.x,size.y);        
        mask.endDraw();
        
    }
    
    private void drawContent(PGraphics maskRef){
    
        content.beginDraw();
        content.background(0);
        if(image!=null){
            content.image(image,position.x,position.y,size.x,size.y);        
        }else{
            content.fill(0,0,255);
            content.rect(position.x,position.y,size.x,size.y);        
        }
        content.mask(maskRef); // maskovani obrazku
        content.endDraw();
    
    }
    
    private void drawContent(){
    
        content.beginDraw();
        content.background(0);
        content.image(image,position.x,position.y,size.x,size.y);        
        content.endDraw();
        
    
    }
    
    /**
     * @param image 
     * predava obrazek ktery tento segment zobrazuje
     */
    public void setImage(PImage image){
        
        this.image = image;
    
    }
    
    public void requestImage(String s){
    
        this.image = app.requestImage(s);
        
    }
    public float getX(){
        return(this.position.x);
    }
    public float getY(){
        return(this.position.y);
    }
    public float getWidth(){
        return(this.size.x);
    }
    public float getHeight(){
        return(this.size.y);
    }
}
