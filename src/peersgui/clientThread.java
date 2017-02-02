
package peersgui;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;


// thread to receive files from other peers 

public class clientThread extends Thread{
    private Socket ct=null;
    private String loc;
    private String FILE_TO_RECEIVED;
    private long FILE_SIZE;
    public clientThread(Socket ct,String loc,String res,long size){
        this.ct=ct;
        this.loc=loc;
        FILE_TO_RECEIVED=res;
        FILE_SIZE=size;
    }
    public void run(){
        try{
            long start_time = System.currentTimeMillis();
            long current_time=start_time;
            double percentage=0;
            double speed=0;
            PrintStream os=new PrintStream(ct.getOutputStream());
            os.println(loc);
            byte [] mybytearray  = new byte [10000000];
            InputStream is = ct.getInputStream();
            FileOutputStream fos = new FileOutputStream(FILE_TO_RECEIVED,true);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = is.read(mybytearray,0,10000000);
            int current = bytesRead;
            int j=0;
            do {
                j++;
                bos.write(mybytearray, 0 , bytesRead);
                bos.flush();
                bytesRead =is.read(mybytearray, 0, 10000000);
                if(bytesRead>=0){
                    current+=bytesRead;	
                }
                if(j%100==0){
                percentage=100*current/FILE_SIZE;
                
                current_time=System.currentTimeMillis();
                
                 speed=current/(current_time-start_time);
                 
                System.out.println(percentage+" "+speed);
                }
                
            } while(bytesRead > -1);
            System.out.println(percentage);
            
            System.out.println("File " + FILE_TO_RECEIVED+ " downloaded (" + current + " bytes read)");
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (ct != null) ct.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }
}
