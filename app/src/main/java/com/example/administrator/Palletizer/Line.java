package com.example.administrator.Palletizer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Random;


public class Line extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;

    private ProgressBar pallet1ProgressBar;
    private ProgressBar pallet2ProgressBar;
    private ImageView guide;
    private ImageView clamp;

    public Line() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_line, container, false);

        pallet1ProgressBar = (ProgressBar) view.findViewById(R.id.line_progressBar_pallet1);
        pallet2ProgressBar = (ProgressBar) view.findViewById(R.id.line_progressBar_pallet2);
        pallet1ProgressBar.setProgress(50);
        pallet2ProgressBar.setProgress(50);

        guide = (ImageView) view.findViewById(R.id.line_imageview_guide);
        clamp = (ImageView) view.findViewById(R.id.line_imageview_clamp);

        /*DEBUG ONLY*/

        final Random r = new Random();
        Button test = view.findViewById(R.id.button);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pallet1ProgressBar.setProgress(r.nextInt(100));
                pallet2ProgressBar.setProgress(r.nextInt(100));
                moveManipulatorTo(r.nextInt(800)+50, r.nextInt(300)+50);
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

    private void moveManipulatorTo(int x, int y) {

        //Animations for guide
        ObjectAnimator guideY = ObjectAnimator.ofFloat(guide, "x", x);

        //Animations for clamp
        ObjectAnimator clampX = ObjectAnimator.ofFloat(clamp, "x", x);
        ObjectAnimator clampY = ObjectAnimator.ofFloat(clamp, "y", y);

        AnimatorSet animSetline = new AnimatorSet();
        animSetline.playTogether(guideY, clampX, clampY);

        animSetline.setDuration(500);
        animSetline.start();
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
