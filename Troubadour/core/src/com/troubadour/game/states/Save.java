package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {
    public static GameData gd;

    public static void save() {

        try {
            FileHandle file = Gdx.files.local("save.sav");
            ObjectOutputStream out = new ObjectOutputStream(file.write(false));
            out.writeObject(gd);
            out.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            if(!saveFileExists()) {
                init();
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
        File f = new File("save.sav");
        return f.exists();
    }

    public static void init() {
        gd = new GameData();
        gd.init();
        save();
    }

}
