package redes3.proyecto.nagiosalert;

import java.util.ArrayList;

import android.app.Application;

public class MyApplication extends Application
{
	private int position;
	private envio[] listaServicios;
	private String url;
	private String urlGCM;
	private String key;

	public void setKey(String u){
		this.key="";
		this.key = u;
	}
	
	public String getKey(){
		return this.key;
	}
	
	
	public void setUrl(String u){
		this.url="";
		this.url = u;
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public void setUrlGCM(String u){
		this.urlGCM="";
		this.urlGCM = u;
	}
	
	public String getUrlGCM(){
		return this.urlGCM;
	}
	
	// ------  envio SETTERS  --------- //
	public void iniciarEnvio(int tamano){
		listaServicios = new envio[tamano];
	}

	
	public void setHost( int position, host h){
		listaServicios[position].setHost(h);;
	}
	
	public void setServicios(int pos, ArrayList<servicio> servicios){
		listaServicios[pos].setServicios(servicios);
	}
	
    public void setEnvio(envio e){
    	listaServicios[position] = e;
    }
	
	public void setPosition(int p){
		this.position = p;
	}
	
    // ---- GETTERS ---- //
    
    public host getHost(int position){
    	return this.listaServicios[position].getHost();
    }

    public ArrayList<servicio> getServicios(int position){
    	return this.listaServicios[position].getServicios();
    } 
    
    public envio getEnvio(int position){
    	return listaServicios[position];
    }
    
    public int getPosition(){
    	return this.position;
    }
		
}		