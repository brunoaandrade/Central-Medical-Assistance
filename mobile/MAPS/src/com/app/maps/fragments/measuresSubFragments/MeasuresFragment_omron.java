package com.app.maps.fragments.measuresSubFragments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.signove.health.service.HealthAgentAPI;
import com.signove.health.service.HealthServiceAPI;
import com.app.maps.R;
import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MeasuresFragment_omron extends Fragment {
	

private static final String ARG_SECTION_NUMBER = "section_number";
private static final String ACTION_ID = "action_id";
private String patientToken;
private String deviceId;
private static int actionId;
private SharedPreferences prefs;


	//-------------------------------------------------------------------------------------------------------------------------------
	 int [] specs = {0x1004};
		Handler tm;
		HealthServiceAPI api;

		private TextView status;
		private TextView msg;
		private TextView device;
		private TextView et_bpm_value;
		private TextView et_min_value;
		private TextView et_max_value;
		private Button b_st;

		Map <String, String> map;
		
		public void show(TextView field, String msg)
		{
			final TextView f = field;
			final String s = msg;
			tm.post(new Runnable() {
				public void run() {
					if (f != null){
						f.setText(s);
					}
				}
			});
		}

		public Document parse_xml(String xml)
		{
			Document d = null;

			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				d = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			} catch (ParserConfigurationException e) {
				Log.w("Antidote", "XML parser error");
			} catch (SAXException e) {
				Log.w("Antidote", "SAX exception");
			} catch (IOException e) {
				Log.w("Antidote", "IO exception in xml parsing");
			}

			return d;
		}

		public void show_dev(String path)
		{
			if (map.containsKey(path)) {
				show(device, "Device " + map.get(path));
			} else {
				show(device, "Unknown device " + path);
			}
		}

		public void handle_packet_connected(String path, String dev)
		{
			map.put(path, dev);
			show_dev(path);
			show(msg, "Connected");
		}

		public void handle_packet_disconnected(String path)
		{
			show(msg, "Disconnected");
			show_dev(path);
		}

		public void handle_packet_associated(String path, String xml)
		{
			show(msg, "Associated");
			show_dev(path);
		}

		public void handle_packet_disassociated(String path)
		{
			show(msg, "Disassociated");
			show_dev(path);
		}

		public void handle_packet_description(String path, String xml)
		{
			show(msg, "MDS received");
			show_dev(path);
		}

		public String get_xml_text(Node n) {
			String s = null;
			NodeList text = n.getChildNodes();

			for (int l = 0; l < text.getLength(); ++l) {
				Node txt = text.item(l);
				if (txt.getNodeType() == Node.TEXT_NODE) {
					if (s == null) {
						s = "";
					}
					s += txt.getNodeValue();							
				}
			}

			return s;
		}


		public void handle_packet_measurement(String path, String xml)
		{
			String measurement = "";
			String bpm_t = "";
			Document d = parse_xml(xml);
			double f;
			NumberFormat df = new DecimalFormat("0.0");
			
			if (d == null) {
				return;
			}

			NodeList datalists = d.getElementsByTagName("data-list");

			for (int i = 0; i < datalists.getLength(); ++i) {

				Log.w("Antidote", "processing datalist " + i);

				Node datalist_node = datalists.item(i);
				NodeList entries = ((Element) datalist_node).getElementsByTagName("entry");

				for (int j = 0; j < entries.getLength(); ++j) {

					Log.w("Antidote", "processing entry " + j);

					boolean ok = false;
					String unit = "";
					String value = "";

					Node entry = entries.item(j);

					// scan immediate children to dodge entry inside another entry
					NodeList entry_children = entry.getChildNodes();

					for (int k = 0; k < entry_children.getLength(); ++k) {					
						Node entry_child = entry_children.item(k);

						Log.w("Antidote", "processing entry child " + entry_child.getNodeName());

						if (entry_child.getNodeName().equals("simple")) {
							// simple -> value -> (text)
							NodeList simple = ((Element) entry_child).getElementsByTagName("value");
							Log.w("Antidote", "simple.value count: " + simple.getLength());
							if (simple.getLength() > 0) {
								String text = get_xml_text(simple.item(0));
								if (text != null) {
									ok = true;
									value = text;
								}
							}
						} else if (entry_child.getNodeName().equals("meta-data")) {
							// meta-data -> meta name=unit
							NodeList metas = ((Element) entry_child).getElementsByTagName("meta");

							Log.w("Antidote", "meta-data.meta count: " + metas.getLength());

							for (int l = 0; l < metas.getLength(); ++l) {
								Log.w("Antidote", "Processing meta " + l);
								NamedNodeMap attr = metas.item(l).getAttributes();
								if (attr == null) {
									Log.w("Antidote", "Meta has no attributes");
									continue;
								}
								Node item = attr.getNamedItem("name");
								if (item == null) {
									Log.w("Antidote", "Meta has no 'name' attribute");
									continue;
								}

								Log.w("Antidote", "Meta attr 'name' is " + item.getNodeValue());

								if (item.getNodeValue().equals("unit")) {
									Log.w("Antidote", "Processing meta unit");
									String text = get_xml_text(metas.item(l));
									if (text != null) {
										unit = text;
									}
								}
							}

						}
					}

					if (ok) {
						if (unit != "")
							measurement += value + " " + unit + " ";
						else
							measurement += value + " ";
					}
				}
			}
			
			//TODO Parser specifically designed for omron device
			
			//Getting values from default parser measured from omron device	
			System.out.println("ESTOU AQUI:"+measurement);
			String[] m_array = measurement.split(" ");
			
			f = Double.parseDouble(m_array[1]);

			show(et_min_value, df.format(f));

			f = Double.parseDouble(m_array[0]);

			show(et_max_value, df.format(f));

			show(msg, "Measurement");
			f = Double.parseDouble(m_array[11]);
			show(et_bpm_value, df.format(f));
			show_dev(path);	 
		}

		private void RequestConfig(String dev)
		{	
			try {
				Log.w("HST", "Getting configuration ");
				String xmldata = api.GetConfiguration(dev);
				Log.w("HST", "Received configuration");
				Log.w("HST", ".." + xmldata);
			} catch (RemoteException e) {
				Log.w("HST", "Exception (RequestConfig)");
			}
		}

		private void RequestDeviceAttributes(String dev)
		{	
			try {
				Log.w("HST", "Requested device attributes");
				api.RequestDeviceAttributes(dev);
			} catch (RemoteException e) {
				Log.w("HST", "Exception (RequestDeviceAttributes)");
			}
		}


		private HealthAgentAPI.Stub agent = new HealthAgentAPI.Stub() {
			@Override
			public void Connected(String dev, String addr) {
				Log.w("HST", "Connected " + dev);
				Log.w("HST", "..." + addr);
				handle_packet_connected(dev, addr);
			}

			@Override
			public void Associated(String dev, String xmldata) {
				final String idev = dev;
				Log.w("HST", "Associated " + dev);			
				Log.w("HST", "...." + xmldata);			
				handle_packet_associated(dev, xmldata);

				Runnable req1 = new Runnable() {
					public void run() {
						RequestConfig(idev);
					}
				};
				Runnable req2 = new Runnable() {
					public void run() {
						RequestDeviceAttributes(idev);
					}
				};
				tm.postDelayed(req1, 1); 
				tm.postDelayed(req2, 500); 
			}
			@Override
			public void MeasurementData(String dev, String xmldata) {
				Log.w("HST", "MeasurementData " + dev);
				Log.w("HST", "....." + xmldata);
				handle_packet_measurement(dev, xmldata);
			}
			@Override
			public void DeviceAttributes(String dev, String xmldata) {
				Log.w("HST", "DeviceAttributes " + dev);			
				Log.w("HST", ".." + xmldata);
				handle_packet_description(dev, xmldata);
			}

			@Override
			public void Disassociated(String dev) {
				Log.w("HST", "Disassociated " + dev);						
				handle_packet_disassociated(dev);
			}

			@Override
			public void Disconnected(String dev) {
				Log.w("HST", "Disconnected " + dev);
				handle_packet_disconnected(dev);
			}
		};

		private ServiceConnection serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.w("HST", "Service connection established");

				// that's how we get the client side of the IPC connection
				api = HealthServiceAPI.Stub.asInterface(service);
				try {
					Log.w("HST", "Configuring...");
					api.ConfigurePassive(agent, specs);
				} catch (RemoteException e) {
					Log.e("HST", "Failed to add listener", e);
				}
			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.w("HST", "Service connection closed");
			}
		};
		
	    public static MeasuresFragment_omron newInstance(int sectionNumber, int actionId) {
	    	MeasuresFragment_omron fragment = new MeasuresFragment_omron();
	        Bundle args = new Bundle();
	        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	        args.putInt(ACTION_ID, actionId);
	        fragment.setArguments(args);
	        return fragment;
	    }

		 @Override
		    public View onCreateView(LayoutInflater inflater, ViewGroup container,
		            Bundle savedInstanceState) {
		    View measuresOmView = inflater.inflate(R.layout.fragment_measures_omron, container, false);
		    
		    prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
		    
		    patientToken = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getString("patientToken", "");
	        deviceId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
		    
		    actionId = getArguments().getInt(ACTION_ID);
		    
		    tm = new Handler();
		    
			status = (TextView) measuresOmView.findViewById(R.id.status_omron_value);
			msg = (TextView) measuresOmView.findViewById(R.id.msg_omron_value);
			et_bpm_value = (TextView) measuresOmView.findViewById(R.id.bpm_value);
			et_min_value = (TextView) measuresOmView.findViewById(R.id.temp_mInsertion_value);
			et_max_value = (TextView) measuresOmView.findViewById(R.id.max_value);
//			device = (TextView) findViewById(R.id.device);
//			data = (TextView) findViewById(R.id.data);

			map = new HashMap<String, String>();

			tm = new Handler();
			Intent intent = new Intent("com.signove.health.service.HealthService");
			getActivity().startService(intent);
			getActivity().bindService(intent, serviceConnection, 0);
			Log.w("HST", "Activity created");

			status.setText("Ready");
			msg.setText("--");
			et_bpm_value.setText("--");
			et_min_value.setText("--");
			et_max_value.setText("--");
//			device.setText("--");
//			data.setText("--");
			
	        b_st =(Button)measuresOmView.findViewById(R.id.save_button_omron);
	        b_st.setOnClickListener(new View.OnClickListener(){
	        	@Override
	            public void onClick(View v) {
	        		//Enviar JSON e guardar na BD local
	        		attemptToSendValue();
	        	} 		
	        });
			
	        Toast.makeText(getActivity(), "Para receber dados tem de emparelhar os dispositivos", Toast.LENGTH_LONG).show();
	        
			return measuresOmView;
		}
		 
		 private void attemptToSendValue(){
			 
			 String min_value;
			 String max_value;
			 String bpm_value;

			 // Reset errors.
			 et_max_value.setError(null);
			 et_min_value.setError(null);
			 et_bpm_value.setError(null);

			 // Store values at the time of the measure insertion.
			 min_value = et_max_value.getText().toString();
			 max_value = et_min_value.getText().toString();
			 bpm_value = et_bpm_value.getText().toString();

			 boolean cancel = false;
			 View focusView = null;

			 // Check if min value is empty realistic.
			 if (min_value.equalsIgnoreCase("--") || max_value.equalsIgnoreCase("--") || bpm_value.equalsIgnoreCase("--")) {
				 b_st.setError(getString(R.string.no_measure_error));
				 focusView = b_st;
				 Log.i("omron","Botão");
				 cancel = true;
				 focusView.requestFocus();
			 }
			 
			 if (!cancel) {
					// There was an error; don't attempt to send and focus the first
					// form field with an error.
				 
					// perform the send attempt.
	        		
	        		Date date = new Date();
	        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        		String formattedDate = sdf.format(date);
	        		
	        		System.out.println(formattedDate); // 12/01/2011 4:48:16 PM
	        		// Sea medição não tiver prescrição associada enviar no actionId o valor -1
	        		String[] min_split = et_min_value.getText().toString().split(",");
	        		String[] max_split = et_max_value.getText().toString().split(",");
	        		String[] bpm_split = et_bpm_value.getText().toString().split(",");
	        		
	        		BloodPressureMeasure bpm1 = new BloodPressureMeasure(actionId, Integer.parseInt(min_split[0]), Integer.parseInt(max_split[0]), Integer.parseInt(bpm_split[0]), Timestamp.valueOf(formattedDate));
	        		Gson gs = new GsonBuilder().create();
	        		JsonHandler js = new JsonHandler();
	        		MySQLiteHelper msh = new MySQLiteHelper(getActivity());

	        		if (!(js.sendJson(prefs.getString("url_server", "")+"/PeiMaps/webresources/registerBPM?token="+patientToken+"&dev_id="+deviceId, gs.toJson(bpm1))).equalsIgnoreCase("ok"))
	        			msh.addPressao(bpm1, getActivity(), 0);
	        		else
	        			msh.addPressao(bpm1, getActivity(), 1);		
	        		Toast.makeText(getActivity(), "Medição guardarda", Toast.LENGTH_LONG).show();
	        		getFragmentManager().popBackStack();
				}
			 }
		 

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			try {
				Log.w("HST", "Unconfiguring...");
				api.Unconfigure(agent);
			} catch (Throwable t) {
				Log.w("HST", "Erro tentando desconectar");
			}
			Log.w("HST", "Activity destroyed");
			getActivity().unbindService(serviceConnection);

		}

}
