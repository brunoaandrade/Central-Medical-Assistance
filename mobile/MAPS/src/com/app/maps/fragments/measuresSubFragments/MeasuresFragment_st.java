package com.app.maps.fragments.measuresSubFragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.app.maps.R;
import com.app.maps.commonClasses.TemperatureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.app.maps.handlers.STHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MeasuresFragment_st extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "action_id";
	private String patientToken;
	private String deviceId;
	static int actionId;
	private Button b_st;
	private TextView et_temp_value;
	private SharedPreferences prefs;

	public MeasuresFragment_st() {
    }
    
//----------------------------------------------------------------------------------------------------------------------------------------
    
    public STHandler st;
    
    private double ir_sensor_value;
    
    public void onCreate(Bundle savedInstanceState)
    {
      super.onCreate(savedInstanceState);      
      st = new STHandler(getActivity());
    }
   
    public void onDestroy()
    {
      st.destroy();
      super.onDestroy();
    }
    
    public static MeasuresFragment_st newInstance(int sectionNumber, int actionId) {
    	MeasuresFragment_st fragment = new MeasuresFragment_st();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ACTION_ID, actionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View measuresView = inflater.inflate(R.layout.fragment_measures_st, container, false);
     
        patientToken = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getString("patientToken", "");
        deviceId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());

        actionId = getArguments().getInt(ACTION_ID);
        st.onCreateBluetooth();
        et_temp_value = (TextView) measuresView.findViewById(R.id.temp_value);
        et_temp_value.setText("--");
        
        Button b_refresh =(Button)measuresView.findViewById(R.id.nMeasure_button);
        b_refresh.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Scanning...", Toast.LENGTH_SHORT).show();
                st.BTScan();
                ir_sensor_value = st.getTemp();
                System.out.println(ir_sensor_value);
                if(ir_sensor_value != 0){
                	et_temp_value.setText(String.format("%.1f", ir_sensor_value));
                }
            }
        });
        
        b_st =(Button)measuresView.findViewById(R.id.save_button_omron);
        b_st.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        		//TODO Enviar JSON e guardar na BD local
        		attemptToSendValue();
        	}
        });   
        Toast.makeText(getActivity(), "Para receber dados ligue o ST carregue em medir", Toast.LENGTH_LONG).show();
        return measuresView;
    }
    
	 private void attemptToSendValue(){
		 
		 String temp_value;

		 // Reset errors.
		 et_temp_value.setError(null);

		 // Store values at the time of the measure insertion.
		 temp_value = et_temp_value.getText().toString();


		 boolean cancel = false;
		 View focusView = null;

		 // Check if temp value is empty realistic.
		 if (temp_value.equalsIgnoreCase("--")) {
			 et_temp_value.setError(getString(R.string.no_measure_error));
			 focusView = et_temp_value;
			 cancel = true;
		 } else if (Integer.parseInt(temp_value) < 15){
			 et_temp_value.setError(getString(R.string.value_too_low));
			 focusView = et_temp_value;
			 cancel = true;
		 } else if(Integer.parseInt(temp_value) > 100){
			 et_temp_value.setError(getString(R.string.value_too_high));
			 focusView = et_temp_value;
			 cancel = true;
		 }
		 if (cancel) {
				// There was an error; don't attempt to send and focus the first
				// form field with an error.
				focusView.requestFocus();
			} else {
				// perform the send attempt.
        		
        		Date date = new Date();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		String formattedDate = sdf.format(date);
        		
        		TemperatureMeasure tm = new TemperatureMeasure(0, actionId, Integer.parseInt(et_temp_value.getText().toString()), Timestamp.valueOf(formattedDate));
        		Gson gs = new GsonBuilder().create();
        		JsonHandler js = new JsonHandler();
        		MySQLiteHelper msh = new MySQLiteHelper(getActivity());
        		
        		//TODO Change URL to temperature measure.
        		if (!(js.sendJson(prefs.getString("url_server", "")+"/PeiMaps/webresources/registerTemp?token="+patientToken+"&dev_id="+deviceId, gs.toJson(tm))).equalsIgnoreCase("ok"))
        			msh.addTemp(tm, getActivity(), 0);
        		else
        			msh.addTemp(tm, getActivity(), 1);		
        		Toast.makeText(getActivity(), "Medição guardarda", Toast.LENGTH_LONG).show();
        		getFragmentManager().popBackStack();
			}
		 }
}


