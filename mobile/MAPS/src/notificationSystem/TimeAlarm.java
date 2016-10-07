package notificationSystem;

import com.app.maps.MainActivity;
import com.app.maps.R;
import com.app.maps.R.drawable;
import com.app.maps.fragments.MeasuresFragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class TimeAlarm extends BroadcastReceiver {
	public TimeAlarm() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String resto = "";
		int flagBeforeMeal = intent.getExtras().getInt("beforeMeal");
		
		int idNoti = intent.getExtras().getInt("id");
		String actionType = intent.getExtras().getString("actionType");
		int idAction = intent.getExtras().getInt("idAction");
		
		String actionToDo = "default";
		String contentTitle = "ops";
		String contentText = "algo de errado";
		
		if (flagBeforeMeal == 1)	//se flag antes das refeicoes ativa o resto da string
			resto = "antes da refeicao";

		
		// se for para fazer MEDIDA
		if(actionType.equals("Measure"))
		{
			String measureType = intent.getExtras().getString("measureType");
			// se for para fazer MEDIDA DE PRESSAO ARTERIAL
			if(measureType.equals("Blood"))
			{
				actionToDo = "OPEN_TAB_MEASURES";		//deve abrir para o sensor Omron
				contentTitle = "Medir Pressao Arterial "+resto;
				contentText = "Clique para prosseguir";
			}
			// se for para fazer MEDIDA DE TEMPERATURA
			if(measureType.equals("Temperature"))
			{
				actionToDo = "OPEN_TAB_MEASURES";		//deve abrir para o sensor Tag
				contentTitle = "Medir Temperatura Corporal "+resto;
				contentText = "Clique para prosseguir";
			}
		}
		
		// se for para fazer TOMA DE MEDICAMENTO
		if(actionType.equals("Drugtake"))
		{
			String drugName = intent.getExtras().getString("drugName");
			String description = intent.getExtras().getString("description");
			
			actionToDo = "OPEN_PRESCRIPTIONS";
			contentTitle = "Tomar "+ drugName +" "+resto;
			contentText = description;
		}
		
		// se for para fazer ACTIVIDADE
		if(actionType.equals("Activity"))
		{
			String description = intent.getExtras().getString("description");
			
			actionToDo = "OPEN_PRESCRIPTIONS";
			contentTitle = "Actividade para fazer "+resto;
			contentText = description;
		}
		
		 // Request the notification manager
		 NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		 int idPending = (int) System.currentTimeMillis();
		 
		 // Create a new intent which will be fired if you click on the notification
		 Intent intentToDo = new Intent(context, MainActivity.class);
		 intentToDo.setAction(actionToDo);
		 //Log.i("TimeAlarm actionTodo", actionToDo);
		 intentToDo.putExtra("idAction", idAction);
		 intentToDo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 //intentMeasureBP.addCategory(Intent.CATEGORY_LAUNCHER);
		 
		 // Attach the intent to a pending intent
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, idPending, intentToDo, PendingIntent.FLAG_UPDATE_CURRENT);

		 // Create the notification
		 Notification notification =
				    new NotificationCompat.Builder(context)
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setContentTitle(contentTitle)
				    .setContentText(contentText) //.setContentText(contentText+", id:"+ idNoti)
		 			.setWhen(System.currentTimeMillis())
		 			.setAutoCancel(true)
		 			.setContentIntent(pendingIntent)
		 			.build();
		 
		 // Fire the notification
		 notificationManager.notify(idNoti, notification);
	}
}
