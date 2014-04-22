/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karous;

import processing.core.PApplet;
import static processing.core.PApplet.map;
import static processing.core.PApplet.sin;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.P2D;
import static processing.core.PConstants.PI;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author ubu
 */
public class Segment {

    private int id;
    private int imageId = -1;

    private PVector position;
    private PVector size;
    private PVector imageOffset;

    // referencni obrazek
    private PImage image;
    private String imagePath;
    //private String dirPath;
    DirectoryManager dm;

    // vlastnosti pulzovani
    private float pulse;
    private float max;
    private float min;
    private float freq;

    // maska a image
    private PImage mask;
    private PGraphics content;

    PApplet app;
    float scalingFactor = 1;
    
    private float phase;
    private float imageRotation = 0;
    private float imageScale = 1;

//    Segment(PApplet app, PVector position, PVector size) {
//
//        this.app = app;
//        this.position = position;
//        this.size = size;
//        this.mask = app.createGraphics((int) size.x, (int) size.y);
//        this.content = app.createGraphics((int) size.x, (int) size.y);
//        this.pulse = 0;
//        this.phase = 0;
//
//    }
    
    Segment(PApplet app, PImage pi) {

        this.app = app;
        this.position = new PVector(0,0);
        this.size = new PVector(app.width,app.height);
        this.mask = pi;
        this.content = app.createGraphics((int) size.x, (int) size.y);
        this.pulse = 0;
        this.phase = 0;
        this.imageOffset = new PVector(0,0);

    }
    Segment(PApplet app, PImage pi, float scalingFactor) {

        this.app = app;
        this.position = new PVector(0,0);
        this.scalingFactor = scalingFactor;
        this.size = new PVector(app.width/scalingFactor,app.height/scalingFactor);
        this.mask = pi;
        this.content = app.createGraphics((int) this.size.x, (int) this.size.y);
        this.pulse = 0;
        this.phase = 0;
        

    }
    Segment(PApplet app, PImage pi, PVector position, PVector size, int id) {

        this.app = app;
        this.position = position;
        //this.scalingFactor = scalingFactor;
        this.size = size;
        this.mask = pi;
        this.content = app.createGraphics((int) this.size.x, (int) this.size.y);
        this.pulse = 0;
        this.phase = 0;
        this.id = id;
        this.imageOffset = new PVector(0,0);
    }

//    Segment(PApplet app, float x, float y, float width, float height) {
//
//        this.app = app;
//
//        this.position = new PVector(x, y);
//        this.size = new PVector(width, height);
//
//        this.mask = app.createGraphics((int) size.x, (int) size.y, P2D);
//        this.content = app.createGraphics((int) size.x, (int) size.y, P2D);
//        this.pulse = 0;
//        this.phase = 0;
//
//    }

    /**
     * vykresluje debug info a debug grafiku
     */
    public void debugDraw() {

        app.noFill();
        app.stroke(255, 0, 0);
        app.strokeWeight(1);
        app.rect(position.x, position.y, size.x, size.y);
        app.fill(0, 255, 255);
        app.text("freq: " + freq, position.x + 20, position.y + 20);
        app.text("min: " + min, position.x + 20, position.y + 40);
        app.text("max: " + max, position.x + 20, position.y + 60);

    }

    /**
     * vykresluje obrazek segmentu do urceneho obdelniku na plose aplikace
     */
    public void draw() {

        //drawMask();
        drawContent();
        //content.mask(mask);        
        if (image != null) {
            if (image.width > 0) {
                PImage c = content.get();
                c.mask(mask);
                app.tint(255,pulse);
                app.image(c, position.x, position.y);
            }
        }
        //app.fill(255,255,0);
        //app.rect(position.x, position.y, size.x, size.y);

    }

    /**
     * vykresluje masku parametry masky ovlivnuji viditelnost obrazku atp
     */
//    public void drawMask() {
//
//        mask.beginDraw();
//        mask.background(0);
//        mask.fill(255, pulse);
//        mask.noStroke();
//        mask.rect(0, 0, size.x, size.y);
//        mask.endDraw();
//
//    }

