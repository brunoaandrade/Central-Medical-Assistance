package notificationSystem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.commonClasses.PAction;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class PrescriptionReceiver extends BroadcastReceiver {
	public PrescriptionReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        
		String action = intent.getAction();

		Log.i("Receiver", "Broadcast received: " + action);

		if(action.equals("NEW_PRESCRIPTION")){
			Toast.makeText(context, "NEW_PRESCRIPTION Detected.", Toast.LENGTH_LONG).show();
			//showNewPresNotification(context); 
			
			//para parar um scheduled
//			Intent intentstop = new Intent(context, TimeAlarm.class);
//			PendingIntent senderstop = PendingIntent.getBroadcast(context ,385004795, intentstop, PendingIntent.FLAG_UPDATE_CURRENT);
//			PendingIntent senderstop2 = PendingIntent.getBroadcast(context ,385004788, intentstop, PendingIntent.FLAG_UPDATE_CURRENT);
//			AlarmManager alarmManagerstop = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//			alarmManagerstop.cancel(senderstop);
//			alarmManagerstop.cancel(senderstop2);
			
		}
		
		if(action.equals("NEW_MEASURE")){
			//String state = intent.getExtras().getString("extra");
			Toast.makeText(context, "NEW_MEASURE Detected.", Toast.LENGTH_LONG).show();
			createScheduledNotification_Measure(context);
			//createScheduledNotification2(context);
		}
	}
	
	//ao passar os x dias da prescri�ao, cancelar o setRepeating do alarmManager, isto se nao for feita uma calendarizacao para cada repeticao
	private void createScheduledNotification_Measure(Context context)
	{
		int idAction = -2; 						// para o mesmo idAction pode haver varias notificacoes se n_repeticoes > 1
		String measureType = "Blood";	// Blood, Temperature
		long periodOfDays = 2;				// numero de dias em que accao deve ser realizada (nao pode ser < 1)
		int nOfRepetitions = 3;				// numero de repeticoes por dia (nao pode ser < 1)
		//primeiro dia a come�ar e hora de todos os dias a come�ar
		String timeToStart = "2014-06-03 14:41:00";
		
		// Retrieve alarm manager from the system
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		// Get new calendar object and set the date to now
		Calendar calendar = Calendar.getInstance();
		
		Timestamp time = Timestamp.valueOf(timeToStart);  	//"2014-05-19 17:10:00"
		calendar.setTimeInMillis(time.getTime());			//a hora e posta para conforme a data recebida (implica data e hora)
		
		int hh = calendar.get(Calendar.HOUR_OF_DAY);
		int mm = calendar.get(Calendar.MINUTE);
		int ss = calendar.get(Calendar.SECOND);
		
		int hourToStartInSec = hh*3600+mm*60+ss;
		int timeToDoInSec = (24*60*60) - hourToStartInSec;
		int breakTimeInSec = timeToDoInSec/nOfRepetitions;

		//data e hora para parar, ex: se comeca 2014-05-19 08:00:00 com 3 durante 10 dias, deve acabar em 2014-05-29 08:00:00
		long timeToFinish = time.getTime() + (periodOfDays*24*60*60*1000);
		
		ArrayList<Integer> idNoti_list = new ArrayList<Integer>();
		int id = 0;
		
		for (int i = 0; i < nOfRepetitions; i++)
		{
			// Add defined amount of days to the date
			//calendar.add(Calendar.SECOND, i * breakTimeInSec);
			calendar.add(Calendar.SECOND, i * 3); //teste
 
			//Every scheduled intent needs a different ID, else it is just executed once
			id = (int) System.currentTimeMillis();
			
			// Prepare the intent which should be launched at the date
			Intent intent = new Intent(context, TimeAlarm.class);
			intent.putExtra("idAction", idAction);
			intent.putExtra("actionType", "Measure");
			intent.putExtra("measureType", measureType);
			intent.putExtra("id", id);	//este id e o da notificacao, nada a ahaver com o idAction
			// Prepare the pending intent
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			// Register the alert in the system. You have the option to define if the device has to wake up on the alert or not
			//alarmManager.set(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), pendingIntent);
			//alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis(), 5*1000, pendingIntent);
			
			idNoti_list.add(id);
			Log.i("PresRec","BP ou T 11111111  one more notification 11111111");
		}
		//------------WHEN TO STOP SCHEDULING--------------
		Intent intentStop = new Intent(context, TimeAlarmToStop.class);
		//intentStop.putExtra("id", id);
		intentStop.putIntegerArrayListExtra("idNoti_list", idNoti_list);
		// id do pendingIntent nao pode ser i pois noutras accoes recebidas o for vai ser o mesmo sendo o i tambem, repondo o pendingIntent ja existente
		// o id corresponde a ultima notificacao criada.
		// tratar if nOfRepetitions == 0
		PendingIntent pendingIntentStop = PendingIntent.getBroadcast(context, (id+1), intentStop, PendingIntent.FLAG_UPDATE_CURRENT); //ate onde dara isto??
		// hora inicial + o n de dias
		//alarmManager.set(AlarmManager.RTC_WAKEUP ,timeToFinish, pendingIntentStop);
		alarmManager.set(AlarmManager.RTC_WAKEUP ,calendar.getTimeInMillis()+20*1000, pendingIntentStop); //teste
		
		Log.i("PresRec","BP ou T 11111111 make stop notificationS 11111111");
	}
	
	private void showNewPresNotification(Context context)
	{
		
		// Request the notification manager
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	 
		 // Create a new intent which will be fired if you click on the notification
		 Intent intentMain = new Intent(context, MainActivity.class);
		 intentMain.setAction("OPEN_PRESCRIPTIONS"); //OPEN_PRESCRIPTIONS  onStart limpar a stack e abrir o fragmento correspondente
		 intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP); //funciona sem estas flags
		 //intentMain.addCategory(Intent.CATEGORY_LAUNCHER);
		 
		 // Attach the intent to a pending intent						        0 = intent_id
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentMain, PendingIntent.FLAG_UPDATE_CURRENT);
		 
		 // Create the notification
		 Notification notification =
				    new NotificationCompat.Builder(context)
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setContentTitle("Nova accao prescrita")
				    .setContentText("Clique para ver accoes a fazer")
		 			.setWhen(System.currentTimeMillis())
		 			.setAutoCancel(true)
		 			.setContentIntent(pendingIntent)
		 			.build(); 
		 // Fire the notification
		 notificationManager.notify(0, notification);
	}
}
