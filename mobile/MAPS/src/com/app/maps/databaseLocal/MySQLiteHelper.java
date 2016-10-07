package com.app.maps.databaseLocal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;








import com.app.maps.commonClasses.BloodPressureMeasure;



import com.app.maps.commonClasses.DrugTakeRegister;
import com.app.maps.commonClasses.PAction;
import com.app.maps.commonClasses.PAction.ActionType;
import com.app.maps.commonClasses.PAction.MeasureType;
import com.app.maps.commonClasses.TemperatureMeasure;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class MySQLiteHelper extends SQLiteOpenHelper {
 
    // versao da base de dados
    private static final int DATABASE_VERSION = 2;
    // nome da base de dados
    private static final String DATABASE_NAME = "Medicao";
 
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement para criar a tabela medi�oes
        
    	//tabela das pressoes
    	String CREATE_MED_TABLE = "create table Persao_art (id INTEGER primary key autoincrement, "+ "systolic INTEGER, "+ "diastolic INTEGER, "+"data TEXT, "+"id_paciente INTEGER, "+" idMesure INTEGER, "+"frequencia INTEGER,"+"sended INTEGER)";
        
        //tabela da temperatura
        String CREATE_TEMP_TABLE = "create table Temperatura (id INTEGER primary key autoincrement, "+ "temp INTEGER, "+"sended INTEGER, "+"data TEXT, "+"id_paciente INTEGER, "+"idMesure INTEGER)";
        
        
        //tabela actions
        String CREATE_ACTIONS_TABLE ="create table Actions(actionId INTEGER, medicName TEXT, actionType TEXT, measureType TEXT, period INTEGER, numberreps INTEGER, timeToStart TEXT, description TEXT, drugName TEXT, id_paciente INTEGER) ";
        
        //drug take
        
        String CREATE_DRUG_TABLE ="create table Drug (idTakeRegister INTEGER , idTake INTEGER, timeTaked TEXT,id_paciente INTEGER,id INTEGER primary key autoincrement,sended INTEGER)";
        
        // cria a tabela de pressoe
        db.execSQL(CREATE_MED_TABLE);
        
        //cria a tabela de temperatura
        db.execSQL(CREATE_TEMP_TABLE);
        
        //cria a tabale das actions
        db.execSQL(CREATE_ACTIONS_TABLE);
        
        //cria a tabela drugtake
        db.execSQL(CREATE_DRUG_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // faz drop a tabela antiga de persao_art e da temperatura
        db.execSQL("DROP TABLE IF EXISTS Persao_art");
        db.execSQL("DROP TABLE IF EXISTS Temperatura");
        db.execSQL("DROP TABLE IF EXISTS Actions");
        db.execSQL("DROP TABLE IF EXISTS Drug");
        
 
        // cria uma nova tabela presao_art e temperatura
        this.onCreate(db);
    }
    //---------------------------------------------------------------------
 
    /**
     * CRUD operations (create "add", read "get", update, delete) medidas + get all medidas + delete all medidas
     */
 
    // nome das tabelas
    private static final String table_pressao = "Persao_art";
    private static final String table_tem= "Temperatura";
    private static final String table_drug ="Drug";
 
    // as colunas que que a persao e temperatura tem
    private static final String key_id = "id";
    private static final String key_sended = "sended";
    private static final String key_data = "data";
    private static final String key_id_pac = "id_paciente";
 
    //colunas so da pressao
    private static final String key_syst = "systolic";
    private static final String key_diast = "diastolic";
    private static final String key_freq ="frequencia";
    private static final String key_idMesure="idMesure";
    
    //colunas so da temperatura
    private static final String key_temp = "temp";
    
    //colunas so da action
    
    private static final String key_acId="actionId";
    private static final String key_medicName="medicName";
    private static final String key_acType="actionType";
    private static final String key_mesuType="measureType";
    private static final String key_period="period";   				
    private static final String key_nReps="numberreps";  			
    private static final String key_TTStart="timeToStart"; 	
    private static final String key_desc="description";				
    private static final String key_drugName="drugName";
    
    //colunas da drug take
    
    private static final String key_idTake="idTake";
    private static final String key_idTakeRegister="idTakeRegister";
    private static final String key_takeTime="timeTaked";
    //private static final String key_drugName ="drugName";
  //  private static final String key_desc="description";
    
    
    //todas as colunas de drage take
    private static final String[] COLUMNS_DRUG={key_idTakeRegister, key_idTake,key_takeTime,key_id_pac,key_id,key_sended};
    
    //array de todas as colunas da pressao por ordem
    private static final String[] COLUMNS_PRE = {key_id,key_syst,key_diast,key_data,key_id_pac,key_idMesure,key_freq,key_sended};
    
    //array de todas as colunas da temperatura por ordem
    private static final String[] COLUMNS_TEMP =  {key_id,key_temp,key_sended,key_data,key_id_pac,key_idMesure};
    
  //array de todas as colunas de action
    private static final String[] COLUMNS_ACTION =  {key_acId,key_medicName,key_acType,key_mesuType,key_period,key_nReps,key_TTStart,key_desc,key_drugName,key_id_pac};
 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void addAction(PAction a,Context context){
    	
    	  // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        System.out.println(a.toString());
        SharedPreferences prefs =  context.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);

        // use a default value using new Date()
        int l = prefs.getInt("patientID", -1); 
              
        
        
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(key_acId,a.getActionId());
        values.put(key_medicName,"");
        values.put(key_acType,a.getActionType().name());
       if(a.getActionType().name().equalsIgnoreCase("Measure"))	
        	values.put(key_mesuType,a.getActionMeasureType().name());		//Linha a dar erro
 
        values.put(key_period,a.getActionPeriod());   				
        values.put(key_nReps,a.getActionNumberOfReps());  			
        values.put(key_TTStart,a.getDateToStart()); 	
        values.put(key_desc,a.getActionDescription());				
        values.put(key_drugName,a.getActionDrugName());
        values.put(key_id_pac,l);
        
        
 
        // 3. insert
        db.insert("Actions", // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
        System.out.println(a + " " +l);
        // 4. close
        db.close(); 
    	
    	
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean hasNotSaved(){
    	  SQLiteDatabase db = this.getReadableDatabase();
    	  
        
          Cursor cursor = 
                  db.query(table_pressao, // a. table
                  COLUMNS_PRE, // b. column names
                  "sended = ?", // c. selections 
                  new String[] { String.valueOf(0) }, // d. selections args
                  null, // e. group by
                  null, // f. having
                  null, // g. order by
                  null); // h. limit
   
          Cursor cursor1 = 
                  db.query("Temperatura", // a. table
                	COLUMNS_TEMP, // b. column names
                  "sended = ?", // c. selections 
                  new String[] { String.valueOf(0) }, // d. selections args
                  null, // e. group by
                  null, // f. having
                  null, // g. order by
                  null); // h. limit
          Log.d("ver",cursor.moveToNext()+"");
          
          if (cursor.moveToNext() ==false && cursor1.moveToNext()==false )
        	  return true;
          return true;
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////
    public void deletAll()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	db.delete(table_pressao, null, null);
    	db.delete("Temperatura", null, null);
    	db.delete("Drug", null, null);
    	db.delete("Actions", null, null);
    	
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  //vais buscar todos os valores da pressao do cliente especifico
    public ArrayList< PAction> getAllActionPaciente(int id) {
        ArrayList< PAction> pressao = new ArrayList< PAction>();
 

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query("Actions", // a. table
                		COLUMNS_ACTION, // b. column names
                " id_paciente = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                key_TTStart, // g. order by
                null); // h. limit
 
        PAction p = null;
        // 3. go over each row, and save to the linked list the respective cursor
        if (cursor.moveToFirst()) {
            do {
            	//System.out.println("Tipo= "+cursor.getString(2));
            	if(cursor.getString(2).equalsIgnoreCase("Measure"))
            		
            	{
            		if(cursor.getString(3).equalsIgnoreCase("Temperature"))
            		{
            		       p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Measure,MeasureType.Temperature,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7));            		
            	    }
            		else if (cursor.getString(3).equalsIgnoreCase("Blood"))
            			   p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Measure,MeasureType.Blood,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7));            		
        
                }
            	
            	else if (cursor.getString(2).equalsIgnoreCase("Drugtake"))
            	{
            		p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Drugtake,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7),cursor.getString(8));            		
        	    }
            			
            	else 
            		p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Activity,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7)); 
            	
            	pressao.add(p);
            	//Log.d("Action", p.toString());
            } while (cursor.moveToNext());
        }
