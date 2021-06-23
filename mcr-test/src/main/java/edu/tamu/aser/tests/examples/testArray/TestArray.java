package edu.tamu.aser.tests.examples.testArray;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.Test2.D2;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class TestArray {

    static int length = 20;
    static int[] arr = new int[length];


    public static void main(String[] args) {

        for (int i = 0; i < length; i++)
            arr[i] = 0;

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < length; i++) {
                    System.out.println("arr[" + i + "]=" + arr[i]);
                    if (arr[i] == 0 && i > 0)
                        throw new RuntimeException("error");
                }


            }


        });
        t2.start();

        for (int i = 0; i < length; i++) {
            arr[i] = i;
        }

        //b = y;
        //int c = y;

        try {
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            TestArray.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}