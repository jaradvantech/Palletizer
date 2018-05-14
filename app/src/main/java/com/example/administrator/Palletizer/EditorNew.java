package com.example.administrator.Palletizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class EditorNew extends Fragment {


    private Gson gson;
    private SharedPreferences sharedPref;
    private AlertDialog.Builder saveBuilder;
    private AlertDialog.Builder overwriteBuilder;

    private Button create_button;

    public EditorNew() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editor_new, container, false);

        create_button = view.findViewById(R.id.editorNew_button_create);

        /*****************************************************
         *                                --SAVE DIALOG--
         *****************************************************/
        saveBuilder.setTitle(getString(R.string.Warning));
        saveBuilder.setMessage("Save this item?");
        //menu buttons listener
        DialogInterface.OnClickListener saveDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        saveBuilder.setPositiveButton(getString(R.string.Save), saveDialogClickListener);
        saveBuilder.setNegativeButton(getString(R.string.Cancel), saveDialogClickListener);


        create_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = saveBuilder.create();
                dialog.setIcon(R.mipmap.faq);
                dialog.show();
            }
        });

        return view;
    }

}