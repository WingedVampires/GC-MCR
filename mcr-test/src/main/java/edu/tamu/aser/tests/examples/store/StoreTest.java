package edu.tamu.aser.tests.examples.store;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.mix0.Mix0;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class StoreTest implements Runnable {
	
	public static int THREAD_COUNT = 2;
	
	public static Store store = new Store();
	
	public static void main(String[] args) throws Exception {
		Thread[] thread = new Thread[THREAD_COUNT];
		for (int i = 0; i < THREAD_COUNT; i++) {
			thread[i] = new Thread(new StoreTest());
			thread[i].start();
		}
		
		for (int i = 0; i < THREAD_COUNT; i++) {
			try {
				thread[i].join();
			} catch (InterruptedException e) {
			}
		}
		
		check();
	}
	
	private static void check() throws Exception {
		if (store.getCost() != THREAD_COUNT * 300) {
			throw new Exception("bug found.");
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			store.consume(100);
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			StoreTest.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
