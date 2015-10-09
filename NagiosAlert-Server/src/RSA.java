import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSA {

	String dir = System.getProperty("user.dir");
	String archivo_privada = dir + "/private";
	String archivo_publica = dir + "/publica";

	public RSA() {
	}

	public void guardarLlave_RSA(String archivo, BigInteger mod, BigInteger exp)
			throws IOException {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(archivo);
			oos = new ObjectOutputStream(new BufferedOutputStream(fos));

			oos.writeObject(mod);
			oos.writeObject(exp);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				oos.close();

				if (fos != null) {
					fos.close();
				}
			}
		}
	}

	public KeyPair generarLlaves_RSA() throws NoSuchAlgorithmException,
			InvalidKeySpecException, IOException, NoSuchProviderException {

		KeyPair kp = null;

		File archivo_pri = new File(archivo_privada);
		File archivo_pu = new File(archivo_publica);

		if (!archivo_pri.exists() || !archivo_pu.exists()) {

			// Creamos llaves nuevas porque el par no esta completo.
			KeyPairGenerator kpg = null;

			try {
				kpg = KeyPairGenerator.getInstance("RSA");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			kpg.initialize(1024);
			kp = kpg.generateKeyPair();
			PublicKey publicKey = kp.getPublic();
			PrivateKey privateKey = kp.getPrivate();

			// Guardamos las llave en los archivos.
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(publicKey,
					RSAPublicKeySpec.class);
			RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(
					privateKey, RSAPrivateKeySpec.class);

			guardarLlave_RSA(archivo_publica, rsaPubKeySpec.getModulus(),
					rsaPubKeySpec.getPublicExponent());
			guardarLlave_RSA(archivo_privada, rsaPrivKeySpec.getModulus(),
					rsaPrivKeySpec.getPrivateExponent());

			return kp;

		} else {

			// Devolvemos las llaves preexistentes
			PublicKey pub = leerPublica(archivo_publica);
			PrivateKey priv = leerPrivada(archivo_privada);

			kp = new KeyPair(pub, priv);
			return kp;
		}

	}

	// Cifra con la llave privada

	public byte[] cifrar_RSA(String data) throws IOException {

		byte[] d = data.getBytes("UTF-8");
		byte[] en = null;
		try {
			PrivateKey p = leerPrivada(archivo_privada);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, p);
			en = cipher.doFinal(d);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return en;
	}

	// Descifra con la llave publica
	public String descifrar_RSA(byte[] data) throws IOException {

		byte[] d = null;
		String s = null;
		try {
			PrivateKey p = leerPrivada(archivo_publica);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, p);
			d = cipher.doFinal(data);
			s = new String(d);
			return s;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return s;
	}

	public PublicKey leerPublica() throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(new File(archivo_publica));
			ois = new ObjectInputStream(fis);

			BigInteger modulo = (BigInteger) ois.readObject();
			BigInteger exponente = (BigInteger) ois.readObject();

			// Buscar Publica
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulo,
					exponente);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey p = fact.generatePublic(rsaPublicKeySpec);

			return p;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return null;
	}
	
	public PublicKey leerPublica(String archivo) throws IOException {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(new File(archivo));
			ois = new ObjectInputStream(fis);

			BigInteger modulo = (BigInteger) ois.readObject();
			BigInteger exponente = (BigInteger) ois.readObject();

			// Buscar Publica
			RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulo,
					exponente);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PublicKey p = fact.generatePublic(rsaPublicKeySpec);

			return p;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return null;
	}

	public PrivateKey leerPrivada(String archivo) throws IOException {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(new File(archivo));
			ois = new ObjectInputStream(fis);

			BigInteger modulo = (BigInteger) ois.readObject();
			BigInteger exponente = (BigInteger) ois.readObject();

			// Buscar Llave Privada
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulo,
					exponente);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey p = fact.generatePrivate(rsaPrivateKeySpec);

			return p;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return null;
	}
	
	public PrivateKey leerPrivada() throws IOException {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(new File(archivo_privada));
			ois = new ObjectInputStream(fis);

			BigInteger modulo = (BigInteger) ois.readObject();
			BigInteger exponente = (BigInteger) ois.readObject();

			// Buscar Llave Privada
			RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulo,
					exponente);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey p = fact.generatePrivate(rsaPrivateKeySpec);

			return p;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (ois != null) {
				ois.close();
				if (fis != null) {
					fis.close();
				}
			}
		}
		return null;
	}

}
