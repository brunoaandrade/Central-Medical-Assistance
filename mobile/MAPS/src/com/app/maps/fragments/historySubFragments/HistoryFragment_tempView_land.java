package com.app.maps.fragments.historySubFragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.app.maps.R;
import com.app.maps.commonClasses.TemperatureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HistoryFragment_tempView_land extends Fragment{

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
    public static HistoryFragment_tempView_land newInstance(int sectionNumber) {
        HistoryFragment_tempView_land fragment = new HistoryFragment_tempView_land();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment_tempView_land() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View historyView = inflater.inflate(R.layout.fragment_history_graph, container, false);
    	
    	//Get patientID
    	SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
    	patientId = prefs.getInt("patientID", -1);
    	
//    	a = fakeTemp();
    	
    	MySQLiteHelper msh = new MySQLiteHelper(getActivity());
    	a = msh.getAllTemperatura(patientId);	
    	
    	LineGraphView lgv = new LineGraphView(this.getActivity().getApplicationContext(), "") {
	        @Override
	        protected String formatLabel(final double value, final boolean isValueX) {
	            if (isValueX) {
	                final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM HH:mm");
	                
	                return dateFormat.format(new Date((long) value));
	            } else {
	                return super.formatLabel(value, isValueX); // let the y-value be normal-formatted
	            }
	        }
	    };
	    
	    lgv.setVerticalLabels(new String[]{"45  ºC", "44", "43", "42", "41", "40", "39.5", "39", "38.5", "38", 
	    		"37.5", "37", "36.5", "36", "35"});
	    lgv.setScrollable(false);
	    lgv.setScalable(false);
	    lgv.setManualYAxis(true);
	    lgv.setManualYAxisBounds(45.0, 35.0);
	    lgv.setEnabled(false);
	    lgv.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
	    lgv.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
	    lgv.getGraphViewStyle().setGridColor(Color.BLACK);
        
	    //Create graph data
	    GraphViewData[] gvdata = new GraphViewData[a.size()];

	    //Getting data for temperature line
	    for (int i=0; i<a.size(); i++){

	    	Long l = a.get(i).getTimeMeasured().getTime();
	    	gvdata[(a.size()-1)-i] = new GraphViewData(l.doubleValue(), a.get(i).gettMeasure());
	    }
	    
	    GraphViewSeries seriesTemp = new GraphViewSeries("Temp", new GraphViewSeriesStyle(Color.parseColor("#4c0000"), 6), gvdata);
	    
	    // add data
	    lgv.addSeries(seriesTemp);
	    
	    final LinearLayout layout = (LinearLayout) historyView.findViewById(R.id.LinearLayout_graph);
		layout.addView(lgv);
		
        return historyView;
        
    }
    
    //TODO REMOVER depois de ter dados.
    public ArrayList<TemperatureMeasure> fakeTemp(){

    	a = new ArrayList<TemperatureMeasure>();

//    	Date date = new Date();
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String formattedDate = sdf.format(date);

    	for(int i=0; i<10; i++){
    		Date date = new Date(2014, 03, 22, 18, 10+i);
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String formattedDate = sdf.format(date);
        	
    		a.add(new TemperatureMeasure(i, i, (int)(Math.random()*40), Timestamp.valueOf(formattedDate)));
    	}
    	return a;
    }
}