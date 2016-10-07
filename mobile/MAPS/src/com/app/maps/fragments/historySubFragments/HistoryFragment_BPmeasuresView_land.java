package com.app.maps.fragments.historySubFragments;

import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.app.maps.R;
import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HistoryFragment_BPmeasuresView_land extends Fragment{

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
    public static HistoryFragment_BPmeasuresView_land newInstance(int sectionNumber) {
        HistoryFragment_BPmeasuresView_land fragment = new HistoryFragment_BPmeasuresView_land();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment_BPmeasuresView_land() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View historyView = inflater.inflate(R.layout.fragment_history_graph, container, false);
        
    	//Get patientID
		SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		patientId = prefs.getInt("patientID", -1);
		
//		a = fakeBP();
		
		MySQLiteHelper msh = new MySQLiteHelper(getActivity());
		a = msh.getAllPressaoPaciente(patientId);
		
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
	    
	    lgv.setVerticalLabels(new String[]{
	    		"200  mmHg","190","180","170","160","150","140","130","120","110","100", 
	    		"90", "80", "70", "60", "50", "40", "30", "20", "10", "0"});
	    lgv.setScrollable(false);
	    lgv.setScalable(false);
	    lgv.setManualYAxis(true);
	    lgv.setManualYAxisBounds(200.0, 0.0);
	    lgv.setEnabled(true);
	    lgv.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
	    lgv.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
	    lgv.getGraphViewStyle().setGridColor(Color.BLACK);

		//Create graph data
		GraphViewData[] gvdata = new GraphViewData[a.size()];
		
		//Getting data for min line
		for (int i=0; i<a.size(); i++){
			
			Long l = a.get(i).getTimeMeasured().getTime();
        	
			gvdata[(a.size()-1)-i] = new GraphViewData(l.doubleValue(), a.get(i).getdMeasure());
		}
		GraphViewSeries seriesMin = new GraphViewSeries("Min", new GraphViewSeriesStyle(Color.parseColor("#005a97"), 6), gvdata);
		
		gvdata = new GraphViewData[a.size()];
		//Getting data for max line
		for (int i=0; i<a.size(); i++){

			Long l = a.get(i).getTimeMeasured().getTime();
			gvdata[(a.size()-1)-i] = new GraphViewData(l.doubleValue(), a.get(i).getsMeasure());
		}
		GraphViewSeries seriesMax = new GraphViewSeries("Máx", new GraphViewSeriesStyle(Color.parseColor("#4c0000"), 6), gvdata);
		
		gvdata = new GraphViewData[a.size()];
		//Getting data for bpm line
		for (int i=0; i<a.size(); i++){

			Long l = a.get(i).getTimeMeasured().getTime();
			gvdata[(a.size()-1)-i] = new GraphViewData(l.doubleValue(), a.get(i).getFreqMeasure());
		}
		GraphViewSeries seriesBpm = new GraphViewSeries("BPM", new GraphViewSeriesStyle(Color.parseColor("#FEC04C"), 6), gvdata);
		
		lgv.addSeries(seriesMin);
		lgv.addSeries(seriesMax);
		lgv.addSeries(seriesBpm);
		lgv.setShowLegend(true);
		lgv.setLegendAlign(LegendAlign.BOTTOM);
		final LinearLayout layout = (LinearLayout) historyView.findViewById(R.id.LinearLayout_graph);
		layout.addView(lgv);
		
        return historyView;
        
    }

    //TODO REMOVER depois de ter dados.
//    public ArrayList<BloodPressureMeasure> fakeBP(){
//
//    	a = new ArrayList<BloodPressureMeasure>();
//    	
//    	for(int i=0; i<10; i++){
//    		Date date = new Date(2014, 03, 22, 18, 10+i);
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        	String formattedDate = sdf.format(date);
//        	
//        	a.add(new BloodPressureMeasure(i, i, (int)(Math.random()*100), (int) (Math.random()*200), 60,Timestamp.valueOf(formattedDate)));
//    	}
//    	return a;
//    }
}