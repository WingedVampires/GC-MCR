package edu.tamu.aser.tests.examples.reorder;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class ReorderTest2 {
    static int iSet=4;

    public void run() {
	SetThread2[] sts = new SetThread2[iSet];
	CheckThread2 ct;
	SetCheck2 sc=new SetCheck2();
	for (int i=0;i<iSet;i++) {
	    (sts[i] = new SetThread2(sc, i)).start();
	}
	try {
   	    for (int i=0;i<iSet;i++) {
	        sts[i].join();
	    }
	}catch(InterruptedException ie) {
    }
    
	ct = new CheckThread2(sc);
	ct.start();
    }

    public static void main(String[] args) {
	if (args != null && args.length == 1) {
		iSet = Integer.parseInt(args[0]);
	}
	ReorderTest2 t = new ReorderTest2();
	t.run();
    }

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			ReorderTest2.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
