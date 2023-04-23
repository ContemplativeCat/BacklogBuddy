package com.example.backlogbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.backlogbuddy.db.AppDatabase;
import com.example.backlogbuddy.db.BacklogBuddyDAO;


public class LoginActivity extends AppCompatActivity {

	private EditText mUsernameField;
	private EditText mPasswordField;

	private Button mButton;

	private BacklogBuddyDAO mBacklogDAO;

	private String mUsername;
	private String mPassword;

	private User mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		wireupDisplay();

		getDatabase();

	}

	private void wireupDisplay(){
		mUsernameField = findViewById(R.id.editTextLoginUserName);
		mPasswordField = findViewById(R.id.editTextLoginPassword);

		mButton = findViewById(R.id.buttonLogin);

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getValuesFromDisplay();
				if(checkForUserInDatabase()){
					if(!validatePassword()){
						Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
					}else{
						Intent intent = MainActivity.intentFactory(getApplicationContext(),mUser.getUserId());
						startActivity(intent);
					}
				};

			}
		});
	}

	private boolean validatePassword(){
		return mUser.getPassword().equals(mPassword);
	}

	private void getValuesFromDisplay(){
		mUsername = mUsernameField.getText().toString();
		mPassword = mPasswordField.getText().toString();
	}

	private boolean checkForUserInDatabase(){
		mUser = mBacklogDAO.getUserByUsername(mUsername);
		if(mUser == null){
			Toast.makeText(this, "no user " + mUsername + " found", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private void getDatabase(){
		mBacklogDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
				.allowMainThreadQueries()
				.build()
				.getBacklogBuddyDAO();
	}

	public static Intent intentFactory(Context context){
		Intent intent = new Intent(context, LoginActivity.class);

		return intent;
	}

}