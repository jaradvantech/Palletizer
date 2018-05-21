package com.example.administrator.Palletizer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditorNewPalletDesign extends AppCompatActivity {

    private AlertDialog.Builder overwriteBuilder;
    private AlertDialog.Builder invalidNameBuilder;

    private EditText nameInput;
    private Button create_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_new);

        nameInput = (EditText) findViewById(R.id.editorNew_editText_name);
        create_button = findViewById(R.id.editorNew_button_create);


        /*****************************************************
         *                  --INVALID NAME DIALOG--
         *****************************************************/
        invalidNameBuilder = new AlertDialog.Builder(this);
        invalidNameBuilder.setTitle("Error");
        invalidNameBuilder.setMessage("The name entered is not valid");


        /*****************************************************
         *                   --SAVE DIALOG--
         *****************************************************/
        overwriteBuilder = new AlertDialog.Builder(this);
        overwriteBuilder.setTitle(getString(R.string.Warning));
        overwriteBuilder.setMessage("This design will be overwritten");
        //menu buttons listener
        DialogInterface.OnClickListener saveDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        returnName(nameInput.getText().toString());
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        overwriteBuilder.setPositiveButton("Continue", saveDialogClickListener);
        overwriteBuilder.setNegativeButton(getString(R.string.Cancel), saveDialogClickListener);


        /*****************************************************
         *                 --CREATE BUTTON LISTENER--
         *****************************************************/
        create_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = nameInput.getText().toString();

                if(PalletDesignManager.alreadyExists(name, EditorNewPalletDesign.this)) {
                    AlertDialog dialog = overwriteBuilder.create();
                    dialog.setIcon(R.mipmap.warning);
                    dialog.show();
                }
                else {
                    returnName(name);
                }
            }
        });
    }


    public void returnName(String name) {

        //Other conditions can be added as well
        if(name.equals("") == false) {
            Intent intent = new Intent();
            intent.putExtra("newDesignName", name);
            setResult(RESULT_OK, intent);
            finish();
        }

        else {
            AlertDialog dialog = invalidNameBuilder.create();
            dialog.setIcon(R.mipmap.error);
            dialog.show();
        }
    }

}