package select.spring.util;

public class MoneyUtils {

	public static String convertHangul(String anum){
		
		String money = anum.replaceAll(",", "");
		
	    String[] han1 = {"","일","이","삼","사","오","육","칠","팔","구"};
	    String[] han2 = {"","십","백","천"};
	    String[] han3 = {"","만","억","조","경"};
	 
	    StringBuffer result = new StringBuffer();
	    int len = money.length();
	    for (int i = len-1; i >= 0; i--) {
	        result.append( han1[Integer.parseInt( money.substring(len-i-1, len-i))] );
	        if ( Integer.parseInt( money.substring(len-i-1, len-i) ) > 0 )
	            result.append( han2[i%4] );
	        if(i%4 == 0)
	            result.append( han3[i/4] );
	    }
	    return result.toString();
	}
	
	public static String convertHangul(int anum){
		String snum = String.valueOf(anum);
		return convertHangul(snum);
	}	
	
	public static String convertHangul(long anum){
		String snum = String.valueOf(anum);
		return convertHangul(snum);
	}
	
	public static int getInt(Object num) {
		int inum = 0;
		if (num instanceof Integer) {
			inum = ((Integer) num).intValue();
		} else if (num instanceof Long) {
			inum = ((Long) num).intValue();
		} else  if (num instanceof String) {
			String snum = ((String) num).replaceAll(",", "");
			inum = Integer.parseInt(snum);
		}
		return inum;
	}

	public static long getLong(Object num) {
		long lnum = 0;
		if (num instanceof Integer) {
			lnum = ((Integer) num).longValue();
		} else if (num instanceof Long) {
			lnum = ((Long) num).longValue();
		} else  if (num instanceof String) {
			String snum = ((String) num).replaceAll(",", "");
			lnum = Long.parseLong(snum);
		}
		return lnum;
	}
}