    private void drawContent(PGraphics maskRef) {

        content.beginDraw();
        content.background(0);
        if (image != null) {
            //System.out.println(image);
            if(image.width>0){
                content.image(image, imageOffset.x, imageOffset.y, image.width, image.height);
            }
        } else {
            //System.out.println("image is null");
        }
        content.endDraw();

    }
    private void drawContent() {

        content.beginDraw();
        content.background(0);
        if (image != null) {
            //System.out.println(image);
            //image.mask(maskRef.get());
            if(image.width>0){
                content.pushMatrix();     
                content.translate(imageOffset.x+size.x/2, imageOffset.y+size.y/2);                                           
                content.rotate(imageRotation);
                content.imageMode(CENTER);
                //content.image(image, imageOffset.x+size.x/2, imageOffset.y+size.y/2, image.width*imageScale, image.height*imageScale);
                content.image(image, 0, 0, image.width*imageScale, image.height*imageScale);
                content.popMatrix();
                
            }
        } else {
            //System.out.println("image is null");
        }
        content.endDraw();

    }
    
    public void pulse(){
        phase+=freq/255;
        pulse = map( sin( phase ), -1, 1, min, max);
    }

//    private void drawContent() {
//
//        content.beginDraw();
//        content.background(0);
//        content.image(image, position.x, position.y, size.x/2, size.y);
//        content.endDraw();
//
//    }

    /**
     * @param image 
     * predava obrazek ktery tento segment zobrazuje
     */
    public void setImage(PImage image) {

        this.image = image;

    }

    public void requestImage(String s) {

        System.out.println("requesting this image file: " + s);
        this.image = app.requestImage(s);

    }

    public float getX() {
        return (this.position.x);
    }

    public float getY() {
        return (this.position.y);
    }

    public float getWidth() {
        return (this.size.x);
    }

    public float getHeight() {
        return (this.size.y);
    }

    public float getPulseValue() {
        return (pulse);
    }

    float getFreq() {
        return (freq);
    }

    float getMin() {
        return (min);
    }

    float getMax() {
        return (max);
    }

    void setMin(float value) {
        min = value;
    }

    void setMax(float value) {
        max = value;
    }

    void setFreq(float value) {
        freq = value;
    }

    void setManager(DirectoryManager dm) {
        this.dm = dm;
    }

    void setDirecotryManager(DirectoryManager currDir) {
        this.dm = dm;
    }

    public DirectoryManager getManager() {
        return (dm);
    }

    public void setImageId(int i) {
        this.imageId = i;
    }

    public int getImageId() {
        return (imageId);
    }
    
    public int getId(){
        return(id);
    }
    public void setId(int i){
        id = i;
    }
    public float getImageWidth(){
        float x;
        if (image != null) {
            if (image.width > 0) {
                x = image.width*imageScale;
            }else{
            x = 0;
            }
        } else {
            x = 0;
        }
        return(x);
    }
    public float getImageHeight(){
        float x;
        if (image != null) {
            if (image.height > 0) {
                x = image.height*imageScale;
            }
            else {
                x = 0;
            }
        }else{
            x = 0;
        }
        return(x);
    }

    public void setImageOffsetX(float value) {
        //System.out.println("setting image offx to: "+value);
        imageOffset.x = value;
    }
    
     public void setImageOffsetY(float value) {
       //  System.out.println("setting image offy to: "+value);
        imageOffset.y = value;
    }

    float getImageX() {
        return(imageOffset.x+position.x+size.x/2);
    }

    float getImageY() {
        return(imageOffset.y+position.y+size.y/2);
    }

    void setImageRotation(float f) {
        this.imageRotation = f;
    }
    void setImageScale(float f) {
        this.imageScale = f;
    }
    public float getBoundsX(){
        float bx = (image.width*imageScale)-size.x;
        return(bx);
    }
    public float getBoundsY(){
        float by = (image.height*imageScale)-size.y;
        return(by);
    }

    float getRotation() {
        return(imageRotation);
    }
}
