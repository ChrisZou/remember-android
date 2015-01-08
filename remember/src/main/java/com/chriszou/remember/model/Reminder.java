package com.chriszou.remember.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.chriszou.androidlibs.TimeHelper;

import java.util.List;

/**
 * Created by Chris on 1/7/15.
 */
@Table(name = "reminders")
public class Reminder extends Model {
    /**
     * The reminderTime of the first occurrence
     */
    @Column
    public long time = System.currentTimeMillis();

    @Column
    public boolean activated = true;
    /**
     * Content mode, corresponds to {@link ContentMode}
     */
    @Column
    public String contentMode = ContentMode.SHUFFLE.name();

    /**
     * Repeat mode, corresponds to {@link RepeatMode}
     */
    @Column
    public String repeatMode = RepeatMode.DAILY.name();

    public Reminder() {
        super();
    }

    public Reminder(long time) {
        super();
        this.time = time;
    }
    public Reminder(long time, ContentMode contentMode) {
        super();
        this.time = time;
        this.contentMode = contentMode.name();
    }

    public String getReminderTime() {
        String timeStr = TimeHelper.getTimeFormat("HH:mm", time);
        return timeStr;
    }

    public static List<Reminder> all() {
        return new Select().from(Reminder.class).execute();
    }

}
