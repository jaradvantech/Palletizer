package com.example.administrator.Palletizer;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PalletDesignManager {

    static ArrayList<Design> getFromPreferences(Context mainApplicationContext) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = mainApplicationContext.getSharedPreferences("PALLETDESIGNS", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("PALLETDESIGNS", "");
        Type type = new TypeToken<List<Design>>() {}.getType();

        ArrayList<Design> palletDesigns = gson.fromJson(jsonPreferences, type);
        if(palletDesigns == null) palletDesigns = new ArrayList<Design>();

        return palletDesigns;

    }

    static boolean alreadyExists(String nameToCheck, Context mainApplicationContext) {
        boolean isContained = false;
        ArrayList<Design> palletDesigns = getFromPreferences(mainApplicationContext);

        for (int i = 0; i < palletDesigns.size(); i++) {
            if (palletDesigns.get(i).getName().equals(nameToCheck))
                isContained = true;
        }

        return isContained;
    }

    static void saveDesign(Design designToSave, Context mainApplicationContext) {
        ArrayList<Design> palletDesigns = getFromPreferences(mainApplicationContext);

        //Find if a same-named design is already on the list and locate it
        int position = -1;
        for (int i = 0; i < palletDesigns.size(); i++) {
            if (palletDesigns.get(i).getName().equals(designToSave.getName()))
                position = i; //There should be no duplicated names
        }

        //If design already exists, overwrite it
        if(position != -1) {
            palletDesigns.set(position, designToSave);
        }
        //If not exists, append design to array
        else {
            palletDesigns.add(designToSave);
        }

        //Save Array
        saveToPreferences(palletDesigns, mainApplicationContext);
    }

    static void saveToPreferences(ArrayList<Design> designList, Context mainApplicationContext) {
        //Do not save default designs to avoid duplication
        for (int i = 0; i < designList.size(); i++) {
            if (designList.get(i).type == 0)
                designList.remove(i);
        }

        Gson gson = new Gson();
        SharedPreferences sharedPref = mainApplicationContext.getSharedPreferences("PALLETDESIGNS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //Serialize objects to a String that can be saved by using the Google Gson library.
        String serializedObjects = gson.toJson(designList);
        editor.putString("PALLETDESIGNS", serializedObjects);
        editor.commit();
    }

}