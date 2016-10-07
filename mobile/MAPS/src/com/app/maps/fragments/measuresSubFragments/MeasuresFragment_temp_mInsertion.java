package com.app.maps.fragments.measuresSubFragments;

import com.app.maps.R;
import com.app.maps.commonClasses.TemperatureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MeasuresFragment_temp_mInsertion extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "action_id";
	private String patientToken;
	private String deviceId;
	static int actionId;
	private EditText et_temp_value;
    private Button b_st;
    private SharedPreferences prefs;

    public static MeasuresFragment_temp_mInsertion newInstance(int sectionNumber, int actionId) {
    	MeasuresFragment_temp_mInsertion fragment = new MeasuresFragment_temp_mInsertion();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ACTION_ID, actionId);
        fragment.setArguments(args);
        return fragment;
    }
    
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
	        View measuresMiView = inflater.inflate(R.layout.fragment_measures_temp_minsertion, container, false);
	        
	        patientToken = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getString("patientToken", "");
	        deviceId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
	        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());

		    actionId = getArguments().getInt(ACTION_ID);
		    
		    et_temp_value =(EditText) measuresMiView.findViewById(R.id.temp_mInsertion_value);
	        
			et_temp_value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int id,
						KeyEvent keyEvent) {
					if (id == EditorInfo.IME_NULL) {
						attemptToSendValue();
						return true;
					}
					return false;
				}
			});
		    
	        b_st =(Button)measuresMiView.findViewById(R.id.save_button_omron);

	        b_st.setOnClickListener(new View.OnClickListener(){
	        	@Override
	            public void onClick(View v) {
	        		//Enviar JSON e guardar na BD local
	        		attemptToSendValue();
	        	}
	        		
	        });
	        
	        return measuresMiView;
	        
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
		 if (TextUtils.isEmpty(temp_value)) {
			 et_temp_value.setError(getString(R.string.empty_error));
			 focusView = et_temp_value;
			 cancel = true;
		 } else if (Integer.parseInt(temp_value) < 35) {
			 et_temp_value.setError(getString(R.string.value_too_low));
			 focusView = et_temp_value;
			 cancel = true;
		 } else if(Integer.parseInt(temp_value) > 45){
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
        		//Try to send measure if fails send it to local DB with flag 0 to sign that the value is not in DB, else send 1 if the value is successful saved in
        		//remote database.
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
