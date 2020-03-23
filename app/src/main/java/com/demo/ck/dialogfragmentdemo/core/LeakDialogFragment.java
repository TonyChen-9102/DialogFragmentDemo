package com.demo.ck.dialogfragmentdemo.core;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.lang.ref.WeakReference;

/**
 * 解决 dialogFragment 内存泄露问题
 */
public class LeakDialogFragment extends DialogFragment {
    public static class DialogDismissListener implements DialogInterface.OnDismissListener {
        private WeakReference<LeakDialogFragment> leakDialogFragmentWeakReference;

        DialogDismissListener(LeakDialogFragment leakDialogFragment) {
            this.leakDialogFragmentWeakReference = new WeakReference<>(leakDialogFragment);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            LeakDialogFragment leakDialogFragment = leakDialogFragmentWeakReference.get();
            if (leakDialogFragment != null) {
                leakDialogFragment.onDismiss(dialog);
            }
        }
    }

    public static class DialogCancelListener implements DialogInterface.OnCancelListener {
        private WeakReference<LeakDialogFragment> leakDialogFragmentWeakReference;

        DialogCancelListener(LeakDialogFragment leakDialogFragment) {
            this.leakDialogFragmentWeakReference = new WeakReference<>(leakDialogFragment);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            LeakDialogFragment leakDialogFragment = leakDialogFragmentWeakReference.get();
            if (leakDialogFragment != null) {
                leakDialogFragment.onCancel(dialog);
            }
        }
    }

    /**
     * 解决原生setOnDismissListener和setOnCancelListener内存泄露问题
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (getShowsDialog()) {
            setShowsDialog(false);
        }
        super.onActivityCreated(savedInstanceState);
        setShowsDialog(true);

        if (getDialog() == null) {
            return;
        }

        View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new IllegalStateException(
                        "DialogFragment can not be attached to a container view");
            }
            getDialog().setContentView(view);
        }
        final Activity activity = getActivity();
        if (activity != null) {
            getDialog().setOwnerActivity(activity);
        }
        getDialog().setCancelable(isCancelable());
        DialogDismissListener mDialogDissmissLitener = new DialogDismissListener(this);//设置一个自定义的DissmissListener
        getDialog().setOnDismissListener(mDialogDissmissLitener);
        DialogCancelListener mDialogCancelListener = new DialogCancelListener(this);//设置一个自定义的DissmissListener
        getDialog().setOnCancelListener(mDialogCancelListener);
        if (savedInstanceState != null) {
            Bundle dialogState = savedInstanceState.getBundle("android:savedDialogState");
            if (dialogState != null) {
                getDialog().onRestoreInstanceState(dialogState);
            }
        }
    }
}
