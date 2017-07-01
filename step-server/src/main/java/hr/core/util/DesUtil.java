package hr.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DesUtil {


	  static final String key = "20140401";
	    static final String iv = "12345678";
	
	 
	    public static byte[] encryptDES(byte[] data) throws CodingException{
	    	  return encryptDES(data,key,iv);
	    }
	 
	    public static byte[] decryptDES(byte[] data) throws CodingException{
	    	return decryptDES(data,key,iv);
	    }
	    
	    public static byte[] encryptDES(byte[] data,String key,String iv) throws CodingException{
	        try{
		    	IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
		        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "DES");
		        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		        cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);
		        byte[] encryptedData = cipher.doFinal(data);
		        return encryptedData;
	        }catch(Exception e){
	        	throw new CodingException(e);
	        }
	    }
	 
	    public static byte[] decryptDES(byte[] data,String key,String iv) throws CodingException{
	    	 try{
		        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
		        SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "DES");
		        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		        cipher.init(Cipher.DECRYPT_MODE, skey, zeroIv);
		        byte decryptedData[] = cipher.doFinal(data);
		        return decryptedData;
	    	 }catch(Exception e){
		        throw new CodingException(e);
		     }
	    }
	

}