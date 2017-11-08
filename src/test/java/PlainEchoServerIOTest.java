import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/6/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class PlainEchoServerIOTest {
    @Test
    public void serve() throws Exception {
        PlainEchoServerIO ps= new PlainEchoServerIO();
        ps.serve(123);
    }

}