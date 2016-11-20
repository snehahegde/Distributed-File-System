import java.io.Serializable;


public class ClientToChunkServerRequest implements Serializable{
	private FileOperation operation;
	private String chunkId;
	private String filename;
	private byte[] data;
	private int offset;
	
	public ClientToChunkServerRequest(FileOperation.Action action , String chunkId, String filename, byte[] data, int offset) {
		operation = new FileOperation();
		operation.setAction(action);
		this.chunkId = chunkId;
		this.filename = filename;
		this.data = data;
		this.offset = offset;
	}

	public FileOperation getOperation() {
		return operation;
	}

	public void setOperation(FileOperation operation) {
		this.operation = operation;
	}

	public String getChunkId() {
		return chunkId;
	}

	public void setChunkId(String chunkId) {
		this.chunkId = chunkId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
