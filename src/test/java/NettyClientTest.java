import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Manoj Kumar on 11/8/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class NettyClientTest {
    @Test
    public void start() throws Exception {
        BufferedReader bfr = org.mockito.Mockito.mock(BufferedReader.class);
        when(bfr.readLine()).thenReturn("ok11").thenReturn("ok12").thenReturn("ok12")
                .thenReturn("ok12").thenReturn("ok12").thenReturn("ok12")
                .thenReturn("ok12").thenReturn("ok12").thenReturn("ok12")
                .thenReturn("ok12").thenReturn("ok12").thenReturn("ok12");
        new NettyClient("127.0.0.1",12312).start();
    }

}