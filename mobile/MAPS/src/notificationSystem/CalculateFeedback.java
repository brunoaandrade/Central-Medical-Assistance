package notificationSystem;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.util.Log;

import com.app.maps.commonClasses.PAction;
import com.app.maps.databaseLocal.MySQLiteHelper;

public class CalculateFeedback {
	
	public CalculateFeedback ()
	{
		
	}
	//calcula as horas do dia em que a accao tem de ser feita, para os Details, so deve ser chamada até ao ultimo dia(periodOfDays), no final do ultimo dia destruir a accao
	public ArrayList<Timestamp> hoursToDo (PAction pa)
	{
		//ter cuidado se houver cenas a null, prnicipalmente as datas
		int nOfRepetitions = pa.getNumberreps();			// numero de repeticoes por dia (nao pode ser < 1), se 0 antes das refeicoes
		String timeToStart = pa.getTimeToStart();			// primeiro dia a comecar e hora de todos os dias a comecar
		ArrayList<Timestamp> hourArray = new ArrayList<Timestamp>();
		//Log.i("CalculateFeedB timeToStart",timeToStart);
		Calendar calendar = Calendar.getInstance();			// Get new calendar object and set the date to now
		Timestamp time = Timestamp.valueOf(timeToStart);	// ex: "2014-05-19 17:10:00"
		calendar.setTimeInMillis(time.getTime());
		int hh, mm, ss;
		Timestamp NextH;
		
		//------------n repeticoes = 0, antes das refeicoes
		if(nOfRepetitions == 0)
		{	    
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			NextH = new Timestamp(calendar.getTimeInMillis());
			hourArray.add(NextH);
			calendar.set(Calendar.HOUR_OF_DAY, 20);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			NextH = new Timestamp(calendar.getTimeInMillis());
			hourArray.add(NextH);
		}
		else
		{
			int breakTimeInSec;							// intervalos de tempo durante as accoes diarias
			
			hh = calendar.get(Calendar.HOUR_OF_DAY);
			mm = calendar.get(Calendar.MINUTE);
			ss = calendar.get(Calendar.SECOND);
			
			int hourToStartInSec = hh*3600+mm*60+ss;
			int timeToDoInSec = (24*60*60) - hourToStartInSec;
			breakTimeInSec = timeToDoInSec/nOfRepetitions; //se arredonda para cima e BANM, APONTA PARA OUTRO DIA
			
			//ver para nRepeti = 0
			for (int i=0; i<nOfRepetitions; i++)
			{
				// Add defined amount of days to the date
				calendar.add(Calendar.SECOND, i * breakTimeInSec);
				NextH = new Timestamp(calendar.getTimeInMillis());
				hourArray.add(NextH);
			}
		}
		return hourArray;  //retorna com os dias da data timeTostart, so queremos mexer nas horas
	}
	
	
	//calcula a próxima hora e dia para fazer a accao
	public Timestamp nextHour (ArrayList<Timestamp> hourArray, Timestamp finishDate)
	{
		int hh, mm, ss;	
		Timestamp nextH = null;
		Timestamp possibleAux;
		
		//get hora atual em milis
		long actual = System.currentTimeMillis();
		Calendar calActual = Calendar.getInstance();
		calActual.setTimeInMillis(actual);
		
		for(int i = 0; i < hourArray.size(); i++)
		{
			Calendar calNext = Calendar.getInstance();
			calNext.setTimeInMillis(hourArray.get(i).getTime()); //buscar as horas da accao seguinte
			hh = calNext.get(Calendar.HOUR_OF_DAY);
			mm = calNext.get(Calendar.MINUTE);
			ss = calNext.get(Calendar.SECOND);
			
			calNext.setTimeInMillis(actual);		//nao sei se e necessario
			calNext.set(Calendar.HOUR_OF_DAY, hh);
			calNext.set(Calendar.MINUTE, mm);
			calNext.set(Calendar.SECOND, ss);

			if (calActual.before(calNext)) {			// se hora atual menor que hora seguinte
				nextH = new Timestamp(calNext.getTimeInMillis());		// nextH = hora seguinte
			    break;
			}
			//se hora atual > que todas as horas seguintes, entao nextH e a primeira do array mas do dia seguinte(tratar das datas que podem ja nao existir no futuro)
			if (i == (hourArray.size()-1) )
			{
				possibleAux = hourArray.get(0);  //em prnicipio existira sempre o incice 0, um repeticao por dia no minimo
				Calendar calAux = Calendar.getInstance();
				calAux.setTimeInMillis(possibleAux.getTime());
				hh = calAux.get(Calendar.HOUR_OF_DAY);
				mm = calAux.get(Calendar.MINUTE);
				ss = calAux.get(Calendar.SECOND);
				
				calAux.setTimeInMillis(actual);		//nao sei se e necessario
				calAux.set(Calendar.HOUR_OF_DAY, hh);
				calAux.set(Calendar.MINUTE, mm);
				calAux.set(Calendar.SECOND, ss);
				calAux.add(Calendar.DATE, 1);
				//se o dia seguinte for para acabar, nextH = null, sem proxima
				Timestamp possibleNextH = new Timestamp(calAux.getTimeInMillis());
				if(possibleNextH.getTime() >= finishDate.getTime())
					nextH = null;
				else
					nextH = possibleNextH;
				break; // nao necessario pois o for vai acabar asseguir
			}
		}
		return nextH;
	}
	
