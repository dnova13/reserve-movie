package reserve;

import java.util.Calendar;

public class Date {
	
	private int year;
	private int month;
	private int date;
	private int dayOfWeek;
	private int hour;
	private int hourOfDay;
	private int AM_PM;
	private int min;
	private int second;
	
	public Date() {
		
	}
	
	public Date(int year, int month, int date) {
		
		this.year = year;
		this.month = month;
		this.date = date;
	}
	
	public Date(int year, int month, int date, int houOfDay, int min) {
		
		this.year = year;
		this.month = month;
		this.date = date;
		this.hourOfDay = houOfDay;
		this.min = min;
	}
	
	public Calendar dateToCal() {
		
		Calendar date = Calendar.getInstance();
		date.set(this.getYear(), this.getMonth(), this.getDate());
		
		return date;

	}
	
	public int showDateToInteger() {
		
		Calendar date = Calendar.getInstance();
		date.set(this.getYear(), this.getMonth(), this.getDate());
		
		return date.get(Calendar.YEAR)*10000 + (date.get(Calendar.MONTH)+1)*100 + date.get(Calendar.DATE);
	}
	
	public String showDate() {
		
		Calendar date = Calendar.getInstance();
		date.set(this.getYear(), this.getMonth(), this.getDate());
		
		return date.get(Calendar.YEAR) + "년 " + (date.get(Calendar.MONTH)+1) + "월 "
					+ date.get(Calendar.DATE)+"일 " 
					+ dayOfWeek(date.get(Calendar.DAY_OF_WEEK))+"요일";
	}
	
	public String showDateTime() {
		
		String hour;
		String min;
		
		Calendar date = Calendar.getInstance();
		date.set(this.getYear(), this.getMonth(), this.getDate(),this.getHourOfDay(),this.getMin());
		
		if(date.get(Calendar.HOUR_OF_DAY)%12 < 10) {
			
			hour = "0" + date.get(Calendar.HOUR_OF_DAY)%12;
		}
		else hour = date.get(Calendar.HOUR_OF_DAY)%12 +"";
		
		if(date.get(Calendar.HOUR_OF_DAY) == 12) 
			hour = "12";
		
		if(date.get(Calendar.MINUTE) < 10) {
			min = "0" + date.get(Calendar.MINUTE);
		}
		else min = "" + date.get(Calendar.MINUTE);
		
		return date.get(Calendar.YEAR) + "년 " 
				+ (date.get(Calendar.MONTH)+1) + "월 "
				+ date.get(Calendar.DATE)+"일 " 
				+ dayOfWeek(date.get(Calendar.DAY_OF_WEEK))+"요일 "
				+ AM_PM(date.get(Calendar.HOUR_OF_DAY))+ " " 
				+ hour + ":" + min;
	}
	
	public String AM_PM(int hour) {
		
		if (hour == Calendar.AM || hour < 11) {
			return "AM";
		}
	
		else // (hour == Calendar.PM || hour > 11) { 
			return "PM";
//		}
//		return "";
	}
	
	public String dayOfWeek(int dayOfWeek) {

		switch ((dayOfWeek - 1) % 7 + 1) {

		case Calendar.SUNDAY:
			return "일";

		case Calendar.MONDAY:
			return "월";

		case Calendar.TUESDAY:
			return "화";

		case Calendar.WEDNESDAY:
			return "수";

		case Calendar.THURSDAY:
			return "목";

		case Calendar.FRIDAY:
			return "금";

		case Calendar.SATURDAY:
			return "토";
		}
		return "";
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getHourOfDay() {
		return hourOfDay;
	}

	public void setHourOfDay(int hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}
	
	
	
	
}
