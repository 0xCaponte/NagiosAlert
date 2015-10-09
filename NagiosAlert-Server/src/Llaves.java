import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;

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
 * Servlet implementation class Llaves, este servlet permitira el intercambio de
 * las llaves publicas del cliente y del servidor.
 */
@WebServlet("/Llaves")
public class Llaves extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Llaves() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// Aqui enviamos nuestra llave publica.
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		// ------ Se busca en un archivo o en su defecto se crea----------
		String json = "";
		RSA rsa = new RSA();

		try {
			KeyPair kp = rsa.generarLlaves_RSA();

		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PublicKey p = rsa.leerPublica();

		json = new Gson().toJson(p);

		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	// Aqui recibimos la clave publica del usuario
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Procesamo el Json Mandado por el cliente
		processRequest(request, response);

	}
	
	// Funcuion que recibe el json de la llave publica del cliente
	protected void processRequest(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("application/json");
			// Gson gson = new Gson();

			try {
				
				//Carga todo lo enviado por el cliente en un solo String
				StringBuilder sb = new StringBuilder();
				String s;
				while ((s = request.getReader().readLine()) != null) {
					sb.append(s);
				}
				
				//Parsea el string del cliente, en funcion del tipo de informacion
				//que contiene, en este caso una PublicKey
				Gson gson = new Gson();
				Type tipo = new TypeToken<PublicKey>(){}.getType();
				
				JsonElement json = new JsonParser().parse(sb.toString());

				PublicKey llave = gson.fromJson(json, tipo);
				
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(llave,
						RSAPublicKeySpec.class);
				
				String dir = System.getProperty("user.dir");
				String archivo_cliente =  dir + "/archivo_cliente";
				
				RSA rsa = new RSA();
				
				//Escribimos en el archivo
				rsa.guardarLlave_RSA(archivo_cliente, rsaPubKeySpec.getModulus(),
						rsaPubKeySpec.getPublicExponent());
				
				response.getOutputStream().print("ok");
				
				//Enviamos respuesta al cliente 
				response.getOutputStream().flush();

			} catch (Exception ex) {
				ex.printStackTrace();
				response.getOutputStream().print("fail");
				response.getOutputStream().flush();
			}
		}


}
