package external.jpf_test_cases.boundedbuffer;

/* from http://www.doc.ic.ac.uk/~jnm/book/ */
/* Concurrency: State Models & Java Programs - Jeff Magee & Jeff Kramer */
/* has a deadlock */

import edu.tamu.aser.reex.JUnit4MCRRunner;
import edu.tamu.aser.tests.examples.even.EvenMain;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

/****************************MAIN**************************/
/* written by me */
@RunWith(JUnit4MCRRunner.class)
public class BoundedBuffer {
  static int SIZE = 1; /* parameter */
  static Buffer buf;
  
  public static void main(String [] args) {
    buf = new Buffer(SIZE);
   
    new Producer(buf).start();
    new Consumer(buf).start();
    new Producer(buf).start();
//    new Consumer(buf).start();

//    new Producer(buf).start();
//    new Consumer(buf).start();
//    new Producer(buf).start();
//    new Consumer(buf).start();

  }

  @Test
  public void test() throws InterruptedException {
    try {
//			lock = new Object();
      BoundedBuffer.main(null);
    } catch (Exception e) {
      System.out.println("here");
      fail();
    }
  }
}


//@The following comments are auto-generated to save options for testing the current file
//@jcute.optionPrintOutput=false
//@jcute.optionLogPath=true
//@jcute.optionLogTraceAndInput=false
//@jcute.optionGenerateJUnit=false
//@jcute.optionExtraOptions=
//@jcute.optionJUnitOutputFolderName=d:\sync\work\cute\java
//@jcute.optionJUnitPkgName=
//@jcute.optionNumberOfPaths=43
//@jcute.optionLogLevel=2
//@jcute.optionLogStatistics=true
//@jcute.optionDepthForDFS=0
//@jcute.optionSearchStrategy=0
//@jcute.optionSequential=false
//@jcute.optionQuickSearchThreshold=100
//@jcute.optionLogRace=true
//@jcute.optionLogDeadlock=true
//@jcute.optionLogException=true
//@jcute.optionLogAssertion=true
//@jcute.optionUseRandomInputs=false
