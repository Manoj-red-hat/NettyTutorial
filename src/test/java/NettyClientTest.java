import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class NettyClientTest {
    @Test
    public void start() throws Exception {
        new NettyClient("127.0.0.1",123).start();
    }

}