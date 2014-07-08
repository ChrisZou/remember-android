package com.chriszou.remember;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Bundle;

@EActivity
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment_())
					.commit();
		}
	}
    
}
