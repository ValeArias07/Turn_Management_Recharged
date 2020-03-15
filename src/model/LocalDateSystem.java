package model;
import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class LocalDateSystem implements Serializable{
	
	private LocalDateTime date ;
	private int year;
	private int month;
	private int day;
	
	private int hour;
	private int minutes;
	private int seconds;
	
	public LocalDateSystem() {
		date=LocalDateTime.now();
		year = date.getYear();
		month = date.getMonthValue();
		day=date.getDayOfMonth();
		
		hour=date.getHour();
		minutes=date.getMinute();
		seconds=date.getSecond();
	}
	
	/**
	 * This method create a object LocalDateSystem using the information giving for the user
	 * @param year is the year where the turn was assigned
	 * @param month is the month where the turn was assigned
	 * @param dayOfMonth is the day where the turn was assigned
	 * @param hour is the hour where the turn was assigned
	 * @param minute is the minute where the turn was assigned
	 * @param seconds is the seconds where the turn was assigned
	 */
	public LocalDateSystem(int year, int month, int dayOfMonth, int hour, int minutes, int seconds) {
		
		date=LocalDateTime.of(year, month, dayOfMonth, hour, minutes, seconds);

		setYear(date.getYear());
		setMonth(date.getMonthValue());
		setDay(date.getDayOfMonth());
		
		setHour(date.getHour());
		setMinutes(date.getMinute());
		setSeconds(date.getSecond());
	}
	
	/**
	 * This method update the date of class adding the difference of seconds between an old date and a new one
	 * @param secondsPlus is the difference in seconds between the old date and the new one
	 */
	public LocalDateSystem updateTime(long secondsPlus) {
		date=date.plus(secondsPlus, ChronoUnit.SECONDS);
		int nyear=date.getYear();
		int nmonth=date.getMonthValue();
		int nday=date.getDayOfMonth();
		int nhour=date.getHour();
		int nminute=date.getMinute();
		int nsecond=date.getSecond();
		LocalDateSystem newDate= new LocalDateSystem(nyear, nmonth, nday, nhour, nminute, nsecond);
		return newDate;
	}
	
	public LocalDateSystem resetDate() {
		LocalDateTime newDate=LocalDateTime.now();
		int nyear = newDate.getYear();
		int nmonth = newDate.getMonthValue();
		int nday=newDate.getDayOfMonth();
		
		int nhour=newDate.getHour();
		int nminutes=newDate.getMinute();
		int nseconds=newDate.getSecond();
		LocalDateSystem newDateS = new LocalDateSystem(nyear, nmonth, nday, nhour, nminutes, nseconds);
		return newDateS;
	}
	
	
	
	/**
	 * This method calculate the difference between one old date with a new one. For do that, this method use the class Duration and plus the seconds to the old date to obtein the new one
	 * @param year is the year that was assigned
	 * @param month is the month  that was assigned
	 * @param dayOfMonth is the day  that was assigned
	 * @param hour is the hour  that was assigned
	 * @param minute is the minute  that was assigned
	 * @param seconds is the seconds  that was was assigned
	 * @return dateFinal is the date with the differences added
	 */
	public LocalDateSystem updateDate(int year, int month, int dayOfMonth, int hour, int minute, int seconds) {

		LocalDateTime date=LocalDateTime.of(getYear(),getMonth(),getDay(),getHour(),getMinutes(),getSeconds(),0); 
		LocalDateTime date2=LocalDateTime.of(year, month, dayOfMonth, hour, minutes, seconds);
		Duration durationPlus= Duration.between(date, date2);
		long secondsToPlus=durationPlus.getSeconds();
		date = date.plus(secondsToPlus, ChronoUnit.SECONDS);
		
		setYear(date.getYear());
		setMonth(date.getMonthValue());
		setDay(date.getDayOfMonth());
		
		setHour(date.getHour());
		setMinutes(date.getMinute());
		setSeconds(date.getSecond());
		
		LocalDateSystem newDateS = new LocalDateSystem(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),date.getHour(),date.getMinute(),date.getSecond());
		return newDateS;
	}
	
	/**
	 * This method shows the date with a better format
	 * @return completeDate date is the date order in a better format
	 */
	public String getCompleteDate() {
		String showDay=(day<9)?("0"+day):""+day;
		String showMonth=(month<9)?("0"+month):""+month;
		String completeDate=year+"/"+showMonth+"/"+showDay; 
		return completeDate;
	}
	
	/**
	 * This method shows the time with a better format
	 * @return completeHour date is the time order in a better format
	 */
	public String getCompleteHour() {
		String hourShow=(hour<9)?("0"+hour):""+hour;
		String minShow=(minutes<9)?("0"+minutes):""+minutes;
		String segsShow=(seconds<9)?("0"+seconds):""+seconds;
		String completeHour= hourShow+":"+minShow+":"+segsShow;
		return completeHour;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}
}
