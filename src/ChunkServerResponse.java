import java.io.Serializable;


public class ChunkServerResponse implements Serializable{
	private int resultCode;
	
	public ChunkServerResponse(int code) {
		resultCode = code;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	
}
