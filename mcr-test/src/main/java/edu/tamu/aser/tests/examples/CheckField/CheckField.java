package edu.tamu.aser.tests.examples.CheckField;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class CheckField {

	static InstanceExample ex;
	private int num;
	public static void main(String[] args) throws InterruptedException {

		ex = new InstanceExample();
		Thread t1 = new Thread() {
			public void run() {
				System.out.println("new thread." + ex.number);
				ex.number = 12;
				ex.num2 = 12;
				assert ex.number == 12;
				int c = ex.num2;
				//c -= ex.number;
			}
		};
		t1.setName("123");

		Thread t2 = new Thread() {
			public void run() {
				//int a = ex.number;
				ex.number = 13;
				ex.num2 = 1;
			}
		};
		t1.start();
		t2.start();
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			CheckField.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
