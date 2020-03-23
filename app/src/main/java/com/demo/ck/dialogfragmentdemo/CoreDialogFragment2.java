package com.demo.ck.dialogfragmentdemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.demo.ck.dialogfragmentdemo.core.CoreDialogFragment;

/**
 * 两种布局方法，一种返回Dialog
 */
public class CoreDialogFragment2 extends CoreDialogFragment {
    private final String TAG = getClass().getSimpleName();

    public static CoreDialogFragment2 netInstance() {
        CoreDialogFragment2 coreDialogFactory = new CoreDialogFragment2();
        Bundle args = new Bundle();
        coreDialogFactory.setArguments(args);
        return coreDialogFactory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppDialogTheme);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("title")
                .setMessage("1121e3212e2e21")
                .setPositiveButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.i(TAG, "onCreateDialog;show=" + getShowsDialog());
                            }
                        }
                )
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
        // 设置宽度为屏宽、位置在屏幕底部
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.white);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.BOTTOM;
            wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.i(TAG, "CoreDialogFragment2,onDismiss");
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i(TAG, "CoreDialogFragment2,onCancel");
    }
}