	//calcula as accoes com warnings e quantas vezes tem de ser feitas para desaparecer o warning
	//tem de se ter em conta que cada accao deve ter um determinado tempo para ser feito, passado esse tempo deve expirar
	//o botao medir no actionFrgments_details deverá estar ativo só quando ha accoes pendentes
		//(ex:mal uma notificacao e recebida ou depois de a receber a e accao nao ter sido completada)
	public int warnings (Context context, PAction pa, int patientId)
	{
		MySQLiteHelper msh = new MySQLiteHelper(context);
		
		int hh, mm, ss;
		boolean status = true;
		int nPending = 0;
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		ArrayList<Timestamp> hourL = hoursToDo(pa);	//vai buscar as horas a fazer a accao
		
		for (int i = 0; i < hourL.size(); i++)	//so precisa de ir ate horas(pre estimadas) antes da atual
		{
			//Log.i("warnings function","hourL: "+hourL.get(i));
			
			cal.setTimeInMillis(hourL.get(i).getTime());
			hh = cal.get(Calendar.HOUR_OF_DAY);
			mm = cal.get(Calendar.MINUTE);
			ss = cal.get(Calendar.SECOND);
			
			long actualTime = System.currentTimeMillis();
			
			cal.setTimeInMillis(actualTime);		//nao sei se e necessario
			cal.set(Calendar.HOUR_OF_DAY, hh);
			cal.set(Calendar.MINUTE, mm);
			cal.set(Calendar.SECOND, ss);
			//Log.i("warnings function","hourL in Timestamp: "+cal.getTimeInMillis()+" <= "+System.currentTimeMillis());
			
			if(cal.getTimeInMillis() <= actualTime)
			{
				// se a hora atual esta entre uma das horas para tomar e + 30 min da mesma
				if(actualTime > cal.getTimeInMillis() && actualTime < cal.getTimeInMillis()+(30*60*1000))
				{
					Timestamp hour = new Timestamp(cal.getTimeInMillis());
					//Log.i("warnings function","hourL in Timestamp: "+hour);
					//verificar se ha accoes realizadas para a hora especifica
					if(pa.getActionType().toString().equals("Measure"))
					{
						if(pa.getMeasureType().toString().equals("Blood"))
							status = msh.verifyPressureM(patientId, pa.getActionId(), hour, 30);
						if(pa.getMeasureType().toString().equals("Temperature"))
							status = msh.verifyTemperatureM(patientId, pa.getActionId(), hour, 30);
					}
					if(pa.getActionType().toString().equals("Drugtake"))
						status = msh.verifyDrugTake(patientId, pa.getActionId(), hour, 30);
					
					if(!status)	//se status FALSE o paciente ainda nao tem pelo menos uma realizacao para essa hora(ou aproximadamente)
						nPending++;
				}
			}
			else	//se hora currente for menor que seguinte hora da accao a testar
				break;
		}
		return nPending;
	}
	
	public Timestamp finalHour (PAction pa)
	{
		long periodOfDays = (long) pa.getPeriod();
		String timeToStart = pa.getTimeToStart();
		
		Timestamp time = Timestamp.valueOf(timeToStart);
		long timeToFinish = time.getTime() + (periodOfDays*24*60*60*1000);
		
		return new Timestamp(timeToFinish);
	}

}
