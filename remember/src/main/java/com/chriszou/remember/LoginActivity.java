package com.chriszou.remember;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Prefs;
import com.chriszou.androidlibs.Toaster;
import com.chriszou.remember.model.Account;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zouyong on 10/20/14.
 */
@EActivity(R.layout.login_activity)
public class LoginActivity extends RmbActivity{
    private static final String PREF_STRING_PREVIOUS_EMAIL = "pref_string_previous_email";

    @ViewById(R.id.login_email)
    EditText mEmailEdit;
    @ViewById(R.id.login_password)
    EditText mPasswordEdit;

    @AfterViews
    void initViews() {
        String previousEmail = Prefs.getString(PREF_STRING_PREVIOUS_EMAIL, "");
        mEmailEdit.setText(previousEmail);
    }

    @AfterTextChange({R.id.login_email, R.id.login_password})
    void textChanged() {

    }

    @Click(R.id.login_button)
    void login() {
        String email = mEmailEdit.getText().toString().trim();
        String password = mPasswordEdit.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            performLogin(email, password);
        }

        //Save the email so that the user don't have to type it again
        Prefs.putString(PREF_STRING_PREVIOUS_EMAIL, email);
    }

    @Click
    void loginRegister() {
        startActivity(RegisterActivity.createIntent(getActivity()));
    }

    @Background
    void performLogin(String email, String password) {
        Account.login(email, password, new Account.LoginCallback() {
            @Override
            public void onLoginResult(boolean succeed, String errorMsg) {
                if (succeed) {
                    onLoginSucceed();
                } else {
                    onLoginFailed(errorMsg);
                }
            }
        });
    }

    @UiThread
    void onLoginFailed(String errorMsg) {
        Toaster.s(this, "Login failed: "+errorMsg);
    }

    @UiThread
    void onLoginSucceed() {
        setResult(RESULT_OK);
        finish();
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, LoginActivity_.class);
    }
}
