package redes3.proyecto.nagiosalert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.lang.reflect.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
 
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class LogActivity extends ListActivity   {
 
    String stringJson;
    HashMap<String,envio> retorno;
    HashMap<String, envio> l1;
	String[] listaHosts;
	String[] duracion;
	String[] revision;
	int[] status;
    envio[] listaServicios ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_services);
      
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        
        try {
			refresh();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    /* 
     * Metodo que se encarga de establecer la conexion con el servidor
     * 
     * */
    public void refresh() throws IOException {
 
    	MyApplication state =((MyApplication) getApplicationContext()); 
    	String url = state.getUrl();
    	StringBuffer output = new StringBuffer("");
        InputStream stream = null;
        URL u = new URL(url);
        URLConnection connection = u.openConnection();

       // De ser posible abre una conexion con el servidor
        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            } else {
                //return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
            //return null;
        }

	        if(stream != null){
		        // Lee el stream de datos que envio el servidor
		        BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
		
		        String s = "";
		        while ((s = buffer.readLine()) != null)
		            output.append(s);
		
		        // Vuelve el Stream un ArrayList de Incidentes basado en el tipo del
		        // objeto
		        String data = output.toString();
		
			     // Vuelve el Stream que me llega en un HashMap de <String, Envio>
			
			     // Esta es una de las clases que manda el servidor en un JSON
			
			
			
			     // Asi se transforma lo que te mande por correo al  HashMap de
			     //<String, envio> con el que trabajaremos.
			
			    // String data = "" // El string que te mande que era inmenso.
			
			     Gson gson = new Gson();
			
			     java.lang.reflect.Type tipo =  (Type) new TypeToken<HashMap<String, envio>>() {
			             }.getType();
			
			     JsonElement json = new JsonParser().parse(data);
			     l1 = gson.fromJson(json, tipo);
			     
			     
//			     RSA rsa = new RSA();
//			     Sobre sobre = null;
//			     AES aes = new AES();
//			     byte[] des_key = rsa.descifrar_RSA(c,en_key);
//			     des_key = rsa.descifrar_RSA(c, sobre.getKey());
//			     byte[] des_text = aes.descifrar_AES(sobre.getText(), new String(des_key));
			     
			     
			     mostrarString(l1);
	             MyApplication state2 =((MyApplication) getApplicationContext());
	             System.out.println("ESTA ES LA CLAVE ANTES DE SER ENVIADA"+state2.getKey());
			     enviarIncidentes(state2.getUrlGCM(),state2.getKey());
	        }
        
        }
    
    public void mostrarString(HashMap<String,envio> listaString){
    	
    	int i = 0; //contador
    	Iterator myIterator = listaString.keySet().iterator();// Iterador para recorrer el HashMap
    	listaHosts = new String [listaString.size()];//Creo un arreglo de host para el listView
    	duracion = new String [listaString.size()];//Creo un arreglo de duracion para el listView
    	revision = new String [listaString.size()];//Creo un arreglo de revision para el listView
    	status = new int [listaString.size()];//Creo un arreglo de status para el listView
    	listaServicios = new envio [listaString.size()];
    	
    	while(myIterator.hasNext()) {
    	    String key   =(String)myIterator.next();
    	    listaServicios[i] = (envio)listaString.get(key);
    	    listaHosts[i] = listaString.get(key).h.getNombre();
    	    duracion[i]  = listaString.get(key).h.getDuracion();
    	    revision[i]  = listaString.get(key).h.getRevision();
    	    status[i]    = listaString.get(key).h.getStatus();
        	i++;
    	}
	    MyApplication state = ((MyApplication) getApplicationContext());
	    state.iniciarEnvio(listaString.size());
    	crearLista();
    }
    
    /* 
     * Metodo que se encarga de crear el listView a partir de un listado de HOSTS 
     * 
     * */
    
    public void crearLista(){
		setListAdapter(new LogArrayAdapter(this, listaHosts , duracion, revision, status));
		 
	}
 
    /* 
     * Metodo que se encarga de crear el listView para un HOst especifico(lista de Servicios) 
     * 
     * */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
	    MyApplication state = ((MyApplication) getApplicationContext());
	    state.setPosition(position);
	    state.setEnvio(listaServicios[position]);
	    
		startActivity(new Intent(LogActivity.this, ServiceActivity.class));
	}
	
	/* Envia una lista de incidentes al servidor */
    public Boolean enviarIncidentes(String url, String info)
            throws IOException {

        InputStream stream = null;
        String result = "";

        
        // De ser posible abre una conexion con el servidor y envia
        try {

            // Crea el cliente http y la solicitud de POST,
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            // Transforma la lista de incidentes enu n objeto JSON
            String json = "";
            json = new Gson().toJson(info);

            // Establece la entity y los header del POST
            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            
            // Ejecuta la solicitud de POST y espera la respuesta
            HttpResponse httpResponse = httpclient.execute(httpPost);
            stream = httpResponse.getEntity().getContent();

            // Transforma el stream a in string
            if (stream != null) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(stream));
                String linea = "";
                result = "";
                while ((linea = bufferedReader.readLine()) != null)
                    result += linea;
            } else
                result = "Fail";

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (result == "Fail") {
            return false;
        } else {
            return true;
        }

    }
	
 
 }//Fin RefreshActivity