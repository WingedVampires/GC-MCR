package edu.tamu.aser.tests.examples.datarace;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class DataRaceMain {
	
	public static int THREAD_NUMBER = 2;

	public static void main(String[] args) throws Exception {
		int nAccount = 10;
		Account[] accounts = new Account[10];
		for (int i = 0; i < 10; i++) {
			accounts[i] = new Account();
		}
		CustomerInfo ci = new CustomerInfo(nAccount, accounts);
		
		ThreadRun[] t = new ThreadRun[THREAD_NUMBER];
		for (int i = 0; i < THREAD_NUMBER; i++) {
			t[i] = new ThreadRun(ci);
			t[i].start();
		}
		for (int i = 0; i < THREAD_NUMBER; i++) {
			t[i].join();
		}
		
		Checker checker = new Checker(ci);
		Thread ct = new Thread(checker);
		ct.start();
		ct.join();
		if (checker.buggy) {
			throw new Exception("bug found.");
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			DataRaceMain.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
