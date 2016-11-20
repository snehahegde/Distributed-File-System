import java.io.Serializable;


public class ChunkServerRegistrationRequest implements Serializable{
	private String domainName;
	private int port;
	
	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ChunkServerRegistrationRequest(String name, int port){
		domainName = name;
		this.port = port;
	}
}
