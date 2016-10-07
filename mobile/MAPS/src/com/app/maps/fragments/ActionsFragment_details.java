package com.app.maps.fragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;

import notificationSystem.CalculateFeedback;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.maps.R;
import com.app.maps.commonClasses.DrugTakeRegister;
import com.app.maps.commonClasses.PAction;
import com.app.maps.commonClasses.PAction.MeasureType;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.handlers.JsonHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActionsFragment_details extends Fragment {
	
	private static final String ACTION = "action";
	private PAction a;
	private SharedPreferences prefs;
	private String patientToken;
	private String deviceId;
	private int patientId;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prescriptions_details, container, false);
        
        prefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
		patientToken = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getString("patientToken", "");
		deviceId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
		patientId = getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE).getInt("patientID", -1);
		
        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    	
        a = (PAction) getArguments().getSerializable(ACTION);
        
        TextView tv_title = (TextView) rootView.findViewById(R.id.presc_title);
        TextView tv_hours_list = (TextView) rootView.findViewById(R.id.hours_list);
        tv_hours_list.setMovementMethod(new ScrollingMovementMethod());
        TextView tv_warning = (TextView) rootView.findViewById(R.id.warning_value);
        TextView tv_start_time = (TextView) rootView.findViewById(R.id.start_time_value);
        TextView tv_end_time = (TextView) rootView.findViewById(R.id.end_time_value);
        TextView tv_description = (TextView) rootView.findViewById(R.id.description_value);
        Button bt_action = (Button) rootView.findViewById(R.id.measure_took_button);
        Button bt_history = (Button) rootView.findViewById(R.id.history_button);
        ImageView iv_liIcon = (ImageView) rootView.findViewById(R.id.p_type_icon_details);
        ImageView iv_warningIcon = (ImageView) rootView.findViewById(R.id.image_warn);
        iv_warningIcon.setVisibility(View.INVISIBLE);
        bt_action.setEnabled(false);
        
        String fDateFinish;
        Timestamp FHour;
        String fDateStart;
        String aux;
        
        CalculateFeedback cf = new CalculateFeedback();
        Log.i("ActionFragDetail PAction", a.toString());
        ArrayList<Timestamp> hourList = cf.hoursToDo(a);
        String hl ="";
        for(int i=0; i < hourList.size(); i++)
        {
			String sNHour = new SimpleDateFormat("HH:mm:ss").format(hourList.get(i));
        	hl += sNHour;
        	if (i < hourList.size()-1)
        		hl += "\n";
        }
        tv_hours_list.setText(hl);
        
        //calcular warnings, accoes pendentes
		int howManyWarning = cf.warnings(getActivity(), a, patientId);
		Log.i("getView", "n warning: "+howManyWarning);
		if (howManyWarning > 0)
		{
			tv_warning.setText(""+howManyWarning+"");
			iv_warningIcon.setVisibility(View.VISIBLE);
			bt_action.setEnabled(true);
		}
		
