package com.app.maps.fragments.historySubFragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.app.maps.R;
import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;

import android.support.v4.app.ListFragment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryFragment_BPmeasuresView extends ListFragment{

	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int patientId;
    private ArrayList<BloodPressureMeasure> a;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment_BPmeasuresView newInstance(int sectionNumber) {
        HistoryFragment_BPmeasuresView fragment = new HistoryFragment_BPmeasuresView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment_BPmeasuresView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.fragment_history_bp, container, false);
        
        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        
    	//Get patientID
        SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
        patientId = prefs.getInt("patientID", -1);

        MySQLiteHelper msh = new MySQLiteHelper(getActivity());
        a = msh.getAllPressaoPaciente(patientId);
        
        if (a.size() != 0)
        	Toast.makeText(getActivity(), "Para visualizar um gráfico rode o dispositivo.", Toast.LENGTH_LONG).show();
        
        //	      a = fakeBP();
        MAPSBPRowAdapter adapter = new MAPSBPRowAdapter(getActivity(),
        		R.layout.list_item_bp_layout, a);
        setListAdapter(adapter);
		
        return historyView;
        
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      
      
    }

    //TODO REMOVER depois de ter dados.
    public ArrayList<BloodPressureMeasure> fakeBP(){

    	a = new ArrayList<BloodPressureMeasure>();

    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String formattedDate = sdf.format(date);

    	for(int i=0; i<10; i++){
    		a.add(new BloodPressureMeasure(i, i, (int)(Math.random()*100), (int) (Math.random()*200), 60,Timestamp.valueOf(formattedDate)));
    	}
    	return a;
    }
}

class MAPSBPRowAdapter extends ArrayAdapter<BloodPressureMeasure> {
	
	ArrayList<BloodPressureMeasure> data;
	int layoutID;
	Context context;
	
	public MAPSBPRowAdapter(Context context, int layoutID, ArrayList<BloodPressureMeasure> data) {
		   super(context, layoutID, data);
		   this.data=data;
		   this.context=context;
		   this.layoutID=layoutID;
		}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		   LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           View rowView = inflater.inflate(layoutID, parent, false);

           TextView tv_time_measured = (TextView) rowView.findViewById(R.id.time_measured);
           TextView tv_min_value = (TextView) rowView.findViewById(R.id.min_lv_value);
           TextView tv_max_value = (TextView) rowView.findViewById(R.id.max_lv_value);
           TextView tv_bpm_value = (TextView) rowView.findViewById(R.id.bpm_lv_value);
           
           tv_time_measured.setText(data.get(position).getTimeMeasured().toString());
           tv_min_value.setText(Integer.toString(data.get(position).getdMeasure()));	
           tv_max_value.setText(Integer.toString(data.get(position).getsMeasure()));
           tv_bpm_value.setText(Integer.toString(data.get(position).getFreqMeasure()));
		
           return rowView;
           
	}
}
