package external.jpf_test_cases.replicatedcasestudies;

//import ca.replicatedworkers.ReplicatedWorkers;
//import ca.replicatedworkers.Configuration;

import edu.tamu.aser.reex.JUnit4MCRRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.fail;

@RunWith(JUnit4MCRRunner.class)
public class Generic {
    public static void main(String arg[]) {
        ReplicatedWorkers theInstance;
        Configuration theConfig;
        Collection workPool;
        Collection resultPool;


        theConfig = new Configuration(Configuration.EXCLUSIVE,
                Configuration.SYNCHRONOUS,
                Configuration.SOMEVALUES);
        workPool = new Collection();
        resultPool = new Collection();

        theInstance = new ReplicatedWorkers(theConfig, workPool, resultPool,
                4, 1);
        theInstance.putWork();
        theInstance.execute();
        System.out.println("pool size " + theInstance.getPoolSize());

        assert (theInstance.GlobalDone || theInstance.getPoolSize() == 0);

        theInstance.destroy();
    }

    @Test
    public void test() throws InterruptedException {
        try {
//			lock = new Object();
            Generic.main(null);
        } catch (Exception e) {
            System.out.println("here");
            fail();
        }
    }
}

