public class Sobre{
	
	byte[] key;

	byte[] text;
	
	protected Sobre(byte[] k, byte[] t){
		
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