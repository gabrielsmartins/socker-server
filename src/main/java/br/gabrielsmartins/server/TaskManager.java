package br.gabrielsmartins.server;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import br.gabrielsmartins.command.CommandC1;
import br.gabrielsmartins.command.DatabaseCommand;
import br.gabrielsmartins.command.WebServiceCommand;

public class TaskManager implements Runnable {

	private ExecutorService threadPool;
	private Socket socket;
	private Server server;
	

	public TaskManager(ExecutorService threadPool, Server server, Socket socket) {
		this.threadPool = threadPool;
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
					CommandC1 c1Command = new CommandC1(response);
					this.threadPool.execute(c1Command);
					break;

				case "C2":
					response.println("Command Confirmation :" + content);
					WebServiceCommand wsC2Command = new WebServiceCommand(response);
					DatabaseCommand databaseC2Command = new DatabaseCommand(response);
					Future<String> futureWS = this.threadPool.submit(wsC2Command);
					Future<String> futureDatabase = this.threadPool.submit(databaseC2Command);
					threadPool.submit(new JoinFuture(response, futureWS,futureDatabase));
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
