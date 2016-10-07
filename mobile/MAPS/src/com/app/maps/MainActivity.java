package com.app.maps;

import com.app.maps.commonClasses.PAction;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.fragments.ActionsFragment;
import com.app.maps.fragments.HistoryFragment;
import com.app.maps.fragments.MeasuresFragment;
import com.app.maps.fragments.MyaccountFragment;
import com.app.maps.fragments.NavigationDrawerFragment;
import com.app.maps.services.ReceiveAction;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v4.widget.DrawerLayout;

public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    
    //patient objecto que contém o ID do paciente actualmente logado no sistema, recebido pela actividade "LoginActivity".
//    public PatientToken patient;

    private static final String TAG = "SampleActivity";
	private static final boolean VERBOSE = true;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //TODO APAGAR com colocar o Login a funcionar.
//        SharedPreferences prefs = getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
//		prefs.edit().putInt("patientID", 1).commit();
		
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
        Intent serviceIntent = new Intent(getApplicationContext(),ReceiveAction.class);
        startService(serviceIntent);
        
    }
    
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (position){
        case 0:
        	ft.addToBackStack("Fragment1");
        	ft.replace(R.id.container, ActionsFragment.newInstance(position, -1, false)).commit();
        	break;
        case 1:
        	ft.addToBackStack("Fragment2");
        	ft.replace(R.id.container, MeasuresFragment.newInstance(position, -1)).commit();
        	break;
        case 2:
        	ft.addToBackStack("Fragment3");
        	ft.replace(R.id.container, HistoryFragment.newInstance(position)).commit();
        	break;
        case 3:
        	ft.addToBackStack("Fragment4");
        	ft.replace(R.id.container, MyaccountFragment.newInstance(position)).commit();
        	break;
        case 4:
        	ft.addToBackStack("Fragment5");
        	logout();
        	break;
        }
    }
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fm = getSupportFragmentManager();
        
        String stackName = null;
        ListView drawer_view = (ListView) mNavigationDrawerFragment.getView();
        for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
            stackName = fm.getBackStackEntryAt(entry).getName();
//            Log.i("BC", "stackEntry" + entry);
        }
        if (stackName == "Fragment1"){
        	drawer_view.setItemChecked(0, true);
        	getActionBar().setTitle(R.string.title_section1);
        	onSectionAttached(0);
        } else if (stackName == "Fragment2") {
        	drawer_view.setItemChecked(1, true);
        	getActionBar().setTitle(R.string.title_section2);
        	onSectionAttached(1);
        } else if (stackName == "Fragment3") {
        	drawer_view.setItemChecked(2, true);
        	getActionBar().setTitle(R.string.title_section3);
        	onSectionAttached(2);
        } else if (stackName == "Fragment4") {
        	drawer_view.setItemChecked(3, true);
        	getActionBar().setTitle(R.string.title_section5);
        	onSectionAttached(3);
        } else if (fm.getBackStackEntryCount() == 0)
        	finish();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
//            case 3:
//                mTitle = getString(R.string.title_section4);
//                break;
            case 3:
            	mTitle = getString(R.string.title_section5);
            	break;
            case 4:
            	mTitle = getString(R.string.logout);
            	break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    
    private void logout(){


    	final MySQLiteHelper bd = new MySQLiteHelper(this);

    	boolean bol=bd.hasNotSaved();
    	Log.d("hasdata",bol+"");
    	if(bol == false)
    	{
    		bd.deletAll();
    		//insert patient ID into application shared preferences
    		SharedPreferences prefs = getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
    		prefs.edit().putInt("patientID", -1).commit();
    		prefs.edit().putString("patientToken", "").commit();

    		Intent serviceIntent = new Intent(getApplicationContext(),ReceiveAction.class);
    		stopService(serviceIntent);

    		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    		intent.setAction("AFTER_LOGOUT");
    		startActivity(intent);
    		finish();
    	}
    	else{

    		
    		AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
    		builder1.setMessage("Há dados não enviados para a base de dados tem a certeza que quer eliminar os dados todos?");
    		builder1.setCancelable(true);
    		builder1.setPositiveButton("Sim",new DialogInterface.OnClickListener()
    		{
    			
    			public void onClick(DialogInterface dialog, int id)
    			{
    				bd.deletAll();
    				//insert patient ID into application shared preferences
    	    		SharedPreferences prefs = getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
    	    		prefs.edit().putInt("patientID", -1).commit();
    	    		prefs.edit().putString("patientToken", "").commit();

    	    		Intent serviceIntent = new Intent(getApplicationContext(),ReceiveAction.class);
    	    		stopService(serviceIntent);

    	    		Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    	    		intent.setAction("AFTER_LOGOUT");
    	    		startActivity(intent);
    	    		finish();
    				
    				dialog.cancel();
    			}
    		});
    		
    		builder1.setNegativeButton("No", new DialogInterface.OnClickListener() 
    		{
    			public void onClick(DialogInterface dialog, int id) 
    			{
    				dialog.cancel();
    			}
    		});

    		AlertDialog alert11 = builder1.create();
    		alert11.show();

    	}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
			logout();
            return true;
        }
        
        if (id == R.id.settings) {
        	Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			intent.setAction("OPEN_SETTINGS");
		    startActivity(intent);
        }
        
