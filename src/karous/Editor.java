/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karous;

import controlP5.Accordion;
import controlP5.Button;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.ListBox;
import controlP5.ListBoxItem;
import controlP5.Slider;
import controlP5.Toggle;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author ubu
 */
public class Editor extends PApplet {

//    private final AtomicBoolean wasRunBefore = new AtomicBoolean(false);
    boolean isReady = false;

    int w;
    int h;
    int col;

    PApplet app;
    ControlP5 cp5;


    ArrayList<String> dirNames;
    ArrayList<DirectoryManager> dirManagers;
    ArrayList<Segment> segments;
    ArrayList<SegmentShape> segmentShapes;

    DirectoryManager currDir;
    String currDirPath;
    SegmentShape currSegShape;

    ListBox dirList;
    ListBox imageList;

    Slider freq;
    Slider min;
    Slider max;
    //Slider empty;

    Segment segment;

    String currDirName;
    private float scale;
    
    int selectedButton;
    
    Group layout;
    PVector layoutPos;
    

    private Editor() {
        //this.scale = 3;
    }

    public Editor(PApplet app, int width, int height, ArrayList<String> dirNames, ArrayList<DirectoryManager> dirManagers, ArrayList<Segment> segments, ArrayList<SegmentShape> segmentShapes) {
        this.scale = 3;

        this.app = app;
        this.w = width;
        this.h = height;

        this.dirNames = dirNames;
        this.dirManagers = dirManagers;
        this.segments = segments;
        this.segmentShapes = segmentShapes;
        layoutPos = new PVector(170,30);
       // lbl = new LayoutButtonListener();

    }

    @Override
    public void setup() {
        this.size(w, h);
        cp5 = new ControlP5(this);

        Group parameters = cp5.addGroup("params")
                .setBarHeight(20)
//                .setBackgroundHeight(50)
                .setBackgroundColor(color(50))
                .setColorBackground(color(100))
                .setColorForeground(color(150)) //.setColorBackground(color(100,0,0))
                //.setColorForeground(color(200,0,0));       
                ;

        freq = cp5.addSlider("freq")
                .setRange(0, 255)
                .setPosition(10, 10)
                .setGroup(parameters)
                .setColorBackground(color(100))
                .setColorForeground(color(150))
                .setColorActive(color(200, 0, 0))
                .setColorForeground(color(100, 0, 0))
                ;

        min = cp5.addSlider("min")
                .setRange(0, 255)
                .setPosition(10, 20)
                .setGroup(parameters)
                .setColorBackground(color(100))                
                .setColorForeground(color(100, 0, 0))
                .setColorActive(color(200, 0, 0))
                ;
                

        max = cp5.addSlider("max")
                .setRange(0, 255)
                .setPosition(10, 30)
                .setGroup(parameters)
                .setColorBackground(color(100))
                .setColorForeground(color(100, 0, 0))
                .setColorActive(color(200, 0, 0))
                ;

        Group direcotry = cp5.addGroup("directories")
                .setBarHeight(20)
                .setBackgroundColor(color(50))
                
                .setColorBackground(color(100))
                .setColorForeground(color(150)) // .setColorBackground(color(100,0,0))
                // .setColorForeground(color(200,0,0));                
                ;

        dirList = cp5.addListBox("dir")
                .setPosition(10, 20)
                .setSize(130, 70)
                .setGroup(direcotry)
                .setColorActive(color(200, 0, 0))
                .setColorBackground(color(100))
                .setColorForeground(color(150))
//                .setColorForeground(color(100, 0, 0))
                ;

       //dirList.captionLabel().toUpperCase(false);
        int i = 0;
        for (String s : dirNames) {
            ListBoxItem lbi = dirList.addItem(s, i);
            //lbi.toUpperCase(false);
            i++;
        }

        Group image = cp5.addGroup("images")
                .setBarHeight(20)
                .setBackgroundColor(color(50))
                .setColorBackground(color(100))
                .setColorForeground(color(150)) //.setColorBackground(color(100,0,0))
                //.setColorForeground(color(200,0,0));  
                ;

        imageList = cp5.addListBox("image")
                .setPosition(10, 20)
                .setSize(130, 100)
                .setGroup(image)
                .setColorBackground(color(100))
                .setColorForeground(color(150))   
                ;

        //imageList.captionLabel().toUpperCase(false);
        Accordion control = cp5.addAccordion("control")
                .setPosition(10, 10)
                .setWidth(150)
                .addItem(direcotry)
                .addItem(image)
                .addItem(parameters)                
                .setItemHeight(130)
                ;
        
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+control.getItemHeight());
        //control.getI
        
        
        control.open(0, 1, 2);

