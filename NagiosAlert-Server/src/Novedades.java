import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

//Verifica si hay novedades en el estado de algun servicio.
public class Novedades implements Runnable {

	String apiKey = "Your Api Key";
	int numOfRetries = 3;
	String registrationId = "";
	boolean primera = true;
	ArrayList<Servicio> s1, s2;
	ArrayList<Host> h1, h2;

	private Thread t;
	private String nombre;

	public Novedades(String n) {
		this.nombre = n;

	}

	@Override
	public void run() {
		// Aqui se revisa periodicamente si ha habido campos ene l status o la
		// cantidad de los host y servicios.
		while (true) {
			System.out.println("Checking Changes");

			if (primera) {
				
				primera = false;
				h1 = Parser.parseHosts();
				s1 = Parser.parseServicios();

			} else {

				h2 = h1;
				h1 = Parser.parseHosts();
				Collections.sort(h1, Host.hostName);
				Collections.sort(h2, Host.hostName);

				s2 = s1;
				s1 = Parser.parseServicios();

				Collections.sort(s1, Servicio.servicioName);
				Collections.sort(s2, Servicio.servicioName);

				boolean c1, c2;
				c1 = cambiosHost(h1, h2);
				c2 = cambiosServicios(s1, s2);

				if (c1 || c2) {
					int i = 0;
					//notificar();
				}
			}

			try {
				Thread.sleep(10000);// 20 seg
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void start() {
		if (t == null) {
			t = new Thread(this, "novedades");
			t.start();
		}
	}

	public void notificar() throws IOException {

		Sender sender = new Sender(apiKey);
		Message message = new Message.Builder().addData("message", "Novedades")
				.build();

		registrationId = getClientId();

		try {
			Result result = sender.send(message, registrationId, numOfRetries);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean cambiosServicios(ArrayList<Servicio> s1,
			ArrayList<Servicio> s2) {

		String i;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String fecha = dateFormat.format(date);

		// Verificamos cantidad de host y de servicios
		if (s1.size() != s2.size()) {
			i = fecha + " -- Cambio en la cantidad de Serivios. Antes= "
					+ s1.size() + " || Ahora= " + s2.size() + "\n";

			escribirLog(i);
			return true;
		}

		// Iteramos por los servicio

		Iterator it1 = s1.iterator();
		Iterator it2 = s2.iterator();

		while (it1.hasNext() && it2.hasNext()) {

			Servicio t1, t2;
			t1 = (Servicio) it1.next();
			t2 = (Servicio) it2.next();

			String n1 = t1.getNombre();
			String n2 = t2.getNombre();
			int st1 = t1.getStatus();
			int st2 = t2.getStatus();

			if (!n1.equals(n2)) {
				System.out.println("Novedad en Nombre Servicio!!");
				i = fecha + " -- Cambio en el host  "  + t1.getHost() + ", nombre del Servicio cambio. Antes= "
						+ n1 + " || Ahora= " + n2 + "\n";

				escribirLog(i);
				return true;
			} else if (st1 != st2) {
				System.out.println("Novedad en Status Servicio!!");

				i = fecha + " -- Cambio en el host "   + t1.getHost() + ", el Status del Servico " + n1 + " cambio. Antes= "
						+ st1 + " || Ahora= " + st2 + "\n";
				escribirLog(i);
				return true;
			}
		}

		return false;
	}

	private boolean cambiosHost(ArrayList<Host> h1, ArrayList<Host> h2) {

		String i;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String fecha = dateFormat.format(date);

		// Verificamos cantidad de host y de servicios
		if (h1.size() != h2.size()) {
			i = fecha + " -- Cambio en la cantidad de Hosts. Antes= "
					+ h1.size() + " || Ahora= " + h2.size() + "\n";
			return true;
		}

		// Iteramos por los servicio
		Iterator it1 = h1.iterator();
		Iterator it2 = h2.iterator();

		while (it1.hasNext() && it2.hasNext()) {

			Host t1, t2;
			t1 = (Host) it1.next();
			t2 = (Host) it2.next();
			String n1 = t1.getNombre();
			String n2 = t2.getNombre();
			int s1 = t1.getStatus();
			int s2 = t2.getStatus();

			if (!n1.equals(n2)) {
				System.out.println("Novedad en Nombre Host!!");

				i = fecha + " -- Cambio en el nombre del host. Antes= " + n1
						+ " || Ahora= " + n2 + "\n";

				escribirLog(i);

				return true;
			} else if (s1 != s2) {
				System.out.println("Novedad en Status Host!!");

				i = fecha + " -- Cambio en el Status del host " + n1 + " . Antes= " + s1
						+ " || Ahora= " + s2 + "\n";

				escribirLog(i);
				return true;
			}

		}

		return false;
	}

	protected String getClientId() throws IOException {

		String id;

		String home = System.getProperty("user.home");
		String archivo = home + "/nagiosAlert/client_id";

		// Cargamos el contenido actual
		FileInputStream fis = new FileInputStream(archivo);

		BufferedReader buffer = new BufferedReader(new InputStreamReader(fis));

		StringBuilder sb = new StringBuilder();
		String linea, info, new_info;
		new_info = "";

		while ((linea = buffer.readLine()) != null) {
			sb.append(linea + "\n");
		}

		fis.close();

		id = sb.toString();

		return id;
	}

	protected boolean escribirLog(String i) {

		// Preparamos el archivo de incidentes
		String home = System.getProperty("user.home");
		String archivo = home + "/nagiosAlert/log-NagiosAlert.txt";
		String data = "";

		// Se lee todo el archivo, y se le anexa los nuevos incidente al inicio.
		try {

			// Cargamos el contenido actual
			FileInputStream fis = new FileInputStream(archivo);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					fis));

			StringBuilder sb = new StringBuilder();
			String linea, info, new_info;
			new_info = "";

			while ((linea = buffer.readLine()) != null) {
				sb.append(linea + "\n");
			}

			fis.close();

			// Agregamos el nuevo incidente
			info = sb.toString();

			info = i + "\n" + info;

			// Guardamos el nuevo archivo.
			FileOutputStream fos = new FileOutputStream(new File(archivo));
			fos.write(info.getBytes());
			fos.close();
			return true;

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return false;

	}
}
