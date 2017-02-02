
package peersgui;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;


// thread for sending the files to other peers requesting the file

public class serverThread extends Thread{
    private Socket st=null;
    private String FILE_TO_SEND;
    public serverThread(Socket st){
        this.st=st;
    } 
    
    public void run(){
        try{
            DataInputStream is=new DataInputStream(st.getInputStream());
            FILE_TO_SEND=is.readLine();
            File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = new byte [10000000];
            FileInputStream fis = new FileInputStream(myFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = st.getOutputStream();
            int bytesRead=0;
            int current=0;
            while((bytesRead=bis.read(mybytearray))>0){
               	current+=bytesRead;
          	os.write(mybytearray,0,bytesRead);
          	os.flush();
            }
            System.out.println("Sending " + FILE_TO_SEND + "(" + current + " bytes)");
            if (fis != null) fis.close();
            if (bis != null) bis.close();
            if (st != null) st.close();
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
