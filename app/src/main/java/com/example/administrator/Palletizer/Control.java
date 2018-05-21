package com.example.administrator.Palletizer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class Control extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private ArrayList<Design> designs;
    private PalletDesignListAdapter listAdapter;
    private ListView designListView;
    private View view;
    private RelativeLayout palletCanvas;
    private AlertDialog.Builder optionsDialogBuilder;
    private AlertDialog.Builder applyDialogBuilder;
    private Button applyButton;
    private int lastSelectedItem = 0;
    private final int CMTOPX = 5;

    public Control() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        view = inflater.inflate(R.layout.fragment_control, container, false);

        //Set everything up
        palletCanvas = (RelativeLayout) view.findViewById(R.id.control_relativeLayout_pallet);
        applyButton = view.findViewById(R.id.control_button_apply);

        /*Listview **************************************/
        designs = PalletDesignManager.getFromPreferences(getContext());
        designListView = (ListView) view.findViewById(R.id.control_ListView_designs);
        listAdapter = new PalletDesignListAdapter(getContext(), designs);
        designListView.setAdapter(listAdapter);

        /*****************************************************
         *                              --User interaction--
         *****************************************************/
        //Select item from list
        designListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Design selectedObject = designs.get(position);
                    loadDesignPreview(selectedObject);
            }
        });

        designListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lastSelectedItem = position;
                AlertDialog dialog = optionsDialogBuilder.create();
                dialog.setIcon(R.mipmap.warning);
                dialog.show();
                return true;
            }
        });

        applyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog dialog = applyDialogBuilder.create();
                dialog.setIcon(R.mipmap.faq);
                dialog.show();
            }
        });


        /*****************************************************
         *                              --Dialogs--
         *****************************************************/
        optionsDialogBuilder = new AlertDialog.Builder(getActivity());
        optionsDialogBuilder.setTitle(getString(R.string.Warning));
        optionsDialogBuilder.setMessage("Do you want to edit or delete this design?");
        //menu buttons listener
        DialogInterface.OnClickListener deleteDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Switch to editor fragment
                        ((MainActivity)getActivity()).loadInEditor(lastSelectedItem);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        designs.remove(lastSelectedItem);
                        PalletDesignManager.saveToPreferences(designs, getContext());
                        listAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        optionsDialogBuilder.setPositiveButton("Edit", deleteDialogClickListener);
        optionsDialogBuilder.setNegativeButton("Delete", deleteDialogClickListener);
        optionsDialogBuilder.setNeutralButton("Cancel", deleteDialogClickListener);


        applyDialogBuilder = new AlertDialog.Builder(getActivity());
        applyDialogBuilder.setTitle("Save configuration to machine");
        applyDialogBuilder.setMessage("Are you sure you want to change the current pallet design to the selected one?");
        //menu buttons listener
        DialogInterface.OnClickListener applyDialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Send command
                        sendCPDC(lastSelectedItem);
                        break;
                }
            }
        };
        applyDialogBuilder.setPositiveButton("Confirm", applyDialogClickListener);
        applyDialogBuilder.setNeutralButton("Cancel", applyDialogClickListener);

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

    public void refreshObjectList() {
        //Refresh objects
        designs.clear();
        designs.addAll(PalletDesignManager.getFromPreferences(getContext()));
        listAdapter.notifyDataSetChanged();
        //Refresh preview
        loadDesignPreview(designs.get(lastSelectedItem));
    }

    private void loadDesignPreview(Design designToPreview) {
        //Remove old preview
        palletCanvas.removeAllViews();

        //Load and draw boxes of new design
        ArrayList<Box> boxList = designToPreview.getSteps();
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
        //Set position
        boxImage.setX(boxToAdd.coords.x);
        boxImage.setY(boxToAdd.coords.y);
        //Set Angle
        boxImage.setRotation((float) boxToAdd.coords.w);
        //Apply parameters to ImageView
        boxImage.setLayoutParams(params);

        //Add ImageView to layout
        palletCanvas.addView(boxImage);
    }

    /*****************************************************
     *                              --Communications--
     *****************************************************/
    /*
     * CPDC - Change Pallet Design Command
     */
    private void sendCPDC(int newDesign) {
        //generate JSON
        //
    }


    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
