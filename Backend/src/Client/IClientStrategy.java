package Client;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tal and Alex on 19/05/2017.
 */
public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
