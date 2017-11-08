import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/6/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class FutureExampleTest {
    @Test
    public void printResult() throws Exception {
        int a[]=new int[1000];
        for(int i=0;i<1000;i++)
            a[i]=i;
        FutureExample fs= new FutureExample(a);
        System.out.println("execution of sumis in background");
        fs.printResult();
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

}