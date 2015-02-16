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
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        L.l("on time set");
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