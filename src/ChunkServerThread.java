import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class ChunkServerThread implements Runnable{
	private HashMap<String, Integer> chunkServerDetails;
	private HashMap<String, File> fileToFileObjectMap;
	
	public ChunkServerThread(HashMap<String, Integer> hashMap, HashMap<String, File> fileMap) {
		chunkServerDetails = hashMap;
		fileToFileObjectMap = fileMap;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(4444);
			System.out.println("Server : Listening on port 4444");
			
			while(true) {
				Socket chunkServerSocket = serverSocket.accept();				
				
				DataInputStream input = new DataInputStream(chunkServerSocket.getInputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(input);
				
				DataOutputStream output = new DataOutputStream(chunkServerSocket.getOutputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
				
				Object o = objectInputStream.readObject(); 
				if( o instanceof ChunkServerRegistrationRequest) {
					ChunkServerRegistrationRequest chunkServerRequest = 
							(ChunkServerRegistrationRequest) o;
					if(chunkServerRequest != null) {
						String chunkServerName =  chunkServerRequest.getDomainName();
						int chunkServerPort = chunkServerRequest.getPort();
						synchronized(chunkServerDetails) {
							chunkServerDetails.put(chunkServerRequest.getDomainName(),
									chunkServerRequest.getPort());
						}
						
						System.out.println("chunkserverName:" + chunkServerName);
						System.out.println("chunkserverName:" + chunkServerPort);
					}
				} else if(o instanceof ChunkServerToServerResponse){
					ChunkServerToServerResponse chunkServerResponse = 
							(ChunkServerToServerResponse) o;
					if(chunkServerResponse != null) {
						File file = fileToFileObjectMap.get(chunkServerResponse.getFileName());
						System.out.println("Free space: " + chunkServerResponse.getFreeSpaceInLastChunk());
						file.setFreeSpace(chunkServerResponse.getFreeSpaceInLastChunk());
					}
					
				}
				objectInputStream.close();
				objectOutputStream.close();
				input.close();
				output.close();
				chunkServerSocket.close();
				//serverSocket.close();
				
			}
			
		
		} catch(IOException | ClassNotFoundException  e) {
			System.out.println(e);
		}
		
	}

}
