package com.sonar.secretspro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Utils {

	public static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, Messages.getString("Utils.0")); //$NON-NLS-1$
		Cipher cipher = Cipher.getInstance(Messages.getString("Utils.1")); //$NON-NLS-1$
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	public static byte[] decrypt1(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, Messages.getString("Utils.2")); //$NON-NLS-1$
		Cipher cipher = Cipher.getInstance(Messages.getString("Utils.3")); //$NON-NLS-1$
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	public static byte[] getRawKey(String password) {
		byte[] key = null ;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(Messages.getString("Utils.4")); //$NON-NLS-1$
			SecureRandom sr = SecureRandom.getInstance(Messages.getString("Utils.5"),Messages.getString("Utils.6")); //$NON-NLS-1$ //$NON-NLS-2$
			sr.setSeed(password.getBytes());
			kgen.init(256, sr); // 192 and 256 bits may not be available
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			key = raw;
		} catch (Exception e) {
		}
		return key;
	}

	public static byte[] encrypt(String textToEncrypt, byte[] key) {
		byte[] dataToEncrypt = textToEncrypt.getBytes();
		byte[] encryptedData = null;
		try {
			encryptedData = Utils.encrypt(key, dataToEncrypt);
		} catch (Exception e) {}
		return encryptedData;
	}

	public static String decrypt(byte[] encryptedText, byte[] key) {
		String decryptedText = null;
		byte[] dataToDecrypt = encryptedText;
		byte[] decryptedData;
		try {
			decryptedData = Utils.decrypt1(key, dataToDecrypt);
			decryptedText = new String(decryptedData);
		} catch (Exception e) {}
		return decryptedText;
	}

	public synchronized static boolean mv(File src, File dst)
	{
		FileChannel inChannel = null ;
		FileChannel outChannel = null ;
		boolean success=false;
		if(src.isDirectory()){
			return true;
		}
		if(src.toString().equals(dst.toString())){
			return true;
		}
		if(dst.exists()){
			success = src.delete();
			return true;
		}
		if(src.renameTo(dst)){
			return true;
		}
		try {
			inChannel = new FileInputStream(src).getChannel();
			outChannel = new FileOutputStream(dst).getChannel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    try
	    {
	        inChannel.transferTo(0, inChannel.size(), outChannel);
	        success = src.delete();
	        
	    }
	    catch (Exception e) {
			
			e.printStackTrace();
		}
	    finally
	    {
	        try {
				if (inChannel != null)
				    inChannel.close();
				if (outChannel != null)
				    outChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		return success;
	}

	public static void saveToFile(String fileName, String data) {
		if(data==null)
			return;
		try {
			FileWriter f=new FileWriter(fileName);
			f.write(data+Messages.getString("Utils.7")); //$NON-NLS-1$
			f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public static String readFromFile(String fileName) {
		String data=Messages.getString("Utils.8"); //$NON-NLS-1$
		try {
			BufferedReader f=new BufferedReader(new FileReader(fileName));
			data=f.readLine();
			f.close();
		} catch (Exception e) {}
		return data;
	}

}