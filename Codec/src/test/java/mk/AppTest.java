package mk;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void echoServer() {
        try {
            App.echoServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}