package enos.meditz.gym.bro;

import android.provider.BaseColumns;

public class FeedEntry implements BaseColumns {
	public static final String DATABASE_NAME = "Gym Bro Database";
	
	public static final String MUSCLE_TABLE = "MUSCLE_TABLE";
    public static final String MUSCLE_COLUMN_NAME = "MUSCLE_COLUMN_NAME";
    
    public static final String EXERCISE_TABLE = "EXERCISE_TABLE";
	public static final String EXERCISE_COLUMN_NAME = "EXERCISE_COLUMN_NAME";
	
	public static final String WORKOUT_TABLE = "WORKOUT_TABLE";
	public static final String WORKOUT_COLUMN_NAME = "WORKOUT_COLUMN_NAME";

	public static final String WORKOUT_EXERCISE_TABLE = "WORKOUT_EXERCISE_TABLE";
	public static final String WORKOUT_EXERCISE_COLUMN_WORKOUT = "WORKOUT_EXERCISE_COLUMN_WORKOUT";
	public static final String WORKOUT_EXERCISE_COLUMN_EXERCISE = "WORKOUT_EXERCISE_COLUMN_EXERCISE";
	
    public static final String SET_TABLE = "SET_TABLE";
    public static final String SET_COLUMN_EXERCISE = "SET_COLUMN_EXERCISE";
    public static final String SET_COLUMN_TIME = "SET_COLUMN_TIME";
    public static final String SET_COLUMN_REPS = "SET_COLUMN_REPS";
    public static final String SET_COLUMN_WEIGHT = "SET_COLUMN_WEIGHT";
    public static final String SET_COLUMN_COMMENT = "SET_COLUMN_COMMENT";
    
    public static final String WORKS_TABLE = "WORKS_TABLE";
    public static final String WORKS_COLUMN_EXERCISE = "WORKS_COLUMN_EXERCISE";
    public static final String WORKS_COLUMN_MUSCLE = "WORKS_COLUMN_MUSCLE";
    
    private FeedEntry() {}
}
