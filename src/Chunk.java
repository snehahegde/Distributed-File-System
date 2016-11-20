
public class Chunk {
	private final String chunkId;
	private String server;
	private int port;
	
	
	public Chunk(String domain, int port, String chunkId) {
		this.chunkId = chunkId;
		server = domain;
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getChunkId() {
		return chunkId;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
}
