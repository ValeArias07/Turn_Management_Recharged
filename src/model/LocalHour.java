package model;

public class LocalHour {
	private int hour;
	private int minutes;
	private int seconds;
	
	public LocalHour(int hour, int minutes, int seconds) {
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	}
	
	public String getCompleteHour() {
		String hourShow=(hour<9)?("0"+hour):""+hour;
		String minShow=(minutes<9)?("0"+minutes):""+minutes;
		String segsShow=(seconds<9)?("0"+seconds):""+seconds;
		String completeHour= hourShow+":"+minShow+":"+segsShow;
		return completeHour;
	}
	
	

}
