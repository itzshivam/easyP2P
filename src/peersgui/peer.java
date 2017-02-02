
package peersgui;

import java.net.*;
import java.io.*;
import java.util.*;



public class peer implements Runnable{
    private static ServerSocket ss=null;
    private static String username="Sahil";   // client's username
    private static String ip="172.25.12.114"; // client's ip
    private static String host = "172.25.12.114";  // central server ip
    private static long size;
    
    
//    function to change the host ip
    public static void changeHost(String s){
        host=s;
    }
    
    
//    function to execute commands based the whether it is for uploading or downloading
    
    public static void command(String input){
        try{
            File file=new File("Userinfo.txt");
            Scanner sc=new Scanner(file);
            username=sc.nextLine();
            ip=sc.nextLine();
            //System.out.println(username+" "+ip);
            sc.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        int portNumber = 2222;
        try{
                if(input.startsWith("INSERT:")){
                    int i=0;
                    for(i=0;i<input.length();i++){
                        if(input.charAt(i)==':'){
                            i++;
                            break;
                        }
                    }
                    String loc="";
                    for(;i<input.length();i++){
                        loc+=input.charAt(i);
                    }
                    Upload up=new Upload();
                    String[] res=up.result(loc,username,ip);
                    
                    try{
		    	Socket clientSocket = new Socket(host, portNumber);
			DataInputStream is=new DataInputStream(clientSocket.getInputStream());
            		PrintStream os=new PrintStream(clientSocket.getOutputStream());
                        for(int j=0;j<res.length;j++){
                            os.println(res[j]);
                        }
                    }
                    catch(Exception e){
			System.out.println(e);			
                    }
                }
                else if(input.startsWith("DATA:")){
                    int i=0;
                    for(i=0;i<input.length();i++){
                        if(input.charAt(i)==':'){
                            i++;
                            break;
                        }
                    }
                    String ip="";
                    for(;i<input.length();i++){
                        if(input.charAt(i)=='~'){
                            i++;
                            break;
                        }
                        ip+=input.charAt(i);
                    }
                    String loc="";
                    for(;i<input.length();i++){
                        if(input.charAt(i)=='~'){
                            i++;
                            break;
                        }
                        loc+=input.charAt(i);
                    }
                    String res="";
                    for(;i<input.length();i++){
                        res+=input.charAt(i);
                    }
                    try{
                        Socket ct=new Socket(ip,3333);
                        new clientThread(ct,loc,res,size).start();
		    }
		    catch(Exception e){
		    	System.out.println(e);
		    }
                }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
//    function to return the results based on the input search string
    public static String search(String input){
        Scanner inp=new Scanner(System.in);
        int portNumber = 2222;
        String host = "172.25.12.114";
        String result="";
        try{
                if(input.startsWith("SEARCH:")){
                    try{
		    	Socket clientSocket = new Socket(host, portNumber);
			DataInputStream is=new DataInputStream(clientSocket.getInputStream());
            		PrintStream os=new PrintStream(clientSocket.getOutputStream());
                    	os.println(input);
                        String num=is.readLine();
                        int nm=Integer.parseInt(num);
                        for(int i=0;i<nm;i++){
                            result+=is.readLine()+"\n";
                        }
                    }
                    catch(Exception e){
                    	System.out.println(e);			
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        return result;
    }
    
    
//    function for downloading the files 
    public static void download(String input,String Downloadloc){
        Scanner inp=new Scanner(System.in);
        int portNumber = 2222;
        String host = "172.25.12.114";
        String result="";
        try{
                if(input.startsWith("DOWNLOAD:")){
                    try{
		    	Socket clientSocket = new Socket(host, portNumber);
			DataInputStream is=new DataInputStream(clientSocket.getInputStream());
            		PrintStream os=new PrintStream(clientSocket.getOutputStream());
                    	os.println(input);
                        String num=is.readLine();
                        int nm=Integer.parseInt(num);
                        String siz=is.readLine();
                        size=Long.parseLong(siz);
                        //System.out.println(size);
                        for(int i=0;i<nm;i++){
                            result+=is.readLine()+"\n";
                        }
                        String temp="";
                        
                            temp="DATA:";
                        int i=0;
                            
                            for(i=0;i<result.length();i++){
                                if(result.charAt(i)=='~'){
                                    i++;
                                    break;
                                }
                                temp+=result.charAt(i);
                            }
                            temp+="~";
                            for(;i<result.length();i++){
                                if(result.charAt(i)=='\n'){
                                    i++;
                                    break;
                                }
                                temp+=result.charAt(i);
                            }
                            temp+="~"+Downloadloc;
                        System.out.println(size);
                        System.out.println(temp);
                        command(temp);
                        
                        //bool arr[]
                        
                    }
                    catch(Exception e){
                    	System.out.println(e);			
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
    }
    
    
//    calls the server thread 
    public void run(){
        try{
            ss=new ServerSocket(3333);
        }
        catch(Exception e){
            System .out.println(e);
        }
        
        try{
            while(true){
                Socket st=ss.accept();
                new serverThread(st).start();
            }
        }
        catch(Exception e){
            System .out.println(e);
        }
    }
    
}


