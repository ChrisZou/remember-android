package com.chriszou.remember;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import com.chriszou.androidlibs.L;

import java.util.Calendar;

/**
 * Created by Chris on 1/9/15.
 */
public class TimePickerFragmentDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private int hour = -1;
    private int minute = -1;

    public TimePickerFragmentDialog() {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    public void setTime(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if (mOnTimePickedListener != null && view.isShown()) {
            mOnTimePickedListener.onTimePicked(hourOfDay,minute, this);
        }
    }

    private OnTimePickedListener mOnTimePickedListener;
    public void setOnTimePickecListener(OnTimePickedListener l) {
        mOnTimePickedListener = l;
    }
    public static interface OnTimePickedListener {
        public void onTimePicked(int hourOfDay, int minute, DialogFragment dialogFragment);
    }
}
