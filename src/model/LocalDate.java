package model;

public class LocalDate {
	private int year;
	private int month;
	private int day;
	
	public LocalDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	
	public String getCompleteDate() {
		String showDay=(day<9)?("0"+day):""+day;
		String showMonth=(month<9)?("0"+month):""+month;
		String completeDate=year+"/"+showDay+"/"+showMonth; 
		return completeDate;
	}
	

}
