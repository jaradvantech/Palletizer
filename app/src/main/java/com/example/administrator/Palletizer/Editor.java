package com.example.administrator.Palletizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


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

    private AlertDialog.Builder deleteDialogBuilder;
    private AlertDialog.Builder saveDialogBuilder;
    private EditorListAdapter adapter;
    private ArrayList<ImageView> onScreenBoxes;
    private ArrayList<BoxPrototype> sideListObjects;
    private ArrayList<Box> palletBoxes;
    private ListView editorListView;
    private View view;


    /*
     * Pallet is 120*100cm in real life, here is 600px*500px, so;
     * 1 cm = 5px
     * 1 px = 0.2cm
     */
    private final int WIDTH_PX = 600;
    private final int HEIGHT_PX = 500;
    private final int WIDTH_CM = 120;
    private final int HEIGHT_CM = 100;
    private final float CMTOPX = 5f;
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
         * This list contains all the boxes present on the pallet. Since boxes
         * are palletized one by one, each box represents one step.
         */
        palletBoxes = new ArrayList<Box>();

        /*
         * This list has all the references to the objects in the screen
         */
        onScreenBoxes = new ArrayList<ImageView>();

        designBeingEdited = new Design(1, "default");








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
            Box newBox = new Box(new Coord(0,0,0,0), protoType.width, protoType.height, protoType.textureResource);

            //Add box to design
            designBeingEdited.boxList.add(newBox);


            //Now the box being edited is the last one
            changeSelectedStep(designBeingEdited.boxList.size()-1);

            //Now, create an image that represents the aforementioned box
            addNewBoxToPallet(newBox);

            //Move it to the center of the pallet TODO think about animations in the future
            //moveTo(onScreenBoxes.get(stepBeingEdited), DEFAULT_X, DEFAULT_Y);
        } else {

            //TODO SHOW DIALOG
            //TODO START NEW DESIGN
        }
    }
    /*****************************************************
     *                         --DESIGN MANAGEMENT--
     *****************************************************/
    private void startNewDesign(String newDesignName) {
        designBeingEdited = new Design(1, newDesignName);
        updateUserInterface();
    }

    private void saveDesign() {
        DesignManager.saveDesign(designBeingEdited, getActivity());
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

    private void moveTo(ImageView itemToMove, int x, int y) {
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

    /*
     * For making this more user understandable, numbering will start at 1
     */
    private void updateStepIndicator() {
        stepIndicator.setText(stepBeingEdited+1 + "/" + designBeingEdited.boxList.size());
    }


    private void updateUserInterface() {
        
    }

    private void updateInfoText() {
        Coord coordsToPrint = designBeingEdited.boxList.get(stepBeingEdited).coords;
        String info = "X: ";
        info += coordsToPrint.x*PXTOCM;
        info += "cm\nY: ";
        info += coordsToPrint.y*PXTOCM;
        info += "cm\nZ: ";
        info += coordsToPrint.z;
        info += "cm\nW: ";
        info += coordsToPrint.w;
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
         * Start an entirely new design.
         */
        newButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Start a new activity for asking the name of the new item. Will return a string with the name
                Intent intent = new Intent(getContext(), EditorNew.class);
                startActivityForResult(intent, 1);
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(designBeingEdited.boxList.size() > 0) {
                    //Remove view fom pallet
                    ((ViewGroup) onScreenBoxes.get(stepBeingEdited).getParent()).removeView(onScreenBoxes.get(stepBeingEdited));
                    onScreenBoxes.remove(stepBeingEdited);

                    designBeingEdited.boxList.remove(stepBeingEdited);

                    //If index was the last one, decrement current step
                    if(designBeingEdited.boxList.size() <= stepBeingEdited) {
                        stepBeingEdited--;
                    }

                    updateStepIndicator();
                }
            }
        });

        rotateLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO tidy up
                int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle-90);// % 360;
            }
        });

        rotateRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO tidy up
                int currentAngle = designBeingEdited.boxList.get(stepBeingEdited).coords.w;
                designBeingEdited.boxList.get(stepBeingEdited).coords.w = (currentAngle+90);// % 360;
            }
        });

        arrowNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int totalSteps = designBeingEdited.boxList.size();
                if(stepBeingEdited < totalSteps-1) {
                    changeSelectedStep(stepBeingEdited+1);
                    adjustBarsToItem(stepBeingEdited);
                }
            }
        });

        arrowPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(stepBeingEdited > 0) {
                    changeSelectedStep(stepBeingEdited-1);
                    adjustBarsToItem(stepBeingEdited);
                }
            }
        });

        confirmStep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = saveDialogBuilder.create();
                dialog.setIcon(R.mipmap.faq);
                dialog.show();
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
                //Trigger only with user-generated interaction
                if(designBeingEdited.boxList.size() > 0 && fromUser) {

                    //Calculate margin to ensure box is inside of the pallet
                    int xMargin = WIDTH_PX - designBeingEdited.boxList.get(stepBeingEdited).width;
                    Log.d("margin", xMargin + "");
                    if(progress < xMargin) {
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
                //Trigger only with user-generated interaction and
                //If the list is not empty
                if(designBeingEdited.boxList.size() > 0 && fromUser) {

                    //Calculate margin to ensure box is inside of the pallet
                    int yMargin = HEIGHT_PX - designBeingEdited.boxList.get(stepBeingEdited).height;
                    Log.d("margin", yMargin + "");
                    if(progress < yMargin) {
                        designBeingEdited.boxList.get(stepBeingEdited).coords.y = progress;
                        onScreenBoxes.get(stepBeingEdited).setY(progress);
                        updateInfoText();
                    }
                }
            }
        });


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
                        adapter.notifyDataSetChanged();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        deleteDialogBuilder.setPositiveButton(getString(R.string.Delete), deleteDialogClickListener);
        deleteDialogBuilder.setNegativeButton(getString(R.string.Cancel), deleteDialogClickListener);

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

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        saveDialogBuilder.setPositiveButton(getString(R.string.Save), saveDialogClickListener);
        saveDialogBuilder.setNegativeButton(getString(R.string.Cancel), saveDialogClickListener);

        //Delete item from list
        editorListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemToDelete = position;
                AlertDialog dialog = saveDialogBuilder.create();
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
        editButton = view.findViewById(R.id.editor_button_edit);
        confirmStep = view.findViewById(R.id.editor_button_confirmStep);
        rotateLeft = view.findViewById(R.id.editor_imageView_rotateLeft);
        rotateRight = view.findViewById(R.id.editor_imageview_rotateRight);
        arrowPrev = view.findViewById(R.id.editor_imageView_prev);
        arrowNext = view.findViewById(R.id.editor_imageView_next);
        stepIndicator = view.findViewById(R.id.editor_textView_steps);
        infoText = view.findViewById(R.id.editor_textView_info);
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


        //ListObjectManager.ReadBoxes();
        sideListObjects.add(new BoxPrototype(100,100, R.mipmap.box_lovalive));
        sideListObjects.add(new BoxPrototype(100,10, R.mipmap.box_side));
        
        
        //This item is not really a Box design, but a button
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.add));
        
        //Set adapter
        adapter = new EditorListAdapter(getActivity(), sideListObjects);
        editorListView.setAdapter(adapter);
    }

    public void refreshObjectList() {
        sideListObjects = BoxTypeManager.getFromPreferences(getActivity());

        //Last item on the list will be the ADD button
        sideListObjects.add(new BoxPrototype(0,0, R.mipmap.add));

        adapter.notifyDataSetChanged();
    }

    /*****************************************************
     *                         --DIALOGS--
     *****************************************************/


    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
