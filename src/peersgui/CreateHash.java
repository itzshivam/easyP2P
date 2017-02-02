
package peersgui;



import java.io.FileInputStream;
import java.security.MessageDigest;


// class for calculating hash of a file

public class CreateHash {

    public String getHash(String datafile) throws Exception {

    MessageDigest md = MessageDigest.getInstance("SHA1");
    FileInputStream fis = new FileInputStream(datafile);
    byte[] dataBytes = new byte[1024];

    int nread = 0;

    while ((nread = fis.read(dataBytes)) != -1) {
      md.update(dataBytes, 0, nread);
    };

    byte[] mdbytes = md.digest();

    //convert the byte to hex format
    StringBuffer sb = new StringBuffer("");
    for (int i = 0; i < mdbytes.length; i++) {
    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    return sb.toString();
  }
}