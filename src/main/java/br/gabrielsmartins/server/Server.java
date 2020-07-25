package br.gabrielsmartins.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
	
	private final ServerSocket serverSocket;
	private final ExecutorService threadPool;
	private AtomicBoolean active;

	public Server(ServerSocket serverSocket) throws IOException {
		this.serverSocket = serverSocket;
		this.threadPool = Executors.newCachedThreadPool(new ThreadServerFactory()); 
		this.active = new AtomicBoolean(true);
	}
	
	public void run() throws IOException {
		System.out.println("----- Starting Server ... ----------");
		System.out.println("----- Server started sucessfully " + serverSocket + " ----------");
		while(isActive().get()) {
			try {
				Socket socket = this.serverSocket.accept();
				System.out.println("New Client at Port : " + socket.getPort());
				TaskManager manager = new TaskManager(threadPool, this, socket);
				threadPool.execute(manager);
			}catch(SocketException e) {
				System.out.println("SocketException active: " + this.active);
			}
		
		}
	}
	
	public void shutdown() throws IOException {
		System.out.println("----- Stopping Server ... ----------");
		this.active = new AtomicBoolean(false);
		this.serverSocket.close();
		this.threadPool.shutdown();
	}
	
	public AtomicBoolean isActive() {
		return active;
	}
	
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(12345);
		Server server = new Server(socket);
		server.run();
		server.shutdown();
	}

}
