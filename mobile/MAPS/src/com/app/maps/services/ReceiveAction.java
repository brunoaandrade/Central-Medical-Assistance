package com.app.maps.services;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import notificationSystem.TimeAlarm;
import notificationSystem.TimeAlarmToStop;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.commonClasses.AndroidStuff;
import com.app.maps.commonClasses.BloodPressureMeasure;
import com.app.maps.commonClasses.DrugTakeRegister;
import com.app.maps.commonClasses.PAction;
import com.app.maps.commonClasses.TemperatureMeasure;
import com.app.maps.databaseLocal.MySQLiteHelper;
import com.app.maps.services.MessageConsumer.OnReceiveMessageHandler;
import com.google.gson.*;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class ReceiveAction extends Service{

	private MessageConsumer mConsumer;
	private int patientId;
	int NOTIFICATION = R.string.sync_service;
	Gson gs;
	private SharedPreferences prefs;
	
	NotificationManager mNM;

	
	
	@Override
    public void onCreate() {
		
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		//Get Server IP from SharedPreferences
        prefs =  PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        URL url = null;
        try {
			url = new URL(prefs.getString("url_server", ""));
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        //Create the consumer
        mConsumer = new MessageConsumer(url.getHost(),
                "action",
                "fanout");
        
        //Send message to a dedicated patient queue on server
        
		SharedPreferences prefs =  this.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);
		patientId = prefs.getInt("patientID", -1); 
 
		new Thread( new Runnable() {
			@Override
			public void run() {

				try {
					//Connect to broker
					mConsumer.setPatientIdQueue(patientId);
					mConsumer.setAndroidIdQueue(Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID));
					mConsumer.connectToRabbitMQ();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				//register for messages
				mConsumer.setOnReceiveMessageHandler(new OnReceiveMessageHandler(){

					public void onReceiveMessage(byte[] message) {
						String text = "";
						try {
							text = new String(message, "UTF8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						gs = new GsonBuilder().create();
						AndroidStuff as = gs.fromJson(text, AndroidStuff.class);
						MySQLiteHelper msh = new MySQLiteHelper(getBaseContext());
						switch (as.getAndroidStuffType()){

						case Action:
							PAction an = as.getpAction();
							msh.addAction(an, getBaseContext());
							/*avisa novas prescricoes
							ocorrer spam de notificacoes 
							de novas prescricoes se na queue estiverem varias mensagens a espera de serem recebidas*/
							//showNewPresNotification(); 
							if(!an.getActionType().toString().equals("Activity"))  //tirar quando as actividades tiverem a vir com valores nao null
								createScheduledNotification(an);
							break;

						case Blood:
							BloodPressureMeasure bpm = as.getBloodPressureMeasure();
							msh.addPressao(bpm, getBaseContext(), 1);
							break;

						case Temperature:
							TemperatureMeasure tm = as.getTemperatureMeasure();
							msh.addTemp(tm, getBaseContext(), 1);
							break;

						case DrugTake:
							//Log.i("ReceiveAction","Recebi Drugtake");
							DrugTakeRegister dtr = as.getDrugTakeRegister();
							msh.addDrugTake(dtr, getBaseContext(), 1);
							break;
						}		
					}
				});
			}
		}).start();
 
    }
	
	// ------------------MAKE MEASURE / DRUG TAKE------------------
	private void createScheduledNotification(PAction pa)
	{
		int flagBeforeM = 0;	// flag para tratar de casos se o n de repeticoes for 0, interpretar como antes das refeicoes
		
		int idAction = pa.getActionId(); 				// para o mesmo idAction pode haver varias notificacoes se n_repeticoes > 1
		Log.i("ReceiveAct - createNotification","idAction: "+idAction);
		long periodOfDays = (long) pa.getPeriod();		// numero de dias em que accao deve ser realizada (nao pode ser < 1)
		int nOfRepetitions = pa.getNumberreps();		// numero de repeticoes por dia (nao pode ser < 1), se 0 antes das refeicoes
		String timeToStart = pa.getTimeToStart();		//primeiro dia a comecar e hora de todos os dias a comecar
		
		// Retrieve alarm manager from the system
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		// Get new calendar object and set the date to now
		Calendar calendar = Calendar.getInstance();
		
		Timestamp time = Timestamp.valueOf(timeToStart);  	//"2014-05-19 17:10:00"
		calendar.setTimeInMillis(time.getTime());			//a hora e posta para conforme a data recebida (implica data e hora)
		int breakTimeInSec;
		//------------n repeticoes = 0
		if(nOfRepetitions == 0)
		{
			flagBeforeM = 1;
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			
			time = Timestamp.valueOf(year+"-"+month+"-"+day+" 12:00:00"); // comeca sempre as 8 (pequeno almoco)
			breakTimeInSec = 28800;	// 8h em sec
			nOfRepetitions = 2;	 		// 12h e 20h
		}
		else
		{
			int hh = calendar.get(Calendar.HOUR_OF_DAY);
			int mm = calendar.get(Calendar.MINUTE);
			int ss = calendar.get(Calendar.SECOND);
			
			int hourToStartInSec = hh*3600+mm*60+ss;
			int timeToDoInSec = (24*60*60) - hourToStartInSec;
			breakTimeInSec = timeToDoInSec/nOfRepetitions;
			Log.i("ReceiveAct - createNotification", "breakTime em Min: "+breakTimeInSec/60+"");
		}
		//data e hora para parar, ex: se comeca 2014-05-19 08:00:00 com 3 durante 10 dias, deve acabar em 2014-05-29 08:00:00
		long timeToFinish = time.getTime() + (periodOfDays*24*60*60*1000);
		
		ArrayList<Integer> idNoti_list = new ArrayList<Integer>();
		int id = 0;
		
		for (int i = 0; i < nOfRepetitions; i++)
		{
			// Add defined amount of days to the date
			calendar.add(Calendar.SECOND, (i * breakTimeInSec) -120);
			Timestamp ts = new Timestamp(calendar.getTimeInMillis()); //DEBUG
			Log.i("ReceiveAct - createNotification", "next hour em timestamp: "+ts.toString()+"");
			//calendar.add(Calendar.SECOND, i * 3); //teste
 
			//Every scheduled intent needs a different ID, else it is just executed once
			id = (int) System.currentTimeMillis();
			
			// Prepare the intent which should be launched at the date
			Intent intent = new Intent(this, TimeAlarm.class);
			intent.putExtra("beforeMeal", flagBeforeM);
			intent.putExtra("idAction", idAction);
			intent.putExtra("id", id);	//este id e o da notificacao, nada a ahaver com o idAction
			if(pa.getActionType().toString().equals("Measure"))
			{
				intent.putExtra("actionType", "Measure");
				intent.putExtra("measureType", pa.getMeasureType().toString());	// Blood, Temperature
			}
			if(pa.getActionType().toString().equals("Drugtake"))
			{
				intent.putExtra("actionType", "Drugtake");
				intent.putExtra("drugName", pa.getDrugName());
				intent.putExtra("description", pa.getDescription());
			}
			if(pa.getActionType().toString().equals("Activity"))
			{
				intent.putExtra("actionType", "Activity");
				intent.putExtra("description", pa.getDescription());
			}
			
			// Prepare the pending intent
			PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			// Register the alert in the system. You have the option to define if the device has to wake up on the alert or not
			//alarmManager.set(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), pendingIntent);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
			//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), 5*1000, pendingIntent); //teste
			
			idNoti_list.add(id);
			Log.i("PresRec","BP ou T 11111111  one more notification 11111111");
		}
		//------WHEN TO STOP SCHEDULING------
		Intent intentStop = new Intent(this, TimeAlarmToStop.class);
		//intentStop.putExtra("id", id);
		intentStop.putIntegerArrayListExtra("idNoti_list", idNoti_list);
		// id do pendingIntent nao pode ser i pois noutras accoes recebidas o for vai ser o mesmo sendo o i tambem, repondo o pendingIntent ja existente
		// o id corresponde a ultima notificacao criada.
		// tratar if nOfRepetitions == 0
		PendingIntent pendingIntentStop = PendingIntent.getBroadcast(this, (id+1), intentStop, PendingIntent.FLAG_UPDATE_CURRENT); //ate onde dara isto??
		// hora inicial + o n de dias
		alarmManager.set(AlarmManager.RTC_WAKEUP ,timeToFinish, pendingIntentStop);
		//alarmManager.set(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis() + 20*1000, pendingIntentStop); //teste
		Log.i("PresRec","BP ou T 11111111 make stop notificationS 11111111");
	}
	
	//private void createScheduledNotification_Activity(PAction pa){}
	
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
	
	private void showNewPresNotification() {
		
		// Request the notification manager
		NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
	 
		 // Create a new intent which will be fired if you click on the notification
		 Intent intentMain = new Intent(this, MainActivity.class);
		 intentMain.setAction("OPEN_PRESCRIPTIONS"); //OPEN_PRESCRIPTIONS  onStart limpar a stack e abrir o fragmento correspondente
		 intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); //funciona sem estas flags
		 //intentMain.addCategory(Intent.CATEGORY_LAUNCHER);
		 
		 // Attach the intent to a pending intent						        0 = intent_id
		 PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Create the notification
		 Notification notification =
				    new NotificationCompat.Builder(getBaseContext())
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setContentTitle("Nova accao prescrita")
				    .setContentText("Clique para ver accao a fazer")
		 			.setWhen(System.currentTimeMillis())
		 			.setAutoCancel(true)
		 			.setContentIntent(pendingIntent)
		 			.build(); 
		 // Fire the notification
		 notificationManager.notify(0, notification);
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
