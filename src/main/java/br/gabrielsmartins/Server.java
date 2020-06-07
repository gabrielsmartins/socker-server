package br.gabrielsmartins;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		System.out.println("----- Starting Server ... ----------");
		try (ServerSocket serverSocket = new ServerSocket(12345)) {
			System.out.println("----- Server started sucessfully " + serverSocket + " ----------");
			
			ExecutorService threadPool = Executors.newCachedThreadPool(); 

			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("New Client at Port : " + socket.getPort());
				TaskManager manager = new TaskManager(socket);
				threadPool.execute(manager);
			}
		}
		
	}

}
