import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class PlainEchoServerNIO2Test {
    @Test
    public void serve() throws Exception {
        PlainEchoServerNIO2 ps= new PlainEchoServerNIO2();
        ps.serve(123);
    }

}