package com.demo.ck.dialogfragmentdemo.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.ck.dialogfragmentdemo.core.CoreDialogFragment;

/**
 * 等待 dialog
 */
public class WaitingDialogFragment extends CoreDialogFragment {
    /*Default*/
    /*Util*/
    /*Flag*/
    private String title;
    private String msg;
    private int gravity;
    private boolean widthMatch;
    /*View*/

    public static WaitingDialogFragment netInstance(String title, String msg) {
        WaitingDialogFragment dialogFragment = new WaitingDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("msg", msg);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            msg = bundle.getString("msg");
            gravity = bundle.getInt("gravity");
            widthMatch = bundle.getBoolean("widthMatch");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(requireActivity());
        dialog.setTitle(title);
        dialog.setMessage(msg);
        setCancelable(false);
        updateGravity(dialog);
        return dialog;
    }

    private void updateGravity(Dialog dialog) {
        if (dialog == null) {
            return;
        }
        // 设置宽度为屏宽、位置在屏幕底部
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.white);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            if (gravity != 0) {
                wlp.gravity = gravity;
            } else {
                wlp.gravity = Gravity.CENTER;
            }
            if (widthMatch) {
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            } else {
                wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
    }

    public WaitingDialogFragment setterGravity(int gravity) {
        this.gravity = gravity;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("gravity", gravity);
        setArguments(bundle);
        updateGravity(getDialog());
        return this;
    }

    public WaitingDialogFragment setterWidthMatch(boolean widthMatch) {
        this.widthMatch = widthMatch;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean("widthMatch", widthMatch);
        setArguments(bundle);
        updateGravity(getDialog());
        return this;
    }


}
