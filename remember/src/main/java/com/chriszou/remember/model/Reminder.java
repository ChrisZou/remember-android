package com.chriszou.remember.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Table;

/**
 * Created by Chris on 1/7/15.
 */
@Table(name = "reminders")
public class Reminder extends Model {
    /**
     * The time of the first occurrence
     */
    public long time = System.currentTimeMillis();
    public boolean activated = true;
    /**
     * Content mode, corresponds to {@link ContentMode}
     */
    public String contentMode = ContentMode.SHUFFLE.name();

    /**
     * Repeat mode, corresponds to {@link RepeatMode}
     */
    public String repeatMode = RepeatMode.DAILY.name();

    public Reminder() {
    }

    public Reminder(long time) {
        this.time = time;
    }
    public Reminder(long time, ContentMode contentMode) {
        this.time = time;
        this.contentMode = contentMode.name();
    }
}
