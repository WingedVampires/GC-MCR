package edu.tamu.aser.tests.examples.atmoerror;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class AtomErrorMain {
	public static void main(String[] args) {

	      BankAccount account = new BankAccount();
	     
	      Thread t1 = new Thread(new Customer(5, account));
	      Thread t2 = new Thread(new Customer(5, account));
	      
	      t1.start();
	      t2.start();
	      
//	      for(int i = 0; i < 2; i++) {
//	          new Thread(new Customer(100, account)).start();
//	      }
	      /*try {
			Thread.sleep(100L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      if(account.getTotal() != 500)
	    	  throw new RuntimeException();*/
	  }

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			AtomErrorMain.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
