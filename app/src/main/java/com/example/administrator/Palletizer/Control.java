package com.example.administrator.Palletizer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class Control extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private ArrayList<Design> designs;
    private DesignListAdapter listAdapter;
    private ListView designListView;
    private View view;
    private RelativeLayout palletCanvas;
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


        /*Listview **************************************/
        designs = DesignManager.getFromPreferences(getContext());
        designListView = (ListView) view.findViewById(R.id.control_ListView_designs);
        listAdapter = new DesignListAdapter(getContext(), designs);
        designListView.setAdapter(listAdapter);

        /*****************************************************
         *                              --User interaction--
         *****************************************************/
        //Select item from list
        designListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    destroyPreviousPreview();
                    Design selectedObject = designs.get(position);
                    loadDesignPreview(selectedObject);
            }
        });

        designListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO dialog with three options: EDIT, DELETE and dismiss
                lastSelectedItem = position;
                /*
                AlertDialog dialog = builder.create();
                dialog.setIcon(R.mipmap.warning);
                dialog.show();
                */

                designs.remove(lastSelectedItem);
                DesignManager.saveToPreferences(designs, getContext());
                listAdapter.notifyDataSetChanged();

                return true;
            }
        });


        /*Do stuff*/

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
        Log.d("refreshing", "objectlist");
        designs = DesignManager.getFromPreferences(getContext());
        Log.d("objectlist", "size: " + designs.size());
        listAdapter.notifyDataSetChanged();
    }

    private void loadDesignPreview(Design designToPreview) {
        //Remove old preview
        destroyPreviousPreview();

        //Load and draw boxes of new design
        ArrayList<Box> boxList = designToPreview.getSteps();
        int totalSteps = boxList.size();

        for(int i=0; i<totalSteps; i++) {
            drawBox(boxList.get(i));
        }
    }

    private void destroyPreviousPreview() {
        palletCanvas.removeAllViews();
    }

    private void drawBox(Box boxToAdd) {
        //Dynamically create ImageView
        ImageView boxImage = new ImageView(getActivity());
        boxImage.setImageResource(boxToAdd.textureResource);

        //Set size
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boxToAdd.height*CMTOPX, boxToAdd.width*CMTOPX);
        //Set position,
        params.setMargins(boxToAdd.coords.x, boxToAdd.coords.y,0,0);
        //Apply parameters to ImageView
        boxImage.setLayoutParams(params);

        //Add ImageView to layout
        palletCanvas.addView(boxImage);
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
