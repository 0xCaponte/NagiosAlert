import java.util.Comparator;

/**
 * Fecha: 29/10/14
 * 
 * Clase: Host 
 * 
 * Clase que implementa el tipo Host, el cual sirve para contener la informacion
 * de interes de los host que son monitoriados por el servidor Nagios. Asi mismo, 
 * cuenta con los metodos para acceder a los parametros de interes y para
 * comparar dos hosts en funcion de su nombre.
 * 
 * @author caponte
 *
 */

public class Host {

	private String nombre;
	private int status;
	private String revision;
	private String duracion;

	// ------------------ Constructor ------------------
	public Host(String nombre, int status, String revision, String duracion) {
		super();
		this.nombre = nombre;// Nombre del host
		this.status = status; // Estado del host (0,1,2,3)
		this.revision = revision; // Ultima fecha de monitoreo
		this.duracion = duracion; // Tiempo desde la ultima vez que fallo
	}

	// ------------------ Getters y Setters ------------------
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	

	// -------------------- Comparadores -----------------------------
	
	//Funcions Comparadora para ordenar hosts por nombre
	public static Comparator<Host> hostName = new Comparator<Host>() {
		@Override
		public int compare(Host s1, Host s2) {
			String host1 = s1.getNombre().toUpperCase();
			String host2 = s2.getNombre().toUpperCase();

			// Orden Ascendente (Invertir nombres para orden descendente)
			return host1.compareTo(host2);
		}
	};
}
