package br.gabrielsmartins;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		System.out.println("---- Getting Server Connection ... ----");
		Socket socket = new Socket("localhost", 12345);
		
	
		Thread requestThread = new Thread(() -> {
			try {
				PrintStream ps = new PrintStream(socket.getOutputStream());
				
				Scanner request = new Scanner(System.in);
				
				while(request.hasNextLine()) {
					String line = request.nextLine();
					
					if(line.trim().equals("")) {
						break;
					}
					
					ps.println(line);
				}
				
				ps.close();
				request.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		
		Thread responseThread = new Thread(() -> {
			try {
				Scanner response = new Scanner(socket.getInputStream());
				
				System.out.println("Getting Response");
				while(response.hasNextLine()) {
					String line = response.nextLine();
					System.out.println(line);
				}

				response.close();
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		});
		
		requestThread.start();
		responseThread.start();
		
		requestThread.join();
		
		System.out.println("Closing Connection ...");
		socket.close();
		System.out.println("---- Successful Connection ... ----");
	}

}
