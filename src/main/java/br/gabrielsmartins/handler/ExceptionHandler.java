package br.gabrielsmartins.handler;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable t) {
		System.out.println("Error on thread " + thread.getName() + ", Exception: " + t);

	}

}
