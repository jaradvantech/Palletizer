package com.example.administrator.Palletizer;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class Palletizer {

    //Generic information
    public static final int TOTAL_ALARMS = 17;
    public static final int EMERGENCY_ALARMS = 2;
    public static final int LIMIT_ALARMS = 9;
    public static final int OTHER_ALARMS = 6;

    /*****************************************************
     *                            --Read from PC--
     *****************************************************/
    public static boolean EmergencyStopOfElectricCabinet;                 //Alarm bit
    public static boolean AutomaticSelectionSwitch;
    public static boolean ManualSelectionSwitch;
    public static boolean StartSwitch;
    public static boolean StopSwitch;
    public static boolean ResetButton;
    public static boolean AlarmSilencing;
    public static boolean xAxisForward;
    public static boolean xAxisReversal;
    public static boolean yAxisForward;
    public static boolean yAxisReversal;
    public static boolean zAxisForward;
    public static boolean zAxisReversal;
    public static boolean wAxisForward;
    public static boolean wAxisReversal;
    public static boolean PalletSelection1;
    public static boolean FrameEmergencyStop;                               //Alarm bit
    public static boolean FrameStartButton;
    public static boolean RegionProtectionGrating;
    public static boolean PalletStorageBinPhotoelectricDetectionOfA;
    public static boolean PalletStorageBinPhotoelectricDetectionOfB;
    public static boolean PalletStorageBinPhotoelectricDetection;
    public static boolean ZeroPositionProximitySwitchDetectionOfxAxis;
    public static boolean ZeroPositionProximitySwitchDetectionOfyAxis;
    public static boolean ZeroPositionProximitySwitchDetectionOfzAxis;
    public static boolean ZeroPositionProximitySwitchDetectionOfwAxis;
    public static boolean PalletSelection2;
    public static boolean ZeroPositionAuxiliaryProximitySwitchOfyAxis1;
    public static boolean AirPressureDetectionSwitch;
    public static boolean LimitProximitySwitchDetectionOfwAxis;                   //Alarm bit
    public static boolean StopProximitySwitchDetectionOfzAxis;
    public static boolean LimitProximitySwitchDetectionOfzAxis;                   //Alarm bit
    public static boolean LeftHandFingerOpenDetection;
    public static boolean LeftHandFingerClosedDetection;
    public static boolean RightHandFingerOpenDetection;
    public static boolean RightHandFingerClosedDetection;
    public static boolean HalfTheMagneticDetectionSwitchOfFinger;
    public static boolean ClosedTheMagneticDetectionSwitchOfFinger;
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfxAxis1;                   //Alarm bit
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfxAxis2;                   //Alarm bit
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfyAxis1;                   //Alarm bit
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfyAxis2;                   //Alarm bit
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfzAxis1;                   //Alarm bit
    public static boolean DetectionOfMechanicalLimitStrokeSwitchOfzAxis2;                   //Alarm bit
    public static boolean AuxiliaryZeroPositionProximitySwitchOfxAxis;
    public static boolean FrequencyConverterAlarmOfxAxis;                   //Alarm bit
    public static boolean FrequencyConverterAlarmOfyAxis;                   //Alarm bit
    public static boolean FrequencyConverterAlarmOfzAxis;                   //Alarm bit
    public static boolean FrequencyConverterAlarmOfwAxis;                   //Alarm bit
    public static boolean PalletSelection3;
    public static boolean ZeroPositionAuxiliaryProximitySwitchOfyAxis2;
    public static boolean PawPositionSelection;
    public static boolean SomeoneEnteredTheLoadingArea;
    public static boolean TheAlarmOfBrickStorehouseDidNotClose1;        //Alarm bit
    public static boolean TheAlarmOfBrickStorehouseDidNotClose2;        //Alarm bit

    public static int PLCSystemState; 						//1.Run 2.Initialize 3.Manual 4.Emergency Stop
    public static int RealTimeValueOfxAxis;					//
    public static int RealTimeValueOfyAxis;
    public static int RealTimeValueOfzAxis;
    public static int RealTimeValueOfwAxis;
    public static int InnerFingerState;							//Clip state 1.Done 2.Wait
    public static int OuterClawState;							//Outer ClawState 1.Done 2.Wait
    public static int LocationRollingOverState;				//Location Rolling-Over 1.Done 2.Action
    public static int StorageBinOfPalletState;				//1.Yes 2.No
    public static int OverTurnTable;							//1.Yes 2.No
    public static int LimitOptoelectronicOfZAxis;				//1.Yes 2.No                               //Alarm bit
    public static int PhotoelectricSensorOfPallet1;			//1.Yes 2.No
    public static int PalletSignalOfA;						//1.Yes 2.No
    public static int OutputChooseA;							//1.Yes 2.No
    public static int PalletSignalOfB;						//1.Yes 2.No
    public static int OutputChooseB;							//1.Yes 2.No
    public static int FlipPlatformLevel;						//1.Yes 2.No
    public static int FlipPlatformVertical;					//1.Yes 2.No
    public static int PhotoelectricSensorOfPallet2;			//Photoelectric sensor of Pallet 2
    public static int PhotoelectricSensorOfPallet3;			//Photoelectric sensor of Pallet 3
    public static int SupplementaryPalletAB;					//1.Yes 2.No
    public static int FingerState;							//FingerOpening 1.All 2.No All
    public static int EquipmentStopState;						//1.Yes 2.No
    public static int OpenThePhotoelectricSensorDetection;	//1.All 2.?  3.All and No All 4.All Off
    public static int CloseThePhotoelectricSensorDetection;	//1.All 2.?  3.All and No All 4.All Off
    public static int ReadyOfA;								//Reset Completion Of A Output
    public static int ReadyOfB;								//Reset Completion Of B Output
    public static int DoorRodBackA;							//A Baffle Back
    public static int DoorRodBackB;							//B Baffle Back
    public static int SequenceOfTheLastGrab;
    public static int NumberOfTheLastGrab;
    public static int TheInstructionsOfTheChangePallet;		//Replacement condition, rear line emptying 1. change
    public static int TheTileOf1200cm;						//1. 1.2M 0.Other


    /*****************************************************
     *                                   --PC Write--
     *****************************************************/
    public static int SystemState; //1:Run 2:Wait

    public static int XAxisSetting;//X axis
    public static int YAxisSetting;//X axis
    public static int ZAxisSetting;//X axis
    public static int WAxisSetting;//X axis

    public static int FingerMovement; 	//Clip the tile 1.Open 2.Half open 3.Closed
    public static int OuterClawMovement;	//OuterClaw 1.Open 2.Half open 3.Closed
    public static int LocationRollingOver;//Location Rolling-Over 1.Flat 2.Rolling-over

    public static int TheSpeedOfXAxis;
    public static int TheSpeedOfYAxis;
    public static int TheSpeedOfZAxis;
    public static int TheSpeedOfWAxis;

    public static int OverRunAlarm;
    public static int HostComputerControlTheSpeedOfXAxis;
    public static int AlarmAndStop;		//Stop position of 18 1.Suspend 2.Start

    public static int ManualControlOfScreen;						//0.Button control 1.Screen control
    public static int ManualControlTheXAxisOfTheScreen;			//0.Stop 1.Forward 2.Back
    public static int ManualControlTheYAxisOfTheScreen;			//0.Stop 1.Left	2.Right
    public static int ManualControlTheZAxisOfTheScreen;			//0.Stop 1.Up 2.Down
    public static int ManualControlTheWAxisOfTheScreen;			//0.Stop 1.Clockwise 2.Anti-clockwisw
    public static int ManualControlTheFingerOfTheScreen;			//1.Open 2.Half 3.Closed
    public static int ManualControlTheOuterClawOfTheScreen;		//1.Open 2.Half 3.Closed

    public static int StorageBinFullA;							//1.Turn OFF 2. Torn ON
    public static int StorageBinFullB;							//1.Turn OFF 2. Torn ON

    public static int TheTimeOfTheOuterClawHalfOpen;
    public static int WipeDataOfTheChangeTileAndStorageBinFull;	//Clear the last grab
    public static int NearALowSpeed;								//1.Near a low speed 0. Go through the pulse value
    public static int TheNumberOfPackages;						//Exchange data bits

    public static int TheHighestSpeedOfXAxis;
    public static int DecelerationTimeOfXAxis;
    public static int DecelerationDistanceOfXAxis;
    public static int NoLoadTimeOfXAxis;
    public static int NoLoadDistanceOfXAxis;
    public static int ManualSpeedOfXAxis;

    public static int TheHighestSpeedOfYAxis;
    public static int DecelerationTimeOfYAxis;
    public static int DecelerationDistanceOfYAxis;
    public static int NoLoadTimeOfYAxis;
    public static int NoLoadDistanceOfYAxis;
    public static int ManualSpeedOfYAxis;

    public static int TheHighestSpeedOfZAxis;
    public static int DecelerationTimeOfZAxis;
    public static int DecelerationDistaceOfZAxis;
    public static int ManualSpeedOfZAxis;

    public static int TheAccuracyOfXAxis;
    public static int TheAccuracyOfYAxis;
    public static int TheAccuracyOfZAxis;
    public static int TheAccuracyOfWAxis;

    public static int CommunicationExchange;

    public static void parsePGSI(String PLCdata) {
        try {
            JSONObject JSONparser = new JSONObject(PLCdata);
            EmergencyStopOfElectricCabinet = JSONparser.getBoolean("EmergencyStopOfElectricCabinet");
            AutomaticSelectionSwitch = JSONparser.getBoolean("AutomaticSelectionSwitch");
            ManualSelectionSwitch = JSONparser.getBoolean("ManualSelectionSwitch");
            StartSwitch = JSONparser.getBoolean("StartSwitch");
            StopSwitch = JSONparser.getBoolean("StopSwitch");
            ResetButton = JSONparser.getBoolean("ResetButton");
            AlarmSilencing = JSONparser.getBoolean("AlarmSilencing");
            xAxisForward = JSONparser.getBoolean("xAxisForward");
            xAxisReversal = JSONparser.getBoolean("xAxisReversal");
            yAxisForward = JSONparser.getBoolean("yAxisForward");
            yAxisReversal = JSONparser.getBoolean("yAxisReversal");
            zAxisForward = JSONparser.getBoolean("zAxisForward");
            zAxisReversal = JSONparser.getBoolean("zAxisReversal");
            wAxisForward = JSONparser.getBoolean("wAxisForward");
            wAxisReversal = JSONparser.getBoolean("wAxisReversal");
            PalletSelection1 = JSONparser.getBoolean("PalletSelection1");
            FrameEmergencyStop = JSONparser.getBoolean("FrameEmergencyStop");
            FrameStartButton = JSONparser.getBoolean("FrameStartButton");
            RegionProtectionGrating = JSONparser.getBoolean("RegionProtectionGrating");
            PalletStorageBinPhotoelectricDetectionOfA = JSONparser.getBoolean("PalletStorageBinPhotoelectricDetectionOfA");
            PalletStorageBinPhotoelectricDetectionOfB = JSONparser.getBoolean("PalletStorageBinPhotoelectricDetectionOfB");
            PalletStorageBinPhotoelectricDetection = JSONparser.getBoolean("PalletStorageBinPhotoelectricDetection");
            ZeroPositionProximitySwitchDetectionOfxAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfxAxis");
            ZeroPositionProximitySwitchDetectionOfyAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfyAxis");
            PalletStorageBinPhotoelectricDetection = JSONparser.getBoolean("PalletStorageBinPhotoelectricDetection");
            ZeroPositionProximitySwitchDetectionOfxAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfxAxis");
            ZeroPositionProximitySwitchDetectionOfyAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfyAxis");
            ZeroPositionProximitySwitchDetectionOfzAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfzAxis");
            ZeroPositionProximitySwitchDetectionOfwAxis = JSONparser.getBoolean("ZeroPositionProximitySwitchDetectionOfwAxis");
            PalletSelection2 = JSONparser.getBoolean("PalletSelection2");
            ZeroPositionAuxiliaryProximitySwitchOfyAxis1 = JSONparser.getBoolean("ZeroPositionAuxiliaryProximitySwitchOfyAxis1");
            AirPressureDetectionSwitch = JSONparser.getBoolean("AirPressureDetectionSwitch");
            LimitProximitySwitchDetectionOfwAxis = JSONparser.getBoolean("LimitProximitySwitchDetectionOfwAxis");
            StopProximitySwitchDetectionOfzAxis = JSONparser.getBoolean("StopProximitySwitchDetectionOfzAxis");
            LimitProximitySwitchDetectionOfzAxis = JSONparser.getBoolean("LimitProximitySwitchDetectionOfzAxis");
            LeftHandFingerOpenDetection = JSONparser.getBoolean("LeftHandFingerOpenDetection");
            LeftHandFingerClosedDetection = JSONparser.getBoolean("LeftHandFingerClosedDetection");
            RightHandFingerOpenDetection = JSONparser.getBoolean("RightHandFingerOpenDetection");
            RightHandFingerClosedDetection = JSONparser.getBoolean("RightHandFingerClosedDetection");
            HalfTheMagneticDetectionSwitchOfFinger = JSONparser.getBoolean("HalfTheMagneticDetectionSwitchOfFinger");
            ClosedTheMagneticDetectionSwitchOfFinger = JSONparser.getBoolean("ClosedTheMagneticDetectionSwitchOfFinger");
            DetectionOfMechanicalLimitStrokeSwitchOfxAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfxAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis2");
            DetectionOfMechanicalLimitStrokeSwitchOfyAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfyAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfyAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfyAxis2");
            DetectionOfMechanicalLimitStrokeSwitchOfzAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfzAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis2");
            AuxiliaryZeroPositionProximitySwitchOfxAxis = JSONparser.getBoolean("AuxiliaryZeroPositionProximitySwitchOfxAxis");
            FrequencyConverterAlarmOfxAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfxAxis");
            FrequencyConverterAlarmOfyAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfyAxis");
            FrequencyConverterAlarmOfzAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfzAxis");
            FrequencyConverterAlarmOfwAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfwAxis");
            PalletSelection3 = JSONparser.getBoolean("PalletSelection3");
            ZeroPositionAuxiliaryProximitySwitchOfyAxis2 = JSONparser.getBoolean("ZeroPositionAuxiliaryProximitySwitchOfyAxis2");
            PawPositionSelection = JSONparser.getBoolean("PawPositionSelection");
            SomeoneEnteredTheLoadingArea = JSONparser.getBoolean("SomeoneEnteredTheLoadingArea");
            TheAlarmOfBrickStorehouseDidNotClose1 = JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose1");
            TheAlarmOfBrickStorehouseDidNotClose2 = JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose2");
            PLCSystemState = JSONparser.getInt("PLCSystemState");
            RealTimeValueOfxAxis = JSONparser.getInt("RealTimeValueOfxAxis");
            RealTimeValueOfyAxis = JSONparser.getInt("RealTimeValueOfyAxis");
            RealTimeValueOfzAxis = JSONparser.getInt("RealTimeValueOfzAxis");
            RealTimeValueOfwAxis = JSONparser.getInt("RealTimeValueOfwAxis");
            InnerFingerState = JSONparser.getInt("InnerFingerState");
            OuterClawState = JSONparser.getInt("OuterClawState");
            LocationRollingOverState = JSONparser.getInt("LocationRollingOverState");
            StorageBinOfPalletState = JSONparser.getInt("StorageBinOfPalletState");
            OverTurnTable = JSONparser.getInt("OverTurnTable");
            LimitOptoelectronicOfZAxis = JSONparser.getInt("LimitOptoelectronicOfZAxis");
            PhotoelectricSensorOfPallet1 = JSONparser.getInt("PhotoelectricSensorOfPallet1");
            PalletSignalOfA = JSONparser.getInt("PalletSignalOfA");
            OutputChooseA = JSONparser.getInt("OutputChooseA");
            PalletSignalOfB = JSONparser.getInt("PalletSignalOfB");
            OutputChooseB = JSONparser.getInt("OutputChooseB");
            FlipPlatformLevel = JSONparser.getInt("FlipPlatformLevel");
            FlipPlatformVertical = JSONparser.getInt("FlipPlatformVertical");
            PhotoelectricSensorOfPallet2 = JSONparser.getInt("PhotoelectricSensorOfPallet2");
            PhotoelectricSensorOfPallet3 = JSONparser.getInt("PhotoelectricSensorOfPallet3");
            SupplementaryPalletAB = JSONparser.getInt("SupplementaryPalletAB");
            FingerState = JSONparser.getInt("FingerState");
            EquipmentStopState = JSONparser.getInt("EquipmentStopState");
            OpenThePhotoelectricSensorDetection = JSONparser.getInt("OpenThePhotoelectricSensorDetection");
            CloseThePhotoelectricSensorDetection = JSONparser.getInt("CloseThePhotoelectricSensorDetection");
            ReadyOfA = JSONparser.getInt("ReadyOfA");
            ReadyOfB = JSONparser.getInt("ReadyOfB");
            FingerState = JSONparser.getInt("FingerState");
            DoorRodBackA = JSONparser.getInt("DoorRodBackA");
            DoorRodBackB = JSONparser.getInt("DoorRodBackB");
            SequenceOfTheLastGrab = JSONparser.getInt("SequenceOfTheLastGrab");
            NumberOfTheLastGrab = JSONparser.getInt("NumberOfTheLastGrab");
            TheInstructionsOfTheChangePallet = JSONparser.getInt("TheInstructionsOfTheChangePallet");
            TheTileOf1200cm = JSONparser.getInt("TheTileOf1200cm");

        } catch(JSONException exc) {
            Log.d("JSON exception", "Can't parse PGSI CMD");
        }
    }

    public static void parseCHAL(String PLCdata) {
        try {
            JSONObject JSONparser = new JSONObject(PLCdata);

            /*Emergency*/
            EmergencyStopOfElectricCabinet = JSONparser.getBoolean("EmergencyStopOfElectricCabinet");
            FrameEmergencyStop = JSONparser.getBoolean("FrameEmergencyStop");

            /*Mechanical limits*/
            LimitProximitySwitchDetectionOfwAxis = JSONparser.getBoolean("LimitProximitySwitchDetectionOfwAxis");
            LimitProximitySwitchDetectionOfzAxis = JSONparser.getBoolean("LimitProximitySwitchDetectionOfzAxis");
            DetectionOfMechanicalLimitStrokeSwitchOfxAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfxAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfxAxis2");
            DetectionOfMechanicalLimitStrokeSwitchOfyAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfyAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfyAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfyAxis2");
            DetectionOfMechanicalLimitStrokeSwitchOfzAxis1 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis1");
            DetectionOfMechanicalLimitStrokeSwitchOfzAxis2 = JSONparser.getBoolean("DetectionOfMechanicalLimitStrokeSwitchOfzAxis2");
            LimitOptoelectronicOfZAxis = JSONparser.getInt("LimitOptoelectronicOfZAxis");

            /*Other Alarms*/
            FrequencyConverterAlarmOfxAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfxAxis");
            FrequencyConverterAlarmOfyAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfyAxis");
            FrequencyConverterAlarmOfzAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfzAxis");
            FrequencyConverterAlarmOfwAxis = JSONparser.getBoolean("FrequencyConverterAlarmOfwAxis");
            TheAlarmOfBrickStorehouseDidNotClose1 = JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose1");
            TheAlarmOfBrickStorehouseDidNotClose2 = JSONparser.getBoolean("TheAlarmOfBrickStorehouseDidNotClose2");

        } catch(JSONException exc) {
            Log.d("JSON exception", "Can't parse PGSI CMD");
        }
    }

    public static boolean[] getAlarmBits() {
        boolean[] alarmBits = new boolean[TOTAL_ALARMS];

        /*Emergency*/
        alarmBits[0] = EmergencyStopOfElectricCabinet;
        alarmBits[1] = FrameEmergencyStop;

        /*Mechanical limits*/
        alarmBits[2] = LimitProximitySwitchDetectionOfwAxis;
        alarmBits[3] = LimitProximitySwitchDetectionOfzAxis;
        alarmBits[4] = DetectionOfMechanicalLimitStrokeSwitchOfxAxis1;
        alarmBits[5] = DetectionOfMechanicalLimitStrokeSwitchOfxAxis2;
        alarmBits[6] = DetectionOfMechanicalLimitStrokeSwitchOfyAxis1;
        alarmBits[7] = DetectionOfMechanicalLimitStrokeSwitchOfyAxis2;
        alarmBits[8] = DetectionOfMechanicalLimitStrokeSwitchOfzAxis1;
        alarmBits[9] = DetectionOfMechanicalLimitStrokeSwitchOfzAxis2;
        alarmBits[10] = intToBool(LimitOptoelectronicOfZAxis);

        /*Other Alarms*/
        alarmBits[11] = FrequencyConverterAlarmOfxAxis;
        alarmBits[12] = FrequencyConverterAlarmOfyAxis;
        alarmBits[13] = FrequencyConverterAlarmOfzAxis;
        alarmBits[14] = FrequencyConverterAlarmOfwAxis;
        alarmBits[15] = TheAlarmOfBrickStorehouseDidNotClose1;
        alarmBits[16] = TheAlarmOfBrickStorehouseDidNotClose2;

        return alarmBits;
    }

    /*
     * RBS This is just retarded.
     * The person who wrote the PLC program had no idea of how to use
     * data types and used integers for depicting booleans.
     * Even worse, 1 means true, 2 means false. Pointless.
     */
    private static boolean intToBool(int mInt) {
        boolean retval = false;
        if(mInt == 2)
            retval = false;
        if(mInt == 1)
            retval = true;
        return retval;
    }

}
