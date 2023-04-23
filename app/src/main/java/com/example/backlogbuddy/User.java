package com.example.backlogbuddy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.backlogbuddy.db.AppDatabase;

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

	@PrimaryKey(autoGenerate = true)
	private int mUserId;

	private String mUserName;
	private String mPassword;

	public User(String userName, String password) {
		mUserName = userName;
		mPassword = password;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int userId) {
		mUserId = userId;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String userName) {
		mUserName = userName;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"mUserId=" + mUserId +
				", mUserName='" + mUserName + '\'' +
				", mPassword='" + mPassword + '\'' +
				'}';
	}
}
