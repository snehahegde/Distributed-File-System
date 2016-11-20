import java.io.Serializable;






public class Request implements Serializable{
	private FileOperation operation;
	private String filename;
	
	/*public enum Action {
		CREATE, APPEND, DELETE, READ
	}*/
	
	
	public Request(FileOperation.Action action, String filename) {
		operation = new FileOperation();
		operation.setAction(action);
		this.filename  = filename;
	}
	
	public FileOperation.Action getAction() {
		return operation.getAction();
	}
	public void setAction(FileOperation.Action action) {
		operation.setAction(action);
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
