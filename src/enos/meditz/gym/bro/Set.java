package enos.meditz.gym.bro;

import java.util.Calendar;
import java.util.Date;

public class Set {
	private long id;
	private long exercise;
	public Calendar calendar = Calendar.getInstance();
	public int reps;
	public double weight;
	private String comment;
	
	public Set(long id, long exercise, Date time, int reps, double weight, String comment) {
		this.id = id;
		this.exercise = exercise;
		calendar.setTime(time);
		this.reps = reps;
		this.weight = weight;
		this.comment = comment;
	}
}