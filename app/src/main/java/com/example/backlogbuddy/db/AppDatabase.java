package com.example.backlogbuddy.db;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.backlogbuddy.BacklogBuddy;
import com.example.backlogbuddy.User;

import com.example.backlogbuddy.db.typeConverters.DateTypeConverter;

@Database(entities = {BacklogBuddy.class, User.class}, version = 2)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
	public static final String DB_NAME = "BACKLOGBUDDY_DATABASE";
	public static final String BACKLOGBUDDY_TABLE = "BACKLOGBUDDY_TABLE";
	public static final String USER_TABLE = "USER_TABLE";

	public abstract BacklogBuddyDAO getBacklogBuddyDAO();

}

