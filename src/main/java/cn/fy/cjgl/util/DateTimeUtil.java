package cn.fy.cjgl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	private static String sdf10 = "yyyy-MM-dd";
	
	private static String sdf17 = "yyyyMMddHHmmssSSS";

	private static String sdf19 = "yyyy-MM-dd HH:mm:ss";

	private static String sdf23 = "yyyy-MM-dd HH:mm:ss:SSS";
	
	/**
	 * yyyy-MM-dd
	 * @return
	 */
	public static final String getDateTime10() {
		return getDateTime10(System.currentTimeMillis());
	}
	
	/**
	 * yyyy-MM-dd
	 * @param dataStr10
	 * @return
	 */
	public static final long getLongTime10(String dataStr10) {
		Date d = new Date();
		try {
			d = new SimpleDateFormat(sdf10).parse(dataStr10);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}

	/**
	 * yyyy-MM-dd
	 * @param time
	 * @return
	 */
	public static final String getDateTime10(long time) {
		Date d = new Date(time);
		return new SimpleDateFormat(sdf10).format(d);
	}
	
	/**
	 * yyyy-MM-dd
	 * @param time
	 * @return
	 */
	public static final Date getDateTime10Str(String time) {
		Date d = new Date();
		try {
			d = new SimpleDateFormat(sdf10).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * yyyyMMddHHmmssSSS
	 * @param time
	 * @return
	 */
	public static final String getDateTime17() {
		Date d = new Date();
		return new SimpleDateFormat(sdf17).format(d);
	}
	
	/**
	 * yyyyMMddHHmmssSSS
	 * @param time
	 * @return
	 */
	public static final String getDateTime17(long time) {
		Date d = new Date(time);
		return new SimpleDateFormat(sdf17).format(d);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static final String getDateTime19() {
		return getDateTime19(System.currentTimeMillis());
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static final String getDateTime19(long time) {
		Date d = new Date(time);
		return new SimpleDateFormat(sdf19).format(d);
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * @param time
	 * @return
	 */
	public static final Date getDateTime19Str(String time) {
		Date d = new Date();
		try {
			d = new SimpleDateFormat(sdf19).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * yyyy-MM-dd HH:mm:ss:SSS
	 * @return
	 */
	public static final String getDateTime23() {
		return getDateTime23(System.currentTimeMillis());
	}

	/**
	 * yyyy-MM-dd HH:mm:ss:SSS
	 * @param time
	 * @return
	 */
	public static final String getDateTime23(long time) {
		Date d = new Date(time);
		return new SimpleDateFormat(sdf23).format(d);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss:SSS
	 * @param dataStr23
	 * @return
	 */
	public static final long getLongTime23(String dataStr23) {
		Date d = new Date();
		try {
			d = new SimpleDateFormat(sdf23).parse(dataStr23);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d.getTime();
	}

	/**
	 * 计算一周的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDateStart(String date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			int d = 0;
			if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				d = -6;
			} else {
				d = 2 - cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DAY_OF_WEEK, d);

			// 开始日期
			String weekStart = new SimpleDateFormat("yyyy-MM-dd").format(cal
					.getTime());

			return weekStart;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 计算一周的结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekDateEnd(String date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			int d = 0;
			if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
				d = -6;
			} else {
				d = 2 - cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DAY_OF_WEEK, d);

			// 结束日期
			cal.add(Calendar.DAY_OF_WEEK, 6);
			String weekEnd = new SimpleDateFormat("yyyy-MM-dd").format(cal
					.getTime());

			return weekEnd;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 计算一个月的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthDateStart(String date) {
		try {
			String monthStart = date.substring(0, 8) + "01";
			return monthStart;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 计算一个月的结束日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthDateEnd(String date) {
		try {
			String monthStart = date.substring(0, 8) + "01";

			// 获取本月的最后一天
			Calendar cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(monthStart));
			cal.add(Calendar.MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			String monthEnd = new SimpleDateFormat("yyyy-MM-dd").format(cal
					.getTime());

			return monthEnd;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
