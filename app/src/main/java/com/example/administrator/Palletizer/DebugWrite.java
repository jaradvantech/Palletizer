package com.example.administrator.Palletizer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONException;
import org.json.JSONObject;

public class DebugWrite extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private View thisView;
    private Button send;
    private EditText SystemState;
	private EditText  XAxisSetting;
	private EditText  YAxisSetting;
	private EditText  ZAxisSetting;
	private EditText  WAxisSetting;
	private EditText  FingerMovement;
	private EditText  OuterClawMovement;
	private EditText  LocationRollingOver;
	private EditText  TheSpeedOfXAxis;
	private EditText  TheSpeedOfYAxis;
	private EditText  TheSpeedOfZAxis;
	private EditText  TheSpeedOfWAxis;
	private EditText  OverRunAlarm;
	private EditText  HostComputerControlTheSpeedOfXAxis;
	private EditText  AlarmAndStop;
	private EditText  ManualControlOfScreen;
	private EditText  ManualControlTheXAxisOfTheScreen;
	private EditText  ManualControlTheYAxisOfTheScreen;
	private EditText  ManualControlTheZAxisOfTheScreen;
	private EditText  ManualControlTheWAxisOfTheScreen;
	private EditText  ManualControlTheFingerOfTheScreen;
	private EditText  ManualControlTheOuterClawOfTheScreen;
	private EditText  StorageBinFullA;
	private EditText  StorageBinFullB;
	private EditText  TheTimeOfTheOuterClawHalfOpen;
	private EditText  WipeDataOfTheChangeTileAndStorageBinFull;
	private EditText  NearALowSpeed;
	private EditText  TheNumberOfPackages;
	private EditText  TheHighestSpeedOfXAxis;
	private EditText  DecelerationTimeOfXAxis;
	private EditText  DecelerationDistanceOfXAxis;
	private EditText  NoLoadTimeOfXAxis;
	private EditText  NoLoadDistanceOfXAxis;
	private EditText  ManualSpeedOfXAxis;
	private EditText  TheHighestSpeedOfYAxis;
	private EditText  DecelerationTimeOfYAxis;
	private EditText  DecelerationDistanceOfYAxis;
	private EditText  NoLoadTimeOfYAxis;
	private EditText  NoLoadDistanceOfYAxis;
	private EditText  ManualSpeedOfYAxis;
	private EditText  TheHighestSpeedOfZAxis;
	private EditText  DecelerationTimeOfZAxis;
	private EditText  DecelerationDistaceOfZAxis;
	private EditText  ManualSpeedOfZAxis;
	private EditText  TheAccuracyOfXAxis;
	private EditText  TheAccuracyOfYAxis;
	private EditText  TheAccuracyOfZAxis;
	private EditText  TheAccuracyOfWAxis;
    private EditText  CommunicationExchange;


    public DebugWrite() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_debug_write, container, false);

        loadUIEelements();

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mFragmentInteraction.onSendCommand(generatePWDA());
            }
        });
        return thisView;
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


    public String generatePWDA() {

        JSONObject JSONoutput = new JSONObject() ;

        try {
            JSONoutput.put("command_ID", "PWDA");
            if(isNotEmpty(SystemState))
                JSONoutput.put("SystemState", inputToInteger(SystemState));
            if(isNotEmpty(XAxisSetting))
                JSONoutput.put("XAxisSetting", inputToInteger(XAxisSetting));
            if(isNotEmpty(YAxisSetting))
                JSONoutput.put("YAxisSetting", inputToInteger(YAxisSetting));
            if(isNotEmpty(ZAxisSetting))
                JSONoutput.put("ZAxisSetting", inputToInteger(ZAxisSetting));
            if(isNotEmpty(WAxisSetting))
                JSONoutput.put("WAxisSetting", inputToInteger(WAxisSetting));
            if(isNotEmpty(FingerMovement))
                JSONoutput.put("FingerMovement", inputToInteger(FingerMovement));
            if(isNotEmpty(OuterClawMovement))
                JSONoutput.put("OuterClawMovement", inputToInteger(OuterClawMovement));
            if(isNotEmpty(LocationRollingOver))
                JSONoutput.put("LocationRollingOver", inputToInteger(LocationRollingOver));
            if(isNotEmpty(TheSpeedOfXAxis))
                JSONoutput.put("TheSpeedOfXAxis", inputToInteger(TheSpeedOfXAxis));
            if(isNotEmpty(TheSpeedOfYAxis))
                JSONoutput.put("TheSpeedOfYAxis", inputToInteger(TheSpeedOfYAxis));
            if(isNotEmpty(TheSpeedOfZAxis))
                JSONoutput.put("TheSpeedOfZAxis", inputToInteger(TheSpeedOfZAxis));
            if(isNotEmpty(TheSpeedOfWAxis))
                JSONoutput.put("TheSpeedOfWAxis", inputToInteger(TheSpeedOfWAxis));
            if(isNotEmpty(OverRunAlarm))
                JSONoutput.put("OverRunAlarm", inputToInteger(OverRunAlarm));
            if(isNotEmpty(HostComputerControlTheSpeedOfXAxis))
                JSONoutput.put("HostComputerControlTheSpeedOfXAxis", inputToInteger(HostComputerControlTheSpeedOfXAxis));
            if(isNotEmpty(AlarmAndStop))
                JSONoutput.put("AlarmAndStop", inputToInteger(AlarmAndStop));
            if(isNotEmpty(ManualControlOfScreen))
                JSONoutput.put("ManualControlOfScreen", inputToInteger(ManualControlOfScreen));
            if(isNotEmpty(ManualControlTheXAxisOfTheScreen))
                JSONoutput.put("ManualControlTheXAxisOfTheScreen", inputToInteger(ManualControlTheXAxisOfTheScreen));
            if(isNotEmpty(ManualControlTheYAxisOfTheScreen))
                JSONoutput.put("ManualControlTheYAxisOfTheScreen", inputToInteger(ManualControlTheYAxisOfTheScreen));
            if(isNotEmpty(ManualControlTheZAxisOfTheScreen))
                JSONoutput.put("ManualControlTheZAxisOfTheScreen", inputToInteger(ManualControlTheZAxisOfTheScreen));
            if(isNotEmpty(ManualControlTheWAxisOfTheScreen))
                JSONoutput.put("ManualControlTheWAxisOfTheScreen", inputToInteger(ManualControlTheWAxisOfTheScreen));
            if(isNotEmpty(ManualControlTheFingerOfTheScreen))
                JSONoutput.put("ManualControlTheFingerOfTheScreen", inputToInteger(ManualControlTheFingerOfTheScreen));
            if(isNotEmpty(ManualControlTheOuterClawOfTheScreen))
                JSONoutput.put("ManualControlTheOuterClawOfTheScreen", inputToInteger(ManualControlTheOuterClawOfTheScreen));
            if(isNotEmpty(StorageBinFullA))
                JSONoutput.put("StorageBinFullA", inputToInteger(StorageBinFullA));
            if(isNotEmpty(StorageBinFullB))
                JSONoutput.put("StorageBinFullB", inputToInteger(StorageBinFullB));
            if(isNotEmpty(TheTimeOfTheOuterClawHalfOpen))
                JSONoutput.put("TheTimeOfTheOuterClawHalfOpen", inputToInteger(TheTimeOfTheOuterClawHalfOpen));
            if(isNotEmpty(WipeDataOfTheChangeTileAndStorageBinFull))
                JSONoutput.put("WipeDataOfTheChangeTileAndStorageBinFull", inputToInteger(WipeDataOfTheChangeTileAndStorageBinFull));
            if(isNotEmpty(NearALowSpeed))
                JSONoutput.put("NearALowSpeed", inputToInteger(NearALowSpeed));
            if(isNotEmpty(TheNumberOfPackages))
                JSONoutput.put("TheNumberOfPackages", inputToInteger(TheNumberOfPackages));
            if(isNotEmpty(TheHighestSpeedOfXAxis))
                JSONoutput.put("TheHighestSpeedOfXAxis", inputToInteger(TheHighestSpeedOfXAxis));
            if(isNotEmpty(DecelerationTimeOfXAxis))
                JSONoutput.put("DecelerationTimeOfXAxis", inputToInteger(DecelerationTimeOfXAxis));
            if(isNotEmpty(DecelerationDistanceOfXAxis))
                JSONoutput.put("DecelerationDistanceOfXAxis", inputToInteger(DecelerationDistanceOfXAxis));
            if(isNotEmpty(NoLoadTimeOfXAxis))
                JSONoutput.put("NoLoadTimeOfXAxis", inputToInteger(NoLoadTimeOfXAxis));
            if(isNotEmpty(NoLoadDistanceOfXAxis))
                JSONoutput.put("NoLoadDistanceOfXAxis", inputToInteger(NoLoadDistanceOfXAxis));
            if(isNotEmpty(ManualSpeedOfXAxis))
                JSONoutput.put("ManualSpeedOfXAxis", inputToInteger(ManualSpeedOfXAxis));
            if(isNotEmpty(TheHighestSpeedOfYAxis))
                JSONoutput.put("TheHighestSpeedOfYAxis", inputToInteger(TheHighestSpeedOfYAxis));
            if(isNotEmpty(DecelerationTimeOfYAxis))
                JSONoutput.put("DecelerationTimeOfYAxis", inputToInteger(DecelerationTimeOfYAxis));
            if(isNotEmpty(DecelerationDistanceOfYAxis))
                JSONoutput.put("DecelerationDistanceOfYAxis", inputToInteger(DecelerationDistanceOfYAxis));
            if(isNotEmpty(NoLoadTimeOfYAxis))
                JSONoutput.put("NoLoadTimeOfYAxis", inputToInteger(NoLoadTimeOfYAxis));
            if(isNotEmpty(NoLoadDistanceOfYAxis))
                JSONoutput.put("NoLoadDistanceOfYAxis", inputToInteger(NoLoadDistanceOfYAxis));
            if(isNotEmpty(ManualSpeedOfYAxis))
                JSONoutput.put("ManualSpeedOfYAxis", inputToInteger(ManualSpeedOfYAxis));
            if(isNotEmpty(TheHighestSpeedOfZAxis))
                JSONoutput.put("TheHighestSpeedOfZAxis", inputToInteger(TheHighestSpeedOfZAxis));
            if(isNotEmpty(DecelerationTimeOfZAxis))
                JSONoutput.put("DecelerationTimeOfZAxis", inputToInteger(DecelerationTimeOfZAxis));
            if(isNotEmpty(DecelerationDistaceOfZAxis))
                JSONoutput.put("DecelerationDistaceOfZAxis", inputToInteger(DecelerationDistaceOfZAxis));
            if(isNotEmpty(ManualSpeedOfZAxis))
                JSONoutput.put("ManualSpeedOfZAxis", inputToInteger(ManualSpeedOfZAxis));
            if(isNotEmpty(TheAccuracyOfXAxis))
                JSONoutput.put("TheAccuracyOfXAxis", inputToInteger(TheAccuracyOfXAxis));
            if(isNotEmpty(TheAccuracyOfYAxis))
                JSONoutput.put("TheAccuracyOfYAxis", inputToInteger(TheAccuracyOfYAxis));
            if(isNotEmpty(TheAccuracyOfZAxis))
                JSONoutput.put("TheAccuracyOfZAxis", inputToInteger(TheAccuracyOfZAxis));
            if(isNotEmpty(TheAccuracyOfWAxis))
                JSONoutput.put("TheAccuracyOfWAxis", inputToInteger(TheAccuracyOfWAxis));
            if(isNotEmpty(CommunicationExchange))
                JSONoutput.put("CommunicationExchange", inputToInteger(CommunicationExchange));

        } catch(JSONException exc) {
            Log.d("JSON exception", "Error generating PWDA");
        }

        return JSONoutput.toString() + "\n";
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand(String command);
    }

    /*
     *RBS: Read number from input and convert to properly formatted String
     */
    private int inputToInteger(EditText mUserInput) {
        //Get int from input
        String inputText = mUserInput.getText().toString();
        return Integer.parseInt(inputText);
    }

    public boolean isNotEmpty(EditText mUserInput) {
        return !mUserInput.getText().toString().equals("");
    }


    private void loadUIEelements() {
         SystemState = (EditText) thisView.findViewById(R.id.SystemState);
         XAxisSetting = (EditText) thisView.findViewById(R.id.XAxisSetting);
         YAxisSetting = (EditText) thisView.findViewById(R.id.YAxisSetting);
         ZAxisSetting = (EditText) thisView.findViewById(R.id.ZAxisSetting);
         WAxisSetting = (EditText) thisView.findViewById(R.id.WAxisSetting);
         FingerMovement = (EditText) thisView.findViewById(R.id.FingerMovement);
         OuterClawMovement = (EditText) thisView.findViewById(R.id.OuterClawMovement);
         LocationRollingOver = (EditText) thisView.findViewById(R.id.LocationRollingOver);
         TheSpeedOfXAxis = (EditText) thisView.findViewById(R.id.TheSpeedOfXAxis);
         TheSpeedOfYAxis = (EditText) thisView.findViewById(R.id.TheSpeedOfYAxis);
         TheSpeedOfZAxis = (EditText) thisView.findViewById(R.id.TheSpeedOfZAxis);
         TheSpeedOfWAxis = (EditText) thisView.findViewById(R.id.TheSpeedOfWAxis);
         OverRunAlarm = (EditText) thisView.findViewById(R.id.OverRunAlarm);
         HostComputerControlTheSpeedOfXAxis = (EditText) thisView.findViewById(R.id.HostComputerControlTheSpeedOfXAxis);
         AlarmAndStop = (EditText) thisView.findViewById(R.id.AlarmAndStop);
         ManualControlOfScreen = (EditText) thisView.findViewById(R.id.ManualControlOfScreen);
         ManualControlTheXAxisOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheXAxisOfTheScreen);
         ManualControlTheYAxisOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheYAxisOfTheScreen);
         ManualControlTheZAxisOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheZAxisOfTheScreen);
         ManualControlTheWAxisOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheWAxisOfTheScreen);
         ManualControlTheFingerOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheFingerOfTheScreen);
         ManualControlTheOuterClawOfTheScreen = (EditText) thisView.findViewById(R.id.ManualControlTheOuterClawOfTheScreen);
         StorageBinFullA = (EditText) thisView.findViewById(R.id.StorageBinFullA);
         StorageBinFullB = (EditText) thisView.findViewById(R.id.StorageBinFullB);
         TheTimeOfTheOuterClawHalfOpen = (EditText) thisView.findViewById(R.id.TheTimeOfTheOuterClawHalfOpen);
         WipeDataOfTheChangeTileAndStorageBinFull = (EditText) thisView.findViewById(R.id.WipeDataOfTheChangeTileAndStorageBinFull);
         NearALowSpeed = (EditText) thisView.findViewById(R.id.NearALowSpeed);
         TheNumberOfPackages = (EditText) thisView.findViewById(R.id.TheNumberOfPackages);
         TheHighestSpeedOfXAxis = (EditText) thisView.findViewById(R.id.TheHighestSpeedOfXAxis);
         DecelerationTimeOfXAxis = (EditText) thisView.findViewById(R.id.DecelerationTimeOfXAxis);
         DecelerationDistanceOfXAxis = (EditText) thisView.findViewById(R.id.DecelerationDistanceOfXAxis);
         NoLoadTimeOfXAxis = (EditText) thisView.findViewById(R.id.NoLoadTimeOfXAxis);
         NoLoadDistanceOfXAxis = (EditText) thisView.findViewById(R.id.NoLoadDistanceOfXAxis);
         ManualSpeedOfXAxis = (EditText) thisView.findViewById(R.id.ManualSpeedOfXAxis);
         TheHighestSpeedOfYAxis = (EditText) thisView.findViewById(R.id.TheHighestSpeedOfYAxis);
         DecelerationTimeOfYAxis = (EditText) thisView.findViewById(R.id.DecelerationTimeOfYAxis);
         DecelerationDistanceOfYAxis = (EditText) thisView.findViewById(R.id.DecelerationDistanceOfYAxis);
         NoLoadTimeOfYAxis = (EditText) thisView.findViewById(R.id.NoLoadTimeOfYAxis);
         NoLoadDistanceOfYAxis = (EditText) thisView.findViewById(R.id.NoLoadDistanceOfYAxis);
         ManualSpeedOfYAxis = (EditText) thisView.findViewById(R.id.ManualSpeedOfYAxis);
         TheHighestSpeedOfZAxis = (EditText) thisView.findViewById(R.id.TheHighestSpeedOfZAxis);
         DecelerationTimeOfZAxis = (EditText) thisView.findViewById(R.id.DecelerationTimeOfZAxis);
         DecelerationDistaceOfZAxis = (EditText) thisView.findViewById(R.id.DecelerationDistaceOfZAxis);
         ManualSpeedOfZAxis = (EditText) thisView.findViewById(R.id.ManualSpeedOfZAxis);
         TheAccuracyOfXAxis = (EditText) thisView.findViewById(R.id.TheAccuracyOfXAxis);
         TheAccuracyOfYAxis = (EditText) thisView.findViewById(R.id.TheAccuracyOfYAxis);
         TheAccuracyOfZAxis = (EditText) thisView.findViewById(R.id.TheAccuracyOfZAxis);
         TheAccuracyOfWAxis = (EditText) thisView.findViewById(R.id.TheAccuracyOfWAxis);
         CommunicationExchange = (EditText) thisView.findViewById(R.id.CommunicationExchange);
         send = (Button) thisView.findViewById(R.id.debug_button_send);
    }
}
