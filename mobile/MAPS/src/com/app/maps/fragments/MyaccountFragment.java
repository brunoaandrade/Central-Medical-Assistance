package com.app.maps.fragments;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.commonClasses.Patient;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyaccountFragment extends Fragment{

	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private SharedPreferences prefs_menu;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MyaccountFragment newInstance(int sectionNumber) {
        MyaccountFragment fragment = new MyaccountFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MyaccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View myAccView = inflater.inflate(R.layout.fragment_myaccount, container, false);
        
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        
        SharedPreferences prefs = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		int patientId= prefs.getInt("patientID", -1);
        
		prefs_menu = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
        TextView tv_p_name = (TextView) myAccView.findViewById(R.id.patientName_value);
        TextView tv_id_patient = (TextView) myAccView.findViewById(R.id.idPatient_value);
        TextView tv_bd_patient = (TextView) myAccView.findViewById(R.id.bdPatient_value);
        TextView tv_p_address = (TextView) myAccView.findViewById(R.id.addressPatient_value);
        
        Gson gs =  new GsonBuilder().create();
		JsonHandler js = new JsonHandler();
		String s;

        Patient pa = gs.fromJson(js.receiveJson(prefs_menu.getString("url_server", "")+"/PeiMaps/webresources/androidGetPatientInfo?id="+patientId), Patient.class);
        
        tv_p_name.setText(pa.getFname()+" "+pa.getLname());
        tv_id_patient.setText(String.valueOf(pa.getIdPatient()));
        tv_bd_patient.setText(pa.getBorndate().toString());
        tv_p_address.setText(pa.getCity());
        
        return myAccView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
