package com.app.maps.fragments.measuresSubFragments;

import com.app.maps.R;
import com.app.maps.LoginActivity.UserLoginTask;
import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
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

public class MeasuresFragment_bp_mInsertion extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "action_id";
	private String patientToken;
	private String deviceId;
	private static final String MY_EDIT_TEXT_VISIBILITY = null;
	private static final String MY_TEXT_VIEW_VISIBILITY = null;
	private static final String MY_BUTTON_VISIBILITY = null;
	static int actionId;
	private EditText et_min_value;
    private EditText et_max_value;
    private EditText et_bpm_value;
    private Button b_st;
    private SharedPreferences prefs;

    public static MeasuresFragment_bp_mInsertion newInstance(int sectionNumber, int actionId) {
    	MeasuresFragment_bp_mInsertion fragment = new MeasuresFragment_bp_mInsertion();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ACTION_ID, actionId);
        fragment.setArguments(args);
        return fragment;
    }
    
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
	        View measuresMiView = inflater.inflate(R.layout.fragment_measures_bp_minsertion, container, false);
	        
	        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
	        patientToken = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getString("patientToken", "");
	        deviceId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
	        
		    actionId = getArguments().getInt(ACTION_ID);
		    
		    et_min_value =(EditText) measuresMiView.findViewById(R.id.temp_mInsertion_value);
		    et_max_value =(EditText) measuresMiView.findViewById(R.id.max_value);
		    et_bpm_value =(EditText) measuresMiView.findViewById(R.id.bpm_value);
	        
		    et_max_value
			.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
		    
	        b_st =(Button)measuresMiView.findViewById(R.id.save_button_mInsertion);

	        b_st.setOnClickListener(new View.OnClickListener(){
	        	@Override
	            public void onClick(View v) {
	        		//Enviar JSON e guardar na BD local
	        		Toast.makeText(getActivity(), "Medição guardarda", Toast.LENGTH_LONG).show();
	        		attemptToSendValue();
	        	}
	        		
	        });
	        
	        return measuresMiView;
	        
	 }
	 
	 private void attemptToSendValue(){
		 
		 String min_value;
		 String max_value;
		 String bpm_value;

		 // Reset errors.
		 et_min_value.setError(null);
		 et_max_value.setError(null);
		 et_bpm_value.setError(null);

		 // Store values at the time of the measure insertion.
		 min_value = et_min_value.getText().toString();
		 max_value = et_max_value.getText().toString();
		 bpm_value = et_bpm_value.getText().toString();

		 boolean cancel = false;
		 View focusView = null;

		 // Check if min value is empty realistic.
		 if (TextUtils.isEmpty(min_value)) {
			 et_min_value.setError(getString(R.string.empty_error));
			 focusView = et_min_value;
			 cancel = true;
		 } else if (Integer.parseInt(min_value) < 15) {
			 et_min_value.setError(getString(R.string.value_too_low));
			 focusView = et_min_value;
			 cancel = true;
		 } else if(Integer.parseInt(min_value) > 100){
			 et_min_value.setError(getString(R.string.value_too_high));
			 focusView = et_min_value;
			 cancel = true;
		 }

		 // Check if max value is empty realistic.
		 if (TextUtils.isEmpty(max_value)) {
			 et_max_value.setError(getString(R.string.empty_error));
			 focusView = et_max_value;
			 cancel = true;
		 } else if (Integer.parseInt(max_value) < 80) {
			 et_max_value.setError(getString(R.string.value_too_low));
			 focusView = et_max_value;
			 cancel = true;
		 } else if(Integer.parseInt(max_value) > 200){
			 et_max_value.setError(getString(R.string.value_too_high));
			 focusView = et_max_value;
			 cancel = true;
		 }
		 
		 // Check if bpm value is empty realistic.
		 if (TextUtils.isEmpty(bpm_value)) {
			 et_bpm_value.setError(getString(R.string.empty_error));
			 focusView = et_bpm_value;
			 cancel = true;
		 } else if (Integer.parseInt(bpm_value) < 15) {
			 et_bpm_value.setError(getString(R.string.value_too_low));
			 focusView = et_bpm_value;
			 cancel = true;
		 } else if(Integer.parseInt(bpm_value) > 100){
			 et_bpm_value.setError(getString(R.string.value_too_high));
			 focusView = et_bpm_value;
			 cancel = true;
		 }
		 
		 if (cancel) {
				// There was an error; don't attempt to send and focus the first
				// form field with an error.
				focusView.requestFocus();
			} else {
				// perform the send attempt.
				System.out.println(actionId);
        		Toast.makeText(getActivity(), "Medição guardarda", Toast.LENGTH_LONG).show();
        		
        		Date date = new Date();
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        		String formattedDate = sdf.format(date);
        		
        		BloodPressureMeasure bpm = new BloodPressureMeasure(actionId, Integer.parseInt(et_min_value.getText().toString()), Integer.parseInt(et_max_value.getText().toString()), Integer.parseInt(et_bpm_value.getText().toString()), Timestamp.valueOf(formattedDate));
        		Gson gs = new GsonBuilder().create();
        		JsonHandler js = new JsonHandler();
        		MySQLiteHelper msh = new MySQLiteHelper(getActivity());
        		//Try to send measure if fails send it to local DB with flag 0 to sign that the value is not in DB, else send 1 if the value is successful saved in
        		//remote database.
        		if (!(js.sendJson(prefs.getString("url_server", "")+"/PeiMaps/webresources/registerBPM?token="+patientToken+"&dev_id="+deviceId, gs.toJson(bpm))).equalsIgnoreCase("ok"))
        			msh.addPressao(bpm, getActivity(), 0);
        		else
        			msh.addPressao(bpm, getActivity(), 1);
        		
//        		Toast.makeText(getActivity(), "Medição guardarda", Toast.LENGTH_LONG).show();
        		getFragmentManager().popBackStack();
			}
	 }
	 
	 @Override
	 public void setUserVisibleHint(boolean isVisibleToUser) {
	     super.setUserVisibleHint(isVisibleToUser);
	     if(isVisibleToUser) {
	         if(getActivity() != null) getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	     }
	 }
}
