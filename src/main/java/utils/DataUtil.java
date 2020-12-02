package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataUtil {

	public static String formate1 = "yyyy-MM-dd";
	public static String formate2 = "yyyy";
	public static String formate3 = "yyyy-MM";
	public static String formate4 = "yyyyMMdd";
	public static String formate5="yyyy-MM-dd HH:mm:ss";
	
	
    /**
     * 比较同一年的日期天数差
     * @param date 日期
     * @return
     */
    public static int 得到同一年的天数日期差(Date date)
    {
        Date date1=createDate(getYear(date),getMonth(date),getDay(date));
        Date date2=StringtoUtilDate("2015-09-30","yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date1);
        int day1=calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date2);
        int day2=calendar.get(Calendar.DAY_OF_YEAR);
        return day1-day2;
    }
    
    /**
     *得到时间戳 计算时间日期天数差
     * @param date
     * @return
     */
     public static Integer  getDateByMillison(Date date){
         Date date1=createDate(getYear(date),getMonth(date),getDay(date));
         Date date2=StringtoUtilDate("2015-09-30","yyyy-MM-dd");
         long times=date1.getTime()-date2.getTime();
         return Integer.valueOf(String.valueOf(times/(1000*3600*24)));
    }
     
     public  static Integer 得到两个日期的天数差(Date start ,Date end){
         long times=end.getTime()-start.getTime();
         return Integer.valueOf(String.valueOf(times/(1000*3600*24)));

     }

	/**
	 * 判断日期是否相等
	 * @param flag
	 * @param date
	 * @return
	 */
	public static boolean iSEqual(Date flag, Date date) {
		return date.getTime() == flag.getTime();
	}


	/**
	 * 
	 * 得到年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 得到月份
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 得到天数
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar calendar = new GregorianCalendar();
//		Calendar calendar =Calendar.getInstance();
		calendar.setTime(date);
		// int date2 = calendar.get(Calendar.DATE);
		int date2 = calendar.get(Calendar.DAY_OF_MONTH);
		return date2;
	}

	/**
	 * 指定日期增加年份
	 * @param date
	 * @param yearNum
	 * @return
	 */
	public static Date addYears(Date date, int yearNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, yearNum);
		return calendar.getTime();
	}

	/**
	 * 指定日期增加月数
	 * 
	 * @param date
	 * @param monthNum
	 * @return
	 */
	public static Date addMonths(Date date, int monthNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, monthNum);
		return calendar.getTime();
	}

	/**
	 * 指定日期增加天数
	 * 
	 * @param date
	 * @param dateNum
	 * @return
	 */
	public static Date addDates(Date date, int dateNum) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, dateNum);
		return calendar.getTime();
	}

	/**
	 * 得到当前时间戳
	 * 
	 * @return
	 */
	public static long getTime() {
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * 得到指定日期时间戳
	 * @param date
	 * @return
	 */
	public static long getTime(Date date) {
		return date.getTime();

	}

	/**
	 * 指定年月日创建日期
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date createDate(int year, int month, int date) {
		Calendar calendar = new GregorianCalendar(year, month - 1, date);
		return calendar.getTime();
	}



	/**
	 * 转换为SQL日期
	 * @param strDate   String
	 * @param strFormat String
	 * @return Date
	 */
	public static java.sql.Date toSQLDate(String strDate, String strFormat) {
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			} else {
				SimpleDateFormat _formatdate = new SimpleDateFormat(strFormat, Locale.getDefault());
				java.sql.Date _date = new java.sql.Date((_formatdate.parse(strDate)).getTime());
				return _date;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定形式的日期格式字符串
	 * @param iType
	 * @return
	 */
	public static String getCurrentSysTime(int iType) {

		Date dtNow = new Date(System.currentTimeMillis());
		String dateString = "";

		try {
			SimpleDateFormat formatter = null;
			switch (iType) {
			case 1:
				formatter = new SimpleDateFormat("yyyy.MM.dd");
				break;
			case 2:
				formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
				break;
			case 3:
				formatter = new SimpleDateFormat("yyyy.MM.dd hh:mm a");
				break;
			case 4:
				formatter = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 5:
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 6:
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
				break;
			case 7:
				formatter = new SimpleDateFormat("yyyy-MM");
				break;
			case 8:
				formatter = new SimpleDateFormat("yyyyMMdd");
				break;
			default:
				formatter = new SimpleDateFormat("yyyy.MM.dd");
				break;
			}
			dateString = formatter.format(dtNow);
		} catch (Exception e) {
			e.printStackTrace();
			dateString = "";
		}
		return dateString;
	}

	/**
	 *
	 * @param dtBeginDate
	 * @param dtEndDate
	 * @return
	 */
	public static long intervalDays(Date dtBeginDate, Date dtEndDate) {
		long interval = 0;
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String begindate = format.format(dtBeginDate);
			String enddate = format.format(dtEndDate);
			Date date_begindate = format.parse(begindate);
			Date date_enddate = format.parse(enddate);
			interval = date_enddate.getTime() - date_begindate.getTime();
			interval = interval / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return interval;
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}

	/**
	 * 得到月末日期
	 * @param date
	 * @return
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return calendar.getTime();
	}



   /**
    * 日期比较大小
    * @param dateA
    * @param dateB
    * @return
    */
    public static int dateCompareOne(Date dateA, Date dateB) {
        SimpleDateFormat format = new SimpleDateFormat(formate3);
        String datea = format.format(dateA);
        String dateb = format.format(dateB);
        return datea.compareTo(dateb);
    }
    /**
     * DateToLocaleDate
     * @param date
     * @return
     */
    public static LocalDate DateToLocaleDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId  = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
	 * 当地日期格式
     * LocalDateToDate
     * @param localDate
     * @return
     */
    public static Date LocalDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ChronoZonedDateTime<LocalDate> zonedDateTime = localDate.atStartOfDay(zoneId);
        return Date.from(zonedDateTime.toInstant());
    }

	/**
	 * 指定字符串日期 转换成指定形式的日期格式
	 * @param strDate  String
	 * @param strFormat String
	 * @return Date
	 * @throws Exception
	 */
	public static Date StringtoUtilDate(String strDate, String strFormat) {
		try {
			if (strDate == null || strDate.equals("")) {
				return null;
			} else {
				SimpleDateFormat format = new SimpleDateFormat(strFormat);
				Date date = new Date((format.parse(strDate)).getTime());
				return date;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
    

    /**
     * 日期格式转换
     * @param date
     * @param form
     * @return
     */
    public static String DateToString(Date  date, String form) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat formater = new SimpleDateFormat(form);
        return formater.format(date);
    }
}
