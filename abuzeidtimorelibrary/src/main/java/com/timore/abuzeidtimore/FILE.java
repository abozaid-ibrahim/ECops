package com.timore.abuzeidtimore;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;

public class FILE {

    public static FilenameFilter filesFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith("png")|name.endsWith("PNG");
        }
    };

/*
    public static ArrayList<Model> getResources(Context context, String fname) {
        ArrayList<Model> images = new ArrayList<Model>();
        File[] files = getPath(context, fname).listFiles(filesFilter);
        for (File inFile : files) {
            try {
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(inFile));
                images.add(new Model(b));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Please sure to put the app files in gemma folder", Toast.LENGTH_LONG).show();
            }

        }

        return images;
    }*/

    public static File getResFirstPath(Context context, String fname) {
        return (getPath(context, fname).listFiles(filesFilter))[0];
    }


    public static File[] getResourcesPath(Context context, String fname) {
        File[] files = getPath(context, fname).listFiles(filesFilter);

        return files;
    }


    public static File getPath(Context context, String fname) {
        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + fname);
        dir.mkdirs();
        return dir;
    }


}