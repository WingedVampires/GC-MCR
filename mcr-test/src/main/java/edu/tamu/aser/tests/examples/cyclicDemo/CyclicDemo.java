package edu.tamu.aser.tests.examples.cyclicDemo;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class CyclicDemo {
    public int count = 0;
    public int leader;

    public CyclicDemo() {
    }

    public static void main(String[] args) throws InterruptedException {
        CyclicDemo shared = new CyclicDemo();
        IncThread[] threads = new IncThread[4];

        int i;
        for(i = 0; i < 4; ++i) {
            threads[i] = new IncThread(i, shared);
            threads[i].start();
        }

        for(i = 1; i < 4; ++i) {
            threads[i].join();
        }

        synchronized(shared) {
            assert shared.count == 4;

        }

        if(shared.leader != 3) {
            System.out.println(shared.leader);
             throw new RuntimeException("error");
        }
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            CyclicDemo.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}