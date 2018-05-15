package com.example.administrator.Palletizer;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import static com.example.administrator.Palletizer.Commands.PING;

public class TcpClient {

    private String mServerMessage;
    private OnMessageReceived mMessageListener = null;
    private final int TIMEOUT_LIMIT = 300; //change to 3
    private final int SOCKET_TIMEOUT = 1000;
    private final int RETRY_INTERVAL = 2000;
    private InetAddress SERVER_IP;
    private int SERVER_PORT;
    private boolean mRun = false;
    private PrintWriter mBufferOut;
    private BufferedReader mBufferIn;
    public static int timeOuts = 0;
    private Socket socket=null;

    /*
     * Constructor of the class. OnMessagedReceived listens for the messages received from server
     */
    public TcpClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    public void sendMessage(String message) {
        //Log.e("TCP Client", "C: Sending...");
        if (mBufferOut != null && !mBufferOut.checkError()) {
            mBufferOut.println(message);
            mBufferOut.flush();
        }
    }

    public void run(Context mContext) {


        mRun = true;

        //Connect to server on first run
        connectToServer(mContext);

        //While connection is required
        while (mRun) {

            //Attempt read
            try{
                mServerMessage = mBufferIn.readLine();
                if (mServerMessage != null && mMessageListener != null) {
                    mMessageListener.messageReceived(mServerMessage);
                    Log.d("CMD received", mServerMessage);
                }
                else{
                    Log.d("CMD received","false");
                    closeSocket();
                    connectToServer(mContext);
                }

            //Timeout Exception
            } catch(SocketTimeoutException timeOutEx){
                sendMessage(PING); //Are you still there? ._.
                timeOuts++;
                if(timeOuts > TIMEOUT_LIMIT) {
                    //Connection lost, Reconnect.
                    closeSocket();
                    connectToServer(mContext);
                }

            //Any other exception
            } catch(Exception e) {
                Log.e("TCP Error", "S: Error", e);

            }
        }
        closeSocket();
    }


    private void connectToServer(Context mContext){
        Boolean Errors=true;
        while(Errors){
            try {
                Errors=false;

                //get IP and Port from preferences
                try {
                    SharedPreferences sharedPref = mContext.getSharedPreferences(mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SERVER_IP = InetAddress.getByName(sharedPref.getString(mContext.getResources().getString(R.string.Stored_PC_IP_Address),"192.168.0.199"));
                    SERVER_PORT = Integer.parseInt(sharedPref.getString(mContext.getResources().getString(R.string.Stored_PC_IP_Port),"24601"));
                } catch (Exception e) {
                    Log.e("String to IP exc.", e.toString());
                }

                //Create new socket and set timeout to 1000m
                socket = new Socket();
                socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT), SOCKET_TIMEOUT);
                socket.setSoTimeout(SOCKET_TIMEOUT);
                Log.e("TCP Client", "C: socket has been connected.");

                mMessageListener.connectionEstablished();

            } catch(Exception e){
                Errors=true;

                Log.e("TCP connection failed", "S: Error", e);
                closeSocket();

                try {
                    Thread.sleep(RETRY_INTERVAL);
                } catch (InterruptedException sleepExc) {
                    sleepExc.printStackTrace();
                }
            }
        }
        try{
            timeOuts=0;
            mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         }catch(Exception e) {
        }
    }


    private void closeSocket(){
        mMessageListener.connectionLost();
        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                Log.e("closeSocket(): ", e.toString());
            }
        }
        socket = null;
    }


    /**
     * Close the connection and release the members
     */
    public void stopClient() {
        mMessageListener.connectionLost();

        mRun = false;

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }

        mMessageListener = null;
        mBufferIn = null;
        mBufferOut = null;
        mServerMessage = null;
    }

    public interface OnMessageReceived {
        void messageReceived(String message);
        void connectionEstablished();
        void connectionLost();
    }

}