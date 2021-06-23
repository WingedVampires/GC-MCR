package edu.tamu.aser.tests.examples.Test;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.mix0.Mix0;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class D {
    int x = 0;

    void m1() {
        x++;
    }

    void m2() {
        x *= 2;
    }

    void test1() throws Exception {
        final D d = new D();
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
        if (d.x < 1) {
            throw new Exception();
        }
    }

    public static void main(String[] args) throws Exception {
        D d = new D();
        d.test1();
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            D.main(null);
        } catch (Exception e) {
            System.out.println("here");
        }
    }
}
