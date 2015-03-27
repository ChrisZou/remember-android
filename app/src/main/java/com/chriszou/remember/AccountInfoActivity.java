package com.chriszou.remember;

import android.widget.EditText;
import android.widget.TextView;

import com.chriszou.androidlibs.OnEnterListener;
import com.chriszou.androidlibs.Toaster;
import com.chriszou.androidlibs.ViewUtils;
import com.chriszou.remember.model.User;
import com.chriszou.remember.model.UserModel;
import com.chriszou.remember.util.Links;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 3/26/15.
 */
@EActivity(R.layout.account_info_activity)
public class AccountInfoActivity extends RmbActivity {
    @ViewById
    TextView emailView;
    @ViewById
    EditText usernameEdit;
    @ViewById
    CircleImageView avatarView;

    User mUser;

    @AfterViews
    void initViews() {
        mUser = UserModel.currentUser();
        usernameEdit.setOnKeyListener(new OnEnterListener() {
            @Override
            public void onEnter() {
                changeUsername();
            }
        });

        updateViews();
    }

    private void updateViews() {
        emailView.setText(mUser.email);
        usernameEdit.setText(mUser.username);
        Picasso.with(getActivity()).load(Links.userAvatar(mUser)).into(avatarView);
    }

    private void changeUsername() {
        if (ViewUtils.inputNotEmpty(usernameEdit)) {
            String username = ViewUtils.getTextTrimed(usernameEdit);
            UserModel.setUsername(username).subscribe(this::onUpdated, this::onError);
        }
    }

    private void onUpdated(User user) {
        UserModel.saveUser(user);
        mUser = user;
        updateViews();
    }

    public void onError(Throwable throwable) {
        String error = throwable==null ? "未知错误" : throwable.getMessage();
        Toaster.s(getActivity(), error);
    }

    @Click
    void avatarView() {
        changeAvatar();
    }

    private void changeAvatar() {

    }



    @Click
    void usernameLayout() {
        usernameEdit.requestFocus();
        usernameEdit.setSelection(usernameEdit.getText().length());
    }
}
