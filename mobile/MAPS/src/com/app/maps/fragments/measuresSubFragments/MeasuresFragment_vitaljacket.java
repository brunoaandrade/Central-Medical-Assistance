package com.app.maps.fragments.measuresSubFragments;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Date;

import com.app.maps.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import Bio.Library.namespace.BioLib;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MeasuresFragment_vitaljacket extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "action_id";

	private BioLib lib = null;

	private String address = "";
	private String mConnectedDeviceName = "";
	private BluetoothDevice deviceToConnect;

	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	
	private TextView text;
	private TextView textRTC;
	private TextView textPUSH;
	private TextView textPULSE;
	private TextView textBAT;
	private TextView textDataReceived;
	private TextView textSDCARD;
	private TextView textACC;
	private TextView textHR;
	private TextView textECG;
	private TextView textDeviceId;
	private TextView textRadioEvent;
	private TextView textTimeSpan;
	
	private Button buttonConnect;
	private Button buttonDisconnect;
	private Button buttonGetRTC;
	private Button buttonSetRTC;
	private Button buttonRequest;
	private Button buttonSetLabel;
	private Button buttonGetDeviceId;
	private Button recordButton;
	
	private int BATTERY_LEVEL = 0;
	private int PULSE = 0;
	private Date DATETIME_PUSH_BUTTON = null;
	private Date DATETIME_RTC = null;
	private Date DATETIME_TIMESPAN = null;
	private int SDCARD_STATE = 0;
	private int numOfPushButton = 0;
	private BioLib.DataACC dataACC = null;
	private String deviceId = "";
	private byte typeRadioEvent = 0;
	private byte[] infoRadioEvent = null;
	private short countEvent = 0;
	
	private boolean isConn = false;
	private boolean recording = false;
	
	private byte[][] ecg = null;
	private int nBytes = 0;
	
	private SharedPreferences prefs;
	
	private HandleVJGraph hvjg;
	
	public static MeasuresFragment_vitaljacket newInstance(int sectionNumber, int actionId) {
		MeasuresFragment_vitaljacket fragment = new MeasuresFragment_vitaljacket();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putInt(ACTION_ID, actionId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
		View measuresViView = inflater.inflate(R.layout.fragment_measures_vitaljacket, container, false);

		hvjg = new HandleVJGraph(measuresViView);

		// ###################################################
		// MACADDRESS:
		address = prefs.getString("sensor_vitaljacket_mac", "");
		// ###################################################

		recordButton = (Button) measuresViView.findViewById(R.id.record_button);

		text = (TextView) measuresViView.findViewById(R.id.lblStatus);
		text.setText("");

		textRTC = (TextView) measuresViView.findViewById(R.id.lblRTC);
		textPUSH = (TextView) measuresViView.findViewById(R.id.lblButton);
		textPULSE = (TextView) measuresViView.findViewById(R.id.lblPulse);
		textBAT = (TextView) measuresViView.findViewById(R.id.lblBAT);
		textDataReceived = (TextView) measuresViView.findViewById(R.id.lblData);
		textSDCARD = (TextView) measuresViView.findViewById(R.id.lblSDCARD);
		textACC = (TextView) measuresViView.findViewById(R.id.lblACC);
		textHR = (TextView) measuresViView.findViewById(R.id.lblHR);
		textECG = (TextView) measuresViView.findViewById(R.id.lblECG);
		textDeviceId = (TextView) measuresViView.findViewById(R.id.lblDeviceId);
		textRadioEvent = (TextView) measuresViView.findViewById(R.id.textRadioEvent);
		textTimeSpan  = (TextView) measuresViView.findViewById(R.id.lblTimeSpan);

		if (recording) {
			recordButton.setText("Parar gravação ECG");
			recordButton.setTextColor(Color.BLACK);
		}else {
			recordButton.setText("Gravar ECG");
			recordButton.setTextColor(Color.RED);
		}
		
		try 
		{
			lib = new BioLib(getActivity(), mHandler);
			text.append("Init BioLib \n");
		} 
		catch (Exception e) 
		{
			text.append("Error to init BioLib \n");
			e.printStackTrace();
		}

		buttonConnect = (Button) measuresViView.findViewById(R.id.buttonConnect);
		buttonConnect.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				Connect();
			}

			/***
			 * Connect to device.
			 */
			 private void Connect() 
			 {	
				 try 
				 {
					 deviceToConnect =  lib.mBluetoothAdapter.getRemoteDevice(address);

					 Reset();

					 text.setText("");
					 lib.Connect(address, 5);
				 } catch (Exception e) 
				 {
					 text.setText("Error to connect device: " + address);
					 e.printStackTrace();
				 }
			 }

		});

		buttonSetRTC = (Button) measuresViView.findViewById(R.id.buttonSetRTC);
		buttonSetRTC.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				try 
				{
					Date date = new Date();
					lib.SetRTC(date);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});

		buttonGetRTC = (Button) measuresViView.findViewById(R.id.buttonGetRTC);
		buttonGetRTC.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				try 
				{
					lib.GetRTC();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});

		buttonRequest = (Button) measuresViView.findViewById(R.id.buttonRequestData);
		buttonRequest.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				RequestData();
			}

			private void RequestData() 
			{
				try
				{
					deviceToConnect =  lib.mBluetoothAdapter.getRemoteDevice(address);

					Reset();
					text.setText("");
					lib.Request(address, 30);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();	
				}
			}
		});

		buttonSetLabel = (Button) measuresViView.findViewById(R.id.buttonSetLabel);
		buttonSetLabel.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				try 
				{
					/*
            		// 1. Sample of radio event: send array of bytes (10Bytes maximum)
            		byte type = 1;
            		// Maximum 10 bytes to send device [Optional]
            		byte[] info = new byte[4];
            		info[0] = 0x31; // 1 ascii table
            		info[1] = 0x32; // 2 ascii table
            		info[2] = 0x33; // 3 ascii table
            		info[3] = 0x34; // 4 ascii table

            		textRadioEvent.setText("Start send");
					if (lib.SetBytesToRadioEvent(type, info))
					{
						countEvent++;
						textRadioEvent.setText("REvent: " + countEvent);
					}
					else
						textRadioEvent.setText("Error");
					 */

					// 2. Sample of radio event: send string (10 char maximum)
					byte type = 2;
					String info = "5678";
					textRadioEvent.setText("Start send");
					if (lib.SetStringToRadioEvent(type, info))
					{
						countEvent++;
						textRadioEvent.setText("REvent: " + countEvent);
					}
					else
						textRadioEvent.setText("Error");


				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});

		buttonGetDeviceId = (Button) measuresViView.findViewById(R.id.buttonGetDeviceId);
		buttonGetDeviceId.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view) 
			{
				try 
				{
					lib.GetDeviceId();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});

		//buttonSearch.setEnabled(false);
		buttonConnect.setEnabled(false);
		buttonRequest.setEnabled(false);
		buttonGetRTC.setEnabled(false);
		buttonSetRTC.setEnabled(false);
		buttonSetLabel.setEnabled(false);
		buttonGetDeviceId.setEnabled(false);
		recordButton.setEnabled(false);

        Toast.makeText(getActivity(), "Para receber dados tem de emparelhar os dispositivos", Toast.LENGTH_LONG).show();
		return measuresViView;
	}

	public void OnDestroy()
	{
		if (isConn)
		{
			Disconnect();
		}

		if (lib.mBluetoothAdapter != null) 
		{	
			lib.mBluetoothAdapter.cancelDiscovery();
		}

		lib = null;
	}

	/***
	 * Disconnect from device.
	 */
	private void Disconnect()
	{
		try 
		{
			lib.Disconnect();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			Reset();
		}
	}

	/***
	 * Reset variables and UI.
	 */
	private void Reset() 
	{
		try
		{
			textBAT.setText("BAT: - - %");
			textPULSE.setText("PULSE: - - bpm");
			textPUSH.setText("PUSH-BUTTON: - - - ");
			textRTC.setText("RTC: - - - ");
			textDataReceived.setText("RECEIVED: - - - ");
			textACC.setText("ACC:  X: - -  Y: - -  Z: - -");
			textSDCARD.setText("SD CARD STATUS: - - ");
			textECG.setText("Ecg stream: -- ");
			textHR.setText("PEAK: --  BPMi: -- bpm  BPM: -- bpm  R-R: -- ms");
			textBAT.setText("BAT: -- %");
			textPULSE.setText("HR: -- bpm     Nb. Leads: -- ");
			textDeviceId.setText("Device Id: - - - - - - - - - -");
			textRadioEvent.setText(".");
			textTimeSpan.setText("SPAN: - - - ");

			SDCARD_STATE = 0;
			BATTERY_LEVEL = 0;
			PULSE = 0;
			DATETIME_PUSH_BUTTON = null;
			DATETIME_RTC = null;
			DATETIME_TIMESPAN = null;
			numOfPushButton = 0;
			countEvent = 0;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/**
	* Convert a unsigned byte to integer
	* 
	* @param b
	*            the byte to convert
	* @return unsigned byte converted to integer
	*/

	private static int unsignedByteToInt(byte b) {

		return b & 0xFF;

	}
	//Class to handle the graph of received data
	private class HandleVJGraph{
		
		private LineGraphView lgv;
		private Handler gHandler = new Handler();
		
		public HandleVJGraph(View v){
			lgv = new LineGraphView(getActivity().getApplicationContext(), "") {
//				@Override
//				protected String formatLabel(final double value, final boolean isValueX) {
//					if (isValueX) {
//						final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
//
//						return dateFormat.format(new Date((long) value));
//					} else {
//						return super.formatLabel(value, isValueX); // let the y-value be normal-formatted
//					}
//				}
			};

			lgv.setScrollable(true);
			lgv.setScalable(true);
			lgv.setManualYAxis(true);
			lgv.setManualYAxisBounds(200, 35.0);
			lgv.setEnabled(false);
			lgv.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
			lgv.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
			lgv.getGraphViewStyle().setGridColor(Color.BLACK);
			
//			GraphViewSeries seriesTemp = new GraphViewSeries(new GraphView.GraphViewData[] { 
//			        new GraphView.GraphViewData(0, 0.0)
//			});
//			
//			lgv.addSeries(seriesTemp);

			final LinearLayout layout = (LinearLayout) v.findViewById(R.id.ecg_graph);
			layout.addView(lgv);
		}
		
		public void appendDataToGraph(final byte[] d){
			
			//Create graph data
			GraphViewData[] gvdata = new GraphViewData[d.length];
			final GraphViewSeries seriesTemp = new GraphViewSeries("ECG", new GraphViewSeriesStyle(Color.parseColor("#4c0000"), 6), gvdata);

			if (recording) {
				recordButton.setText("Parar gravação ECG");
				recordButton.setTextColor(Color.BLACK);
			}else {
				recordButton.setText("Gravar ECG");
				recordButton.setTextColor(Color.RED);
			}
			
//			Thread t = new Thread() {
//				public void run() {
								lgv.removeAllSeries();
					//Getting data for temperature line
					for (int i=0; i<d.length; i++){	
//						seriesTemp.appendData(new GraphViewData(i, unsignedByteToInt(d[i])), true);
						 gvdata[i] = new GraphViewData(i, unsignedByteToInt(d[i]));
//					gHandler.postDelayed(this, 300);
					}
//								GraphViewSeries seriesTemp = new GraphViewSeries("ECG", new GraphViewSeriesStyle(Color.parseColor("#4c0000"), 6), gvdata);
					// add data

//					lgv.redrawAll();
								lgv.addSeries(seriesTemp);
					if(recording)
						ecgToFile(d);
//				}
//		};
//		t.start();
		}
	}
	
	private void ecgToFile(byte[] ab) {
		try {
			File sdcard = Environment.getExternalStorageDirectory();
			File dir = new File(sdcard.getAbsolutePath()+"/downloads/ecg_data-"+Math.random()*450);

			Writer writer = null;

			try {
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(dir), "utf-8"));
				for(int i=0; i<ab.length; i++) {
					writer.write(ab[i]);
					writer.flush();
				}
			} catch (IOException ex) {
				// report
				ex.printStackTrace();
			} finally {
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} finally{}

	}

	/**
	 * The Handler that gets information back from the BioLib
	 */
	private final Handler mHandler = new Handler() 
	{
		@Override
		public void handleMessage(Message msg) 
		{	
			switch (msg.what) 
			{	    
			case BioLib.MESSAGE_READ:
				textDataReceived.setText("RECEIVED: " + msg.arg1);
				break;

			case BioLib.MESSAGE_DEVICE_NAME:
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getActivity().getApplicationContext(), "Connected to " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
				text.append("Connected to " + mConnectedDeviceName + " \n");
				break;

			case BioLib.MESSAGE_BLUETOOTH_NOT_SUPPORTED:
				Toast.makeText(getActivity().getApplicationContext(), "Bluetooth NOT supported. Aborting! ", Toast.LENGTH_SHORT).show();
				text.append("Bluetooth NOT supported. Aborting! \n");
				isConn = false;
				break;

			case BioLib.MESSAGE_BLUETOOTH_ENABLED:
				Toast.makeText(getActivity().getApplicationContext(), "Bluetooth is now enabled! ", Toast.LENGTH_SHORT).show();
				text.append("Bluetooth is now enabled \n");
				text.append("Macaddress selected: " + address + " \n");
				buttonConnect.setEnabled(true);
				buttonRequest.setEnabled(true);
				break;

			case BioLib.MESSAGE_BLUETOOTH_NOT_ENABLED:
				Toast.makeText(getActivity().getApplicationContext(), "Bluetooth not enabled! ", Toast.LENGTH_SHORT).show();
				text.append("Bluetooth not enabled \n");
				isConn = false;
				break;

			case BioLib.REQUEST_ENABLE_BT:
				Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableIntent, BioLib.REQUEST_ENABLE_BT);
				text.append("Request bluetooth enable \n");
				break;

			case BioLib.STATE_CONNECTING:
				text.append("   Connecting to device ... \n");
				break;

			case BioLib.STATE_CONNECTED:
				Toast.makeText(getActivity().getApplicationContext(), "Connected to " + deviceToConnect.getName(), Toast.LENGTH_SHORT).show();
				text.append("   Connect to " + deviceToConnect.getName() + " \n");
				isConn = true;

				buttonConnect.setEnabled(false);
				buttonRequest.setEnabled(false);
				buttonGetRTC.setEnabled(true);
				buttonSetRTC.setEnabled(true);
				buttonSetLabel.setEnabled(true);
				buttonGetDeviceId.setEnabled(true);
				recordButton.setEnabled(true);
				break;

			case BioLib.UNABLE_TO_CONNECT_DEVICE:
				Toast.makeText(getActivity().getApplicationContext(), "Unable to connect device! ", Toast.LENGTH_SHORT).show();
				text.append("   Unable to connect device \n");
				isConn = false;

				buttonConnect.setEnabled(true);
				buttonRequest.setEnabled(true);
				buttonGetRTC.setEnabled(false);
				buttonSetRTC.setEnabled(false);
				buttonSetLabel.setEnabled(false);
				buttonGetDeviceId.setEnabled(false);
				recordButton.setEnabled(false);
				break;

			case BioLib.MESSAGE_DISCONNECT_TO_DEVICE:
				Toast.makeText(getActivity().getApplicationContext(), "Device connection was lost", Toast.LENGTH_SHORT).show();
				text.append("   Disconnected from " + deviceToConnect.getName() + " \n");
				isConn = false;

				buttonConnect.setEnabled(true);
				buttonRequest.setEnabled(true);
				buttonGetRTC.setEnabled(false);
				buttonSetRTC.setEnabled(false);
				buttonSetLabel.setEnabled(false);
				buttonGetDeviceId.setEnabled(false);
				recordButton.setEnabled(false);
				break;

			case BioLib.MESSAGE_PUSH_BUTTON:
				DATETIME_PUSH_BUTTON = (Date)msg.obj;
				numOfPushButton = msg.arg1;
				textPUSH.setText("PUSH-BUTTON: [#" + numOfPushButton + "]" + DATETIME_PUSH_BUTTON.toString());
				break;

			case BioLib.MESSAGE_RTC:
				DATETIME_RTC = (Date)msg.obj;
				textRTC.setText("RTC: " + DATETIME_RTC.toString());
				break;


			case BioLib.MESSAGE_TIMESPAN:
				DATETIME_TIMESPAN = (Date)msg.obj;
				textTimeSpan.setText("SPAN: " + DATETIME_TIMESPAN.toString());
				break;

			case BioLib.MESSAGE_DATA_UPDATED:
				BioLib.Output out = (BioLib.Output)msg.obj;
				BATTERY_LEVEL = out.battery;
				textBAT.setText("BAT: " + BATTERY_LEVEL + " %");
				PULSE = out.pulse;
				textPULSE.setText("HR: " + PULSE + " bpm     Nb. Leads: " + lib.GetNumberOfChannels());
				break;

			case BioLib.MESSAGE_SDCARD_STATE:
				SDCARD_STATE = (int)msg.arg1;
				if (SDCARD_STATE == 1)
					textSDCARD.setText("SD CARD STATE: ON");
				else
					textSDCARD.setText("SD CARD STATE: OFF");
				break;

			case BioLib.MESSAGE_RADIO_EVENT:
				textRadioEvent.setText("Radio-event: received ... ");

				typeRadioEvent = (byte)msg.arg1;
				infoRadioEvent = (byte[]) msg.obj;

				String str = "";
				try {
					str = new String(infoRadioEvent, "UTF8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				textRadioEvent.setText("Radio-event: " + typeRadioEvent + "[" + str + "]");
				break;

			case BioLib.MESSAGE_DEVICE_ID:
				deviceId = (String)msg.obj;
				textDeviceId.setText("Device Id: " + deviceId);
				break;

			case BioLib.MESSAGE_PEAK_DETECTION:
				BioLib.QRS qrs = (BioLib.QRS)msg.obj;
				textHR.setText("PEAK: " + qrs.position + "  BPMi: " + qrs.bpmi + " bpm  BPM: " + qrs.bpm + " bpm  R-R: " + qrs.rr + " ms");
				break;

			case BioLib.MESSAGE_ACC_UPDATED:
				dataACC = (BioLib.DataACC)msg.obj;
				textACC.setText("ACC:  X: " + dataACC.X + "  Y: " + dataACC.Y + "  Z: " + dataACC.Z);
				break;

			case BioLib.MESSAGE_ECG_STREAM:
				try
				{
					textECG.setText("ECG received");
					ecg = (byte[][]) msg.obj;
					
					//Add new data to graph
					hvjg.appendDataToGraph(ecg[0]);
					
					
					
					System.out.println(unsignedByteToInt(ecg[0][2]));
					int nLeads = ecg.length;
					nBytes = ecg[0].length;
					textECG.setText("ECG stream: OK   nBytes: " + nBytes + "   nLeads: " + nLeads);
				}
				catch (Exception ex)
				{
					textECG.setText("ERROR in ecg stream");
				}
				break;

			case BioLib.MESSAGE_TOAST:
				Toast.makeText(getActivity().getApplicationContext(), msg.getData().getString(TOAST), Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
