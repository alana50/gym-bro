package enos.meditz.gym.bro;

import android.provider.BaseColumns;

public class FeedEntry implements BaseColumns {
	public static final String DATABASE_NAME = "Gym Bro Database";
	
	public static final String MUSCLE_TABLE = "muscles";
    public static final String MUSCLE_COLUMN_NAME = "name";
    
    public static final String EXERCISE_TABLE = "exercises";
	public static final String EXERCISE_COLUMN_NAME = "name";
    
    public static final String SET_TABLE = "sets";
    public static final String SET_COLUMN_EXERCISE = "exercise";
    public static final String SET_COLUMN_TIME = "time";
    public static final String SET_COLUMN_REPS = "reps";
    public static final String SET_COLUMN_WEIGHT = "weight";
    public static final String SET_COLUMN_COMMENT = "comment";
    
    public static final String WORKS_TABLE = "works";
    public static final String WORKS_COLUMN_EXERCISE = "exercise";
    public static final String WORKS_COLUMN_MUSCLE = "muscle";
    
    private FeedEntry() {}
}
