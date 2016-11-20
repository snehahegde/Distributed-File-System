import java.io.Serializable;


public class FileOperation implements Serializable{
	private Action action;
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public enum Action {
		CREATE, APPEND, DELETE, READ
	}
}
