package br.gabrielsmartins.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import br.gabrielsmartins.task.ConsumerTask;
import br.gabrielsmartins.task.TaskManager;

public class Server {
	
	private final ServerSocket serverSocket;
	private final ExecutorService threadPool;
	private final BlockingQueue<String> commandQueue;
	private AtomicBoolean active;

	public Server(ServerSocket serverSocket, BlockingQueue<String> commandQueue) throws IOException {
		this.serverSocket = serverSocket;
		this.commandQueue = commandQueue;
		this.threadPool = Executors.newCachedThreadPool(new ThreadServerFactory()); 
		this.active = new AtomicBoolean(true);
		initializeConsumers();
	}
	
	private void initializeConsumers() {
		for(int i=0; i < 3; i++) {
			ConsumerTask consumerTask = new ConsumerTask(commandQueue);
			this.threadPool.execute(consumerTask);
		}
	}

	public void run() throws IOException {
		System.out.println("----- Starting Server ... ----------");
		System.out.println("----- Server started sucessfully " + serverSocket + " ----------");
		while(isActive().get()) {
			try {
				Socket socket = this.serverSocket.accept();
				System.out.println("New Client at Port : " + socket.getPort());
				TaskManager manager = new TaskManager(threadPool, commandQueue, this, socket);
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
		BlockingQueue<String> commandQueue = new ArrayBlockingQueue<>(3);
		Server server = new Server(socket, commandQueue);
		server.run();
		server.shutdown();
	}

}
