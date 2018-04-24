package com.example.administrator.Palletizer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.administrator.Palletizer.Commands.PGSI;


public class Debug extends Fragment {

    private OnFragmentInteractionListener mFragmentInteraction;
    private final Handler looperHandler = new Handler(Looper.getMainLooper());
    private final int INTERVAL = 200;
    private CheckBox autoUpdate;
    private TextView PlcDataBool;
    private TextView PlcDataInt;
    private Button buttonRead;
    private Button buttonWrite;
    private Button buttonClear;

    public Debug() {
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debug, container, false);

        //GUI
        PlcDataBool = (TextView) view.findViewById(R.id.debug_TextView_data_bool);
        PlcDataInt = (TextView) view.findViewById(R.id.debug_TextView_data_int);
        PlcDataBool.setMovementMethod(new ScrollingMovementMethod());
        PlcDataInt.setMovementMethod(new ScrollingMovementMethod());
        autoUpdate = (CheckBox) view.findViewById(R.id.debug_checkBox_autoupdate);
        buttonWrite = (Button) view.findViewById(R.id.debug_write);
        buttonRead = (Button) view.findViewById(R.id.debug_read);
        buttonClear = (Button) view.findViewById(R.id.debug_clear);

        buttonWrite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Launch write screen
                ((MainActivity)getActivity()).switchToLayout(R.id.opt_debug_write);
            }
        });

        buttonRead.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mFragmentInteraction.onSendCommand(PGSI);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PlcDataBool.setText("PLC data (Boolean):\n\n");
                PlcDataInt.setText("PLC data (Integer):\n\n");
            }
        });

        Runnable autoUpdater = new Runnable() {
            @Override
            public void run() {
                if (autoUpdate.isChecked()) {
                    mFragmentInteraction.onSendCommand(PGSI);
                }
                looperHandler.postDelayed(this, INTERVAL);
            }
        };
        autoUpdater.run();
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

    public void updateDebugData(String PLCdata) {
        PlcDataBool.setText("PLC data (Boolean):\n\n");
        PlcDataInt.setText("PLC data (Integer):\n\n");

        try {
            //build json object
            JSONObject JSONparser = new JSONObject(PLCdata);
            PlcDataBool.append("EmergencyStopOfElectricCabinet: " + printBool(JSONparser.getBoolean("EmergencyStopOfElectricCabinet")) + "\n");
            PlcDataBool.append("AutomaticSelectionSwitch: " + printBool(JSONparser.getBoolean("AutomaticSelectionSwitch")) + "\n");
            PlcDataBool.append("ManualSelectionSwitch: " + printBool(JSONparser.getBoolean("ManualSelectionSwitch")) + "\n");
            PlcDataBool.append("StartSwitch: " + printBool(JSONparser.getBoolean("StartSwitch")) + "\n");
            PlcDataBool.append("StopSwitch: " + printBool(JSONparser.getBoolean("StopSwitch")) + "\n");
            PlcDataBool.append("ResetButton: " + printBool(JSONparser.getBoolean("ResetButton")) + "\n");
            PlcDataBool.append("xAxisForward: " + printBool(JSONparser.getBoolean("xAxisForward")) + "\n");
            PlcDataBool.append("xAxisReversal: " + printBool(JSONparser.getBoolean("xAxisReversal")) + "\n");
            PlcDataBool.append("yAxisForward: " + printBool(JSONparser.getBoolean("yAxisForward")) + "\n");
            PlcDataBool.append("yAxisReversal: " + printBool(JSONparser.getBoolean("yAxisReversal")) + "\n");
            PlcDataBool.append("zAxisForward: " + printBool(JSONparser.getBoolean("zAxisForward")) + "\n");
            PlcDataBool.append("zAxisReversal: " + printBool(JSONparser.getBoolean("zAxisReversal")) + "\n");
            PlcDataBool.append("wAxisForward: " + printBool(JSONparser.getBoolean("wAxisForward")) + "\n");
            PlcDataBool.append("wAxisReversal: " + printBool(JSONparser.getBoolean("wAxisReversal")) + "\n");
            PlcDataBool.append("PalletSelection1: " + printBool(JSONparser.getBoolean("PalletSelection1")) + "\n");
            PlcDataBool.append("FrameEmergencyStop: " + printBool(JSONparser.getBoolean("FrameEmergencyStop")) + "\n");
            PlcDataBool.append("FrameStartButton: " + printBool(JSONparser.getBoolean("FrameStartButton")) + "\n");
            PlcDataBool.append("RegionProtectionGrating: " + printBool(JSONparser.getBoolean("RegionProtectionGrating")) + "\n");
            PlcDataBool.append("PalletStorageBinPhotoelectricDetectionOfA: " + printBool(JSONparser.getBoolean("PalletStorageBinPhotoelectricDetectionOfA")) + "\n");
            PlcDataBool.append("PalletStorageBinPhotoelectricDetectionOfB: " + printBool(JSONparser.getBoolean("PalletStorageBinPhotoelectricDetectionOfB")) + "\n");
            PlcDataBool.append("PalletStorageBinPhotoelectricDetection: " + printBool(JSONparser.getBoolean("PalletStorageBinPhotoelectricDetection")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfxAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfxAxis")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfyAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfyAxis")) + "\n");
            PlcDataBool.append("PalletStorageBinPhotoelectricDetection: " + printBool(JSONparser.getBoolean("PalletStorageBinPhotoelectricDetection")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfxAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfxAxis")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfyAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfyAxis")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfzAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfzAxis")) + "\n");
            PlcDataBool.append("ZeroPositionProximitySwitchDetectionOfwAxis: " + printBool(JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfwAxis")) + "\n");
            PlcDataBool.append("PalletSelection2: " + printBool(JSONparser.getBoolean("PalletSelection2")) + "\n");
            PlcDataBool.append("ZeroPositionAuxiliaryProximitySwitchOfyAxis1: " + printBool(JSONparser.getBoolean("ZeroPositionAuxiliaryProximitySwitchOfyAxis1")) + "\n");
            PlcDataBool.append("AirPressureDetectionSwitch: " + printBool(JSONparser.getBoolean("AirPressureDetectionSwitch")) + "\n");
            PlcDataBool.append("LimitProximitySwitchDetectionOfwAxis: " + printBool(JSONparser.getBoolean("LimitProximitySwitchDetectionOfwAxis")) + "\n");
            PlcDataBool.append("StopProximitySwitchDetectionOfzAxis: " + printBool(JSONparser.getBoolean("StopProximitySwitchDetectionOfzAxis")) + "\n");
            PlcDataBool.append("LimitProximitySwitchDetectionOfzAxis: " + printBool(JSONparser.getBoolean("LimitProximitySwitchDetectionOfzAxis")) + "\n");
            PlcDataBool.append("LeftHandFingerOpenDetection: " + printBool(JSONparser.getBoolean("LeftHandFingerOpenDetection")) + "\n");
            PlcDataBool.append("LeftHandFingerClosedDetection: " + printBool(JSONparser.getBoolean("LeftHandFingerClosedDetection")) + "\n");
            PlcDataBool.append("RightHandFingerOpenDetection: " + printBool(JSONparser.getBoolean("RightHandFingerOpenDetection")) + "\n");
            PlcDataBool.append("RightHandFingerClosedDetection: " + printBool(JSONparser.getBoolean("RightHandFingerClosedDetection")) + "\n");
            PlcDataBool.append("HalfTheMagneticDetectionSwitchOfFinger: " + printBool(JSONparser.getBoolean("HalfTheMagneticDetectionSwitchOfFinger")) + "\n");
            PlcDataBool.append("ClosedTheMagneticDetectionSwitchOfFinger: " + printBool(JSONparser.getBoolean("ClosedTheMagneticDetectionSwitchOfFinger")) + "\n");
            PlcDataBool.append("DetectionOfMechanicalLimitStrokeSwitchOfxAxis1: " + printBool(JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis1")) + "\n");
            PlcDataBool.append("DetectionOfMechanicalLimitStrokeSwitchOfxAxis2: " + printBool(JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis2")) + "\n");
            PlcDataBool.append("DetectionOfMechanicalLimitStrokeSwitchOfyAxis2: " + printBool(JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfyAxis2")) + "\n");
            PlcDataBool.append("DetectionOfMechanicalLimitStrokeSwitchOfzAxis1: " + printBool(JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis1")) + "\n");
            PlcDataBool.append("DetectionOfMechanicalLimitStrokeSwitchOfzAxis2: " + printBool(JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis2")) + "\n");
            PlcDataBool.append("AuxiliaryZeroPositionProximitySwitchOfxAxis: " + printBool(JSONparser.getBoolean("AuxiliaryZeroPositionProximitySwitchOfxAxis")) + "\n");
            PlcDataBool.append("FrequencyConverterAlarmOfxAxis: " + printBool(JSONparser.getBoolean("FrequencyConverterAlarmOfxAxis")) + "\n");
            PlcDataBool.append("FrequencyConverterAlarmOfyAxis: " + printBool(JSONparser.getBoolean("FrequencyConverterAlarmOfyAxis")) + "\n");
            PlcDataBool.append("FrequencyConverterAlarmOfzAxis: " + printBool(JSONparser.getBoolean("FrequencyConverterAlarmOfzAxis")) + "\n");
            PlcDataBool.append("FrequencyConverterAlarmOfwAxis: " + printBool(JSONparser.getBoolean("FrequencyConverterAlarmOfwAxis")) + "\n");
            PlcDataBool.append("PalletSelection3: " + printBool(JSONparser.getBoolean("PalletSelection3")) + "\n");
            PlcDataBool.append("ZeroPositionAuxiliaryProximitySwitchOfyAxis2: " + printBool(JSONparser.getBoolean("ZeroPositionAuxiliaryProximitySwitchOfyAxis2")) + "\n");
            PlcDataBool.append("PawPositionSelection: " + printBool(JSONparser.getBoolean("PawPositionSelection")) + "\n");
            PlcDataBool.append("SomeoneEnteredTheLoadingArea: " + printBool(JSONparser.getBoolean("SomeoneEnteredTheLoadingArea")) + "\n");
            PlcDataBool.append("TheAlarmOfBrickStorehouseDidNotClose1: " + printBool(JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose1")) + "\n");
            PlcDataBool.append("TheAlarmOfBrickStorehouseDidNotClose2: " + printBool(JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose2")) + "\n");
            PlcDataInt.append("PLCSystemState: " + Integer.valueOf(JSONparser.getInt("PLCSystemState")) + "\n");
            PlcDataInt.append("RealTimeValueOfxAxis: " + Integer.valueOf(JSONparser.getInt("RealTimeValueOfxAxis")) + "\n");
            PlcDataInt.append("RealTimeValueOfyAxis: " + Integer.valueOf(JSONparser.getInt("RealTimeValueOfyAxis")) + "\n");
            PlcDataInt.append("RealTimeValueOfzAxis: " + Integer.valueOf(JSONparser.getInt("RealTimeValueOfzAxis")) + "\n");
            PlcDataInt.append("RealTimeValueOfwAxis: " + Integer.valueOf(JSONparser.getInt("RealTimeValueOfwAxis")) + "\n");
            PlcDataInt.append("InnerFingerState: " + Integer.valueOf(JSONparser.getInt("InnerFingerState")) + "\n");
            PlcDataInt.append("OuterClawState: " + Integer.valueOf(JSONparser.getInt("OuterClawState")) + "\n");
            PlcDataInt.append("LocationRollingOverState: " + Integer.valueOf(JSONparser.getInt("LocationRollingOverState")) + "\n");
            PlcDataInt.append("StorageBinOfPalletState: " + Integer.valueOf(JSONparser.getInt("StorageBinOfPalletState")) + "\n");
            PlcDataInt.append("OverTurnTable: " + Integer.valueOf(JSONparser.getInt("OverTurnTable")) + "\n");
            PlcDataInt.append("LimitOptoelectronicOfZAxis: " + Integer.valueOf(JSONparser.getInt("LimitOptoelectronicOfZAxis")) + "\n");
            PlcDataInt.append("PhotoelectricSensorOfPallet1: " + Integer.valueOf(JSONparser.getInt("PhotoelectricSensorOfPallet1")) + "\n");
            PlcDataInt.append("PalletSignalOfA: " + Integer.valueOf(JSONparser.getInt("PalletSignalOfA")) + "\n");
            PlcDataInt.append("OutputChooseA: " + Integer.valueOf(JSONparser.getInt("OutputChooseA")) + "\n");
            PlcDataInt.append("PalletSignalOfB: " + Integer.valueOf(JSONparser.getInt("PalletSignalOfB")) + "\n");
            PlcDataInt.append("OutputChooseB: " + Integer.valueOf(JSONparser.getInt("OutputChooseB")) + "\n");
            PlcDataInt.append("FlipPlatformLevel: " + Integer.valueOf(JSONparser.getInt("FlipPlatformLevel")) + "\n");
            PlcDataInt.append("FlipPlatformVertical: " + Integer.valueOf(JSONparser.getInt("FlipPlatformVertical")) + "\n");
            PlcDataInt.append("PhotoelectricSensorOfPallet2: " + Integer.valueOf(JSONparser.getInt("PhotoelectricSensorOfPallet2")) + "\n");
            PlcDataInt.append("PhotoelectricSensorOfPallet3: " + Integer.valueOf(JSONparser.getInt("PhotoelectricSensorOfPallet3")) + "\n");
            PlcDataInt.append("SupplementaryPalletAB: " + Integer.valueOf(JSONparser.getInt("SupplementaryPalletAB")) + "\n");
            PlcDataInt.append("FingerState: " + Integer.valueOf(JSONparser.getInt("FingerState")) + "\n");
            PlcDataInt.append("EquipmentStopState: " + Integer.valueOf(JSONparser.getInt("EquipmentStopState")) + "\n");
            PlcDataInt.append("OpenThePhotoelectricSensorDetection: " + Integer.valueOf(JSONparser.getInt("OpenThePhotoelectricSensorDetection")) + "\n");
            PlcDataInt.append("CloseThePhotoelectricSensorDetection: " + Integer.valueOf(JSONparser.getInt("CloseThePhotoelectricSensorDetection")) + "\n");
            PlcDataInt.append("ReadyOfA: " + Integer.valueOf(JSONparser.getInt("ReadyOfA")) + "\n");
            PlcDataInt.append("ReadyOfB: " + Integer.valueOf(JSONparser.getInt("ReadyOfB")) + "\n");
            PlcDataInt.append("FingerState: " + Integer.valueOf(JSONparser.getInt("FingerState")) + "\n");
            PlcDataInt.append("DoorRodBackA: " + Integer.valueOf(JSONparser.getInt("DoorRodBackA")) + "\n");
            PlcDataInt.append("DoorRodBackB: " + Integer.valueOf(JSONparser.getInt("DoorRodBackB")) + "\n");
            PlcDataInt.append("SequenceOfTheLastGrab: " + Integer.valueOf(JSONparser.getInt("SequenceOfTheLastGrab")) + "\n");
            PlcDataInt.append("NumberOfTheLastGrab: " + Integer.valueOf(JSONparser.getInt("NumberOfTheLastGrab")) + "\n");
            PlcDataInt.append("TheInstructionsOfTheChangePallet: " + Integer.valueOf(JSONparser.getInt("TheInstructionsOfTheChangePallet")) + "\n");
            PlcDataInt.append("TheTileOf1200cm: " + Integer.valueOf(JSONparser.getInt("TheTileOf1200cm")) + "\n");

        } catch(JSONException exc) {
            Log.d("JSON exception", "Can't parse PGSI CMD");
        }
    }

    String printBool(Boolean mBol) {
        if (mBol)
            return "true";
        else
            return "false";
    }

    public interface OnFragmentInteractionListener {
        void onSendCommand( String command );
    }
}
