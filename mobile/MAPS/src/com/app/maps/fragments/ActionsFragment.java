package com.app.maps.fragments;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import notificationSystem.CalculateFeedback;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.commonClasses.PAction;
import com.app.maps.commonClasses.PAction.ActionType;
import com.app.maps.commonClasses.PAction.MeasureType;
import com.app.maps.databaseLocal.MySQLiteHelper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewDebug.FlagToString;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ActionsFragment extends ListFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ACTION_ID = "actionId";  //added
	private static final String FLAG_FROM_NOTI = "flagFromNoti";  //added
	private int actionId; //added
	private boolean flagFromN; //added, se for true colocar com highlight a linha que corresponde ao actionId recebido
	
    private ArrayList<PAction> a;
    private int patientId;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    
    public static ActionsFragment newInstance(int sectionNumber, int actionId, boolean fromNoti) { //added
    	ActionsFragment fragment = new ActionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(ACTION_ID, actionId);
        args.putBoolean(FLAG_FROM_NOTI, fromNoti);
        fragment.setArguments(args);
        return fragment;
    }

    public ActionsFragment() {
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      MySQLiteHelper msh = new MySQLiteHelper(getActivity());
//      MAPSActionsRowAdapter adapter = new MAPSActionsRowAdapter(getActivity(),
//          R.layout.list_item_action_layout, fakePresc());
      a = msh.getAllActionPaciente(patientId);
      MAPSActionsRowAdapter adapter = new MAPSActionsRowAdapter(getActivity(),
              R.layout.list_item_action_layout, a, actionId, flagFromN, patientId);
      setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	//Get patientID
		SharedPreferences prefs =  getActivity().getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		patientId = prefs.getInt("patientID", -1); 
		
        View rootView = inflater.inflate(R.layout.fragment_prescriptions, container, false);
        
        //Force screen orientation to stay in portrait
    	getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
    	actionId = getArguments().getInt(ACTION_ID); //added
    	flagFromN = getArguments().getBoolean(FLAG_FROM_NOTI); //added
    	
        return rootView;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      // do something with the data
    	Fragment f = ActionsFragment_details.newInstance(a.get(position));
	    FragmentManager fragmentManager = getFragmentManager();
	    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	    fragmentTransaction.replace(R.id.container, f);
	    fragmentTransaction.addToBackStack(null);
	    fragmentTransaction.commit();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

//esta a correr varias vezes para cada accao, nao sei porque (colocar um print firenciado por iteracao no getView para exemplificar)
class MAPSActionsRowAdapter extends ArrayAdapter<PAction> {
	
	ArrayList<PAction> data;
	int layoutID;
	Context context;
	int actionId;  //added
	boolean flagFromNoti;  //added
	int patientId; //added
	
	public MAPSActionsRowAdapter(Context context, int layoutID, ArrayList<PAction> data, int actionId, boolean falgFromNoti, int patientId) {
		   super(context, layoutID, data);
		   this.data=data;
		   this.context=context;
		   this.layoutID=layoutID;
		   this.actionId = actionId;  //added
		   this.flagFromNoti = falgFromNoti;  //added
		   this.patientId = patientId; //added
		}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			//Log.i("getView", "----------------------inicio----------------------------");
			
		   LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           View rowView = inflater.inflate(layoutID, parent, false);
           
           TextView tv_title = (TextView) rowView.findViewById(R.id.prescription_name);
           TextView tv_finish = (TextView) rowView.findViewById(R.id.prescription_finish);
           TextView tv_nextHour = (TextView) rowView.findViewById(R.id.next_hour);   
           ImageView iv_liIcon = (ImageView) rowView.findViewById(R.id.p_type_icon);
           ImageView iv_warningIcon = (ImageView) rowView.findViewById(R.id.img_warning);
           iv_warningIcon.setVisibility(View.INVISIBLE);
           
           CalculateFeedback cf = new CalculateFeedback();
           Timestamp FHour;	//data final
           String sFHour;
           Timestamp NHour;	//proxima data
           String sNHour;
           int howManyWarning;
           
           try {
			switch (data.get(position).getActionType()) {

				case Measure:
					
					if(flagFromNoti){  //added
						if(data.get(position).getActionId() == actionId)
							rowView.setBackgroundColor(Color.argb(150, 255, 221, 0));
					}
					if (data.get(position).getActionMeasureType() == MeasureType.Blood) {
						tv_title.setText("Pressão arterial");
						iv_liIcon.setImageResource(R.drawable.blood_pressure);
					} else {
						tv_title.setText("Temperatura");
						iv_liIcon.setImageResource(R.drawable.thermometer);
					}
					
					//fnishhour
					FHour = cf.finalHour(data.get(position));
					sFHour = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
					tv_finish.setText("Acaba: "+sFHour);
					//nexthour
					NHour = cf.nextHour(cf.hoursToDo(data.get(position)), FHour);
					if (NHour != null){
						sNHour = new SimpleDateFormat("HH:mm:ss").format(NHour);
						tv_nextHour.setText(sNHour);
					}
					else
						tv_nextHour.setText("não há");
					
					howManyWarning = cf.warnings(context, data.get(position), patientId);
					//Log.i("getView", "n warning: "+howManyWarning);
					if (howManyWarning > 0)
						iv_warningIcon.setVisibility(View.VISIBLE);
					break;
	
				case Drugtake:
					
					if(flagFromNoti){  //added
						if(data.get(position).getActionId() == actionId)
							rowView.setBackgroundColor(Color.argb(150, 255, 221, 0));
					}
					tv_title.setText("Tomar: "+data.get(position).getActionDrugName());
					iv_liIcon.setImageResource(R.drawable.medicine);
					//fnishhour
					FHour = cf.finalHour(data.get(position));
					sFHour = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
					tv_finish.setText("Acaba: "+sFHour);
					//nexthour
					NHour = cf.nextHour(cf.hoursToDo(data.get(position)), FHour);
					if (NHour != null){
						sNHour = new SimpleDateFormat("HH:mm:ss").format(NHour);
						tv_nextHour.setText(sNHour);
					}
					else
						tv_nextHour.setText("não há");
					
					howManyWarning = cf.warnings(context, data.get(position), patientId);
					//Log.i("getView", "n warning: "+howManyWarning);
					if (howManyWarning > 0)
						iv_warningIcon.setVisibility(View.VISIBLE);
					break;
	
				case Activity:
					
					if(flagFromNoti){  //added
						if(data.get(position).getActionId() == actionId)
							rowView.setBackgroundColor(Color.argb(150, 255, 221, 0));
					}
					tv_title.setText("Atividade");
					iv_liIcon.setImageResource(R.drawable.sports);
					//fnishhour
					FHour = cf.finalHour(data.get(position));
					sFHour = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(FHour);
					tv_finish.setText("Acaba: "+sFHour);
					//nexthour
					NHour = cf.nextHour(cf.hoursToDo(data.get(position)), FHour);
					if (NHour != null){
						sNHour = new SimpleDateFormat("HH:mm:ss").format(NHour);
						tv_nextHour.setText(sNHour);
					}
					else
						tv_nextHour.setText("não há");
					
					howManyWarning = cf.warnings(context, data.get(position), patientId);
					//Log.i("getView", "n warning: "+howManyWarning);
					if (howManyWarning > 0)
						iv_warningIcon.setVisibility(View.VISIBLE);
					break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
          //Log.i("getView", "-----------------------fim-----------------------------");
		return rowView;
           
	}
}