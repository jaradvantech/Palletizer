package com.example.administrator.Palletizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class EditorNewBoxDesign extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;

    private ImageView boxPreview;
    private EditText name;
    private EditText height;
    private EditText width;
    private TextView boxText;
    private Button done;
    private Button preview;
    private String boxName;
    private int x;
    private int y;
    private final int MAXWIDTH = 1200;
    private final int MAXLENGTH = 1200;
    private final int MINWIDTH = 3;
    private final int MINLENGTH = 3;
    private AlertDialog.Builder saveBuilder;
    private AlertDialog.Builder overwriteBuilder;


    public EditorNewBoxDesign() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_editor_add, container, false);

        //Load GUI
        boxPreview = (ImageView) view.findViewById(R.id.editor_imageView_pallet);
        boxText = (TextView) view.findViewById(R.id.add_textView_boxText);
        name = (EditText) view.findViewById(R.id.add_editText_name);
        width = (EditText) view.findViewById(R.id.add_editText_width);
        height = (EditText) view.findViewById(R.id.add_editText_height);
        done = view.findViewById(R.id.add_button_done);
        preview = view.findViewById(R.id.add_button_preview);

        //Create objects
        saveBuilder = new AlertDialog.Builder(getActivity());
        overwriteBuilder = new AlertDialog.Builder(getActivity());


        /*****************************************************
         *                                --SAVE DIALOG--
         *****************************************************/
        saveBuilder.setTitle(getString(R.string.Warning));
        saveBuilder.setMessage("Save this item?");
        //menu buttons listener
        DialogInterface.OnClickListener saveDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        saveNewObject(false);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        saveBuilder.setPositiveButton(getString(R.string.Save), saveDialogClickListener);
        saveBuilder.setNegativeButton(getString(R.string.Cancel), saveDialogClickListener);


        /*****************************************************
         *                            --OVERWRITE DIALOG--
         *****************************************************/
        overwriteBuilder.setTitle(getString(R.string.Warning));
        overwriteBuilder.setMessage("There is another item with the same name. Do you wish to continue?");
        //menu buttons listener
        DialogInterface.OnClickListener overwriteDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        saveNewObject(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        overwriteBuilder.setPositiveButton("Overwrite", overwriteDialogClickListener);
        overwriteBuilder.setNegativeButton(getString(R.string.Cancel), overwriteDialogClickListener);

        /*****************************************************
         *                            --ACTION BUTTONS--
         *****************************************************/
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = saveBuilder.create();
                dialog.setIcon(R.mipmap.faq);
                dialog.show();
            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setPreview();
            }
        });
        return view;
    }

    @Override
    public void onAttach( Context context ) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mFragmentInteraction = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteraction = null;
    }

    public void setPreview() {
        readEnteredData();

        if(enteredDataIsValid()) {
            //This is not to scale, preview will just show the ratio.
            //For this screen, default is 300x300dp
            float ratio = x / y;
            int scaledX, scaledY;

            if(x > y) {
                scaledX = 300;
                scaledY = (int) (300*((float) y / (float) x));
            } else {
                scaledX = (int) (300*((float) x / (float) y));
                scaledY = 300;
            }

            boxText.setText(boxName + " (" + x + "cm x " + y + "cm)");

            ConstraintLayout.LayoutParams boxParams = (ConstraintLayout.LayoutParams) boxPreview.getLayoutParams();
            boxParams.width = scaledX;
            boxParams.height = scaledY;

            boxPreview.setLayoutParams(boxParams);
        }
    }

    private boolean enteredDataIsValid() {
        boolean isValid = false;

        if(x > MAXWIDTH || x < MINWIDTH  || y > MAXLENGTH || y < MINLENGTH) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.Warning));
            builder.setMessage(getString(R.string.invalidBoxSize));
            AlertDialog dialog = builder.create();
            dialog.setIcon(R.mipmap.warning);
            dialog.show();

        } else {
            isValid = true;
        }

        return isValid;
    }

    private void readEnteredData() {
        x = inputToInteger(width);
        y = inputToInteger(height);
        boxName = name.getText().toString();
        if(boxName.equals("")) boxName = "Untitled Box";
    }

    private void resetData() {
        height.setText("");
        width.setText("");
        name.setText("Untitled Box");
        boxText.setText("Untitled Box");

        ConstraintLayout.LayoutParams boxParams = (ConstraintLayout.LayoutParams) boxPreview.getLayoutParams();
        boxParams.width = 300;
        boxParams.height = 300;
        boxPreview.setLayoutParams(boxParams);
    }


    private void saveNewObject(boolean overwrite) {
        readEnteredData();
        setPreview();

        if(enteredDataIsValid()) {
            if(BoxDesignManager.alreadyExists(boxName, getContext()) && overwrite==false) {
                //Name conflict
                AlertDialog dialog = overwriteBuilder.create();
                dialog.setIcon(R.mipmap.warning);
                dialog.show();

             //No name conflict
            } else {
                /*Create new object*/
                BoxDesign newCustomBox = new BoxDesign(0, boxName, x, y, R.mipmap.box);

                /*Write objects to SharedPrefs*/
                BoxDesignManager.saveDesign(newCustomBox, getContext());

                /*Pop tart*/
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();

                /*Reset everything to default and Switch back to Editor*/
                resetData();
                ((MainActivity)getActivity()).switchToLayout(R.id.nav_editor);
            }

         //If data is not valid
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.Warning));
            builder.setMessage(getString(R.string.invalidBoxSize));
            AlertDialog dialog = builder.create();
            dialog.setIcon(R.mipmap.error);
            dialog.show();
        }
    }

    int inputToInteger(EditText mUserInput) {
        //Get int from input
        String inputText = mUserInput.getText().toString();
        //default is zero
        if(TextUtils.isEmpty(inputText))
            inputText = "0";
        return Integer.parseInt(inputText);
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand(String command);
    }
}
