package Server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Tal and Alex on 19/05/2017.
 */
public class Server {
    private int port;
    private int listeningInterval;
    private volatile boolean stop;
    private IServerStrategy serverStrategy;
    //private static ExecutorService serverThreadPool;



    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        stop = false;
        //serverThreadPool=Executors.newFixedThreadPool(2);
    }
    public void start(){
        new Thread(()->runServer()).start();
        //serverThreadPool.execute(this::runServer); //ThreadPool for Servers

    }
    public void runServer(){
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            ExecutorService executor = propertiesConfig();//ThreadPool for each client
            while (!stop) {
                try {
                    Socket aClient = server.accept(); // blocking call
                    executor.execute(() -> handleClient(aClient));
                }
                catch (SocketTimeoutException e)
                {

                }
            }
            executor.shutdown();
            server.close();
        }
        catch (IOException e)
        {
        }
    }
    private void handleClient(Socket aClient){
        try{
            serverStrategy.serverStrategy(aClient.getInputStream(), aClient.getOutputStream());
            aClient.getInputStream().close();
            aClient.getOutputStream().close();
        }
        catch(IOException e)
        {
        }


    }
    public void stop() {
        System.out.println("Server has stopped.");
       // serverThreadPool.shutdown();
        stop = true;

    }
    private ExecutorService propertiesConfig(){

        Properties p=new Properties();
        try {
            InputStream is = new FileInputStream("config.properties");
            p.load(is);
            try {
                int returnValue= Integer.parseInt(p.getProperty("NumOfThreads"));
                ExecutorService returnEx=Executors.newFixedThreadPool(returnValue);
                return returnEx;
            }catch (NumberFormatException nE){return Executors.newCachedThreadPool();}

        }catch ( IOException io){
           return Executors.newCachedThreadPool();
        }

    }
}
