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

public class Debug_Write extends Fragment {

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


    public Debug_Write() {
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
            JSONoutput.put("SystemState", inputToInteger(SystemState));
            JSONoutput.put("XAxisSetting", inputToInteger(XAxisSetting));
            JSONoutput.put("YAxisSetting", inputToInteger(YAxisSetting));
            JSONoutput.put("ZAxisSetting", inputToInteger(ZAxisSetting));
            JSONoutput.put("WAxisSetting", inputToInteger(WAxisSetting));
            JSONoutput.put("FingerMovement", inputToInteger(FingerMovement));
            JSONoutput.put("OuterClawMovement", inputToInteger(OuterClawMovement));
            JSONoutput.put("LocationRollingOver", inputToInteger(LocationRollingOver));
            JSONoutput.put("TheSpeedOfXAxis", inputToInteger(TheSpeedOfXAxis));
            JSONoutput.put("TheSpeedOfYAxis", inputToInteger(TheSpeedOfYAxis));
            JSONoutput.put("TheSpeedOfZAxis", inputToInteger(TheSpeedOfZAxis));
            JSONoutput.put("TheSpeedOfWAxis", inputToInteger(TheSpeedOfWAxis));
            JSONoutput.put("OverRunAlarm", inputToInteger(OverRunAlarm));
            JSONoutput.put("HostComputerControlTheSpeedOfXAxis", inputToInteger(HostComputerControlTheSpeedOfXAxis));
            JSONoutput.put("AlarmAndStop", inputToInteger(AlarmAndStop));
            JSONoutput.put("ManualControlOfScreen", inputToInteger(ManualControlOfScreen));
            JSONoutput.put("ManualControlTheXAxisOfTheScreen", inputToInteger(ManualControlTheXAxisOfTheScreen));
            JSONoutput.put("ManualControlTheYAxisOfTheScreen", inputToInteger(ManualControlTheYAxisOfTheScreen));
            JSONoutput.put("ManualControlTheZAxisOfTheScreen", inputToInteger(ManualControlTheZAxisOfTheScreen));
            JSONoutput.put("ManualControlTheWAxisOfTheScreen", inputToInteger(ManualControlTheWAxisOfTheScreen));
            JSONoutput.put("ManualControlTheFingerOfTheScreen", inputToInteger(ManualControlTheFingerOfTheScreen));
            JSONoutput.put("ManualControlTheOuterClawOfTheScreen", inputToInteger(ManualControlTheOuterClawOfTheScreen));
            JSONoutput.put("StorageBinFullA", inputToInteger(StorageBinFullA));
            JSONoutput.put("StorageBinFullB", inputToInteger(StorageBinFullB));
            JSONoutput.put("TheTimeOfTheOuterClawHalfOpen", inputToInteger(TheTimeOfTheOuterClawHalfOpen));
            JSONoutput.put("WipeDataOfTheChangeTileAndStorageBinFull", inputToInteger(WipeDataOfTheChangeTileAndStorageBinFull));
            JSONoutput.put("NearALowSpeed", inputToInteger(NearALowSpeed));
            JSONoutput.put("TheNumberOfPackages", inputToInteger(TheNumberOfPackages));
            JSONoutput.put("TheHighestSpeedOfXAxis", inputToInteger(TheHighestSpeedOfXAxis));
            JSONoutput.put("DecelerationTimeOfXAxis", inputToInteger(DecelerationTimeOfXAxis));
            JSONoutput.put("DecelerationDistanceOfXAxis", inputToInteger(DecelerationDistanceOfXAxis));
            JSONoutput.put("NoLoadTimeOfXAxis", inputToInteger(NoLoadTimeOfXAxis));
            JSONoutput.put("NoLoadDistanceOfXAxis", inputToInteger(NoLoadDistanceOfXAxis));
            JSONoutput.put("ManualSpeedOfXAxis", inputToInteger(ManualSpeedOfXAxis));
            JSONoutput.put("TheHighestSpeedOfYAxis", inputToInteger(TheHighestSpeedOfYAxis));
            JSONoutput.put("DecelerationTimeOfYAxis", inputToInteger(DecelerationTimeOfYAxis));
            JSONoutput.put("DecelerationDistanceOfYAxis", inputToInteger(DecelerationDistanceOfYAxis));
            JSONoutput.put("NoLoadTimeOfYAxis", inputToInteger(NoLoadTimeOfYAxis));
            JSONoutput.put("NoLoadDistanceOfYAxis", inputToInteger(NoLoadDistanceOfYAxis));
            JSONoutput.put("ManualSpeedOfYAxis", inputToInteger(ManualSpeedOfYAxis));
            JSONoutput.put("TheHighestSpeedOfZAxis", inputToInteger(TheHighestSpeedOfZAxis));
            JSONoutput.put("DecelerationTimeOfZAxis", inputToInteger(DecelerationTimeOfZAxis));
            JSONoutput.put("DecelerationDistaceOfZAxis", inputToInteger(DecelerationDistaceOfZAxis));
            JSONoutput.put("ManualSpeedOfZAxis", inputToInteger(ManualSpeedOfZAxis));
            JSONoutput.put("TheAccuracyOfXAxis", inputToInteger(TheAccuracyOfXAxis));
            JSONoutput.put("TheAccuracyOfYAxis", inputToInteger(TheAccuracyOfYAxis));
            JSONoutput.put("TheAccuracyOfZAxis", inputToInteger(TheAccuracyOfZAxis));
            JSONoutput.put("TheAccuracyOfWAxis", inputToInteger(TheAccuracyOfWAxis));
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
    int inputToInteger(EditText mUserInput) {
        //Get int from input
        String inputText = mUserInput.getText().toString();
        //default is zero
        if(TextUtils.isEmpty(inputText))
            inputText = "0";
        return Integer.parseInt(inputText);
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
