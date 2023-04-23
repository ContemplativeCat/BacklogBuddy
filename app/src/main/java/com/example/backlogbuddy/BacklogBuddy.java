package com.example.backlogbuddy;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.backlogbuddy.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.BACKLOGBUDDY_TABLE)
public class BacklogBuddy {

	@PrimaryKey(autoGenerate = true)
	private int mLogId;

	private String mBookTitle;
	private String mBookGenre;
	private String mBookStatus;
	private int mBookId;

	private Date mDate;

	private int mUserId;

	public BacklogBuddy(String bookTitle, String bookGenre, String bookStatus, int bookId, int userId) {
		mBookTitle = bookTitle;
		mBookGenre = bookGenre;
		mBookStatus = bookStatus;
		mBookId = bookId;

		mDate = new Date();

		mUserId = userId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int userId) {
		mUserId = userId;
	}

	public int getLogId() {
		return mLogId;
	}

	public void setLogId(int logId) {
		mLogId = logId;
	}

	public String getBookTitle() {
		return mBookTitle;
	}

	public void setBookTitle(String mBookTitle) {
		this.mBookTitle = mBookTitle;
	}

	public String getBookGenre() {
		return mBookGenre;
	}

	public void setBookGenre(String mBookGenre) {
		this.mBookGenre = mBookGenre;
	}

	public String getBookStatus() {
		return mBookStatus;
	}

	public void setBookStatus(String mBookStatus) {
		this.mBookStatus = mBookStatus;
	}

	public int getBookId() {
		return mBookId;
	}

	public void setBookId(int mBookId) {
		this.mBookId = mBookId;
	}


	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	@Override
	public String toString() {
		String output;

		output = "Title: " + mBookTitle  + "\nGenre: " + mBookGenre + "\nBook ID: " + mBookId + "\nReading Status: " + mBookStatus;
		output += "\n";
		output += "\n";
		output += getDate();
		output += "\n";
		output += "userId == " + mUserId;

		return output;
	}
}
