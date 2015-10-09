package redes3.proyecto.nagiosalert;

import java.util.ArrayList;

import redes3.proyecto.nagiosalert.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ServiceArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] servicios;
	int host;
	envio envio;
	//private final ArrayList<servicio>[] listaServicios;
 
	public ServiceArrayAdapter(Context context,String[] servicios, envio e) {
		super(context, R.layout.activity_row_layout, servicios);
		this.context  = context;
		this.servicios = servicios;
		this.envio	  = e;
	}
 
	/*
	 * Metodo que crea una fila (Imagen, nombre) para el listado de HOSTS
	 * 
	 */
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.activity_row_layout, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView tvNombre = (TextView) rowView.findViewById(R.id.nombre);
		TextView tvduracionRevision = (TextView) rowView.findViewById(R.id.duracionRevision);
		
		tvNombre.setText(servicios[position]);
		tvduracionRevision.setText(position+"holsa"+host);
		tvduracionRevision.setText("Duración : "+envio.s.get(position).getDuracion()+" | Revisión : "+envio.s.get(position).getRevision());

		if(envio.s.get(position).getStatus()== 0){
			imageView.setImageResource(R.drawable.fine);	
		}else if(envio.s.get(position).getStatus()== 1){
			imageView.setImageResource(R.drawable.fail);	
		}if(envio.s.get(position).getStatus()== 2){
			imageView.setImageResource(R.drawable.warning);	
		}if(envio.s.get(position).getStatus()== 3){
			imageView.setImageResource(R.drawable.unknown);	
		}
		 
		return rowView;
	}
}