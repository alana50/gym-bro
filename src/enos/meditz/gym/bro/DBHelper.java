package enos.meditz.gym.bro;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	private static final String MUSCLE_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.MUSCLE_TABLE + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.MUSCLE_COLUMN_NAME + " TEXT UNIQUE);";
	
	private static final String EXERCISE_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.EXERCISE_TABLE + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.EXERCISE_COLUMN_NAME + " TEXT UNIQUE);";
	
	private static final String SET_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.SET_TABLE + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.SET_COLUMN_EXERCISE + " INT, "
			+ FeedEntry.SET_COLUMN_TIME + " TEXT, "
			+ FeedEntry.SET_COLUMN_REPS + " INT, "
			+ FeedEntry.SET_COLUMN_WEIGHT + " DECIMAL, "
			+ FeedEntry.SET_COLUMN_COMMENT + " TEXT);";
	
	private static final String WORKS_TABLE_CREATE = "CREATE TABLE "
			+ FeedEntry.WORKS_TABLE + " ("
			+ FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ FeedEntry.WORKS_COLUMN_EXERCISE + " INT, "
			+ FeedEntry.WORKS_COLUMN_MUSCLE + " INT);";
	
    public DBHelper(Context context) {
        super(context, FeedEntry.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(MUSCLE_TABLE_CREATE);
        db.execSQL(EXERCISE_TABLE_CREATE);
        db.execSQL(SET_TABLE_CREATE);
        db.execSQL(WORKS_TABLE_CREATE);
        
        // initial data population would go here
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.MUSCLE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.EXERCISE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.SET_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.WORKS_TABLE);
    	onCreate(db);
	}
	
	public static void addExercise(Context context, String name) {
		ContentValues values = new ContentValues();
        values.put(FeedEntry.EXERCISE_COLUMN_NAME, name);
        (new DBHelper(context)).getWritableDatabase().insert(FeedEntry.EXERCISE_TABLE, null, values);
	}
	
	public static Exercise getExercise(Context context, long id) {
		Cursor c = (new DBHelper(context)).getReadableDatabase().rawQuery(
				"SELECT * FROM " + FeedEntry.EXERCISE_TABLE + " WHERE "
						+ FeedEntry._ID + "='" + id + "';", null);
		if(c.moveToFirst()) {
			return new Exercise(c.getLong(0), c.getString(1));
		} else {
			return null;
		}
	}
	
	public static ArrayList<Exercise> getExercises(Context context) {
		ArrayList<Exercise> result = new ArrayList<Exercise>();
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();
		Cursor c = db.rawQuery(
				"SELECT * FROM " + FeedEntry.EXERCISE_TABLE + ";", null);
		while (c.moveToNext()) {
			result.add(new Exercise(c.getLong(0), c.getString(1)));
		}
		return result;
	}
	
	public static ArrayList<Set> getSets(Context context, Exercise exercise) {
		ArrayList<Set> result = new ArrayList<Set>();
		SQLiteDatabase db = (new DBHelper(context)).getReadableDatabase();

		Cursor c = db.rawQuery("SELECT * FROM " + FeedEntry.SET_TABLE
				+ " WHERE " + FeedEntry.SET_COLUMN_EXERCISE + "='" + exercise.id + "';", null);
		while (c.moveToNext()) {
			Date date;
			try {
				date = df.parse(c.getString(2));
			} catch (ParseException e) {
				// Hopefully never reached
				date = new Date();
			}
			result.add(new Set(c.getLong(0), c.getLong(1), date, c.getInt(3), c.getDouble(4), c.getString(5)));
		}
		return result;
	}
}
