package com.test.bookproject.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

	
	public static String getEncrypt(String source) {
		return getEncrypt(source, "test".getBytes());
	}
	
	public static String getEncrypt(String source, byte[] salt) {
		String result = "";
		
		byte[] a = source.getBytes();
		byte[] bytes = new byte[a.length + salt.length];
		
		System.arraycopy(a, 0, bytes, 0, a.length);
		System.arraycopy(salt, 0, bytes, a.length, salt.length);
		
		try {
			// 암호화 방식 지정 메소드
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);
			byte[] byteData = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