//        if ( a.getNumberreps() != 0 ) //FALTAAAAAAAA
//        	tv_warning.setText("De "+breakTimeInSec/3600+" em "+breakTimeInSec/3600+" h");
//    	else
//    		tv_warning.setText("Antes das refeições");
        
        switch (a.getActionType())
        {
	        case Measure:
	
	        	if(a.getActionMeasureType() == MeasureType.Blood){ 
	        		tv_title.setText("Pressão arterial");
	        		iv_liIcon.setImageResource(R.drawable.blood_pressure);
	        		onClickGoTo(getFragmentManager(), bt_history, HistoryFragment.newInstanceOpeningTab(2,0));
	        	}else{
	        		tv_title.setText("Temperatura");
	        		iv_liIcon.setImageResource(R.drawable.thermometer);
	        		onClickGoTo(getFragmentManager(), bt_history, HistoryFragment.newInstanceOpeningTab(2,1));
	        	}
	        	
	        	fDateStart = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Timestamp.valueOf(a.getTimeToStart()));
	        	tv_start_time.setText(fDateStart);
	        	FHour = cf.finalHour(a);
	        	fDateFinish = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
	        	tv_end_time.setText(fDateFinish);
	        	// ver se e antes das refeicoes
	        	aux = "";
	        	if ( a.getNumberreps() == 0 )
	        		aux = "Antes das refeições, ";
	        	
	        	tv_description.setText(aux + a.getActionDescription());
	        	if (a.getActionDescription() == null)
	        		tv_description.setText(aux+ "Sem descrição.");
	        	bt_action.setText("Medir");
	        	onClickGoTo(getFragmentManager(), bt_action, MeasuresFragment.newInstance(1, a.getActionId()));  
	        	break;
	
	        case Drugtake:
	        	tv_title.setText(a.getActionDrugName());
	        	iv_liIcon.setImageResource(R.drawable.medicine);
	        	
	        	tv_description.setText(a.getActionDescription());
	        	
	        	fDateStart = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Timestamp.valueOf(a.getDateToStart()));
	        	tv_start_time.setText(fDateStart);
	        	FHour = cf.finalHour(a);
	        	fDateFinish = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
	        	tv_end_time.setText(fDateFinish);
	        	// ver se e antes das refeicoes
	        	aux = "";
	        	if ( a.getNumberreps() == 0 )
	        		aux = "Antes das refeições, ";
	        	tv_description.setText(aux + a.getActionDescription());
	        	if (a.getActionDescription() == null)
	        		tv_description.setText(aux+"Sem descrição.");
	
	        	bt_action.setText("Tomar");
	        	bt_action.setOnClickListener(new View.OnClickListener(){
	            	@Override
	                public void onClick(View v) {
	            		//TODO colocar na BD o medicamento como tomado em tal data a x horas
	            		MySQLiteHelper msh = new MySQLiteHelper(getActivity());
	            		Gson gs = new GsonBuilder().create();
		        		JsonHandler js = new JsonHandler();
		        		
	            		Date de = new Date(System.currentTimeMillis());
	            		Timestamp ts = new Timestamp(de.getTime());
	            		DrugTakeRegister dtr = new DrugTakeRegister(0, a.getActionId(), ts);
	            		
	            		String s = js.sendJson(prefs.getString("url_server", "")+"/PeiMaps/webresources/registerDrugTake?token="+patientToken+"&dev_id="+deviceId, gs.toJson(dtr));
	            		
	            		if (!s.equalsIgnoreCase("ok"))
		        			msh.addDrugTake(dtr, getActivity(), 0);
		        		else
		        			msh.addDrugTake(dtr, getActivity(), 1);	
	            		
		        		Toast.makeText(getActivity(), "Guardado como efectuado.", Toast.LENGTH_LONG).show();
		        		getFragmentManager().popBackStack();
	            	}
	            });
	        	onClickGoTo(getFragmentManager(), bt_history, HistoryFragment.newInstanceOpeningTab(2,2));
	        	break;
	
	        case Activity:
	        	tv_title.setText("Atividade");
	        	iv_liIcon.setImageResource(R.drawable.sports);
	        	tv_start_time.setText(a.getTimeToStart());
	        	tv_description.setText(a.getActionDescription());
	        	if (a.getActionDescription() == null)
	        		tv_description.setText("Descrição não disponivel.");
	        	bt_action.setVisibility(View.INVISIBLE);
	        	bt_history.setVisibility(View.INVISIBLE);
	        	
//	        	fDateStart = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(Timestamp.valueOf(a.getDateToStart()));
//	        	tv_start_time.setText(fDateStart);
//	        	FHour = cf.finalHour(a);
//	        	fDateFinish = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
//	        	tv_end_time.setText(fDateFinish);
//	        	// ver se e antes das refeicoes
//	        	aux = "";
//	        	if ( a.getNumberreps() == 0 )
//	        		aux = "Antes das refeições, ";
//	        	tv_description.setText(aux + a.getActionDescription());
        };
        return rootView;
    }
	
    private static void onClickGoTo(final FragmentManager fm, Button bt_action, final Fragment f){
    	bt_action.setOnClickListener(new View.OnClickListener(){
        	@Override
            public void onClick(View v) {
        	    FragmentTransaction fragmentTransaction = fm.beginTransaction();
        	    fragmentTransaction.replace(R.id.container, f);
        	    fragmentTransaction.addToBackStack(null);
        	    fragmentTransaction.commit();
        	}
        });
    }
        
	public static ActionsFragment_details newInstance(PAction a) {
		ActionsFragment_details fragment = new ActionsFragment_details();
		Bundle bundle = new Bundle();
		bundle.putSerializable(ACTION, a);
		fragment.setArguments(bundle);

		return fragment;
	}
}
