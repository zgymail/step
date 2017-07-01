package hr.core.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatUtil {
	private static Pattern idCardPattern = Pattern.compile("^[\\d]{6}(19|20)*[\\d]{2}((0[1-9])|(11|12))([012][\\d]|(30|31))[\\d]{3}[xX\\d]*$");
	
	public static boolean isIdCard(String t){
		Matcher matcher = idCardPattern.matcher(t);
	    return matcher.find();
	}
	
	public static String transDefaultDateFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}

	public static Date transDefaultDateFormat(String date)
			throws ParseException {
		if (date == null)
			return null;
		if (date.length() < 16) {
			return transYearMonthDayFormat(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.parse(date);
	}
	
	public static String transYYYYMMDDHHMMSSDateFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Date transYYYYMMDDHHMMSSDateFormat(String date)
			throws ParseException {
		if (date == null)
			return null;
		if (date.length() < 19) {
			return transDefaultDateFormat(date);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}


	public static String transHHMMSSDateFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(date);
	}
	
	
	public static String transChinaYearMonthDayFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.format(date);
	}
	
	public static String transChinaYearMonthDayHMFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh点mm分");
		return sdf.format(date);
	}
	
	public static String transChinaYearMonthDayHMSFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh点mm分ss秒");
		return sdf.format(date);
	}
	
	public static String transYearMonthDayFormat(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}



	public static Date transYearMonthDayFormat(String date)
			throws ParseException {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	public static Date transChinaYearMonthDayFormat(String date)
			throws ParseException {
		if (date == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		return sdf.parse(date);
	}

	public static String transYearMonthDayFormat(java.sql.Date date) {
		if (date == null) {
			return "";
		}
		return transYearMonthDayFormat(new Date(date.getTime()));
	}

	public static String transDefaultDateFormat(java.sql.Date date) {
		if (date == null) {
			return "";
		}
		return transDefaultDateFormat(new Date(date.getTime()));
	}

	public static String transYearMonthDayFormat(Timestamp date) {
		if (date == null) {
			return "";
		}
		return transYearMonthDayFormat(new Date(date.getTime()));
	}

	public static String transDefaultDateFormat(Timestamp date) {
		if (date == null) {
			return "";
		}
		return transDefaultDateFormat(new Date(date.getTime()));
	}

	public static String transBooleanToSex(Boolean sex) {
		if (sex.booleanValue()) {
			return "男";
		} else {
			return "女";
		}
	}



	public static double getDouble(String str) {

		double dResult = 0;
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return dResult;
		}
		try {
			dResult = java.lang.Double.parseDouble(str);
		} catch (NumberFormatException e) {
			System.out.println("getDouble()" + e.getMessage());
		}
		return dResult;
	}

	public static Double getDoubleObject(String str) {
		return new Double(getDouble(str));
	}

	public static int getInt(String str) {
		int nResult = 0;
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return nResult;
		}
		try {
			nResult = java.lang.Integer.parseInt(str);
		} catch (NumberFormatException e) {
			System.out.println("getInt()" + e.getMessage());
		}

		return nResult;
	}

	public static Integer getIntObject(String str) {
		return new Integer(getInt(str));
	}

	public static String getString(String str) {
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return "";
		} else {
			return str.trim();
		}
	}
	
	
	/**
	 * Deprecated.
	 * 
	 * @param d
	 * @return
	 */
	//
	public static float floatFormat(float d) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00"); // ��һ�ָ�ʽ:"###,##0.00"
		return Float.parseFloat(df.format(d));
	}

	/**
	 * Deprecated.
	 * 
	 * @param d
	 * @param n
	 * @return double
	 */
	public static float floatFormat(float d, int n) {
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		return Float.parseFloat(df.format(d));
	}

	public static String floatFormatStringBy2dig(Object d) {
		return floatFormatString(d, 2);
	}

	public static String floatFormatString(Object d, int n) {
		/*
		 * double d = 1000.0 / 3.0; NumberFormat formatter =
		 * NumberFormat.getNumberInstance();
		 * formatter.setMaximumFractionDigits(4);
		 * //formatter.setMinimumIntegerDigits(6); String s =
		 * formatter.format(d); System.out.println(s);
		 */
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		return df.format(d);
	}

	public static String floatFormatString(float d, int n) {
		/*
		 * double d = 1000.0 / 3.0; NumberFormat formatter =
		 * NumberFormat.getNumberInstance();
		 * formatter.setMaximumFractionDigits(4);
		 * //formatter.setMinimumIntegerDigits(6); String s =
		 * formatter.format(d); System.out.println(s);
		 */
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		if (d == 0.0)
			return df.format(0.0);
		return df.format(d);
	}
	
	
	
	

	/**
	 * Deprecated.
	 * 
	 * @param d
	 * @return
	 */
	//
	public static double doubleFormat(double d) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00"); // ��һ�ָ�ʽ:"###,##0.00"
		return Double.parseDouble(df.format(d));
	}

	/**
	 * Deprecated.
	 * 
	 * @param d
	 * @param n
	 * @return double
	 */
	public static double doubleFormat(double d, int n) {
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		return Double.parseDouble(df.format(d));
	}

	public static String doubleFormatStringBy2dig(Object d) {
		return doubleFormatString(d, 2);
	}

	public static String doubleFormatString(Object d, int n) {
		/*
		 * double d = 1000.0 / 3.0; NumberFormat formatter =
		 * NumberFormat.getNumberInstance();
		 * formatter.setMaximumFractionDigits(4);
		 * //formatter.setMinimumIntegerDigits(6); String s =
		 * formatter.format(d); System.out.println(s);
		 */
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		return df.format(d);
	}

	public static String doubleFormatString(double d, int n) {
		/*
		 * double d = 1000.0 / 3.0; NumberFormat formatter =
		 * NumberFormat.getNumberInstance();
		 * formatter.setMaximumFractionDigits(4);
		 * //formatter.setMinimumIntegerDigits(6); String s =
		 * formatter.format(d); System.out.println(s);
		 */
		String strFormat = "0.";
		for (int i = 1; i <= n; i++) {
			strFormat += "0";
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat); // ��һ�ָ�ʽ:"###,##0.00"
		if (d == 0.0)
			return df.format(0.0);
		return df.format(d);
	}

	public static Date transAgeToBirthday(int age, Date currentDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.YEAR, -age);
		return calendar.getTime();
	}

	public static int transBirthdayToAge(Date birthday, Date currentDate) {
		if (birthday == null || currentDate == null) {
			return 0;
		}
		Calendar birthdayCal = Calendar.getInstance();
		birthdayCal.setTime(birthday);
		Calendar currentDateCal = Calendar.getInstance();
		currentDateCal.setTime(currentDate);
		return currentDateCal.get(Calendar.YEAR)
				- birthdayCal.get(Calendar.YEAR);
	}

	public static long getLong(String str) {
		long nResult = 0;
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return nResult;
		}
		try {
			nResult = Long.parseLong(str);
		} catch (NumberFormatException e) {
			System.out.println("getLong()" + e.getMessage());
		}

		return nResult;
	}

	public static Long getLongObject(String str) {
		return new Long(getLong(str));
	}

	public static Float getFloatObject(String str) {
		return new Float(getFloat(str));
	}

	public static float getFloat(String str) {
		float dResult = 0;
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return dResult;
		}
		try {
			dResult = java.lang.Float.parseFloat(str);
		} catch (NumberFormatException e) {
			System.out.println("getFloat()" + e.getMessage());
		}
		return dResult;
	}

	public static Boolean getBooleanObject(String str) {
		return new Boolean(getBoolean(str));
	}

	public static boolean getBoolean(String str) {
		
		if ((str.equals("0")) || str.equals("false")) {
			return false;
		} else {
			return true;
		}
	}

	public static Short getShortObject(String str) {
		Short dResult = new Short("0");
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return dResult;
		}
		try {
			dResult = new Short(str);
		} catch (NumberFormatException e) {
			System.out.println("getShort()" + e.getMessage());
		}
		return dResult;
	}

	public static short getShort(String str) {
		short dResult = 0;
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return dResult;
		}
		try {
			dResult = Short.parseShort(str);
		} catch (NumberFormatException e) {
			System.out.println("getShort()" + e.getMessage());
		}
		return dResult;
	}

}
