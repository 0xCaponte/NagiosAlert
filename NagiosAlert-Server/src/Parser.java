import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 * Fecha: 29/10/14
 * 
 * Clase: Parser
 * 
 * Clase que cuenta con las funciones necesarias para obtener la informacion del estado 
 * de los servicios y de los hosts de el archivo "status.dat" de cualquier servidor Nagios.
 *  
 * @author caponte
 *
 */
public class Parser {

	// Funcion para parsear el archivo status.dat en busca de todos los hosts
	@SuppressWarnings("deprecation")
	public static ArrayList<Host> parseHosts() {

		// Path to your STATUS.DAT file
		String archivo = "/usr/local/nagios/var/status.dat";
		ArrayList<Host> hosts = new ArrayList<Host>();

		Scanner scan = null;
		try {
			scan = new Scanner(new File(archivo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {

			String t = scan.nextLine();

			if (t.contains("hoststatus")) {

				String n = ""; // Nombre del host
				int s = 3; // Codigo de estado desconocido
				String r = ""; // Ultimo checkeo
				String d = ""; // Tiempo activo

				String tmp = "";
				Date temp;

				int pos;

				t = scan.nextLine();

				while (!t.contains("}")) {

					if (t.contains("host_name")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						n = tmp;

					} else if (t.contains("current_state")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						s = Integer.parseInt(tmp);

					} else if (t.contains("last_check")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						r = tmp;
						temp = new java.util.Date(
								(long) Integer.parseInt(r) * 1000);
						r = temp.toLocaleString();

					} else if (t.contains("last_time_up")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						d = tmp;


						/** REVISAR COMO CALCULAR LA DURACION UP **/
						temp = new java.util.Date(
								(long) Integer.parseInt(d) * 1000);
						d = temp.toLocaleString();
					}

					t = scan.nextLine();
				}

				Host h = new Host(n, s, r, d);

				hosts.add(h);
			}
		}

		scan.close();

		return hosts;
	}

	// Funcion para parsear el archivo status.dat en busca de todos los
	// servicios
	public static ArrayList<Servicio> parseServicios() {

		// Path to your STATUS.DAT file
		String archivo = "/usr/local/nagios/var/status.dat";
		ArrayList<Servicio> serv = new ArrayList<Servicio>();

		Scanner scan = null;
		try {
			scan = new Scanner(new File(archivo));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {
			String t = scan.nextLine();

			if (t.contains("servicestatus")) {

				String h = ""; // Nombre del host
				String n = ""; // Nombre del servicio
				int s = 3; // Codigo de estado desconocido
				String r = ""; // Ultimo checkeo
				String d = ""; // Tiempo activo
				String i = ""; // Informacion del servicio
				String tmp = "";
				int pos;
				Date temp;

				t = scan.nextLine();

				while (!t.contains("}")) {

					if (t.contains("service_description")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						n = tmp;

					} else if (t.contains("host_name")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						h = tmp;

					} else if (t.contains("current_state")) {
						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						s = Integer.parseInt(tmp);

					} else if (t.contains("last_check")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						r = tmp;
						temp = new java.util.Date(
								(long) Integer.parseInt(r) * 1000);
						r = temp.toLocaleString();

					} else if (t.contains("last_time_ok")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						d = tmp;
						/** REVISAR COMO CALCULAR LA DURACION UP **/

						temp = new java.util.Date(
								(long) Integer.parseInt(d) * 1000);
						d = temp.toLocaleString();

					} else if (t.contains("plugin_output")
							&& !t.contains("long_plugin_output")) {

						tmp = t.toString();
						tmp = tmp.trim();
						pos = tmp.indexOf("=");
						tmp = tmp.substring(pos + 1);
						i = tmp;

					}

					t = scan.nextLine();
				}

				Servicio se = new Servicio(n, h, s, r, d, i);

				serv.add(se);
			}
		}

		scan.close();

		return serv;

	}

}