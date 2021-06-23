package edu.tamu.aser.tests.examples.twoStage;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.Test2.D2;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

/**
 * @author Xuan
 * Created on Apr 27, 2005
 * 
 * Test Case 1 
   number of twostage threads :  1
   number of read threads     :  1
 */
@RunWith(JUnit4MCRRunner.class)
public class TwoStageMain extends Thread {
    static int iTthreads=1;
    static int iRthreads=1;
    TwoStage ts;
    Data data1,data2;
	
	public void run() {
		data1=new Data();
		data2=new Data();
		ts = new TwoStage(data1,data2);
		for (int i=0;i<iTthreads;i++)
			new TwoStageThread(ts).start();
		for (int i=0;i<iRthreads;i++)
			new ReadThread(ts).start();
	}
	
	public static void main(String[] args) {
		if (args.length < 2){
			//System.out.println("ERROR: Expected 2 parameters");
			TwoStageMain t=new TwoStageMain();
			t.run();
		}else{
			iTthreads = Integer.parseInt(args[0]);
			iRthreads = Integer.parseInt(args[1]);
			TwoStageMain t=new TwoStageMain();
			t.run();
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
//			lock = new Object();
			TwoStageMain.main(null);
		} catch (Exception e) {
			System.out.println("here");
			fail();
		}
	}
}
