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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Editor extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    
    private ImageButton confirmStep;
    private Button newButton;
    private Button removeButton;
    private Button editButton;
    private ImageView rotateLeft;
    private ImageView rotateRight;
    private ImageView arrowPrev;
    private ImageView arrowNext;
    private TextView stepIndicator;
    private TextView infoText;
    private RelativeLayout pallet;
    private SeekBar xAxisPos;
    private SeekBar yAxisPos;

    private int stepBeingEdited;
    private Design designBeingEdited;
    private int currentStepNumber;
    private int itemToDelete = 0;

    private AlertDialog.Builder builder;
    private EditorListAdapter adapter;
    private ArrayList<ImageView> onScreenBoxes;
    private ArrayList<BoxPrototype> sideListObjects;
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
         * Set up the list showing the different box design for the user to choose from.
         */
        createSideMenu();

        /*
         * This list contains all the boxes present on the pallet. Since boxes
         * are palletized one by one, each box represents one step.
         */
        palletBoxes = new ArrayList<Box>();

        /*
         * This list has all the references to the objects in the screen
         */
        onScreenBoxes = new ArrayList<ImageView>();

        designBeingEdited = new Design();








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


    /*
     * Called when the user selects an item from the side list.
     */
    private void addElementToPallet(BoxPrototype protoType) {
        if(designBeingEdited != null) {
            //TODO create box from box prototype
            Box newBox = new Box(protoType.width, protoType.height, protoType.textureResource);

            //Add box to design
            designBeingEdited.boxList.add(newBox);


            //now the box being edited is the last one
            stepBeingEdited = designBeingEdited.boxList.size();

            //TODO update display text

            //Now, create an image that represents the aforementioned box
            addNewBoxToPallet(newBox);

            //Move it to the center of the pallet TODO think about animations in the future
            //moveTo(onScreenBoxes.get(stepBeingEdited), DEFAULT_X, DEFAULT_Y);
        }
    }
    /*****************************************************
     *                         --DESIGN MANAGEMENT--
     *****************************************************/
    private void startNewDesign() {
        designBeingEdited = new Design();
        updateUserInterface();
    }

    private void saveDesign() {
        //Read prefs
        //Add object to list
        //save prefs
        startNewDesign();
    }
    
    

    /*****************************************************
     *                         --GRAPHIC FUNCTIONS--
     *****************************************************/
    private void addNewBoxToPallet(Box boxToAdd) {
        //Dynamically create ImageView
        ImageView boxImage = new ImageView(getActivity());
        boxImage.setImageResource(boxToAdd.textureResource);

        //Set size
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boxToAdd.height, boxToAdd.width);
        //Set position
        params.setMargins(boxToAdd.coords.x, boxToAdd.coords.y,0,0);
        //Apply parameters to ImageView
        boxImage.setLayoutParams(params);

        //Add ImageView to layout
        pallet.addView(boxImage);
        
        //Save Image (this is to keep every ImageView properly referenced)
        onScreenBoxes.add(boxImage);
    }

    private void moveTo(ImageView itemToMove, int x, int y) {
        ObjectAnimator editorLayoutAnimation_x = ObjectAnimator.ofFloat(itemToMove, "x", x);
        ObjectAnimator editorLayoutAnimation_y = ObjectAnimator.ofFloat(itemToMove, "y", y);
        AnimatorSet animSetline = new AnimatorSet();
        animSetline.playTogether(editorLayoutAnimation_x, editorLayoutAnimation_y);

        animSetline.setDuration(500);
        animSetline.start();
    }

    private void setPosition(ImageView itemToMove, int x, int y) {

    }


    private void setFocused(int focused) {
        int totalItems = onScreenBoxes.size();
        
        //Set all the boxes greyed out except for the selected one
        for(int i=0; i<totalItems; i++) {
            if(i != focused) onScreenBoxes.get(i).setImageAlpha(50);
        }
    }
    
    private void removeFocus() {
        int totalItems = onScreenBoxes.size();
        for(int i=0; i<totalItems; i++) {
            onScreenBoxes.get(i).setImageAlpha(100);
        }
    }
    
    private void updateStepIndicator() {
        int totalSteps = designBeingEdited.boxList.size();
        stepIndicator.setText(currentStepNumber + "/" + totalSteps);
    }


    private void updateUserInterface() {
        
    }


    /*****************************************************
     *                         --USER INTERFACE--
     *****************************************************/
    private void createListeners() {

        /*
         * Start an entirely new design. 
         */
        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO: show Warning to user
                
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                designBeingEdited.boxList.remove(stepBeingEdited);
            }
        });
        /*

        confirmStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(designBeingEdited != null) {
                    //TODO Esto tiene sentido? no se tratara siempe de añadir
                    //a veces se estará editando un paso que ya existe
                    designBeingEdited.boxList.add(boxBeingEdited);
                }
            }
        });
        */

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO tidy up
                int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle-90) % 360;
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO tidy up
                int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle+90) % 360;
            }
        });

        arrowNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int totalSteps = designBeingEdited.boxList.size();
                if(currentStepNumber < totalSteps) {
                    currentStepNumber++;
                    updateStepIndicator();
                }
            }
        });

        arrowPrev.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int totalSteps = designBeingEdited.boxList.size();
                    if(currentStepNumber > 0) {
                        currentStepNumber--;
                        updateStepIndicator();
                    }
                }
        });

        /*
        xAxisPos.setOnSeekBarChangeListener(new View.onChangeListener() {
            public void onSeekbarChangeListener(View v) {
                int totalSteps = designBeingEdited.boxList.size();
                if(currentStepNumber > 0) {
                    currentStepNumber--;
                    updateStepIndicator();
                }
            }
        });*/


        //Select item from list
        editorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // If the item selected is the last one on the list ("Add" button):
                if(position == sideListObjects.size()-1) {
                    ((MainActivity)getActivity()).switchToLayout(R.id.opt_editor_add);

                //Otherwise
                } else {
                    addElementToPallet(sideListObjects.get(position));
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
                        sideListObjects.remove(itemToDelete);
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
        newButton = view.findViewById(R.id.editor_button_new);
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
        pallet = (RelativeLayout) view.findViewById(R.id.control_relativeLayout_pallet);
        xAxisPos = (SeekBar) view.findViewById(R.id.editor_seekBar_xaxis);
        yAxisPos = (SeekBar) view.findViewById(R.id.editor_seekBar_yaxis);
    }

    /*****************************************************
     *                       --SIDE MENU FUNCTIONS--
     *****************************************************/
    private void createSideMenu() {
        sideListObjects = new ArrayList<>();
        
        //ListObjectManager.ReadBoxes();
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.box_lovalive));
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.box_side));
        
        
        //This item is not really a Box design, but a button
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.add));
        
        //Set adapter
        adapter = new EditorListAdapter(getActivity(), sideListObjects);
        editorListView.setAdapter(adapter);
    }

    public void refreshObjectList() {
        //TODO replace this with a static class
        Gson gson = new Gson();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("CUSTOMBOXES", Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString("CUSTOMBOXES", "");

        Type type = new TypeToken<List<BoxPrototype>>() {}.getType();
        sideListObjects = gson.fromJson(jsonPreferences, type);

        //Last item on the list will be the ADD button
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.add));

        adapter.notifyDataSetChanged();
    }



    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
