import java.util.ArrayList;
import java.util.HashMap;


public class File {
	private String fileName;
	private ArrayList<Chunk> chunks;
	private long freeSpace;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArrayList<Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(ArrayList<Chunk> chunks) {
		this.chunks = chunks;
	}

	public long getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(long freeSpace) {
		this.freeSpace = freeSpace;
	}

	public File(String fileName) {
		this.fileName = fileName;
		chunks = new ArrayList<Chunk>();
		freeSpace = 0;
	}
}
