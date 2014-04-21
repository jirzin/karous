/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package karous;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

/**
 *
 * @author ubu
 */
public class DirectoryManager {
    
    //private PImage[] images;
    private ArrayList<String> names;
    private ArrayList<String> usedNames;
    
    private int imageQuantity;
    
    String directoryPath;
    PApplet app;
    
    int id;
    
    DirectoryManager(PApplet app){        
        this.app = app;      
        names = new ArrayList<>();                
    }
    
    DirectoryManager(PApplet app, String directoryPath, int id){        
        this.app = app;
        this.directoryPath = directoryPath;        
        names = new ArrayList<>();   
        usedNames = new ArrayList<>();
        
        loadNames(this.directoryPath);     
        this.id = id;
    }
    
    private void loadNames(String directoryPath){           
        if(directoryPath==null){
            this.directoryPath = directoryPath;
        }        
        File[] files = new File(directoryPath).listFiles();        
        //images = new PImage[files.length];   
        int i = 0;
        for (File file : files) {
            if (file.isFile()) {                
               // System.out.println(file.getName());
                names.add(file.getName());
                
                i++;
            }
        }           
        imageQuantity = i;
    }
    
    public int getQunatity(){
        return (int) imageQuantity;
    }
    
//    public PImage getImageAt(int i){
//        return (PImage) images[i];
//    }        
    
    public String getNameAt(int i){
        return (String) names.get(i);
    }
    
    public String getDirPath(){
        return(directoryPath);
    }
    
    public void printOutFiles(){
        for (String s : names) {
            System.out.print(s);
        }
    }
    
    public ArrayList getNames(){
        return(names);
    }
    
    public void addNameToUsed(String name){
        usedNames.add(name);
    }
    
    public void printUsedNames() {
            System.out.println(usedNames);
    }
    
    public ArrayList<String> getUsedNames(){
        return(usedNames);
    }
    public int getId(){
        return(id);
    }
//    public PImage getFirstLoadedImage(){
//        PImage ii = new PImage();
//        for (PImage img : images) {
//            if (img != null) {
//                if (img.width > 0) {
//                    ii = img;
//                }
//            }
//        }
//        return (PImage) ii;
//    }
}