//        Log.d("getAllpressao()", pressao.toString());
 
        // return pressao
        return pressao;
    }
    ///////////adicina o drugtakeset////////////////////////////////////////////////////////////////////////////////////
    public void addDrugTake(DrugTakeRegister d,Context context,int sended){
        
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
      
        SharedPreferences prefs =  context.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);

        // use a default value using new Date()
        int l = prefs.getInt("patientID", -1); 
              
        
        
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
   
        values.put(key_id_pac, l); // o id do paciente
        values.put(key_idTake,d.getIdTake());
        values.put(key_idTakeRegister, d.getIdTakeRegister());
        values.put(key_takeTime, d.getTimeTaked().toString());
        values.put(key_sended, sended);
      
        
 
        // 3. insert
        db.insert(table_drug, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        Log.d("Drug, final of addDrugTake",d.toString());
        // 4. close
        db.close(); 
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //adiciona uma nova pressao
    public void addPressao(BloodPressureMeasure p,Context context, int sended){
        
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
      
        SharedPreferences prefs =  context.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);

        // use a default value using new Date()
        int l = prefs.getInt("patientID", -1); 
              
        
        
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(key_syst, p.getsMeasure()); // systolic 
        values.put(key_diast, p.getdMeasure()); // diastolic
        values.put(key_data, p.getTimeMeasured().toString()); // a data em que os dados foram enviados
        values.put(key_id_pac, l); // o id do paciente
        values.put(key_idMesure,p.getIdMeasureprescript());
        values.put(key_freq,p.getFreqMeasure());
        values.put(key_sended,sended);
        
        
        
        
 
        // 3. insert
        db.insert(table_pressao, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        Log.d("pressao",p.toString());
        // 4. close
        db.close(); 
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   public PAction getAction (int id){
	   
       // 1. get reference to readable DB
       SQLiteDatabase db = this.getReadableDatabase();

       // 2. build query
       Cursor cursor = 
               db.query("Actions", // a. table
               COLUMNS_ACTION, // b. column names
               " actionID = ?", // c. selections 
               new String[] { String.valueOf(id) }, // d. selections args
               null, // e. group by
               null, // f. having
               null, // g. order by
               null); // h. limit

       // 3. if we got results get the first one
       if (cursor != null)
           cursor.moveToFirst();
       
       //4 converte o cursor para pressao
       PAction p = null;
      
       if(cursor.getString(2).equalsIgnoreCase("Measure"))
   		
   	{
   		if(cursor.getString(3).equalsIgnoreCase("Temperature"))
   		{
   		       p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Measure,MeasureType.Temperature,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7));            		
   	    }
   		else if (cursor.getString(3).equalsIgnoreCase("Blood"))
   			   p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Measure,MeasureType.Blood,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7));            		

       }
   	
   	else if (cursor.getString(2).equalsIgnoreCase("Drugtake"))
   	{
   		p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Drugtake,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7),cursor.getString(8));            		
	    }
   			
   	else 
   		p = new  PAction(Integer.parseInt(cursor.getString(0)),ActionType.Activity,cursor.getString(6),Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)),cursor.getString(7)); 
       
       
       // 5. retuna a pressao
       return p;
	   
   }
   
   ///get do drug take//////////////////////////////////////////////////////////////////////////////////////////
   public DrugTakeRegister getDrugTake(int id){
  	 
       // 1. get reference to readable DB
       SQLiteDatabase db = this.getReadableDatabase();



       // 2. build query
       Cursor cursor = 
               db.query(table_drug, // a. table
               COLUMNS_DRUG, // b. column names
               " idTake = ?", // c. selections 
               new String[] { String.valueOf(id) }, // d. selections args
               null, // e. group by
               null, // f. having
               null, // g. order by
               null); // h. limit

       // 3. if we got results get the first one
       if (cursor != null)
           cursor.moveToFirst();
       
       //4 converte o cursor para pressao
       Timestamp ts = Timestamp.valueOf(cursor.getString(2));
       DrugTakeRegister d = new DrugTakeRegister(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),ts);

       // 5. retuna a pressao
       return d;
   }
   
   
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public BloodPressureMeasure getPressao(int id){
    	 
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(table_pressao, // a. table
                COLUMNS_PRE, // b. column names
                " id = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
 
        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();
        
        //4 converte o cursor para pressao
        Timestamp ts = Timestamp.valueOf(cursor.getString(3));
        BloodPressureMeasure p = new BloodPressureMeasure(Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(6)),ts);
 
        // 5. retuna a pressao
        return p;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //vais buscar todos os valores da pressao
    public ArrayList< BloodPressureMeasure> getAllPressao() {
        ArrayList< BloodPressureMeasure> pressao = new ArrayList< BloodPressureMeasure>();
        
        // 1. build the query
        String query = "SELECT  * FROM " + table_pressao;
 
     // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, and save to the linked list the respective cursor
        Timestamp ts;
        if (cursor.moveToFirst()) {
            do {
            	 ts = Timestamp.valueOf(cursor.getString(3));
                 BloodPressureMeasure p = new BloodPressureMeasure(Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(6)),ts);
            	pressao.add(p);
            } while (cursor.moveToNext());
        }
        Log.d("getAllpressao()", pressao.toString());
 
        // return pressao
        return pressao;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //vais buscar todos os valores da pressao do cliente especifico
    public ArrayList< BloodPressureMeasure> getAllPressaoPaciente(int id) {
        ArrayList< BloodPressureMeasure> pressao = new ArrayList< BloodPressureMeasure>();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
 
        // 2. build query
        Cursor cursor = 
                db.query(table_pressao, // a. table
                COLUMNS_PRE, // b. column names
                " id_paciente = ?", // c. selections 
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                "data DESC" , // g. order by
                null); // h. limit
 
 
        // 3. go over each row, and save to the linked list the respective cursor
        Timestamp ts;
        if (cursor.moveToFirst()) {
            do {
            	 ts = Timestamp.valueOf(cursor.getString(3));
                 BloodPressureMeasure p = new BloodPressureMeasure(Integer.parseInt(cursor.getString(5)),Integer.parseInt(cursor.getString(2)),Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(6)),ts);
            	pressao.add(p);
            } while (cursor.moveToNext());
        }
        Log.d("getAllpressao()", pressao.toString());
 
        // return pressao
        return pressao;
    }
    
    
    
    //adiciona uma nova temperatura
    public void addTemp(TemperatureMeasure t,Context context,int sended){
        
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        SharedPreferences prefs =  context.getSharedPreferences("com.app.maps", Context.MODE_PRIVATE);

        // use a default value using new Date()
        int l = prefs.getInt("patientID", -1); 
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(key_temp, t.gettMeasure()); // Temperatura
        values.put(key_sended, sended); // da data em que a medida foi enviada para a base de dados exterior
        values.put(key_data, t.getTimeMeasured().toString()); // a data em que os dados foram enviados
        values.put(key_id_pac, l); // o id do paciente
        values.put(key_idMesure, t.getIdMeasurepresvript());       
 
        // 3. insert
        db.insert(table_tem, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values
 
        Log.d("temperatra",t.toString());
        // 4. close
        db.close(); 
    }
   
//   //get de todos os valores de temperatura nao guardados 
    public List<TemperatureMeasure> getTempNaoGuardados() {
        List<TemperatureMeasure> temperatura = new LinkedList<TemperatureMeasure>();
 
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + table_tem+ " where "+key_sended+"=?",new String[]{"0"});
 
        // 2. go over each row, and save to the linked list the respective cursor
        Timestamp ts ;
        if (cursor.moveToFirst()) {
            do {
                
                ts = Timestamp.valueOf(cursor.getString(3));
            	
                TemperatureMeasure t = new TemperatureMeasure();
                t.setIdMeasure(Integer.parseInt(cursor.getString(0)));
                t.setIdTMeasure(Integer.parseInt(cursor.getString(1)));
                t.setTimeMeasured(ts);
                t.setIdMeasurepresvript(Integer.parseInt(cursor.getString(5)));
            	temperatura.add(t);
            } while (cursor.moveToNext());
        }
 
        Log.d("getnaoenviados", temperatura.toString());
 
        //return temeperatura
        return temperatura;
    }
