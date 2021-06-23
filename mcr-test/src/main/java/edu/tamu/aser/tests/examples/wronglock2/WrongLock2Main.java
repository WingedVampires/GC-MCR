package edu.tamu.aser.tests.examples.wronglock2;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.Test2.D2;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class WrongLock2Main implements Runnable {
	
	public static Struct s = new Struct(1, 0);
	
	public static int THREADS = 5;
	
	public static void main(String[] args) throws Exception {
		Thread[] t = new Thread[THREADS];
		for (int i = 0; i < THREADS; i++) {
			t[i] = new Thread(new WrongLock2Main());
			t[i].start();
		}
		for (int i = 0; i < THREADS; i++) {
			t[i].join();
		}
		
		if (s.getCount() != THREADS) {
			throw new Exception("bug found.");
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			WrongLock2Main.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}

	@Override
	public void run() {
		s = new Struct(s.getNumber() * 2, s.getCount() + 1);
	}
	
	public static class Struct {
		int number;
		int count;
		public Struct(int number, int count) {
			this.number = number;
			this.count = count;
		}
		public int getNumber() {
			return number;
		}
		
		public int getCount() {
			return count;
		}
	}
}
