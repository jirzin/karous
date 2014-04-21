/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karous;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import static processing.core.PApplet.max;
import static processing.core.PApplet.min;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.CLOSE;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author ubu
 */
public class SegmentShape {
    
    ArrayList<PVector> vertexes;
    PApplet app;
    boolean editting = true;
    private int id;
    private boolean active = false;
    private PVector buttonPosition;
    private PVector size;
    private PVector position;
    
    public SegmentShape(PApplet app){
        vertexes = new ArrayList<>();
        this.app = app;
    }
    public void addVertex(float x, float y) {
        if (editting) {
            vertexes.add(new PVector(x, y));
        }
    }

    public void draw() {
        if (vertexes.size() > 1) {
            if(editting){
            for (int i = 0; i < vertexes.size(); i++) {
                    PVector pos1 = (PVector) vertexes.get(i);
                    PVector pos2;
                    if(i!=vertexes.size()-1){
                    pos2 = (PVector) vertexes.get(i + 1);
                    }else{
                    pos2 = new PVector(app.mouseX,app.mouseY);
                    }
                    //PVector pos2 = new PVector(app.mouseX,app.mouseY);
                    app.stroke(255, 255, 0);
                    app.line(pos1.x, pos1.y, pos2.x, pos2.y);
                }
            }else{
                
                app.fill(0, 0, 150);
                app.stroke(255, 0, 0);
                app.strokeWeight(2);
                app.beginShape();
                for(PVector pv: vertexes){
                    app.vertex(pv.x, pv.y);
                }
                app.endShape(CLOSE);
                app.noStroke();
                app.fill(255);
                app.rectMode(CENTER);
                app.rect(buttonPosition.x, buttonPosition.y, 20, 20);
            }
        }
        if (vertexes.size() == 1) {
            if(editting){
                PVector pos1 = (PVector) vertexes.get(0);
                app.stroke(255, 255, 0);
                app.line(pos1.x, pos1.y, app.mouseX, app.mouseY);
            }
        }
    }
    
    public void drawReference(PApplet app, float offX, float offY, float scale){
        
        
        if(active){
        app.noStroke();
        app.fill(255,0,0);
        }else{
            app.noFill();
        app.stroke(255);
        }
        app.beginShape();
        for (PVector pv : vertexes) {
            app.vertex((pv.x/scale)+offX, (pv.y/scale)+offY);
        }
        app.endShape(CLOSE);
    }

    public void closeShape() {
        editting = false;
        buttonPosition = new PVector(app.mouseX,app.mouseY);
        calcSizeAndPosition();
    }    
    public int getId(){
        return(id);
    }
    public void setId(int i){
        id = i;
    }
    
    public void setActive(Boolean a){
        active = a;
    }
    public PVector getButtonPosition(){
        return(buttonPosition);
    }
    
    public PImage getShapeMask(){
        PGraphics m = app.createGraphics((int)size.x, (int)size.y);
        m.beginDraw();
        m.background(0);
        m.fill(255);
        m.beginShape();
        for (PVector pv : vertexes) {
            m.vertex(pv.x-position.x, pv.y-position.y);
        }
        m.endShape();
        m.endDraw();
        PImage mask = m.get();
        return(mask);
    }
    void calcSizeAndPosition(){
        int n = vertexes.size();
        float[]nx = new float[n];
        float[]ny = new float[n];
        int i = 0;
        for(PVector pv: vertexes){
            nx[i] = pv.x;
            ny[i] = pv.y;
            i++;
        }
        float minx = min(nx);
        float maxx = max(nx);
        float miny = min(ny);
        float maxy = max(ny);
        size = new PVector(maxx-minx,maxy-miny);
        //System.out.println("calculated size of mask is: "+size.x+" & "+size.y);
        position = new PVector(minx,miny);
    }
    
    public PVector getSize(){
        return(size);
    }
    public PVector getPosition(){
        return(position);
    }
}