        control.setCollapseMode(Accordion.MULTI);
        
//        Group layout = cp5.addGroup("layout")
//                .setPosition(200, 30)
//                .setWidth((int)(((float)app.width)/scale))    
//                .activateEvent(true)                
//                .setColorBackground(color(100))
//                .setColorForeground(color(150))                   
//                .setBackgroundHeight((int)(((float)app.width)/scale))                                                
//                .setBarHeight(20)
//                ;
        
        layout = cp5.addGroup("layout")
                .setBarHeight(20)
                .setPosition((int)layoutPos.x,(int)layoutPos.y)
                .setWidth((int)(app.width/scale))
                .activateEvent(true)
                .setBackgroundColor(color(255,10))
                .setColorBackground(color(100))
                .setColorForeground(color(150))
                .setBackgroundHeight((int)(app.height/scale))
                .setLabel("layout")
                ;
        
//        int id = 0;
//        
//        for(Segment s:segments){
//            String idString = "b-"+id;
//            Button b = cp5.addButton(idString)
//                    .setPosition((int)(s.getX()/scale), (int)(s.getY()/scale))
//                    .setSize((int)(s.getWidth()/scale)-1, (int)(s.getHeight()/scale)-1)
//                    .setColorBackground(color(100))
//                    .setColorForeground(color(150))
//                    .setColorActive(color(200,0,0))                    
//                    .setGroup(layout)
//                    .setId(id)
//                    .setValue(id)
//                    ;
//            id++;
//        }

        
        
