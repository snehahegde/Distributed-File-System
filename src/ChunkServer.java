import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;




public class ChunkServer {
	public static void main(String[] args) {
		String domain = args[0];
		int port = Integer.parseInt(args[1]);
		//String dir = args[2];
		try {
					
			Socket clientSocket = new Socket("localhost", 4444);
			System.out.println("Sending request from chunk server");
			
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
			
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			ObjectInputStream objectInputStream = new ObjectInputStream(input);
			
			
			
			ChunkServerRegistrationRequest chunkServerRequest = 
					new ChunkServerRegistrationRequest(domain, port);
			System.out.println("Sending request from chunk server for port" + port);
			objectOutputStream.writeObject(chunkServerRequest);			
			
			objectInputStream.close();
			objectOutputStream.close();
			input.close();
			output.close();
			clientSocket.close();
				
			
		}catch(IOException e) {
			System.out.println(e);
		}
		
		//creating a new directory
		File directory = new File(domain);
		directory.mkdir();
		if(directory.exists()) {
			System.out.println("Directory created succesfully");
		}
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true) {
			try {
				
				Socket clientSocket = serverSocket.accept();
				
				DataInputStream input = new DataInputStream(clientSocket.getInputStream());
				ObjectInputStream objectInputStream = new ObjectInputStream(input);
				
				ClientToChunkServerRequest requestFromClient = (ClientToChunkServerRequest) objectInputStream.readObject();
				System.out.println("Request from client to chunkserver");
				System.out.println(requestFromClient.getFilename());
				System.out.println(requestFromClient.getOperation().getAction());
				
				if(requestFromClient.getOperation().getAction() == FileOperation.Action.APPEND) {
					//File newFile = new File(directory, requestFromClient.getChunkId());
					try {
						FileOutputStream output = new FileOutputStream(directory.toString() + "/" + requestFromClient.getChunkId(), true);
							//FileWriter fileWriter = new FileWriter(requestFromClient.getChunkId()));
							//BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
							//bufferedWriter.write(requestFromClient.getData().toString());
						output.write(requestFromClient.getData());
						
					} catch(FileNotFoundException e) {
						System.out.println(e);
					}
				}
				File newFile = new File(directory, requestFromClient.getChunkId());
				ChunkServerToServerResponse chunkServerToServerResponse =
						new ChunkServerToServerResponse(requestFromClient.getFilename(),
								100 - newFile.length());
				try {
					
					Socket socket = new Socket("localhost", 4444);
					System.out.println("Sending request from chunk server");
					
					DataOutputStream output = new DataOutputStream(socket.getOutputStream());
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);
					
					objectOutputStream.writeObject(chunkServerToServerResponse);
					
					socket.close();
					output.close();
					objectOutputStream.close();
				} catch(IOException e) {
					System.out.println(e);
				}
				clientSocket.close();
				input.close();
				objectInputStream.close();
				
				
				
			} catch(IOException | ClassNotFoundException e) {
				System.out.println(e);
			}
			
		}		
	}
}
