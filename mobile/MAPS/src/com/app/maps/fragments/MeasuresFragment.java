package com.app.maps.fragments;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.commonClasses.PAction;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.fragments.measuresSubFragments.MeasuresFragment_bp_mInsertion;
import com.app.maps.fragments.measuresSubFragments.MeasuresFragment_omron;
import com.app.maps.fragments.measuresSubFragments.MeasuresFragment_st;
import com.app.maps.fragments.measuresSubFragments.MeasuresFragment_temp_mInsertion;
import com.app.maps.fragments.measuresSubFragments.MeasuresFragment_vitaljacket;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MeasuresFragment extends Fragment {

	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "actionId";
	private int actionId;
	private SharedPreferences prefs;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MeasuresFragment newInstance(int sectionNumber, int actionId) {
        MeasuresFragment fragment = new MeasuresFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putSerializable(ACTION_ID, actionId);
        fragment.setArguments(args);
        return fragment;
    }

    public MeasuresFragment() {
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View measuresView = inflater.inflate(R.layout.fragment_measures, container, false);
        
        ImageButton b_st =(ImageButton)measuresView.findViewById(R.id.measures_st_button_img);
        ImageButton b_om =(ImageButton)measuresView.findViewById(R.id.measures_om_button_img);
        ImageButton b_bp_mi =(ImageButton)measuresView.findViewById(R.id.measures_bp_mi_button_img);
        ImageButton b_temp_mi =(ImageButton)measuresView.findViewById(R.id.measures_temp_mi_button_img);
        ImageButton b_vj =(ImageButton)measuresView.findViewById(R.id.measures_vj_button_img);
        View sep_ecg = measuresView.findViewById(R.id.sep_ecg);
        TextView text_ecg = (TextView) measuresView.findViewById(R.id.choose_ecg_sensor_label);
       
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		if (!prefs.getBoolean("sensor_vitaljacket", false)) {
			b_vj.setVisibility(View.INVISIBLE);
			sep_ecg.setVisibility(View.INVISIBLE);
			text_ecg.setVisibility(View.INVISIBLE);
		}
		
		if (!prefs.getBoolean("sensor_omron", false))
			b_om.setVisibility(View.INVISIBLE);
		
		if (!prefs.getBoolean("sensor_sensortag", false))
			b_st.setVisibility(View.INVISIBLE);
		
		//Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        actionId = getArguments().getInt(ACTION_ID);
        
        System.out.println(actionId);
		
        if ( actionId != -1){
        	MySQLiteHelper msh = new MySQLiteHelper(getActivity());
    		PAction pa = msh.getAction(actionId);
    		Log.i("MeFrag","PAction"+ pa.toString());
        	switch (pa.getMeasureType()){
        	case Blood:
        		b_st.setEnabled(false);
        		b_temp_mi.setEnabled(false);
        		b_vj.setEnabled(false);
        		break;
        	case Temperature:
        		b_om.setEnabled(false);
        		b_bp_mi.setEnabled(false);
        		b_vj.setEnabled(false);
        	}
        }
        
        
        b_st.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        	    MeasuresFragment_st st_fragment = MeasuresFragment_st.newInstance(-1, actionId);
        	    FragmentManager fragmentManager = getFragmentManager();
        	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, st_fragment);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        		
        });
        
        
        b_om.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        	    MeasuresFragment_omron om_fragment = MeasuresFragment_omron.newInstance(-1, actionId);
        	    FragmentManager fragmentManager = getFragmentManager();
        	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, om_fragment);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        		
        });
        
        
        b_bp_mi.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        	    MeasuresFragment_bp_mInsertion mi_fragment = MeasuresFragment_bp_mInsertion.newInstance(-1, actionId);
//        		MeasuresFragment_mInsertion mi_fragment = new MeasuresFragment_mInsertion();
        	    FragmentManager fragmentManager = getFragmentManager();
        	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, mi_fragment);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        		
        });
        
        
        b_temp_mi.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        	    MeasuresFragment_temp_mInsertion temp_mi_fragment = MeasuresFragment_temp_mInsertion.newInstance(-1, actionId);
//        		MeasuresFragment_mInsertion mi_fragment = new MeasuresFragment_mInsertion();
        	    FragmentManager fragmentManager = getFragmentManager();
        	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, temp_mi_fragment);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        		
        });
        
        
        b_vj.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        		MeasuresFragment_vitaljacket vj_fragment = MeasuresFragment_vitaljacket.newInstance(-1, actionId);
        	    FragmentManager fragmentManager = getFragmentManager();
        	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, vj_fragment);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        		
        });
    return measuresView;    
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

