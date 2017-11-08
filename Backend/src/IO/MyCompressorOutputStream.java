package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Tal and Alex on 02/05/2017.
 */

/**
 * Compressor of a Maze
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;
    public MyCompressorOutputStream(OutputStream out){
       this.out=out;
    }
    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    @Override
    public void write(byte[] b) throws IOException {
        int counter=0;
        for(int i=0;i<b.length-1;i++){
            if(b[i]!=b[i+1]){
                write(++counter);
                counter=0;
            }
            else{
                if(counter>255) {
                    write(255);
                    write(0);
                    counter=1;
                }
                counter++;
            }
            if(i+1==b.length-1)
                write(++counter);
        }
    }
}
