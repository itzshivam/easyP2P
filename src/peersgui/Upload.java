

package peersgui;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
    
public class Upload {

//    temp queue to store all the upload files and their hashes
    private Queue <String> filesToUpload;

    
//    result function to be called from peer for calculating the hash of the files
    public String[] result(String location , String username,String ip)
    {
        filesToUpload=new LinkedList <String> ();
        fillQueue(location,username,ip);

        int size=filesToUpload.size();

        String [] resultToReturn=new String[size];

        Iterator it=filesToUpload.iterator();

        int i=0;
        while(it.hasNext())
        {
            resultToReturn[i]=(String)it.next();
            i++;
        }
        
        
        return resultToReturn;

    }

//  fills the queue with the hash values of the files and directories
    
    public void fillQueue(String location,String username,String ip){

        File[] listOfFiles;
        
        char separator='/';
        
        
        try{
            
            File folder=new File(location);

            if(folder.exists())
            {
                if(folder.isDirectory())
                {
                    listOfFiles=folder.listFiles();
                    
                    for(int j=0;j<listOfFiles.length;j++)
                    {
                            fillQueue(location+separator+listOfFiles[j].getName(),username,ip);                           
                    }
                    
                }
                
                else
                {
                     CreateHash ch=new CreateHash();
                     String hash=ch.getHash(location);
                     File f=new File(location);
                     long bytes=f.length();
                     String type="";
                     String filename="";
                     int i=location.length()-1;
                     for(;i>=0;i--){
                         if(location.charAt(i)=='.'){
                             i--;
                             break;
                         }
                         type=location.charAt(i)+type;
                     }
                     for(;i>=0;i--){
                         if(location.charAt(i)=='/' || location.charAt(i)=='\\'){
                             i--;
                             break;
                         }
                         filename=location.charAt(i)+filename;
                     }
                    
                    filesToUpload.add("INSERT:"+username+"~"+ip+"~"+hash+"~"+filename+"~"+location+"~"+bytes+"~"+type);
                }
            }

        }
        catch(Exception e){
            System.out.println(e);
        }

            

    }
    
}