package com.app.maps.fragments.historySubFragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.app.maps.R;
import com.app.maps.commonClasses.TemperatureMeasure;
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

public class HistoryFragment_tempView extends ListFragment{

	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int patientId;
    private ArrayList<TemperatureMeasure> a;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment_tempView newInstance(int sectionNumber) {
        HistoryFragment_tempView fragment = new HistoryFragment_tempView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment_tempView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View historyView = inflater.inflate(R.layout.fragment_history_temp, container, false);
    	
        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    	
    	//Get patientID
    	SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
    	patientId = prefs.getInt("patientID", -1);
    	
        
        MySQLiteHelper msh = new MySQLiteHelper(getActivity());
        a = msh.getAllTemperatura(patientId);
        
        System.out.println(a.size());
        
        if (a.size() != 0)
        	Toast.makeText(getActivity(), "Para visualizar um gráfico rode o dispositivo.", Toast.LENGTH_LONG).show();
        
        MAPSTempRowAdapter adapter = new MAPSTempRowAdapter(getActivity(),
                R.layout.list_item_temp_layout, a);
        setListAdapter(adapter);
        
        return historyView;
        
    }
    
    //TODO REMOVER depois de ter dados.
    public ArrayList<TemperatureMeasure> fakeTemp(){

    	a = new ArrayList<TemperatureMeasure>();
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String formattedDate = sdf.format(date);

    	for(int i=0; i<10; i++){
    		a.add(new TemperatureMeasure(i, i, 36, Timestamp.valueOf(formattedDate)));
    	}
    	return a;
    }
}

class MAPSTempRowAdapter extends ArrayAdapter<TemperatureMeasure> {
	
	ArrayList<TemperatureMeasure> data;
	int layoutID;
	Context context;
	
	public MAPSTempRowAdapter(Context context, int layoutID, ArrayList<TemperatureMeasure> data) {
		   super(context, layoutID, data);
		   this.data=data;
		   this.context=context;
		   this.layoutID=layoutID;
		}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		   LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           View rowView = inflater.inflate(layoutID, parent, false);
           
           TextView tv_time_measures = (TextView) rowView.findViewById(R.id.time_measured);
           TextView tv_temp_value = (TextView) rowView.findViewById(R.id.temp_lv_value);

           tv_time_measures.setText(data.get(position).getTimeMeasured().toString());
           tv_temp_value.setText(Integer.toString(data.get(position).gettMeasure()));
		return rowView;
           
	}
}
