package com.jeancoder.ticketingsys.ready.util;

public class StringUtil {
	
	public static String trim(String str){
		if(str == null){
			return null;
		}
		return str.trim();
	}
	
	public static boolean isEmpty(String str){
		if(str == null){
			return  true;
		}
		return str.isEmpty();
	}
	
	
	public static byte[] subStringByBytes(String str, int start, int end) {
		
		if (str == null || str.length() == 0) {
			return  new byte[0];
		}
		
		int endI = end;
		if (end > str.getBytes().length) {
			endI = str.getBytes().length;
		}
		byte[] bytes = new byte[endI - start];
			
		System.arraycopy(str.getBytes(), start, bytes, 0, bytes.length);
		return bytes;
	}
	
	
	public static byte[] addBytes(byte[] bytes1, byte[] bytes2) {
		byte[] bytes3 = new byte[bytes1.length + bytes2.length];
		System.arraycopy(bytes1, 0, bytes3, 0, bytes1.length);
		System.arraycopy(bytes2, 0, bytes3, bytes1.length, bytes2.length);
		return  bytes3;
	}
	
	
	public static void  main(String[] arg) {
		String content = "MFwwDQYJKoZIhvcNAQEBBQA是DSwAwSAJBAIC8ui+TIUfbFoTdtzcJNZqTGnuogVbsM/xnqo0wMH/K/qk7dx0iIhoC+Fr7HvxRSpFvlEUJDu45r3sKRErnjT8CAwEAAQ==";

		byte[] bytes = new byte[0];
		for (int i = 0; i < content.getBytes().length; i += 50) {
			byte[] bytes1 = subStringByBytes(content, i, i+50);
			bytes = addBytes(bytes, bytes1);
		}
		String aa = new String(bytes);
		
		println content.length() +"___"+ aa.length()
		
		println content.getBytes().length;
		
		println aa + "--" + content
		println aa.equals(content);
		
	}
}
