package redes3.proyecto.nagiosalert;

public class Sobre {

	byte[] key;
	byte[] text;

	public Sobre(byte[] k, byte[] t) {

		this.key = k;
		this.text = t;
	}

	public byte[] getKey() {
		return key;
	}

	public byte[] getText() {
		return text;
	}
}
