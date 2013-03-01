package enos.meditz.gym.bro;

import java.util.ArrayList;
import java.util.List;

public class Workout {
	public long id;
	public String name;
	
	public Workout(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
