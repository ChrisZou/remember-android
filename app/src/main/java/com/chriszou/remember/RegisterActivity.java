package com.chriszou.remember;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewUtils;
import com.chriszou.remember.model.User;
import com.chriszou.remember.model.UserModel;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Chris on 1/2/15.
 */
@EActivity(R.layout.register_activity)
public class RegisterActivity extends RmbActivity {
    @ViewById(R.id.register_email)
    EditText mEmailEdit;

    @ViewById(R.id.register_password)
    EditText mPasswordEdit;
    @ViewById(R.id.register_password_confirm)
    EditText mPasswordConfirmEdit;

    @ViewById(R.id.register)
    View mRegisterBtn;

    @AfterTextChange({R.id.register_password_confirm, R.id.register_password, R.id.register_email})
    void updateButtonEnabled() {
        mRegisterBtn.setEnabled(false);

        if(ViewUtils.inputNotEmpty(mEmailEdit) && ViewUtils.inputNotEmpty(mPasswordConfirmEdit) && ViewUtils.inputNotEmpty(mPasswordEdit)) {
            String password = ViewUtils.getTextTrimed(mPasswordEdit);
            String passwordConf = ViewUtils.getTextTrimed(mPasswordConfirmEdit);
            if (password.equals(passwordConf)) {
                mRegisterBtn.setEnabled(true);
            }
        }
    }

    @Click
    void register() {
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        String email = ViewUtils.getTextTrimed(mEmailEdit);
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toaster.s(getActivity(), "邮箱格式不正确");
            return;
        }

        User user = new User();
        user.email = email;
        user.password = ViewUtils.getTextTrimed(mPasswordEdit);
        user.passwordConfirmation = ViewUtils.getTextTrimed(mPasswordConfirmEdit);

        performRegister(user);
    }

    void performRegister(User user) {
        UserModel.register(user).subscribe(this::onRegisterSucceed, this::onError);
    }

    public static Intent createIntent(Activity activity) {
        return new Intent(activity, RegisterActivity_.class);
    }

    void onRegisterSucceed(User user) {
        UserModel.saveUser(user);
        startActivity(MainActivity.createIntent(getActivity()));
        finish();
    }
}
