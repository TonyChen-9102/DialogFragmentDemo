package com.demo.ck.dialogfragmentdemo.core;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.demo.ck.dialogfragmentdemo.R;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * 常用处理
 */
public class CoreDialogFragment extends LeakDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
    }

    public void show(@NonNull FragmentActivity activity) {
        show(activity.getSupportFragmentManager(), activity.getClass().getCanonicalName());
    }

    public void showAllowingStateLoss(@NonNull FragmentActivity activity) {
        showAllowingStateLoss(activity.getSupportFragmentManager(), activity.getClass().getCanonicalName());
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (!isAdded()) {
            super.show(manager, tag);
        }
    }

    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        if (!isAdded()) {
            return super.show(transaction, tag);
        }

        return -1;
    }

    @Override
    public void showNow(@NonNull FragmentManager manager, @Nullable String tag) {
        if (!isAdded()) {
            super.showNow(manager, tag);
        }
    }

    public void showAllowingStateLoss(@NonNull FragmentManager manager, @Nullable String tag) {
        if (isAdded()) {
            return;
        }
        try {
            Class<?> c = Class.forName(Objects.requireNonNull(DialogFragment.class.getCanonicalName()));
            Field dismissed = c.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(CoreDialogFragment.this, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(this, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    public void showAllowingStateLoss(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        if (isAdded()) {
            return;
        }
        try {
            Class<?> c = Class.forName(Objects.requireNonNull(DialogFragment.class.getCanonicalName()));
            Field dismissed = c.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
            Field shownByMe = c.getDeclaredField("mShownByMe");
            shownByMe.setAccessible(true);
            shownByMe.set(this, true);
            Field viewDestroyed = c.getDeclaredField("mViewDestroyed");
            viewDestroyed.setAccessible(true);
            viewDestroyed.set(this, false);
            transaction.add(this, tag);
            int mBackStackId = transaction.commitAllowingStateLoss();
            Field backStackId = c.getDeclaredField("mBackStackId");
            backStackId.setAccessible(true);
            backStackId.set(this, mBackStackId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
