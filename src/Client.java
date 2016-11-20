import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;





public class Client {
	private String chunkServerIP;
	private int chunkServerPort;
	private long freeSpace;
	private String chunkId;
	
	public Client() {
		chunkServerIP = null;
		chunkServerPort = 0;
		freeSpace = 0;
		chunkId = null;
	}
	public static void main(String[] args) {
		Client client = new Client();
		try {
			Socket socket = new Socket("localhost", 6666);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
			
			Request request = new Request(FileOperation.Action.APPEND, "test1");
			System.out.println("SEnding request from the client");
			objectOutputStream.writeObject(request);
			
			DataInputStream input = new DataInputStream(socket.getInputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(input);
			
			Response response = (Response) objectInputStream.readObject();
			if(response != null) {
				client.chunkServerIP = response.getChunkServerIP();
				System.out.println(response.getChunkServerIP());
				client.chunkServerPort = response.getPort();
				System.out.println(response.getPort());
				client.chunkId = response.getChunkId();
				client.freeSpace = response.getLastChunkFreeSpace();
				System.out.println(response.getLastChunkFreeSpace());
			}
			//output.writeUTF(new String("Hello!!"));
			output.flush();
			objectInputStream.close();
			objectOutputStream.close();
			output.close();
			socket.close();
			
			
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
		try{
			Socket socket = new Socket(client.chunkServerIP, client.chunkServerPort);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
			
			//Request request = new Request("GET", "test1");
			//Request request = new Request(FileOperation.Action.APPEND, "test1");
			String string = "Hello there!";
			byte[] data = string.getBytes();
			ClientToChunkServerRequest clientToChunkServerRequest = new ClientToChunkServerRequest(FileOperation.Action.APPEND, client.chunkId, "test1", data, 0);
			objectOutputStream.writeObject(clientToChunkServerRequest);
			
			DataInputStream input = new DataInputStream(socket.getInputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(input);
			
			Response response = (Response) objectInputStream.readObject();
			if(response != null) {
				System.out.println("domain name :" + response.getChunkServerIP());
				System.out.println("port" + response.getPort());
			}
			//output.writeUTF(new String("Hello!!"));
			output.flush();
			objectInputStream.close();
			objectOutputStream.close();
			output.close();
			socket.close();
			
			
		}catch(IOException | ClassNotFoundException e) {
			System.out.println(e);
		}
		
	}
}
