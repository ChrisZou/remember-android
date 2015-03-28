package com.chriszou.remember;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Chris on 3/26/15.
 */
@EActivity(R.layout.account_info_activity)
public class AccountInfoActivity extends RmbActivity {
    private static final int SELECT_PICTURE = 1;
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
        Picasso picasso = Picasso.with(getActivity());
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);
        picasso.load(Links.userAvatar(mUser)).into(avatarView);
    }

    private void changeUsername() {
        if (ViewUtils.inputNotEmpty(usernameEdit)) {
            String username = ViewUtils.getTextTrimed(usernameEdit);
            UserModel.setUsername(username).subscribe(this::onUpdated, this::onError);
        }
    }

    private void onUpdated(final User user) {
        mUser = user;
        UserModel.saveUser(mUser);
        updateViews();
        setResult(RESULT_OK);
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
        Intent pickIntent = new Intent();
        pickIntent.setType("image/*");
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String pickTitle = getString(R.string.select_pic_or_take_pic);
        Intent chooserIntent = Intent.createChooser(pickIntent, pickTitle);
        chooserIntent.putExtra ( Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePhotoIntent } );

        startActivityForResult(chooserIntent, SELECT_PICTURE);
    }

    @OnActivityResult(SELECT_PICTURE)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK  && data != null && data.getData() != null) {
            Uri _uri = data.getData();

            //User had pick an image.
            Cursor cursor = getContentResolver().query(_uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
            cursor.moveToFirst();

            //Link to the image
            final String imageFilePath = cursor.getString(0);
            cursor.close();

            UserModel.setUserAvater(imageFilePath).subscribe(this::onUpdated, this::onError);
        }
    }

    @Click
    void usernameLayout() {
        usernameEdit.requestFocus();
        usernameEdit.setSelection(usernameEdit.getText().length());
    }
}
