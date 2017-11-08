import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class PlainEchoServerNIOTest {
    @Test
    public void serve() throws Exception {
        PlainEchoServerNIO ps= new PlainEchoServerNIO();
        ps.serve(123);
    }

}