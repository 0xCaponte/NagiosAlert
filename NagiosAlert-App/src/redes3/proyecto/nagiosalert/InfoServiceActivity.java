package redes3.proyecto.nagiosalert;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class InfoServiceActivity extends Activity  {
	
	String nombre;
	int status;
	String duracion;
	String revision;
	String info;
	ImageView image     ;
	TextView tvNombre   ;
	TextView tvStatus   ;
	TextView tvDuracion ;
	TextView tvRevision ;
	TextView tvInfo     ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_service);
    	nombre     = new String();
    	status     = 0 ;
    	duracion   = new String();
    	revision   = new String();
    	info       = new String();
    	image      = (ImageView) findViewById(R.id.image);
    	tvNombre   = (TextView)this.findViewById(R.id.tvNombreServicio);
    	tvStatus   = (TextView)this.findViewById(R.id.status);
    	tvDuracion = (TextView)this.findViewById(R.id.duracion);
    	tvRevision = (TextView)this.findViewById(R.id.revision);
    	tvInfo     = (TextView)this.findViewById(R.id.info);

        Bundle extras = getIntent().getExtras();
        
        nombre = extras.getString("nombre");
        status = extras.getInt("status");
        duracion = extras.getString("duracion");
        revision = extras.getString("revision");
        info = extras.getString("info");
        
		tvNombre.setText(nombre);

		tvDuracion.setText("Duracion : \n"+duracion);
		tvRevision.setText("Revision : \n"+revision);
		tvInfo.setText("Descripción : \n"+info);
		
		if(status== 0){
			tvStatus.setText("Estado : \nOk");
			image.setImageResource(R.drawable.fine);	
		}else if(status== 1){
			tvStatus.setText("Estado : \nFail");
			image.setImageResource(R.drawable.fail);	
		}if(status== 2){
			tvStatus.setText("Estado : \nWarning");
			image.setImageResource(R.drawable.warning);	
		}if(status== 3){
			tvStatus.setText("Estado : \nUnknown");
			image.setImageResource(R.drawable.unknown);	
		}
        
        
    }
	

}