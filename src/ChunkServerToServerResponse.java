import java.io.Serializable;


public class ChunkServerToServerResponse implements Serializable {
	private String fileName;
	private long freeSpaceInLastChunk;
	
	public ChunkServerToServerResponse(String filename, long freeSpace) {
		fileName = filename;
		freeSpaceInLastChunk = freeSpace;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFreeSpaceInLastChunk() {
		return freeSpaceInLastChunk;
	}
	public void setFreeSpaceInLastChunk(long freeSpaceInLastChunk) {
		this.freeSpaceInLastChunk = freeSpaceInLastChunk;
	}
	
	
}
