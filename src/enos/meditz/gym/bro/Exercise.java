package enos.meditz.gym.bro;

public class Exercise {
	public long id;
	public String name;
	
	public Exercise(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}