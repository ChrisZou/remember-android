package com.chriszou.remember;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.chriszou.androidlibs.L;
import com.chriszou.androidlibs.Toaster;
import com.chriszou.remember.model.Account;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zouyong on 10/20/14.
 */
@EActivity(R.layout.login_activity)
public class LoginActivity extends Activity{

    @ViewById(R.id.login_email)
    EditText mEmailEdit;
    @ViewById(R.id.login_password)
    EditText mPasswordEdit;

    @Click(R.id.login_button)
    void login() {
        String email = mEmailEdit.getText().toString().trim();
        String password = mPasswordEdit.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            performLogin(email, password);
        }
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

}
