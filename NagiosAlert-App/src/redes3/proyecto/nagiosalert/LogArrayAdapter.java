package redes3.proyecto.nagiosalert;

import redes3.proyecto.nagiosalert.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
 
public class LogArrayAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final String[] hosts;
	private final String[] duracion;
	private final String[] revision;
	private final int[] status;
 
	public LogArrayAdapter(Context context, String[] hosts , String[] duracion, String[] revision, int[] status) {
		super(context, R.layout.activity_row_layout, hosts);
		this.context  = context;
		this.hosts 	  = hosts;
		this.duracion = duracion;
		this.revision = revision;
		this.status   = status;
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
		
		
		tvNombre.setText(hosts[position]);
		tvduracionRevision.setText("Actual : "+duracion[position]+" | Anterior : "+revision[position]);

	
			imageView.setImageResource(R.drawable.log);	
		
		 
		return rowView;
	}
}