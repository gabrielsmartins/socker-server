package br.gabrielsmartins.task;

import java.util.concurrent.BlockingQueue;

public class ConsumerTask implements Runnable {
	 
	private final BlockingQueue<String> commandQueue;
	
	public ConsumerTask(BlockingQueue<String> commandQueue) {
		this.commandQueue = commandQueue;
	}
	
	@Override
	public void run() {
		try {
			String command = null;
			while((command = commandQueue.take())!= null) {
				System.out.println("Consuming comand " + command + ", Thread: " + Thread.currentThread().getName());
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
