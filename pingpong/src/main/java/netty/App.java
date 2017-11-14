package netty;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
      Logger log = Logger.getLogger(App.class.getName());
      new Client(12312,10);
       // new Server("127.0.0.1",12312);
    }
}
