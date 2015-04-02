package com.chriszou.remember;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chriszou.androidlibs.DeviceUtils;
import com.chriszou.androidlibs.TimeHelper;
import com.chriszou.androidlibs.ViewBinderAdapter;
import com.chriszou.androidlibs.ViewBinderAdapter.ViewBinder;
import com.chriszou.remember.TimePickerFragmentDialog.OnTimePickedListener;
import com.chriszou.remember.model.ContentMode;
import com.chriszou.remember.model.Reminder;
import com.chriszou.remember.util.ReminderAlarmHelper;
import com.chriszou.remember.util.UMengUtils;
import com.fortysevendeg.swipelistview.SwipeListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Chris on 1/8/15.
 */
@EActivity(R.layout.reminder_list_activity)
@OptionsMenu(R.menu.reminder_list_menu)
public class ReminderListActivity extends RmbActivity implements OnTimePickedListener {
    @ViewById(R.id.listview)
    SwipeListView mListView;

    private ViewBinderAdapter<Reminder> mAdapter;
    /**
     * The reminder that is currently editing.
     */
    private Reminder mEditingReminder;

    @AfterViews
    void initViews() {
        mListView.setOffsetLeft(DeviceUtils.screenWidth(getActivity()) - DeviceUtils.dpToPixel(getActivity(), 180));
        mAdapter = new ViewBinderAdapter<>(getActivity(), Reminder.all(), mReminderViewBinder);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_REMINDER_ITEM_CLICKED);
        });
    }

    @OptionsItem
    void actionNew() {
        mEditingReminder = null;
        showTimePickerDialog(System.currentTimeMillis());
        UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_ADD_REMINDER_CLICKED);
    }

    private void editReminder(Reminder item) {
        mEditingReminder = item;
        showTimePickerDialog(item.time);
    }

    private void showTimePickerDialog(long time) {
        TimePickerFragmentDialog fragment = new TimePickerFragmentDialog();
        fragment.setTime(time);
        fragment.setOnTimePickecListener(this);
        fragment.show(getFragmentManager(), "timePicker");
    }

    private ViewBinder<Reminder> mReminderViewBinder = new ViewBinder<Reminder>() {
        @Override
        public int getLayoutRes() {
            return R.layout.reminder_item;
        }

        @Override
        public void bindView(int position, View view, final Reminder item, ViewGroup parent) {
            TextView textView = (TextView) view.findViewById(R.id.reminder_item_time);
            textView.setText(item.getReminderTime());

            view.findViewById(R.id.reminder_item_delete).setOnClickListener(v -> {
                ReminderAlarmHelper.cancelAlarm(getActivity(), item);
                mAdapter.remove(item);
                item.delete();
                UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_REMOVED_REMINDER);
            });

            view.findViewById(R.id.reminder_item_edit).setOnClickListener( v-> {
                mListView.closeOpenedItems();
                editReminder(item);
                UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_MODIFY_REMINDER_CLICKED);
            });
        }
    };

    @Override
    public void onTimePicked(int hourOfDay, int minute, DialogFragment dialogFragment) {
        long time = TimeHelper.getNextHourAndMinite(hourOfDay, minute);
        Reminder reminder = mEditingReminder;
        if (mEditingReminder == null) {
            reminder = new Reminder(time, ContentMode.SHUFFLE);
            reminder.save();
            mAdapter.add(reminder);
            UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_ADDED_REMINDER);
        } else {
            reminder.time = time;
            reminder.save();
            mAdapter.notifyDataSetChanged();
            UMengUtils.logEvent(getActivity(), UMengUtils.EVENT_MODIFIED_REMINDER);
        }
        ReminderAlarmHelper.setupAlarm(getActivity(), reminder);
    }

    public static Intent createIntent(Activity activity) {
        Intent intent = new Intent(activity, ReminderListActivity_.class);
        return intent;
    }

}
