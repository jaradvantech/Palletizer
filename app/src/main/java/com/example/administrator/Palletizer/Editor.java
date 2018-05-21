package com.example.administrator.Palletizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class Editor extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    
    private ImageButton saveDesign;
    private Button newButton;
    private Button removeButton;
    private ImageView rotateLeft;
    private ImageView rotateRight;
    private ImageView arrowPrev;
    private ImageView arrowNext;
    private TextView stepIndicator;
    private TextView infoText;
    private TextView designName;
    private RelativeLayout pallet;
    private SeekBar xAxisPos;
    private SeekBar yAxisPos;

    private int stepBeingEdited;
    private Design designBeingEdited;
    private int itemToDelete = 0;

    private AlertDialog.Builder deleteDialogBuilder;
    private AlertDialog.Builder saveDialogBuilder;
    private BoxDesignListAdapter adapter;
    private ArrayList<ImageView> onScreenBoxes;
    private ArrayList<BoxPrototype> sideListObjects;
    private ListView editorListView;
    private View view;


    /*
     * Pallet is 120*100cm in real life, here is 600px*500px, so;
     * 1 cm = 5px
     * 1 px = 0.2cm
     */
    private final int WIDTH_PX = 600;
    private final int HEIGHT_PX = 500;
    private final int CMTOPX = 5;
    private final float PXTOCM = 0.2f;
    private final int DEFAULT_X = 0;
    private final int DEFAULT_Y = 0;

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
         * This list has all the references to the objects in the screen
         */
        onScreenBoxes = new ArrayList<ImageView>();

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


    /*
     * Called when the user selects an item from the side list.
     */
    private void addElementToPallet(BoxPrototype protoType) {
        if(designBeingEditedIsValid()) {
            //TODO create box from box prototype
            Box newBox = new Box(new Coord(0, 0, 0, 0), protoType.width, protoType.height, protoType.textureResource);

            //Add box to design
            designBeingEdited.boxList.add(newBox);

            //Now the box being edited is the last one
            changeSelectedStep(designBeingEdited.boxList.size() - 1);

            //Now, create an image that represents the aforementioned box
            drawBox(newBox);

        }
    }

    /*****************************************************
     *                         --DESIGN MANAGEMENT--
     *****************************************************/
    private void startNewDesign(String newDesignName) {
        designBeingEdited = new Design(1, newDesignName);
        clearUI();
        designName.setText(designBeingEdited.getName());
    }

    private void saveDesign() {
        if(designBeingEditedIsValid()) {
            PalletDesignManager.saveDesign(designBeingEdited, getContext());
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void editDesign(int designToEdit) {
        designBeingEdited = PalletDesignManager.getFromPreferences(getContext()).get(designToEdit);
        loadDesign();
        designName.setText(designBeingEdited.getName());
        stepBeingEdited = 0;
        updateInfoText();
        updateStepIndicator();
    }

    public Boolean designBeingEditedIsValid() {
        if(designBeingEdited != null) {
            return true;
        }
        else {
            //Show dialog asking for a design to be edited
            AlertDialog.Builder noDesignDialog = new AlertDialog.Builder(getActivity());
            noDesignDialog.setTitle("No design selected");
            noDesignDialog.setMessage("Please press «New Design» to create a new one, OR edit an already existing design by long pressing on the list (Control Screen)");
            AlertDialog dialog = noDesignDialog.create();
            dialog.setIcon(R.mipmap.error);
            dialog.show();
            return false;
        }
    }

    /*****************************************************
     *                         --GRAPHIC FUNCTIONS--
     *****************************************************/
    private void loadDesign() {
        removeAllBoxes();

        //Load and draw boxes of new design
        ArrayList<Box> boxList = designBeingEdited.getSteps();
        int totalSteps = boxList.size();

        for(int i=0; i<totalSteps; i++) {
            drawBox(boxList.get(i));
        }
    }

    private void drawBox(Box boxToAdd) {
        //Dynamically create ImageView
        ImageView boxImage = new ImageView(getActivity());
        boxImage.setImageResource(boxToAdd.textureResource);

        //Set size
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boxToAdd.height*CMTOPX, boxToAdd.width*CMTOPX);
        //Set position,
        params.setMargins(DEFAULT_X, DEFAULT_Y,0,0);
        //Apply parameters to ImageView
        boxImage.setLayoutParams(params);
        //Reset bars to default position
        adjustBarsToDefault();

        //Add ImageView to layout
        pallet.addView(boxImage);
        
        //Save Image (this is to keep every ImageView properly referenced)
        onScreenBoxes.add(boxImage);
    }

    private void rotateBox(ImageView boxToRotate, int angle) {
        boxToRotate.setRotation((float) angle);
    }

    private void removeAllBoxes() {
        pallet.removeAllViews();
        onScreenBoxes.clear();
    }

    private void moveWithAnimation(ImageView itemToMove, int x, int y) {
        ObjectAnimator editorLayoutAnimation_x = ObjectAnimator.ofFloat(itemToMove, "x", x);
        ObjectAnimator editorLayoutAnimation_y = ObjectAnimator.ofFloat(itemToMove, "y", y);
        AnimatorSet animSetline = new AnimatorSet();
        animSetline.playTogether(editorLayoutAnimation_x, editorLayoutAnimation_y);

        animSetline.setDuration(500);
        animSetline.start();
    }

    private void adjustBarsToItem(int step) {
        yAxisPos.setProgress(designBeingEdited.boxList.get(step).coords.y);
        xAxisPos.setProgress(designBeingEdited.boxList.get(step).coords.x);
    }

    private void adjustBarsToDefault() {
        yAxisPos.setProgress(DEFAULT_X);
        xAxisPos.setProgress(DEFAULT_Y);
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

    /*****************************************************
     *                                        --TEXTS--
     *****************************************************/
    /*
     * For making this more user understandable, numbering will start at 1
     */
    private void updateStepIndicator() {
        stepIndicator.setText(stepBeingEdited+1 + "/" + designBeingEdited.boxList.size());
    }

    private void resetStepIndicator() {
        stepIndicator.setText("0/0");
    }

    private void clearUI() {
        removeAllBoxes();
        resetInfoText();
        resetStepIndicator();
        adjustBarsToDefault();
        designName.setText("No Design Selected");
    }

    private void updateInfoText() {
        Coord coordsToPrint = designBeingEdited.boxList.get(stepBeingEdited).coords;
        String info = "X: ";
        info += String.format("%.2f", coordsToPrint.x*PXTOCM);
        info += "cm\nY: ";
        info += String.format("%.2f", coordsToPrint.y*PXTOCM);
        info += "cm\nZ: ";
        info += String.format("%.2f", coordsToPrint.z*PXTOCM);
        info += "cm\nW: ";
        info += coordsToPrint.w;
        info += "deg";
        infoText.setText(info);
    }

    private void resetInfoText() {
        String info = "X: ";
        info += "0.0";
        info += "cm\nY: ";
        info += "0.0";
        info += "cm\nZ: ";
        info += "0.0";
        info += "cm\nW: ";
        info += "0";
        info += "deg";
        infoText.setText(info);
    }

    void changeSelectedStep(int newStep) {
        stepBeingEdited = newStep;
        updateInfoText();
        updateStepIndicator();
    }

    /*****************************************************
     *                         --USER INTERFACE--
     *****************************************************/
    private void createListeners() {

        /*
         * Arrows to select an specific step from the design
         */
        arrowNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (designBeingEditedIsValid()) {
                    int totalSteps = designBeingEdited.boxList.size();
                    if (stepBeingEdited < totalSteps - 1) {
                        changeSelectedStep(stepBeingEdited + 1);
                        adjustBarsToItem(stepBeingEdited);
                    }
                }
            }
        });

        arrowPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(designBeingEditedIsValid() && stepBeingEdited > 0) {
                    changeSelectedStep(stepBeingEdited-1);
                    adjustBarsToItem(stepBeingEdited);
                }
            }
        });

        //Start an entirely new design.
        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start a new activity for asking the name of the new item. Will return a string with the name
                Intent intent = new Intent(getContext(), EditorNewPalletDesign.class);
                startActivityForResult(intent, 1);
            }
        });

        //Remove the selected step
        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (designBeingEditedIsValid() && designBeingEdited.boxList.size() > 0) {
                    //Remove view fom pallet
                    ((ViewGroup) onScreenBoxes.get(stepBeingEdited).getParent()).removeView(onScreenBoxes.get(stepBeingEdited));
                    onScreenBoxes.remove(stepBeingEdited);

                    designBeingEdited.boxList.remove(stepBeingEdited);

                    //If index was the last one, decrement current step
                    if (designBeingEdited.boxList.size() <= stepBeingEdited) {
                        stepBeingEdited--;
                    }

                    updateStepIndicator();
                    adjustBarsToItem(stepBeingEdited);
                }
            }
        });

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (designBeingEditedIsValid()) {
                    int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                    if(currentAngle > 0) {
                        designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle - 90) % 360;
                        rotateBox(onScreenBoxes.get(stepBeingEdited), currentAngle);
                    }
                    updateInfoText();
                }
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (designBeingEditedIsValid()) {
                    int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                    designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle + 90) % 360;
                    rotateBox(onScreenBoxes.get(stepBeingEdited), currentAngle);
                    updateInfoText();
                }
            }
        });

        saveDesign.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(designBeingEditedIsValid()) {
                    AlertDialog dialog = saveDialogBuilder.create();
                    dialog.setIcon(R.mipmap.faq);
                    dialog.show();
                }
            }
        });


        xAxisPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(designBeingEditedIsValid()) {
                    //Trigger only with user-generated interaction
                    if (designBeingEdited.boxList.size() > 0 && fromUser) {
                        designBeingEdited.boxList.get(stepBeingEdited).coords.x = progress;
                        onScreenBoxes.get(stepBeingEdited).setX(progress);
                        updateInfoText();
                    }
                }
            }
        });

        yAxisPos.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(designBeingEditedIsValid()) {
                    //Trigger only with user-generated interaction and
                    //If the list is not empty
                    if (designBeingEdited.boxList.size() > 0 && fromUser && designBeingEditedIsValid()) {
                        designBeingEdited.boxList.get(stepBeingEdited).coords.y = progress;
                        onScreenBoxes.get(stepBeingEdited).setY(progress);
                        updateInfoText();
                    }
                }
            }
        });

        /*
         * Dialog shown when long pressing an item in the side list for deletion
         */
        deleteDialogBuilder = new AlertDialog.Builder(getActivity());
        deleteDialogBuilder.setTitle(getString(R.string.Warning));
        deleteDialogBuilder.setMessage(getString(R.string.Doyoureallywanttodeletethisitem));
        //menu buttons listener
        DialogInterface.OnClickListener deleteDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        sideListObjects.remove(itemToDelete);
                        BoxDesignManager.saveToPreferences(sideListObjects, getContext());
                        adapter.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        deleteDialogBuilder.setPositiveButton(getString(R.string.Delete), deleteDialogClickListener);
        deleteDialogBuilder.setNegativeButton(getString(R.string.Cancel), deleteDialogClickListener);

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

        //Delete item from list
        editorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(position != sideListObjects.size()-1) {
                    itemToDelete = position;
                    AlertDialog dialog = deleteDialogBuilder.create();
                    dialog.setIcon(R.mipmap.warning);
                    dialog.show();
                }
                return true;
            }
        });

        /*
         * Dialog shown whe saving hte current design
         */
        saveDialogBuilder = new AlertDialog.Builder(getActivity());
        saveDialogBuilder.setTitle(getString(R.string.Warning));
        saveDialogBuilder.setMessage("Do you want to save this design?");
        //menu buttons listener
        DialogInterface.OnClickListener saveDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        saveDesign();
                        designBeingEdited = null;
                        clearUI();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Discard changes
                        designBeingEdited = null;
                        clearUI();
                        break;


                }
            }
        };
        saveDialogBuilder.setPositiveButton(getString(R.string.Save), saveDialogClickListener);
        saveDialogBuilder.setNegativeButton("Discard", saveDialogClickListener);
        saveDialogBuilder.setNeutralButton(getString(R.string.Cancel), saveDialogClickListener);

        //Delete item from list
        editorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemToDelete = position;
                AlertDialog dialog = deleteDialogBuilder.create();
                dialog.setIcon(R.mipmap.warning);
                dialog.show();
                return true;
            }
        });
    }


    /*
     * Name for new design has been defined
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                startNewDesign(data.getStringExtra("newDesignName"));
            }
        }
    }


    private void findUIElements() {
        newButton = view.findViewById(R.id.editor_button_new);
        removeButton = view.findViewById(R.id.editor_button_remove);
        saveDesign = view.findViewById(R.id.editor_button_saveDesign);
        rotateLeft = view.findViewById(R.id.editor_imageView_rotateLeft);
        rotateRight = view.findViewById(R.id.editor_imageview_rotateRight);
        arrowPrev = view.findViewById(R.id.editor_imageView_prev);
        arrowNext = view.findViewById(R.id.editor_imageView_next);
        stepIndicator = view.findViewById(R.id.editor_textView_steps);
        infoText = view.findViewById(R.id.editor_textView_info);
        designName = view.findViewById(R.id.editor_textView_designName);
        editorListView = view.findViewById(R.id.editor_listView_items);
        pallet = view.findViewById(R.id.editor_relativeLayout_pallet);
        xAxisPos = view.findViewById(R.id.editor_seekBar_xaxis);
        yAxisPos = view.findViewById(R.id.editor_seekBar_yaxis);

        //configure seekbars
        xAxisPos.setMax(WIDTH_PX);
        yAxisPos.setMax(HEIGHT_PX);
    }

    /*****************************************************
     *                       --SIDE MENU FUNCTIONS--
     *****************************************************/
    private void createSideMenu() {
        sideListObjects = new ArrayList<>();
        sideListObjects = BoxDesignManager.getFromPreferences(getContext());
        
        //This item is not really a Box design, but a button
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.add));
        
        //Set adapter
        adapter = new BoxDesignListAdapter(getActivity(), sideListObjects);
        editorListView.setAdapter(adapter);
    }

    public void refreshObjectList() {
        /*
         * RBS. It might be tempting just to assign sideListObjects = BoxDesignManager.getFromPreferences()
         * but doing so would de-reference the list from the adapter
         */
        sideListObjects.clear();
        sideListObjects.addAll(BoxDesignManager.getFromPreferences(getActivity()));

        //Last item on the list will be the ADD button
        //RBS I'm sure there is a decent way of doing this, but we are running out of time
        //so I will fix it in the future
        sideListObjects.add(new BoxPrototype(0, "add", 0,0, R.mipmap.add));

        adapter.notifyDataSetChanged();
    }

    /*****************************************************
     *                         --DIALOGS--
     *****************************************************/


    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