//   
    
   
    public ArrayList<DrugTakeRegister> getAllDrugTake(int idPaciente) {
        ArrayList<DrugTakeRegister> drug = new ArrayList<DrugTakeRegister>();

 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT  * FROM Drug where "+key_id_pac+"=?",new String[]{String.valueOf(idPaciente)});
 
        // 2. go over each row, and save to the linked list the respective cursor
        Timestamp ts ;
        if (cursor.moveToFirst() && cursor !=null) { //added
            do {	
              
            	ts = Timestamp.valueOf(cursor.getString(2));
                DrugTakeRegister d = new DrugTakeRegister(Integer.parseInt(cursor.getString(0)),Integer.parseInt(cursor.getString(1)),ts);
            	drug.add(d);
            	Log.d("o serafim e gay",cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        
       
        Log.d("getAlldrug()", drug.toString());
 
        // return temperatura
        return drug;}
    
    
//    
//   
//    
//    // todas as temperaturas
    public ArrayList<TemperatureMeasure> getAllTemperatura(int idPaciente) {
        ArrayList<TemperatureMeasure> temperatura = new ArrayList<TemperatureMeasure>();

 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 1. build query
        Cursor cursor = 
                db.query("Temperatura", // a. table
                COLUMNS_TEMP, // b. column names
                key_id_pac+" = ?", // c. selections 
                new String[] { String.valueOf(idPaciente) }, // d. selections args
                null, // e. group by
                null, // f. having
                "data DESC" , // g. order by
                null); // h. limit
        
        // 2. go over each row, and save to the linked list the respective cursor
        Timestamp ts ;
        if (cursor.moveToFirst()) {
            do {
                
                ts = Timestamp.valueOf(cursor.getString(3));
            	
                TemperatureMeasure t = new TemperatureMeasure();
                t.setIdMeasure(Integer.parseInt(cursor.getString(0)));
                t.settMeasure(Integer.parseInt(cursor.getString(1)));
                t.setTimeMeasured(ts);
                t.setIdMeasurepresvript(Integer.parseInt(cursor.getString(5)));
            	temperatura.add(t);
            	Log.d("o serafim e gay",cursor.getString(1));
            } while (cursor.moveToNext());
        }
 
        
       
        Log.d("getAllTemperatura()", temperatura.toString());
 
        // return temperatura
        return temperatura;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    
//  // todas as temperaturas/////extendP in minutes
  public boolean verifyTemperatureM(int idPaciente,int idAction,Timestamp measure,int extendedP) {
  


      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery( "SELECT  * FROM Temperatura where "+key_id_pac+"=?",new String[]{String.valueOf(idPaciente)} );

 
      
      // 2. go over each row, and save to the linked list the respective cursor
      Timestamp ts ;
      boolean has=false;
      if (cursor.moveToFirst()) {
          do {
              if(Integer.parseInt(cursor.getString(5))==idAction){
              ts = Timestamp.valueOf(cursor.getString(3));
              if(ts.getTime() >= measure.getTime() && ts.getTime() <= measure.getTime()+(extendedP*60*1000))
            	  has = true;
        
              }
          } while (cursor.moveToNext());
      }


      // return temperatura
      return has;
  }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////
  public boolean verifyPressureM(int idPaciente,int idAction,Timestamp measure,int extendedP) {
	  


      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery( "SELECT  * FROM Persao_art where "+key_id_pac+"=?",new String[]{String.valueOf(idPaciente)} );

      // 2. go over each row, and save to the linked list the respective cursor
      Timestamp ts ;
      boolean has = false;
      if (cursor.moveToFirst()) {
          do {
              if(Integer.parseInt(cursor.getString(5))==idAction){
	              ts = Timestamp.valueOf(cursor.getString(3));
	              if(ts.getTime() >= measure.getTime() && ts.getTime() <= measure.getTime()+(extendedP*60*1000))
	            	  has = true;
              }
          } while (cursor.moveToNext());
      }


      // return pressure
      return has;
  }
    
  
  public boolean verifyDrugTake(int idPaciente,int idAction,Timestamp measure,int extendedP) {
	  


      // 1. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery( "SELECT  * FROM Drug where "+key_id_pac+"=?",new String[]{String.valueOf(idPaciente)} );

      // 2. go over each row, and save to the linked list the respective cursor
      Timestamp ts ;
      boolean has=false;
      if (cursor.moveToFirst()) {
          do {
              if(Integer.parseInt(cursor.getString(1))==idAction){
              ts = Timestamp.valueOf(cursor.getString(2));
              if(ts.getTime() >= measure.getTime() && ts.getTime() <= measure.getTime()+(extendedP*60*1000))
            	  has = true;
        
              }
          } while (cursor.moveToNext());
      }


      // return pressure
      return has;
  }
    
    
    
    
    
    
    
    
    
    
    
    
 
//     // Updating single pressao nao sera necessario mas vou manter o codigo para futuras referencias
//    public int updateBook(Cursor pressao) {
// 
//        // 1. get reference to writable DB
//        SQLiteDatabase db = this.getWritableDatabase();
// 
//        // 2. create ContentValues to add key "column"/value
//        ContentValues values = new ContentValues();
//        values.put(key_syst, pressao.getInt(1)); // get systolic
//        values.put(key_diast, pressao.getInt(2)); // get diastolic
// 
//        // 3. updating row
//        int i = db.update(table_pressao, //table
//                values, // column/value
//                key_id+" = ?", // selections
//                new String[] { String.valueOf(pressao.getInt(0)) }); //selection args
// 
//        // 4. close
//        db.close();
// 
//        return i;
// 
//    }
 
    // Deleting single book
    public void deletePressao(int id) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(table_pressao,
                key_id+" = ?",
                new String[] { String.valueOf(id) });
 
        // 3. close
        db.close();
 
        Log.d("pressao", "A medi��o com o id "+id+" foi eliminada");
 
    }
    
    // Deleting single book 
    public void deleteTemperatura(int id) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(table_tem,
                key_id+" = ?",
                new String[] { String.valueOf(id) });
 
        // 3. close
        db.close();
 
        Log.d("temeperatura", "A medi��o com o id "+id+" foi eliminada");
      
        
        
     
 
    }
    


}
