package reserve;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.JLabel;

public class DateManager {
	
	Calendar today = Calendar.getInstance();
	
	int todayYear = today.get(Calendar.YEAR);
	int todayMonth = today.get(Calendar.MONTH)+1;
	int todayDay = today.get(Calendar.DATE);
	int todayDayOfWeek = today.get(Calendar.DAY_OF_WEEK);
	int todayHour = today.get(Calendar.HOUR)+1;
	int todayHourOfDay = today.get(Calendar.HOUR_OF_DAY);
	int todayMin = today.get(Calendar.MINUTE);
	int todaySecond = today.get(Calendar.SECOND);
	
		
	public String today() {
		
		return todayYear + "년 " + todayMonth + "월 "+ todayDay+ "일 " + 
				dayOfWeek(todayDayOfWeek) + "요일";
	}
	
	public int todayToInteger() {

		return todayYear * 10000 + todayMonth*100 + todayDay;
	}
	
	public int todayTimeToInt() {
		
		return today.get(Calendar.HOUR_OF_DAY)*100 + today.get(Calendar.MINUTE);
	}
	
	public int todayTimeToIntPlus10() {
		
		today.add(Calendar.MINUTE, 10);
		int todayTimePlus10 = today.get(Calendar.HOUR_OF_DAY)*100 + today.get(Calendar.MINUTE);
		today.add(Calendar.MINUTE, -10);
		
		return todayTimePlus10; 
	}
	
	public Long todayDateAndTimeToIntPlus10() {
		
		today.add(Calendar.MINUTE, 10);
		Long datePlus10 = (long) today.get(Calendar.YEAR)*10000*10000 + (today.get(Calendar.MONTH)+1)*1000000 
						+ today.get(Calendar.DATE)*10000 + today.get(Calendar.HOUR_OF_DAY)*100 
						+ today.get(Calendar.MINUTE);
		today.add(Calendar.MINUTE, -10);
		System.out.println(datePlus10);
		return datePlus10; 
	}
	
	public String IntegerToDate(int date) {
		
		int year = date/10000;
		int month = Integer.parseInt((date+"").substring(4, 6));
		int day = Integer.parseInt((date+"").substring(6, 8));
		
		return year + "년 " + month + "월 " + day +"일";
	}
	
	public String IntegerToDateSlash(int date) {
		
		int year = date/10000;
		int month = Integer.parseInt((date+"").substring(4, 6));
		int day = Integer.parseInt((date+"").substring(6, 8));
		
		return year + "/" + month + "/" + day;
	}
	
	public String IntegerToDateExYear(int date) {
		
		int month = Integer.parseInt((date+"").substring(4, 6));
		int day = Integer.parseInt((date+"").substring(6, 8));
		
		return month + "월 " + day +"일";
	}
	
	public int getAmericanAge(int date) { //생년월일 받을때 8개로 제한 문자수로 막으면 장땡
		
		String month;
		String day;
		int today;
		
		if (todayMonth < 10 ) 
			month = "0" + todayMonth;
		else 
			month = todayMonth+"";
		
		if (todayDay < 10 ) 
			day = "0" + todayDay;
		else 
			day = todayDay+"";
		
		today = Integer.parseInt(todayYear+month+day);
				
		return (int) ((today-date)*0.0001);
	}
	
	public int getKoreanAge(int date) { // 생년월일 받을때 8개로 제한 문자수로 막으면 장땡

		int today;
		
		today = todayYear*10000;
				
		return (int) ((today - date) * 0.0001)+1;
	}
	
	public int timeToInteger (String time) {
		
		int hour = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3, 5));
		
		return hour*100 + min;
	}
	
	public String showDate(Calendar date) {
		
		return date.get(Calendar.YEAR) + "년 " + (date.get(Calendar.MONTH)+1) + "월 "
					+ date.get(Calendar.DATE)+"일 " 
					+ dayOfWeek(date.get(Calendar.DAY_OF_WEEK))+"요일";
	}
	
	public String showDateAndTime(Calendar date) {
		
		String hour;
		String min;
		
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
	
		else // (hour == Calendar.PM || hour > 11)  
			return "PM";
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
	
	public static void main(String[] args) {
		DateManager dm = new DateManager();
		
		Calendar date = Calendar.getInstance();
		
//		System.out.println(dm.todayDateAndTimeToIntPlus10());
		
		
		date.set(2016, 10-1, 8);
		
		long dif = (date.getTimeInMillis()- dm.today.getTimeInMillis())/1000; 
		
		System.out.println(dm.showDate(date));
		System.out.println(dm.today());
		System.out.println(dm.today.get(Calendar.DATE));
		System.out.println((dif/(24*60*60)));
		
//		int date = 20160912;
		
		
//		System.out.println(year+" "+month + " "+ day);
		

//		System.out.println(dm.todayToInteger());
//		System.out.println(date.get(Calendar.HOUR_OF_DAY));
//		System.out.println(dm.timeToInteger("14:22"));
//		System.out.println(dm.todayTimeToInteger());
//		System.out.println(dm.getKoreanAge(19981127)); //
//		System.out.println(dm.getKoreanAge(20061127)); //
//		System.out.println(dm.getAmericanAge(19871127));
	}
	
}

