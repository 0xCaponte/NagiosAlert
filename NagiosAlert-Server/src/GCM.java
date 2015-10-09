
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class GCM
 */
@WebServlet("/GCM")
public class GCM extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GCM() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {

			System.out.println("Reciving CLIENT's ID....");
			// Carga todo lo enviado por el cliente en un solo String
			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			// Parsea el string del cliente, en funcion del tipo de informacion
			// que contiene, en este caso una PublicKey
			Gson gson = new Gson();
			Type tipo = new TypeToken<String>() {
			}.getType();

			JsonElement json = new JsonParser().parse(sb.toString());

			String clientid = gson.fromJson(json, tipo);

			String dir = System.getProperty("user.dir");
			String archivo_id = dir + "nagiosAlert/client_id";

			try {

				// Guardamos el nuevo archivo.
				FileOutputStream fos = new FileOutputStream(
						new File(archivo_id));
				fos.write(clientid.getBytes());
				fos.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			System.out.println("Client's ID ---> " + clientid.getBytes());
			
			response.getOutputStream().print("ok");

			// Enviamos respuesta al cliente
			response.getOutputStream().flush();

		} catch (Exception ex) {
			ex.printStackTrace();
			response.getOutputStream().print("fail");
			response.getOutputStream().flush();
		}
	}

}
