package edu.tamu.aser.tests.examples.simpleExample;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class SimpleExampleTest {
    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            SimpleExample.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}
