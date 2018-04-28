package com.example.administrator.Palletizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Editor extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private ImageButton confirmStep;
    private Button addButton;
    private Button removeButton;
    private Button editButton;
    private ImageView rotateLeft;
    private ImageView rotateRight;
    private ImageView arrowPrev;
    private ImageView arrowNext;
    private TextView stepIndicator;
    private TextView infoText;

    private Box boxBeingEdited;
    private int currentStepNumber;
    private int itemToDelete = 0;

    private AlertDialog.Builder builder;
    private EditorListAdapter adapter;
    private ArrayList<EditorObject> listObjects;
    private ArrayList<Box> palletBoxes;
    private ListView editorListView;
    private View view;
    private final int DEFAULT_X = 500;
    private final int DEFAULT_Y = 500;

    public Editor() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.fragment_editor, container, false);

        /*
         * Set up GUI
         */
        findUIElements();

        /*
         * Create onclick listeners for every interactive object in the screen
         */
        createListeners();

        /*
         * Set up side list
         */
        createSideMenu();

        /*
         * This list contains all the boxes present on the pallet. Since boxes
         * are palletized one by one, each box represents one step.
         */
        palletBoxes = new ArrayList<Box>();












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

    public void onItemSelected(int selectedItem) {

        ///moveTo(dynamicObjects[drawnObjects], DEFAULT_X, DEFAULT_Y);
    }


    private void addElementToPallet(EditorObject newObject) {

        //instantiate object

        //move object to destination
        //moveTo(ImageView itemToMove, int x, int y)

    }



    /*****************************************************
     *                         --GRAPHIC FUNCTIONS--
     *****************************************************/
    private void moveTo(ImageView itemToMove, int x, int y) {
        ObjectAnimator editorLayoutAnimation_x = ObjectAnimator.ofFloat(itemToMove, "x", x);
        ObjectAnimator editorLayoutAnimation_y = ObjectAnimator.ofFloat(itemToMove, "y", y);
        AnimatorSet animSetline = new AnimatorSet();
        animSetline.playTogether(editorLayoutAnimation_x, editorLayoutAnimation_y);

        animSetline.setDuration(500);
        animSetline.start();
    }

    private void setFocused(int focused) {

    }



    /*****************************************************
     *                         --USER INTERFACE--
     *****************************************************/
    private void createListeners() {

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //creo qeue este boton se puede ir, porque se anyaden pasos metiendo objetos
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(boxBeingEdited != null) {
                    palletBoxes.remove(currentStepNumber);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(boxBeingEdited != null) {
                    boxBeingEdited = palletBoxes.get(currentStepNumber);
                }
            }
        });

        confirmStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(palletBoxes != null) {
                    palletBoxes.add(boxBeingEdited);
                }
            }
        });

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(boxBeingEdited != null) {
                    int currentAngle = boxBeingEdited.coords.w;
                    boxBeingEdited.coords.w = (currentAngle-90) % 360;
                }
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(boxBeingEdited != null) {
                    int currentAngle = boxBeingEdited.coords.w;
                    boxBeingEdited.coords.w = (currentAngle+90) % 360;
                }
            }
        });

        arrowNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int totalSteps = palletBoxes.size();
                if(currentStepNumber < totalSteps) {
                    currentStepNumber++;
                    stepIndicator.setText(currentStepNumber + "/" + totalSteps);
                }
            }
        });

        arrowPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int totalSteps = palletBoxes.size();
                if(currentStepNumber > 0) {
                    currentStepNumber--;
                    stepIndicator.setText(currentStepNumber + "/" + totalSteps);
                }
            }
        });


        //Select item from list
        editorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // If the item selected is the last one on the list ("Add" button):
                if(position == listObjects.size()-1) {
                    ((MainActivity)getActivity()).switchToLayout(R.id.opt_editor_add);

                    //Otherwise
                } else {
                    EditorObject selectedObject = listObjects.get(position);
                    addElementToPallet(selectedObject);
                }
            }
        });

        //Create delete dialog
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.Warning));
        builder.setMessage(getString(R.string.Doyoureallywanttodeletethisitem));
        //menu buttons listener
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        listObjects.remove(itemToDelete);
                        adapter.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        builder.setPositiveButton(getString(R.string.Delete), dialogClickListener);
        builder.setNegativeButton(getString(R.string.Cancel), dialogClickListener);

        //Delete item from list
        editorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemToDelete = position;
                AlertDialog dialog = builder.create();
                dialog.setIcon(R.mipmap.warning);
                dialog.show();
                return true;
            }
        });
    }

    private void findUIElements() {
        addButton = view.findViewById(R.id.editor_button_add);
        removeButton = view.findViewById(R.id.editor_button_remove);
        editButton = view.findViewById(R.id.editor_button_edit);
        confirmStep = view.findViewById(R.id.editor_button_confirmStep);
        rotateLeft = view.findViewById(R.id.editor_imageView_rotateLeft);
        rotateRight = view.findViewById(R.id.editor_imageview_rotateRight);
        arrowPrev = view.findViewById(R.id.editor_imageView_prev);
        arrowNext = view.findViewById(R.id.editor_imageView_next);
        stepIndicator = view.findViewById(R.id.editor_textView_steps);
        infoText = view.findViewById(R.id.editor_textView_info);
        editorListView = view.findViewById(R.id.editor_listView_items);
    }

    /*****************************************************
     *                       --SIDE MENU FUNCTIONS--
     *****************************************************/
    private void createSideMenu() {

        listObjects = new ArrayList<>();
        //add objects to list
        listObjects.add(new EditorObject("",0,0, R.mipmap.box_lovalive));
        listObjects.add(new EditorObject("",0,0, R.mipmap.box_side));
        listObjects.add(new EditorObject("",0,0, R.mipmap.add));
        //Set adapter
        adapter = new EditorListAdapter(getActivity(), listObjects);
        editorListView.setAdapter(adapter);
    }

    public void refreshObjectList() {
        Gson gson = new Gson();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("CUSTOMBOXES", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("CUSTOMBOXES", "");

        Type type = new TypeToken<List<EditorObject>>() {}.getType();
        listObjects = gson.fromJson(jsonPreferences, type);

        adapter.notifyDataSetChanged();
    }



    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
