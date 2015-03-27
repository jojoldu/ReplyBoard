package zum.potal.dwlee.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coremedia.iso.Hex;

public class SHA256 {

	private static final Logger logger = LoggerFactory.getLogger(SHA256.class);
	
	public static String encode(String password) throws NoSuchAlgorithmException{
		String SHA = ""; 
		String salt ="zuminternet-pilot";
		try{
			MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
			
			sh.update((password+salt).getBytes()); 
			byte byteData[] = sh.digest();
			
			SHA = Hex.encodeHex(byteData);
			
		}catch(NoSuchAlgorithmException e){
			logger.error("암호화 error : "+e);
			throw e; 
		}
		return SHA;
	}
}
