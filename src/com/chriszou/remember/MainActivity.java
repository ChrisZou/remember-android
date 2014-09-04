package com.chriszou.remember;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

@EActivity
public class MainActivity extends Activity {
	public static final String EXTRA_BOOL_REMINDER = "extra_bool_reminder";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		boolean reminder = getIntent().getBooleanExtra(EXTRA_BOOL_REMINDER, false);
		Fragment fragment;
		if(reminder) {
			fragment = new ReminderFragment_();
			fragment.setArguments(getIntent().getExtras());
		} else {
			fragment = new MainFragment_();
			Utils.setupAlarms(this);
		}
		getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
	}
    
}
