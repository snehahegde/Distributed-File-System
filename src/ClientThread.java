
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class ClientThread implements Runnable{
	private HashMap<String, Integer> chunkServerDetails;
	private HashMap<String, File> fileToFileObjectMap;
	
	public ClientThread(HashMap<String, Integer> hashMap, HashMap<String, File> fileMap) {
		chunkServerDetails = hashMap;
		fileToFileObjectMap = fileMap;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			ServerSocket serverSocket = new ServerSocket(6666);
			System.out.println("Server : Listening on port 6666");
			
			while(true) {
				Socket clientSocket = serverSocket.accept();
				DataInputStream input = new DataInputStream(clientSocket.getInputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(input);
				
				DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
				
				
				Request request = (Request) objectInputStream.readObject();
				//String readData = input.readUTF();
					
				if(request != null) {
					System.out.print("Action: ");
					System.out.println(request.getAction());
					System.out.print("Filename: ");
					System.out.println(request.getFilename());
					
					File newFile = null;
					Response response = null;
					if(request.getAction() == FileOperation.Action.CREATE) {
						if(!fileToFileObjectMap.containsKey(request.getFilename())) {
							newFile = new File(request.getFilename());
							fileToFileObjectMap.put(request.getFilename(), newFile);
							
							//to get random chunk server
							Random random = new Random();
							List<String> keys =  new ArrayList<String>(chunkServerDetails.keySet());
							String randomChunkServer = keys.get(random.nextInt(keys.size()));
							int port = chunkServerDetails.get(randomChunkServer);
							
							//to get the new chunk id
							int chunkNumber = newFile.getChunks().size();
							String chunkId = newFile.getFileName() + "_" + chunkNumber;
							Chunk chunk = new Chunk(randomChunkServer, port, chunkId);
							ArrayList<Chunk> chunks = newFile.getChunks();
							chunks.add(chunk);
							newFile.setFreeSpace(1000);
						} else {
							newFile = fileToFileObjectMap.get(request.getFilename());
						}
						//send response code successful/ unsuccessful
						
					} else if(request.getAction() == FileOperation.Action.APPEND) {
						if(fileToFileObjectMap.containsKey(request.getFilename())) {
							newFile = fileToFileObjectMap.get(request.getFilename());
							
						}else {
							newFile = new File(request.getFilename());
							fileToFileObjectMap.put(request.getFilename(), newFile);
							
							//to get random chunk server
							Random random = new Random();
							List<String> keys =  new ArrayList<String>(chunkServerDetails.keySet());
							String randomChunkServer = keys.get(random.nextInt(keys.size()));
							int port = chunkServerDetails.get(randomChunkServer);
							
							//to get the new chunk id
							int chunkNumber = newFile.getChunks().size();
							String chunkId = newFile.getFileName() + "_" + chunkNumber;
							Chunk chunk = new Chunk(randomChunkServer, port, chunkId);
							ArrayList<Chunk> chunks = newFile.getChunks();
							chunks.add(chunk);
							newFile.setFreeSpace(100);
						}
						
						System.out.println("Free space: " + newFile.getFreeSpace());
						
						if(newFile.getFreeSpace() > 0) {
							Chunk chunk = newFile.getChunks().get(newFile.getChunks().size() - 1);
							response = new Response(chunk.getServer(), chunk.getPort(), chunk.getChunkId(), newFile.getFreeSpace());
						//} else if(newFile.getFreeSpace() == 0) {
						} else {
							//to get random chunk server
							Random random = new Random();
							List<String> keys =  new ArrayList<String>(chunkServerDetails.keySet());
							String randomChunkServer = keys.get(random.nextInt(keys.size()));
							int port = chunkServerDetails.get(randomChunkServer);
							
							//to get the new chunk id
							int chunkNumber = newFile.getChunks().size();
							String chunkId = newFile.getFileName() + "_" + chunkNumber;
							Chunk chunk = new Chunk(randomChunkServer, port, chunkId);
							newFile.getChunks().add(chunk);
							newFile.setFreeSpace(100);
							response = new Response(chunk.getServer(), chunk.getPort(), chunk.getChunkId(), newFile.getFreeSpace());
						}
					}
					
					/*Response response;
					synchronized (chunkServerDetails) {
						Random random = new Random();
						List<String> keys =  new ArrayList<String>(chunkServerDetails.keySet());
						String randomChunkServer = keys.get(random.nextInt(keys.size()));
						response = new Response(randomChunkServer, chunkServerDetails.get(randomChunkServer), newFile.getFreeSpace());	
					}*/
					objectOutputStream.writeObject(response);
					
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
