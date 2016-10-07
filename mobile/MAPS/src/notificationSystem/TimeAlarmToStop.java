package notificationSystem;

import java.util.ArrayList;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeAlarmToStop extends BroadcastReceiver {
	public TimeAlarmToStop() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//int id = intent.getExtras().getInt("id");
		ArrayList<Integer> idNoti_list = intent.getIntegerArrayListExtra("idNoti_list");
		
		Log.i("PresRec","AQUIIIIIIIIIIIII 22222222222222222");
		for(Integer id : idNoti_list)
		{
			//Make the copy of the intent to eliminate from schedule
			Intent intentCopy = new Intent(context, TimeAlarm.class);
			PendingIntent pendingIntentCopy = PendingIntent.getBroadcast(context, id, intentCopy, PendingIntent.FLAG_UPDATE_CURRENT);
			
			AlarmManager alarmManagerstop = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManagerstop.cancel(pendingIntentCopy);	
		}
		Log.i("PresRec","AQUIIIIIIIIIIIII 3333333333333333");
	}
}