        isReady = true;

    }

    @Override
    public void draw() {

        background(20);
        if(segmentShapes.size()>0){
            drawSegmentShapes();
        }
       // System.out.println(freq.getValue());

    }

    public ControlP5 getControl() {
        return cp5;
    }

    private void clearImageList(){
    int oldListLength = imageList.getListBoxItems().length - 1;

        // remove previous list
        for (int i = oldListLength; i >= 0; i--) {
            ListBoxItem lbi = imageList.getItem(i);
            imageList.removeItem(lbi.getName());
        }
    }
    
    private void createImageList(ArrayList<String> names) {
        
        clearImageList();

        // generaates new list from atributte list
        int x = 0;
        for (String s : names) {
            String name = names.get(x);
            imageList.addItem(name, x);
            x++;
        }
    }

    private void colorLineOfUsedImage(int x) {

        ArrayList<String> usedNames = currDir.getUsedNames();
        ArrayList<String> names = currDir.getNames();

        for (int i = 0; i < imageList.getListBoxItems().length; i++) {

            // get list item and its corresponding name
            ListBoxItem lbi = imageList.getItem(i);
            String name = names.get(i);

            //if name is in usedNames color line to dark red
            int occurance = 0;
            for (String s : usedNames) {
                if (s.equals(name)) {
                    occurance++;
                }
            }
            if (occurance > 0) {
                lbi.setColorBackground(color(100, 0, 0));
                lbi.setColorForeground(color(200, 0, 0));
            }
            if(x==i){
                lbi.setColorBackground(color(200, 0, 0));
                lbi.setColorForeground(color(200, 0, 0));
            }

        }

    }

    private void colorLineOfSelectedDir(int x) {

        for (int i = 0; i < dirList.getListBoxItems().length; i++) {

            ListBoxItem lbi = dirList.getItem(i);
            if (i == x) {
                lbi.setColorBackground(color(200, 0, 0));
                lbi.setColorForeground(color(200, 0, 0));
            } else {
                lbi.setColorBackground(color(100));
                lbi.setColorForeground(color(150));
            }
        }
    }
    
    private void colorCurrentButton(String sx){
        
        for(int i = 0; i<segments.size();i++){
            String si = i+"";
            Button b = (Button) cp5.getController("b-"+i);
            if(si.equals(sx)){
                b.setColorBackground(color(200,0,0));
                b.setColorForeground(color(200,0,0));       
            }else{
                b.setColorBackground(color(100));
                b.setColorForeground(color(150));
            }
            
        }
            
    }

    public void controlEvent(ControlEvent e) {
        if (isReady) {

            System.out.println(e);
            
            if ("dir".equals(e.getName())) {
                //System.out.println("dir value :" + e.getValue());
                currDir = dirManagers.get((int) e.getValue());
                currDirName = dirNames.get((int) e.getValue());
                colorLineOfSelectedDir((int) e.getValue());
                createImageList(currDir.getNames());
                colorLineOfUsedImage(-1);
                if(segment!=null){
                    //System.out.println("shoul set the:"+currDir+" as manager to segment");
                    segment.setManager(currDir);
                }
            }
            
            if ("image".equals(e.getName())) {                                               
                colorLineOfUsedImage(-1);
                ListBoxItem lbi = imageList.getItem((int) e.getValue());
                String name = currDir.getNameAt((int) e.getValue());
                int occurance = 0;
                for (String s : currDir.getUsedNames()) {
                    if (s.equals(name)) {
                        occurance++;
                    }
                }
                if (occurance == 0) {
                    currDir.addNameToUsed(name);
                   // System.out.println("adding " + name + " to used names");                    
                }
                lbi.setColorBackground(color(200, 0, 0));
                lbi.setColorForeground(color(200, 0, 0));
                
                if(segment!=null){
                    segment.setImageId((int) e.getValue());
                    segment.requestImage(currDir.getDirPath()+name);
                }
            }
            if ("min".equals(e.getName())) {
//                if (max.getValue() <= e.getValue()) {
//                    max.setValue(e.getValue());
//                }
                if(segment!=null){
                    segment.setMin(e.getValue());
                    //segment.setMax(max.getValue());
                }
            }            
            if ("max".equals(e.getName())) {
//                if (min.getValue() >= e.getValue()) {
//                    min.setValue(e.getValue());
//                }
                if(segment!=null){
                    segment.setMax(e.getValue());
                    //segment.setMin(min.getValue());
                }
            }     
            if ("freq".equals(e.getName())) {
                
                if(segment!=null){
                    segment.setFreq(e.getValue());
                    //segment.setMin(min.getValue());
                }
            }     
            
            if("b-".equals(e.getName().substring(0,2))){
                String n = e.getName().substring(2);
                int x = (int) e.getValue();
                //System.out.println(x);
                //extractInt(n);
                //System.out.println("got layout button message from : "+n);
                Button b = (Button) cp5.getController(e.getName());
                colorCurrentButton(n);                
                segment = segments.get(x);
                freq.setValue(segment.getFreq());
                min.setValue(segment.getMin());
                max.setValue(segment.getMax());
               // System.out.println(segment.getManager());
                if(segment.getManager()!=null){
                    System.out.println("segment have manager with id: "+segment.getManager().getId());
                    colorLineOfSelectedDir(segment.getManager().getId());                    
                    currDir = segment.getManager();
                    createImageList(currDir.getNames());
                    colorLineOfUsedImage(segment.getImageId());
                }else{
                    currDir = null;
                    colorLineOfSelectedDir(-1);
                    clearImageList();
                }
                currSegShape = segmentShapes.get(x);
            }
        
        }
    }    
    private void drawSegmentShapes(){
//        this.fill(255);
//        this.rect(30,30,400,400);
        for(SegmentShape ss: segmentShapes){
            ss.drawReference(this,layoutPos.x,layoutPos.y,scale);
            ss.setActive(ss==currSegShape);
        }
    }
    
    public void addSegmentButton(SegmentShape s){
        System.out.println("will add button with id: "+s.getId());
        cp5.addButton("b-"+s.getId())
                .setPosition((int)(s.getButtonPosition().x/scale),(int)(s.getButtonPosition().y/scale))
                .setValue(s.getId())
                .setGroup(layout)
                .setSize(20, 20)
                ;
    
    }
    
//    private void extractInt(String s){
//        //char [] x = new char[s.length()];
//        
//        int lminus = -s.length();
//        int lplus = s.length()-1;
//        
//        for(int i = 0; i > lminus; i--){
//            
//            char x = s.charAt(lplus-i);
//            System.out.println(x);
//        }
//
//    }
   
}
