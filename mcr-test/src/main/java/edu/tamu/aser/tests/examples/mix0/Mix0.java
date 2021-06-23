/**
 * This package is for testing MCR under RMMs
 *
 * @author Alan
 * @author Alan
 */
/**
 * @author Alan
 *
 */
package edu.tamu.aser.tests.examples.mix0;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.mergesort.MergeSort;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class Mix0 {
    static int x;
    static int y;
    static int z = 0;
    static int b = 0;
    public static void main(String[] args) {
        int a = 0;
        x = 0;
        y = 0;
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                y = 1;
                b = x;
            }
        });
        t2.start();

        x = 1;
        a = y;
		try {
			t2.join();
			System.out.println("a= " + a+ ","+ "b= "+b);
//			if(a==0 && b==0){
//				System.out.println("error");
//			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        if(a!=1 && b!=0){
            throw new RuntimeException("error");
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            Mix0.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}