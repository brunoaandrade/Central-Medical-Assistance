package com.app.maps.fragments.historySubFragments;

import java.util.ArrayList;

import com.app.maps.R;
import com.app.maps.commonClasses.DrugTakeRegister;
import com.app.maps.commonClasses.PAction;
import com.app.maps.databaseLocal.MySQLiteHelper;

import android.support.v4.app.ListFragment;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoryFragment_TakesView extends ListFragment{

	/**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int patientId;
    private ArrayList<DrugTakeRegister> a;
    private MySQLiteHelper msh;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HistoryFragment_TakesView newInstance(int sectionNumber) {
        HistoryFragment_TakesView fragment = new HistoryFragment_TakesView();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment_TakesView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.fragment_history_takes, container, false);
        
        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
    	//Get patientID
		SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		patientId = prefs.getInt("patientID", -1);
		
		msh = new MySQLiteHelper(getActivity());
	   
		Log.i("HistoryFragment_TakesView", Integer.toString(patientId));
        a = msh.getAllDrugTake(patientId);
       
        Log.d("manel","size"+a.size()+" valoes"+a.toString());
        
       MAPSDTRowAdapter adapter = new MAPSDTRowAdapter(getActivity(),
        		R.layout.list_item_dt_layout, a);
       setListAdapter(adapter);
		
        return historyView;
        
    }
}

class MAPSDTRowAdapter extends ArrayAdapter<DrugTakeRegister> {
	
	ArrayList<DrugTakeRegister> data;
	int layoutID;
    private PAction pa;
    private MySQLiteHelper msh;
	Context context;
	
	public MAPSDTRowAdapter(Context context, int layoutID, ArrayList<DrugTakeRegister> data) {
		   super(context, layoutID, data);
		   this.data=data;
		   this.context=context;
		   this.layoutID=layoutID;
		}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		   LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           View rowView = inflater.inflate(layoutID, parent, false);

           TextView tv_drug_name = (TextView) rowView.findViewById(R.id.drug_name);
           TextView tv_time_last_take = (TextView) rowView.findViewById(R.id.time_last_take);
           
           msh = new MySQLiteHelper(getContext());
           
//           System.out.println( msh.getAction(2));
           pa = msh.getAction(data.get(position).getIdTake());
           
           tv_drug_name.setText(pa.getActionDrugName());
           tv_time_last_take.setText(data.get(position).getTimeTaked().toString());	
		
           return rowView;
           
	}
}


