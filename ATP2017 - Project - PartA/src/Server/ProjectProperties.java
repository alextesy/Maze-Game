package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by Tal and Alex on 02/06/2017.
 */
public class ProjectProperties {
    private static final Properties prop = new Properties();

    static {
         OutputStream output = null;
         try {
             output = new FileOutputStream("config.properties");
             prop.setProperty("Generator", "SimpleMazeGenerator");
             prop.setProperty("Search", "DFS");
             prop.setProperty("NumOfThreads", "10");
             prop.store(output, null);
         } catch (IOException io) {
             io.printStackTrace();
         } finally {
             if (output != null) {
                 try {
                     output.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
     }
}
