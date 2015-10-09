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
 
public class ServiceActivity extends ListActivity  {
 
    String stringJson;
    String[] servicios;
    ArrayList<servicio>[] listaServicios ;
    envio hostService;
    int position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buscarEstructuras();
    }
    
    /* 
     * Metodo que trae las estructuras del viejo activity
     * */
    public void buscarEstructuras(){
    	MyApplication state = ((MyApplication) getApplicationContext());
    	position    = state.getPosition();
    	hostService = state.getEnvio(position);
    	leerServicios();
	
    }
    
    public void leerServicios(){
    	ArrayList<servicio> s = hostService.getServicios();
    	servicios = new String[s.size()];
    	int i = 0;
    	while(i<s.size()) {
    	    servicios[i]  = s.get(i).getNombre();
    	    //System.out.println("PRUEBA"+s.get(i).getNombre());
        	i++;
    	}
    	
    	crearLista();
    }
    
    public void crearLista(){
    	
    	setListAdapter(new ServiceArrayAdapter(this,servicios, hostService));
    }
    
    
    /* 
     * Metodo que se encarga de crear el listView para un Servicio especifico
     * 
     * */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Intent i = new Intent(ServiceActivity.this, InfoServiceActivity.class);
		servicio s = hostService.getServicios().get(position);
		i.putExtra( "nombre", s.getNombre());
		i.putExtra( "status", s.getStatus());
		i.putExtra( "duracion", s.getDuracion());
		i.putExtra( "revision", s.getRevision());
		i.putExtra( "info", s.getInfo());
		startActivity(i);
	}
}