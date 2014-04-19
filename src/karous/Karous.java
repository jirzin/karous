/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karous;

import java.awt.Frame;
import java.io.File;
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
    
    private PVector appSize = new PVector(2000,400);
    //public float[] ps = new float[4];
   // ArrayList<float[]> positionsAndSizes;
    public PVector testSize = new PVector(100,100);
    
    public ArrayList<Segment> segments; 
    
    public String rootFolder = "data/";
    
    public ArrayList<String> dirNames;
    public ArrayList<DirectoryManager> dirManagers;            
    
    public Editor editor;                
    
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

       // dir1 = new DirectoryManager(this, "data/fotky");
        dirNames = new ArrayList();
        
        // get every folder in data/ dir
        File[] dirs = new File(rootFolder).listFiles();
        for(File file:dirs){
            if(file.isDirectory()){
                dirNames.add(rootFolder+file.getName());
            }
        }
        
        
        dirManagers = new ArrayList<>();
       // positionsAndSizes = new ArrayList<>();
        segments = new ArrayList<>();
                
        
        
        // testing position and value
//        float[] ps = new float[4];
//        
//        ps[0]=0;
//        ps[1]=200;
//        ps[2]=100;
//        ps[3]=100;
//        
//        float[] ps2 = new float[4];
//        
//        ps2[0]=200;
//        ps2[1]=0;
//        ps2[2]=400;
//        ps2[3]=100;
//        
//        positionsAndSizes.add(ps);
//        positionsAndSizes.add(ps2);
        
        // for every position and size generate graphic segment
        //for(float[] f:positionsAndSizes){
        //    System.out.println(f);
            segments.add(new Segment(this,100,100,100,100));
            segments.add(new Segment(this,0,150,100,100));
            segments.add(new Segment(this,200,50,60,60));
            segments.add(new Segment(this,280,90,20,400));
            //System.out.println(segments.size());
        //}                
        
        // create managers for evrery direcotry entry in data folder
        int i = 0;
        for(String s: dirNames){
            dirManagers.add(new DirectoryManager(this, s));
            i++;
        }
                
        //creates GUI editor
        editor = createEditor("editor", 1024,600,dirNames,dirManagers,segments);
   
    }
    
    @Override
    public void draw() {
        this.background(0);      
        for(Segment seg:segments){
            seg.draw();
        }
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
    
    
    private Editor createEditor(String name, int w, int h, ArrayList<String> dirNames,ArrayList<DirectoryManager> dirManagers,ArrayList<Segment> segments){
        Frame f = new Frame(name);
        Editor e = new Editor(this,w,h,dirNames,dirManagers,segments);
        f.add(e);
        e.init();
        f.setTitle(name);
        f.setSize(w, h);
        f.setLocation(0, 0);
        f.setResizable(false);
        f.setVisible(true);
        f.removeNotify();
        f.setUndecorated(true);
        f.addNotify();
        return e;
    }
    
    @Override
    public void mousePressed(){
       // dir1.printOutFiles();
    }//
}
