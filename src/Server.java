import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
	private static String chunkServerName;
	private static int chunkServerPort;
	private HashMap<String, Integer> chunkServerDetails;
	private HashMap<String, File> fileToFileObjectMap;

	public Server() {
		chunkServerDetails = new HashMap<String, Integer>();
		fileToFileObjectMap = new HashMap<String, File>();
	}

	public static void main(String[] args) {
		
		Server server = new Server();
		
		ChunkServerThread chunkServerThread = new ChunkServerThread(
				server.chunkServerDetails, server.fileToFileObjectMap);
		Thread csThread = new Thread(chunkServerThread);
		csThread.start();

		ClientThread cThread = new ClientThread(server.chunkServerDetails, server.fileToFileObjectMap);
		Thread clientThread = new Thread(cThread);
		clientThread.start();
	}
}
