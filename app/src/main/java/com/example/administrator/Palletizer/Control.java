package com.example.administrator.Palletizer;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import java.util.Random;


public class Control extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private ArrayList<Design> designs;
    private ArrayList<ImageView> screenObjects;
    private DesignListAdapter listAdapter;
    private ListView designListView;
    private View view;
    private RelativeLayout palletCanvas;

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
        screenObjects = new ArrayList<>();


        /*Listview **************************************/
        designs = new ArrayList<>();
        designs.add(new Design(0, "Design 1"));
        designs.add(new Design(0, "Design 2"));
        designs.add(new Design(1, "Design 3"));
        designs.add(new Design(1, "Design 4"));
        designs.add(new Design(1, "Design 5"));
        designListView = (ListView) view.findViewById(R.id.control_ListView_designs);
        listAdapter = new DesignListAdapter(getActivity(), designs);
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

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentInteraction = null;
    }

    private void loadDesignPreview(Design designToPreview) {
        ArrayList<Coord> designSteps = designToPreview.getSteps();
        //

        //int totalSteps = designSteps.size();
        Box aBox = new Box(new Coord(10, 10, 0, 0), 100, 400, R.mipmap.box);
        addOnTheFly(aBox);
    }

    private void destroyPreviousPreview() {
        palletCanvas.removeAllViews();
    }

    private void addOnTheFly(Box boxToAdd){
        //Dynamically create ImageView
        ImageView boxImage = new ImageView(getActivity());
        boxImage.setImageResource(boxToAdd.getTextureResource());

        //Set size
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boxToAdd.getHeight(), boxToAdd.getWidth());
        //Set position
        params.setMargins(boxToAdd.getCoords().getX(), boxToAdd.getCoords().getY() ,0,0);
        //Apply parameters to ImageView
        boxImage.setLayoutParams(params);

        //Add ImageView to layout
        palletCanvas.addView(boxImage);
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
