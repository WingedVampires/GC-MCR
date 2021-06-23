package edu.tamu.aser.tests.examples.Test2;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.mix0.Mix0;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class D2 {

    int x = 0;

    void m1() {
        x++;
    }

    void m2() {
        x *= 2;
    }

    void test1() throws Exception {
        final D2 d = new D2();
        Thread d1 = new Thread() {
            public void run() {
                d.m1();
            }
        };
        Thread d2 = new Thread() {
            public void run() {
                d.m2();
            }
        };
        d1.start();
        d2.start();
        d1.join();
        d2.join();
        if (d.x < 2) {
            throw new Exception();
        }

    }

    public static void main(String[] args) throws Exception {
        D2 d = new D2();
        d.test1();
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            D2.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}
