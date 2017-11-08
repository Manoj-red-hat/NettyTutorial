import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Manoj Kumar on 11/6/2017.
 * Contact: manoj.kumar.mbm@gmail.com
 */
public class PlainEchoServerIO {

    public void serve(int port) throws IOException{
        final ServerSocket ss=  new ServerSocket(port);
        try{
            while(true){
                final Socket clientSocket =ss.accept();
                System.out.println("Accepted Connection from"+clientSocket);
                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                            PrintWriter print= new PrintWriter(clientSocket.getOutputStream(),true);
                            while (true){
                                print.println(reader.readLine());
                                print.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
