package com.app.maps.handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import com.app.maps.commonClasses.PatientToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.os.Looper;
import android.util.Log;

public class JsonHandler {
	
	public JsonHandler(){
	}
//------------------------------------------------------------------------------------------------------------------------
	
	public String sendJson(final String s_url, final String urlParameters) {
		
		
    	//Permite a thread espere até obter resposta não permitindo o lançamento de uma nova.
    	final CountDownLatch latch = new CountDownLatch(1);
    	
    	//A variavél com a resposta não pode ser do tipo primitivo.
    	final String[] outputMsg = new String[1];
    	
    	outputMsg[0] = "Erro";
		
    	Thread t = new Thread() {
    		public void run() {
    			URL url;
    			HttpURLConnection urlConnection = null;
    			Looper.prepare(); //For Preparing Message Pool for the child Thread
    			try {

    				StringBuilder output = new StringBuilder();

    				/* converte o JSON Object para String */

    				 url = new URL(s_url);
    				 urlConnection = (HttpURLConnection) url.openConnection();

    				urlConnection.setDoOutput(true);
    				urlConnection.setDoInput(true);
    				urlConnection.setConnectTimeout(10000);
    				urlConnection.setRequestMethod("POST"); 
    				urlConnection.setRequestProperty("Content-Type", "application/json"); 
    				urlConnection.setRequestProperty("charset", "utf-8");
    				urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));          
    				urlConnection.connect();

    				DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
    				wr.writeBytes(urlParameters);
    				wr.flush();
    				wr.close();

    				InputStream is;

    				if (urlConnection.getResponseCode() == 200) {

    					is = urlConnection.getInputStream();

    					/* obtém a resposta do pedido */
    					int n = 1;	
    					while (n > 0) {

    						byte[] b = new byte[4096];

    						n =  is.read(b);

    						if (n > 0)
    							output.append(new String(b, 0, n));
    					}
    					latch.countDown();
    				}
    				
    				outputMsg[0]=output.toString();
    				urlConnection.disconnect();
    			} 
    	
    			
    			catch (Exception ex) {    
    				 Log.d("conection", "Conection time out");
    				 latch.countDown();
    				 urlConnection.disconnect();
    				 
    			}
    			
    			Looper.loop(); //Loop in the message queue
    		}

    	};
    	t.start();
    	  try {
		latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Geração do objecto do tipo paciente.
        return outputMsg[0];
 }
	
public String receiveJson(final String s_url) {
		
	 
    	//Permite a thread espere até obter resposta não permitindo o lançamento de uma nova.
    	final CountDownLatch latch = new CountDownLatch(1);
    	
    	//A variavél com a resposta não pode ser do tipo primitivo.
    	final String[] outputMsg = new String[1];
    	
    	outputMsg[0] = "Erro";
		

    	Thread t = new Thread() {
    		public void run() {
    			Looper.prepare(); //For Preparing Message Pool for the child Thread
    			try {

    				StringBuilder output = new StringBuilder();
    				/* converte o JSON Object para String */

    				URL url = new URL(s_url);
    				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    				//urlConnection.setDoOutput(true);
    				//urlConnection.setDoInput(true);
    				urlConnection.setRequestMethod("GET"); 
    				//urlConnection.setRequestProperty("Content-Type", "application/json"); 
    				//urlConnection.setRequestProperty("charset", "utf-8");
//    				urlConnection.setRequestProperty("Content-Length", "" + Integer.toString(" ".getBytes().length));          
    			//	urlConnection.connect();
    				final InputStream connect;
    				 connect = (InputStream) urlConnection.getInputStream();
    		            BufferedReader in = new BufferedReader(new InputStreamReader(connect));
    		            String content = in.readLine();
    		            outputMsg[0]=content;
//    				System.out.priCntln("Entrei no receive action"+urlConnection.getResponseCode()+content);

//    				DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream ());
//    				wr.writeBytes("");
//    				wr.flush();
//    				wr.close();

    				InputStream is;

    				if (urlConnection.getResponseCode() == 200) {
//    					System.out.println("Tenho resposta");
    					is = urlConnection.getInputStream();

    					/* obtém a resposta do pedido */
    					int n = 1;	
    					while (n > 0) {

    						byte[] b = new byte[4096];

    						n =  is.read(b);

    						if (n > 0)
    							output.append(new String(b, 0, n));
    					}
    					latch.countDown();
    				}
    				
    				
    				urlConnection.disconnect();
    			} 
    			catch (Exception ex) {    
    				ex.printStackTrace();;
    			}
    			Looper.loop(); //Loop in the message queue
    		}

    	};
    	t.start();
    	  try {
		latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Geração do objecto do tipo paciente.
        return outputMsg[0];
 }
   
    
    public PatientToken loginPA(final String s_url, final String mail, final String pass, final String deviceId){
    	
    	//Permite a thread espere até obter resposta não permitindo o lançamento de uma nova.
    	final CountDownLatch latch = new CountDownLatch(1);
    	
    	//A variavél com a resposta não pode ser do tipo primitivo.
    	final String[] pa = new String[1];
    	
    	Thread t = new Thread() {	
            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                	HttpPost post = new HttpPost(s_url+"/PeiMaps/webresources/patientlogin?dev_id="+deviceId);
                	System.out.println(s_url+"/PeiMaps/webresources/patientlogin?dev_id="+deviceId);
                	StringEntity entity = new StringEntity( "{\"idPatient\":-1,\"sns\":0,\"mail\":\""+mail+"\",\"passwordHash\":\""+pass+"\"}", HTTP.UTF_8 );  
                	entity.setContentType("application/json");
                	post.setHeader("Content-Type", "application/json");
                	post.setHeader("Accept", "application/json");
                	post.setEntity(entity);
                	response = client.execute(post);

                	if (response != null){
                		// process incoming messages here
                		InputStream in = response.getEntity().getContent(); //Get the data in the entity
                		BufferedReader buf = new BufferedReader(new InputStreamReader(in));
                		pa[0] = buf.readLine();
                		latch.countDown();
                	}

                } catch(Exception e) {
                    e.printStackTrace();
                    e.getStackTrace()[0].toString();
                    //Toast.makeText(getActivity(), "Error Cannot Estabilish Connection\n", Toast.LENGTH_SHORT).show();
                }
                Looper.loop(); //Loop in the message queue
            }
        };
        
        t.start();
        
        try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //Geração do objecto do tipo paciente.
        Gson gson = new GsonBuilder().create();
        PatientToken pt = gson.fromJson(pa[0], PatientToken.class);
        
        return pt;
    }
}
