import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class EchoServerNettyTest {
    @Test
    public void start() throws Exception {
        new EchoServerNetty(12312).start();
    }

}