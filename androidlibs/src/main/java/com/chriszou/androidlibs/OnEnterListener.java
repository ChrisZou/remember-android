/**
 * 
 */
package com.chriszou.androidlibs;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

/**
 * @author Chris
 *
 */
public abstract class OnEnterListener implements OnKeyListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnKeyListener#onKey(android.view.View, int,
	 * android.view.KeyEvent)
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			onEnter();
			return true;
		}
		return false;
	}

	public abstract void onEnter();

}
