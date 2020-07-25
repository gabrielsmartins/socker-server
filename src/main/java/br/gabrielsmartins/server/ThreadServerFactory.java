package br.gabrielsmartins.server;

import java.util.concurrent.ThreadFactory;

import br.gabrielsmartins.handler.ExceptionHandler;

public class ThreadServerFactory implements ThreadFactory {

	private static int number = 1;

	@Override
	public Thread newThread(Runnable runnable) {
		Thread thread = new Thread(runnable, "Thread Server" + number);
		ThreadServerFactory.number++;
		thread.setUncaughtExceptionHandler(new ExceptionHandler());
		return thread;
	}

}
