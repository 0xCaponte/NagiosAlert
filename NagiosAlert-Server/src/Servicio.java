import java.util.Comparator;


/**
 * Fecha: 29/10/14
 * 
 * Clase: Servicio
 * 
 * Clase que implementa el tipo servicio, el cual sirve para contener la informacion
 * de interes de los servicios queson monitoriados por el servidor Nagios. Asi mismo, 
 * cuenta con los metodos para acceder a los parametros de interes y para
 * comparar dos servicios en funcion del nombre del host al que pertenecen o del nombre
 * del servicio.
 * 
 * @author caponte
 *
 */
public class Servicio{
	
	private String nombre; // Nombre del servicio
	private String host; // Nombre del host al que pertenece
	private int status; // Estado del servicio (0,1,2,3)
	private String revision; // Ultima fecha en que se monitoreo
	private String duracion; // Tiempo desde la ultima vez que fallo
	private String info; // Informacion extra sobre el servicio.
	

//	---------------------   Constructor  ---------------------
	public Servicio(String nombre, String host, int status, String revision,
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
	
	//Funcions Comparadora para ordenar servicios por nombre del servicio
	public static Comparator<Servicio> servicioName = new Comparator<Servicio>() {
		@Override
		public int compare(Servicio s1, Servicio s2) {
			String servicio1 = s1.getNombre().toUpperCase();
			String servicio2 = s2.getNombre().toUpperCase();

			// Orden Ascendente (Invertir nombres para orden descendente)
			return servicio1.compareTo(servicio2);
		}
	};
	
	//Funcions Comparadora para ordenar servicios por HOST al que pertenecen
		public static Comparator<Servicio> servicioHost= new Comparator<Servicio>() {
			@Override
			public int compare(Servicio s1, Servicio s2) {
				String servicio1 = s1.getHost().toUpperCase();
				String servicio2 = s2.getHost().toUpperCase();

				// Orden Ascendente (Invertir nombres para orden descendente)
				return servicio1.compareTo(servicio2);
			}
		};
}
