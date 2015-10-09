package redes3.proyecto.nagiosalert;

import java.util.Comparator;

public class servicio{
	
	private String nombre;
	private String host;
	private int status;
	private String revision;
	private String duracion;
	private String info;
	

//	---------------------   Constructor  ---------------------
	public servicio(String nombre, String host, int status, String revision,
			String duracion, String info) {
		super();
		this.nombre = nombre;
		this.host = host;
		this.status = status;
		this.revision = revision;
		this.duracion = duracion;
		this.info = info;
	}
	
//	--------------------- Getters y Setters ---------------------
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getDuracion() {
		return duracion;
	}
	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}	
	
	
	// -------------------- Comparadores -----------------------------
	//Funcions Comparadora para ordenar servicios por nombre
	public static Comparator<servicio> servicioName = new Comparator<servicio>() {
		@Override
		public int compare(servicio s1, servicio s2) {
			String servicio1 = s1.getNombre().toUpperCase();
			String servicio2 = s2.getNombre().toUpperCase();

			// Orden Ascendente (Invertir nombres para orden descendente)
			return servicio1.compareTo(servicio2);
		}
	};
	
	//Funcions Comparadora para ordenar servicios por HOST al que pertenecen
		public static Comparator<servicio> servicioHost= new Comparator<servicio>() {
			@Override
			public int compare(servicio s1, servicio s2) {
				String servicio1 = s1.getHost().toUpperCase();
				String servicio2 = s2.getHost().toUpperCase();

				// Orden Ascendente (Invertir nombres para orden descendente)
				return servicio1.compareTo(servicio2);
			}
		};
}
