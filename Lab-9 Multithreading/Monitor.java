package lab9;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Monitor extends Thread {
	private Data d; // shared object
	private int tails = 0; // count number of tails
	private int heads = 0; // count number of heads public

	public Monitor(Data d) {
		this.d = d;
	}

	public void run() {

		for (int i = 0; i < d.getNop(); i++) {

			synchronized (d) {

				while (d.ispChance()) {
					System.out.println("Monitor waiting");
					try {
						d.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Monitor is going to read value: ");
				if (d.getResult() == 0) {
					tails++;
				} else {
					heads++;
				}

				d.setmChance(false);
				d.setpChance(true);
				d.notifyAll();
			}
		}

		System.out.println("Monitor writing to output.txt");
		PrintWriter outStream = null;

		try {
			outStream = new PrintWriter(new FileOutputStream("out.txt"));
			outStream.println("HEADS: " + heads);
			outStream.println("TAILS: " + tails);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

	public int getTails() {
		return tails;
	}

	public int getHeads() {
		return heads;
	}

}
