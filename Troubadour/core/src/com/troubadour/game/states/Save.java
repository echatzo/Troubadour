package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {
    private static Preferences hScores ;

    public static void save(){
        if(hScores==null) {
            init();
        }
        hScores.flush();
        return;
    }

    public static boolean saveFileExists() {
        return hScores.equals(null);
    }

    public static void init() {
        hScores = Gdx.app.getPreferences("Save");
        hScores.putInteger("1",0);
        hScores.putInteger("2",0);
        hScores.putInteger("3",0);
        hScores.putInteger("4",0);
    }

    public static int getHighScores(int i) {
        if(hScores==null) {
            init();
            return getHighScores(i);
        }
        return hScores.getInteger(Integer.toString(i));
    }

    public static void addHighScore(int newScore, int world) {
        if(hScores==null) {
            init();
        }
        if(newScore > getHighScores(world)) {
            hScores.putInteger(Integer.toString(world),newScore);
        }
    }

    public static void resetScores(){
        if(hScores==null) {
            init();
            return;
        }
        hScores.putInteger("1",0);
        hScores.putInteger("2",0);
        hScores.putInteger("3",0);
        hScores.putInteger("4",0);
        return;
    }

    /*public static void load(){
        if(!saveFileExists()) {
            init();
            return;
        }
    }*/

    /*
    public static GameData gd;

    public static void save() {

        try {
            FileHandle file = Gdx.files.local("save.sav");
            System.out.println(file+" := "+file.exists()+"   "+file.path()+"   "+file.pathWithoutExtension());
            ObjectOutputStream out = new ObjectOutputStream(file.write(false));
            out.writeObject(gd) ;
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            //System.out.println("1: "+Save.gd.getHighScores(1)+" "+Save.gd.getHighScores(2)+" "+Save.gd.getHighScores(3)+" "+Save.gd.getHighScores(4));
            if(!saveFileExists()) {
                //System.out.println("2: "+Save.gd.getHighScores(1)+" "+Save.gd.getHighScores(2)+" "+Save.gd.getHighScores(3)+" "+Save.gd.getHighScores(4));
                init();
                System.out.println(saveFileExists());
                //System.out.println("1: "+Save.gd.getHighScores(1)+" "+Save.gd.getHighScores(2)+" "+Save.gd.getHighScores(3)+" "+Save.gd.getHighScores(4));
                return;
            }
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("save.sav")
            );
            gd = (GameData) in.readObject();
            in.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        //File f = new File("save.sav");
        File f = new File("./Android/data/com.troubadour.game/android/assets/save.sav");
        System.out.println(f+" = "+f.exists()+"   "+f.getAbsolutePath()+"   "+f.getPath());
        return f.exists();
    }

    public static void init() {
        gd = new GameData();
        gd.init();
        save();
    }
*/
}
