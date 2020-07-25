package br.gabrielsmartins;

import java.io.PrintStream;

public class CommandC2 implements Runnable {

	private PrintStream response;

	public CommandC2(PrintStream response) {
		this.response = response;
		
	}

	@Override
	public void run() {
		System.out.println("Executing C2 Command ...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
        response.println("C2 Command was successfully executed");
	}

}
