package br.gabrielsmartins.command;

import java.io.PrintStream;
import java.util.Random;
import java.util.concurrent.Callable;

public class DatabaseCommand implements Callable<String> {

	private PrintStream response;

	public DatabaseCommand(PrintStream response) {
		this.response = response;
	}

	@Override
	public String call() throws Exception {
		System.out.println("Executing Database C2 Command ...");
		response.println("Processing Database C2 Command ...");
		Thread.sleep(2000);
		Integer number = new Random().nextInt(100) + 1;
        System.out.println("Database C2 Command was successfully executed");
        return number.toString();
	}

	

}
