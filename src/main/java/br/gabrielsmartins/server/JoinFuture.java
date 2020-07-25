package br.gabrielsmartins.server;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class JoinFuture implements Callable<Void> {

	private PrintStream response;
	private Future<String> futureWS;
	private Future<String> futureDatabase;

	public JoinFuture(PrintStream response, Future<String> futureWS, Future<String> futureDatabase) {
		this.response = response;
		this.futureWS = futureWS;
		this.futureDatabase = futureDatabase;
	}

	@Override
	public Void call() {
		try {
			System.out.println("Waiting for results WS and Database");
			String resultWS = this.futureWS.get(15, TimeUnit.SECONDS);
			String resultDatabase = this.futureDatabase.get(15,TimeUnit.SECONDS);
			this.response.println("Result C2 Command: " + resultWS + " , " + resultDatabase);
		} catch (Exception e) {
			this.response.println("Timeout C2 Command ...");
			System.out.println("Stopping tasks ...");
			this.futureDatabase.cancel(true);
			this.futureDatabase.cancel(true);
		}
		System.out.println("C2 Command Finished");
		response.println("C2 Command was successfully executed");
		return null;
	}

	
}
