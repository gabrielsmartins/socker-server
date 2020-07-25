package br.gabrielsmartins;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class TaskManager implements Runnable {

	private Socket socket;
	private Server server;

	public TaskManager(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			System.out.println("Starting task " + socket);

			Scanner request = new Scanner(socket.getInputStream());

			PrintStream response = new PrintStream(socket.getOutputStream());

			while (request.hasNextLine()) {
				String content = request.nextLine();
				System.out.println(content);

				switch (content) {

				case "C1":
					response.println("Command Confirmation :" + content);
					break;

				case "C2":
					response.println("Command Confirmation :" + content);
					break;
					
				case "shutdown":
					response.println("Command Confirmation :" + content);
					this.server.shutdown();
					break;
					
				default: 
					response.println("Command Not Found");

				}

			}

			request.close();
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
