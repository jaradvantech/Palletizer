package com.example.administrator.Palletizer;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BoxDesignManager {

    static ArrayList<BoxDesign> getFromPreferences(Context mainApplicationContext) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = mainApplicationContext.getSharedPreferences("BOXTYPES", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("BOXTYPES", "");
        Type type = new TypeToken<List<BoxDesign>>() {}.getType();

        //Get saved boxes
        ArrayList<BoxDesign> boxDesigns = gson.fromJson(jsonPreferences, type);
        if(boxDesigns == null) boxDesigns = new ArrayList<BoxDesign>();

        //Append default designs
        boxDesigns.add(new BoxDesign(0, "LOVALIVE", 40,40, R.mipmap.box_lovalive));
        boxDesigns.add(new BoxDesign(0, "LOVALIVE side", 6, 80, R.mipmap.box_side));

        return boxDesigns;

    }

    /*
     *  Check if there is another object with the same name, false; does not exist
     */
    static boolean alreadyExists(String nameToCheck, Context mainApplicationContext) {
        boolean isContained = false;
        ArrayList<BoxDesign> boxDesigns = getFromPreferences(mainApplicationContext);

        for (int i = 0; i < boxDesigns.size(); i++) {
            if (boxDesigns.get(i).name.equals(nameToCheck))
                isContained = true;
        }

        return isContained;
    }

    static void saveDesign(BoxDesign boxToSave, Context mainApplicationContext) {
        ArrayList<BoxDesign> boxDesigns = getFromPreferences(mainApplicationContext);

        //Append design to array
        boxDesigns.add(boxToSave);

        //Save Array
        saveToPreferences(boxDesigns, mainApplicationContext);
    }

    static void saveToPreferences(ArrayList<BoxDesign> boxDesigns, Context mainApplicationContext) {
        //Do not save default designs to avoid duplication
        for (int i = 0; i < boxDesigns.size(); i++) {
            if (boxDesigns.get(i).type == 0)
                boxDesigns.remove(i);
        }

        Gson gson = new Gson();
        SharedPreferences sharedPref = mainApplicationContext.getSharedPreferences("BOXTYPES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Serialize objects to a String that can be saved by using the Google Gson library.
        String serializedObjects = gson.toJson(boxDesigns);
        editor.putString("BOXTYPES", serializedObjects);
        editor.commit();
    }
}