//        if (id == R.id.to_activities){
//    	    FragmentManager fragmentManager = getSupportFragmentManager();
//        	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        	fragmentTransaction.replace(R.id.container, ActionsFragment.newInstance(0));
//    	    fragmentTransaction.addToBackStack("Fragment1");
//    	    fragmentTransaction.commit();
//        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // getIntent() should always return the most recent
        setIntent(intent);
        Log.i("onNewIntent actionTodo", intent.getAction());
        Log.i("onNewIntent", "a intent was catched");
    }
    
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (VERBOSE) Log.v(TAG, "++ ON START ++");
//    }

   @Override
    public void onResume() {
        super.onResume();
        if (VERBOSE) Log.v(TAG, "+ ON RESUME +");

        Intent i = getIntent();
        FragmentManager fm = this.getSupportFragmentManager();
        
        //abrir a zona apropriada como fosse a partir do inicio
        //para isso retirar os fragmentos
        if(!i.getAction().equals("AFTER_SOMETHING"))
        {
        	for(int x = 0; x < fm.getBackStackEntryCount() - 1; ++x) {    
        	    fm.popBackStack();
        	}
        }
        	
        if(i.getAction().equals("OPEN_PRESCRIPTIONS"))
        {
        	Log.i("onResume", "OPEN_PRESCRIPTIONS catched, with idAction = " +i.getExtras().getInt("idAction"));
        	//onNavigationDrawerItemSelected(0);
        	//true, fragmento instanciado com a flag "de notificacao" ativa, para fazer higlight
        	//a linha correspondente da notificacao
        	fm.beginTransaction().addToBackStack(null)	
    			.replace(R.id.container, ActionsFragment.newInstance(0, i.getExtras().getInt("idAction"), true)).commit();
        }
        
        if(i.getAction().equals("OPEN_TAB_MEASURES"))
        {
        	Log.i("onResume", "OPEN_TAB_MEASURES catched, with idAction = " +i.getExtras().getInt("idAction"));
        	//onNavigationDrawerItemSelected(1);
        	fm.beginTransaction().addToBackStack(null)
        		.replace(R.id.container, MeasuresFragment.newInstance(1, i.getExtras().getInt("idAction") )).commit();
        }
        
        i.setAction("AFTER_SOMETHING");
        Log.i("onResume", "after setAction, action = "+ i.getAction().toString());
        setIntent(i);
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        if (VERBOSE) Log.v(TAG, "- ON PAUSE -");
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (VERBOSE) Log.v(TAG, "-- ON STOP --");
//    }
//
//   @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (VERBOSE) Log.v(TAG, "- ON DESTROY -");
//    }
//   @Override
//   
//   public void onWindowFocusChanged(boolean hasFocus) {
//       super.onWindowFocusChanged(hasFocus);
//
//     if(hasFocus)
//    	 if (VERBOSE) Log.v(TAG, "ON WINDOWS FOCUS CHANGED - TRUE ");
//     
//     if(!hasFocus)
//    	 if (VERBOSE) Log.v(TAG, "ON WINDOWS FOCUS CHANGED - FALSE ");
//    	 
//   }

}


class ConnectionHandler extends AsyncTask<Intent, Void, String>{
	
	protected String doInBackground(Intent... t) {
		
		return null;
	}
}