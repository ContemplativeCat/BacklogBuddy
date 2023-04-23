package com.example.backlogbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.backlogbuddy.db.AppDatabase;
import com.example.backlogbuddy.db.BacklogBuddyDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String USER_ID_KEY = "com.example.backlogbuddy.userIDKey";
	private static final String PREFERENCES_KEY = "com.example.backlogbuddy.PREFERENCES_KEY";

	private TextView mMainDisplay;

	private EditText mBookTitle;
	private EditText mBookGenre;
	private EditText mBookId;
	private EditText mBookStatus;

	private Button mSubmitButton;

	private BacklogBuddyDAO mBacklogBuddyDAO;

	private List<BacklogBuddy> mBacklogBuddyLogs;

	private int mUserId = -1;

	private SharedPreferences mPreferences = null;

	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getDatabase();

		checkForUser();

		loginUser(mUserId);

		mMainDisplay = findViewById(R.id.mainBacklogBuddyDisplay);
		mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

		mBookTitle = findViewById(R.id.mainBookTitleEditText);
		mBookGenre = findViewById(R.id.mainBookGenreEditText);
		mBookId = findViewById(R.id.mainBookIdEditText);
		mBookStatus = findViewById(R.id.mainBookStatusEditText);

		mSubmitButton = findViewById(R.id.mainSubmitButton);

		refreshDisplay();

		mSubmitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				BacklogBuddy log = getValuesFromDisplay();

				mBacklogBuddyDAO.insert(log);

				refreshDisplay();
			}
		});
	}

	private void loginUser(int userId) {
		mUser = mBacklogBuddyDAO.getUserByUserId(userId);
		addUserToPreference(userId);
		invalidateOptionsMenu();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mUser != null) {
			MenuItem item = menu.findItem(R.id.userMenuLogout);
			item.setTitle(mUser.getUserName());
		}
		return super.onPrepareOptionsMenu(menu);
	}

	private void addUserToPreference(int userId) {
		if (mPreferences == null) {
			getPrefs();
		}
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt(USER_ID_KEY, userId);
		editor.apply();
	}

	private void getDatabase() {
		mBacklogBuddyDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
				.allowMainThreadQueries()
				.build()
				.getBacklogBuddyDAO();
	}

	private void checkForUser() {
		mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

		//do we have a user in the preferences?
		if(mUserId != -1) {
			return;
		}

		if (mPreferences == null) {
			getPrefs();
		}

		mUserId = mPreferences.getInt(USER_ID_KEY, -1);

		if (mUserId != -1) {
			return;
		}

		//do we have any users at all?
		List<User> users = mBacklogBuddyDAO.getAllUsers();
		if(users.size() <= 0) {
			User defaultUser = new User("Default", "Password");
			User altUser = new User("Admin", "Password");
			mBacklogBuddyDAO.insert(defaultUser, altUser);
		}

		Intent intent = LoginActivity.intentFactory(this);
		startActivity(intent);
	}

	private void getPrefs() {
		mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
	}

	private void logoutUser() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

		alertBuilder.setMessage("Logout?");

		alertBuilder.setPositiveButton(getString(R.string.yes),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						clearUserFromIntent();
						clearUserFromPref();
						mUserId = -1;
						checkForUser();
					}
				});
		alertBuilder.setNegativeButton(getString(R.string.no),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//We don't really need to do anything here.

					}
				});

		alertBuilder.create().show();
	}

	private void clearUserFromIntent() {
		getIntent().putExtra(USER_ID_KEY, -1);
	}

	private void clearUserFromPref() {
		addUserToPreference(-1);
	}

	private BacklogBuddy getValuesFromDisplay() {
		String bookTitle = "No record found";
		String bookGenre = "No record found";
		String bookStatus = "No record found";
		int bookId = 0;

		bookTitle = mBookTitle.getText().toString();
		bookGenre = mBookGenre.getText().toString();
		bookStatus = mBookStatus.getText().toString();

		try {
			bookId = Integer.parseInt(mBookId.getText().toString());
		} catch (NumberFormatException e) {
			Log.d("BACKLOGBUDDY", "Couldn't convert book id");
		}

		BacklogBuddy log = new BacklogBuddy(bookTitle, bookGenre, bookStatus, bookId, mUserId);

		return log;

	}

	private void refreshDisplay(){
		mBacklogBuddyLogs = mBacklogBuddyDAO.getBacklogBuddyLogsByUserId(mUserId);

		if(mBacklogBuddyLogs.size() <= 0) {
			mMainDisplay.setText("No records, enter some books!");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for(BacklogBuddy log : mBacklogBuddyLogs) {
			sb.append(log);
			sb.append("\n");
			sb.append("=-=-=-=-=-=-=-=");
			sb.append("\n");
		}

		mMainDisplay.setText(sb.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.user_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.userMenuLogout:
				logoutUser();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public static Intent intentFactory(Context context, int userId) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra(USER_ID_KEY, userId);

		return intent;
	}
}