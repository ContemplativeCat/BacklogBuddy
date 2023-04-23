package com.example.backlogbuddy.db;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.backlogbuddy.BacklogBuddy;
import com.example.backlogbuddy.User;
import java.util.List;

@Dao
public interface BacklogBuddyDAO {

	@Insert
	void insert(BacklogBuddy... gymLogs);

	@Update
	void update(BacklogBuddy... gymLogs);

	@Delete
	void delete(BacklogBuddy gymLog);

	@Query("SELECT * FROM " + AppDatabase.BACKLOGBUDDY_TABLE + " ORDER BY  mDate DESC")
	List<BacklogBuddy> getAllBacklogBuddyLogs();

	@Query("SELECT * FROM " + AppDatabase.BACKLOGBUDDY_TABLE + " WHERE mLogId = :logId  ORDER BY  mDate DESC")
	List<BacklogBuddy> getBacklogBuddyLogsById(int logId);

	@Query("SELECT * FROM " + AppDatabase.BACKLOGBUDDY_TABLE + " WHERE mUserId = :userId  ORDER BY  mDate DESC")
	List<BacklogBuddy> getBacklogBuddyLogsByUserId(int userId);

	@Insert
	void insert(User...users);

	@Update
	void update(User... users);

	@Delete
	void delete(User user);

	@Query("SELECT * FROM " + AppDatabase.USER_TABLE)
	List<User> getAllUsers();

	@Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserName = :username")
	User getUserByUsername(String username);

	@Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE mUserId = :userId")
	User getUserByUserId(int userId);

}