package br.gabrielsmartins.command;

import java.io.PrintStream;

public class CommandC1 implements Runnable {

	private PrintStream response;

	public CommandC1(PrintStream response) {
		this.response = response;
		
	}

	@Override
	public void run() {
		System.out.println("Executing C1 Command ...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
        response.println("C1 Command was successfully executed");
	}

}
