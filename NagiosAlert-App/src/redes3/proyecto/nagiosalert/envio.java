package redes3.proyecto.nagiosalert;

import java.util.ArrayList;
import redes3.proyecto.nagiosalert.servicio;
import redes3.proyecto.nagiosalert.host;


public class envio {

        host h;
        ArrayList<servicio> s;

        private envio(host h, ArrayList<servicio> s){

            this.h = h;
            this.s = s;
        }
        
        // ---- SETTERS ---- //
        
        public void setHost(host h){
        	this.h = h;
        }

        public void setServicios(ArrayList<servicio> s){
        	this.s = s;
        }
        
        // ---- GETTERS ---- //
        
        public host getHost(){
        	return this.h;
        }

        public ArrayList<servicio> getServicios(){
        	return this.s;
        }      
}
