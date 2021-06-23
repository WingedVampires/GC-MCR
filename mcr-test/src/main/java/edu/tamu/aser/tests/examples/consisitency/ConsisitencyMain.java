package edu.tamu.aser.tests.examples.consisitency;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class ConsisitencyMain{
	public static int THREAD_NUMBER = 3;


	
	public static void main(String[] args) throws Exception {
		Thread[] t = new Thread[THREAD_NUMBER];
		for (int i = 0; i < THREAD_NUMBER; i++) {
			t[i] = new Thread(new Consisitency(i));
			t[i].start();
		}
		
		for (int i = 0; i < THREAD_NUMBER; i++) {
			t[i].join();
		}
		
		System.out.println("a = " + Consisitency.a + ", b = " + Consisitency.b);
		if (Consisitency.a != Consisitency.b) {
			throw new Exception("bug found.");
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
			ConsisitencyMain.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
