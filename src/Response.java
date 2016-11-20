import java.io.Serializable;


public class Response implements Serializable{
	private String chunkServerIP;
	private int port;
	private long lastChunkFreeSpace;
	private String chunkId;
	
	public Response(String response, int portNumber,String chunkId, long freeSpace) {
		chunkServerIP = response;
		port = portNumber;
		lastChunkFreeSpace = freeSpace;
		this.chunkId = chunkId;
	}

	public String getChunkId() {
		return chunkId;
	}

	public void setChunkId(String chunkId) {
		this.chunkId = chunkId;
	}

	public long getLastChunkFreeSpace() {
		return lastChunkFreeSpace;
	}

	public void setLastChunkFreeSpace(long lastChunkFreeSpace) {
		this.lastChunkFreeSpace = lastChunkFreeSpace;
	}

	public String getChunkServerIP() {
		return chunkServerIP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setChunkServerIP(String res) {
		chunkServerIP = res;
	}

}
