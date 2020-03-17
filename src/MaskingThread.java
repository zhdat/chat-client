import java.io.*;

public class MaskingThread extends Thread {
	private volatile boolean stop;
	private char echoChar = '*';
	
	public MaskingThread(String prompt) {
		System.out.print(prompt);
	}
	
	public void run() {
		
		int priority = Thread.currentThread().getPriority();
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		
		try {
			stop = true;
			while(stop) {
				System.out.print("\010" + echoChar);
				try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		} finally {
			Thread.currentThread().setPriority(priority);
		}
	}
	
	public void stopMasking() {
		this.stop = false;
	}
}
