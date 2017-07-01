package hr.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	public static boolean isNewMonth(Date oldDate, Date newDate) {
		if (oldDate == null) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		int oldMonth = calendar.getActualMaximum(Calendar.MONTH);
		calendar.setTime(newDate);
		int newMonth = calendar.getActualMaximum(Calendar.MONTH);
		return oldMonth != newMonth;
	}

	public static Date getEndDate(int year, int month) throws ParseException {
		String endDateStr = year + "-" + month + "-";
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Date endDate = f.parse(endDateStr + "1");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		return f.parse(endDateStr + calendar.getActualMaximum(Calendar.DATE));
	}

	public static Date getEndDate(Date currentDate) throws ParseException {
		return getEndDate(getYear(currentDate), getMonth(currentDate));
	}

	public static Date getStartDate(int year, int month) throws ParseException {
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		return f.parse(year + "-" + month + "-1");
	}

	public static Date getStartDate(Date currentDate) throws ParseException {
		return getStartDate(getYear(currentDate), getMonth(currentDate));
	}

	
	public static Date getStartDay(Date currentDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}
	
	public static Date getEndDay(Date currentDate) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return calendar.getTime();
	}
	
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	

	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}
	
	public static int getCecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	public static Date parseDateFromString(java.lang.String dateString,
			String format) throws ParseException {
		DateFormat f = new SimpleDateFormat(format);
		return f.parse(dateString);
	}

	public static Date[] splitDateRangeToMonths(Date startDate, Date endDate)
			throws ParseException {
		List re = new ArrayList();
		re.add(startDate);
		Date eDate = getEndDate(startDate);
		Calendar calendar = Calendar.getInstance();
		while (eDate.before(endDate)) {
			re.add(eDate);
			calendar.setTime(eDate);
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			eDate = getEndDate(year, month + 1);
		}
		re.add(endDate);
		return (Date[]) re.toArray(new Date[re.size()]);
	}
	
	public static Date[] splitDateRangeToDays(Date startDate, Date endDate)
			throws ParseException {
		List re = new ArrayList();
		re.add(getStartDay(startDate));
		Date endPoint = getEndDay(endDate);
		Date eDate = getEndDay(startDate);
		Calendar calendar = Calendar.getInstance();
		while (eDate.before(endPoint)) {
			re.add(eDate);
			calendar.setTime(eDate);
			calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+1);
			eDate = calendar.getTime();
		}
		re.add(endPoint);
		
		return (Date[]) re.toArray(new Date[re.size()]);
	}

	public static float getBetweenHourByDate(Date before, Date after) {
		float distance = (float) ((after.getTime() - before.getTime()) / 1000 / 60 / 60);
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		return Float.parseFloat(df.format(distance));
	}
	
	public static String getBetweenHourByDateString(Date before, Date after) {
		float distance = (float) ((after.getTime() - before.getTime()) / 1000 / 60 / 60);
		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
		float time= Float.parseFloat(df.format(distance));
		int itime=(int)Math.floor(time);
		String stime;
		if(itime>=365*24){
			stime=(itime/(365*24))+"年以前";
		}else if(itime>=24){
			stime=(itime/24)+"天以前";
		}else if(itime>=1){
			stime=itime+"小时以前";
		}else{
			itime=(int)time*100;
			if(itime<10){
				stime="刚刚发布";
			}else{
				stime=itime+"分钟";
			}
		}
		return stime;
	}
	
	public static Timestamp getTimestampByDate(Date date){
		if(date==null)return null;
		return new Timestamp(date.getTime());
	}
	
	public static Date getDateByTimestamp(Timestamp date){
		if(date==null)return null;
		return new Date(date.getTime());
	}
	
	public static Date getBeforDay(Date day,int dayNumber){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(day);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)-dayNumber);
		return calendar.getTime();
	}
}
