package com.chriszou.remember;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.androidlibs.ViewBinderAdapter;
import com.chriszou.androidlibs.ViewBinderAdapter.ViewBinder;
import com.chriszou.remember.TimePickerFragmentDialog.OnTimePickedListener;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Reminder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.reminder_list_activity)
public class ReminderListActivity extends RmbActivity implements OnTimePickedListener {
    @ViewById(R.id.listview)
    ListView mListView;

    @AfterViews
    void initViews() {
        List<Reminder> reminders = Reminder.all();
        ViewBinderAdapter<Reminder> adapter = new ViewBinderAdapter<Reminder>(getActivity(), reminders, mReminderViewBinder);
        mListView.setAdapter(adapter);
    }

    @OptionsItem
    void actionNew() {
        addReminderDialog();
    }

    private void addReminderDialog() {
        TimePickerFragmentDialog fragment = new TimePickerFragmentDialog();
        fragment.setOnTimePickecListener(this);
        fragment.show(getFragmentManager(), "timePicker");
    }

    public static Intent createIntent(Activity activity) {
        Intent intent = new Intent(activity, ReminderListActivity_.class);
        return intent;
    }

    private ViewBinder<Reminder> mReminderViewBinder = new ViewBinder<Reminder>() {
        @Override
        public int getLayoutRes() {
            return 0;
        }

        @Override
        public void bindView(int position, View view, Reminder item, ViewGroup parent) {
            TextView textView = (TextView) view;
            textView.setText(item.getReminderTime());
        }
    };

    @Override
    public void onTimePicked(int hourOfDay, int minute, DialogFragment dialogFragment) {
        L.l("on time picked");
        long time = TimeHelper.getNextHourAndMinite(hourOfDay, minute);
        Reminder reminder = new Reminder(time, ContentMode.SHUFFLE);
        reminder.save();
    }

}
