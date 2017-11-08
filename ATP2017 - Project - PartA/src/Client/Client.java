package Client;

import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Tal and Alex on 19/05/2017.
 */
public class Client {
    private InetAddress IP;
    private int port;
    private IClientStrategy clientStrategy;

    public Client(InetAddress IP, int port, IClientStrategy clientStrategy) {
        this.IP = IP;
        this.port = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer() {
        try{
            Socket toServer = new Socket(IP,port);
            System.out.println("Connected to Server.");
            clientStrategy.clientStrategy(toServer.getInputStream(),toServer.getOutputStream());
            toServer.close();
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
}
