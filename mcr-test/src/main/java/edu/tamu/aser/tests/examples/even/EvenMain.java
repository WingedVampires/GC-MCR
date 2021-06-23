package edu.tamu.aser.tests.examples.even;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class EvenMain {

	public static void main(String[] args) {
		EvenGenerator generator = new EvenGenerator();
		Thread t1 = new Thread(new EvenChecker(generator));
		Thread t2 = new Thread(new EvenChecker(generator));
//		Thread t3 = new Thread(new EvenChecker(generator));
//		Thread t4 = new Thread(new EvenChecker(generator));
		
		t1.start();
		t2.start();
//		t3.start();
//		t4.start();
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			EvenMain.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
