package com.chriszou.remember.adapters;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chriszou.remember.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Chris on 3/28/15.
 */
@EFragment(R.layout.confirm_dialog)
public class ConfirmDialog extends DialogFragment {
    private static final String KEY_STRING_MESSAGE = "key_string_message";
    @ViewById
    TextView messageView;
    @ViewById
    Button ok;

    private Activity mActivity;

    @AfterViews
    void initViews() {
        messageView.setText(getArguments().getString(KEY_STRING_MESSAGE));
    }

    @Click
    void cancel() {
        dismiss();
    }

    public void onOk(OnClickListener listener) {
        ok.setOnClickListener(v -> {
            listener.onClick(ok);
            dismiss();
        });
    }

    public static void showAlertDialog(Activity activity, String msg, DialogInterface.OnClickListener okListener) {
        new Builder(activity).setMessage(msg)
                .setPositiveButton(R.string.text_ok, okListener)
                .setNegativeButton(R.string.text_cancel, (dialogInterface, which) -> dialogInterface.dismiss()).create().show();
    }


    public static ConfirmDialog show(Activity activity, String message) {
        ConfirmDialog dialog = new ConfirmDialog_();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_STRING_MESSAGE, message);
        dialog.setArguments(bundle);
        dialog.setStyle(STYLE_NO_TITLE, 0);

        dialog.show(activity.getFragmentManager(), "HELLO");
        return dialog;
    }

    public static ConfirmDialog_ newInstance(Activity activity) {
        return new ConfirmDialog_();
    }


    public static ConfirmDialog show(Activity activity, int msgRes) {
        return show(activity, activity.getString(msgRes));
    }
}
