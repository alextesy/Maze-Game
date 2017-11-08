package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tal and Alex on 02/05/2017.
 */
/**
 * DeCompressor of a Maze
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;
    public MyDecompressorInputStream(InputStream in){
        this.in=in;
    }

    @Override
    public int read() throws IOException {
      return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        int temp=0; //First free place in b
        byte num;
            int i=0;
            int iter;
            while((iter=in.read())!=-1){
            if(i%2==0)
                num=0;
            else
                num=1;
            while(iter>0){
                b[temp]=num;
                temp++;
                iter--;
            }
            i++;
        }
    return 0;
    }
}
