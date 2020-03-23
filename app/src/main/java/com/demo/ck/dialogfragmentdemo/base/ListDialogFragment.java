package com.demo.ck.dialogfragmentdemo.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.demo.ck.dialogfragmentdemo.core.CoreDialogFragment;

import java.io.Serializable;
/**
 * 列表 dialog
 */
public class ListDialogFragment extends CoreDialogFragment {
    /*Default*/
    /*Util*/
    private DialogListener dialogListener;
    /*Flag*/
    private String title;
    private int gravity;
    private String[] items;
    private boolean widthMatch;
    /*View*/

    public interface DialogListener extends Serializable {
        void onResult(int which, String tag);
    }

    public static ListDialogFragment netInstance(String title, String[] items) {
        ListDialogFragment dialogFragment = new ListDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putCharSequenceArray("items", items);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dialogListener = (DialogListener) bundle.getSerializable("listener");
            title = bundle.getString("title");
            items = (String[]) bundle.getCharSequenceArray("items");
            gravity = bundle.getInt("gravity");
            widthMatch = bundle.getBoolean("widthMatch");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setItems(items, onClickListener);
        AlertDialog dialog = builder.create();
        updateGravity(dialog);
        return dialog;
    }

    private DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialogListener != null) {
                dialogListener.onResult(which, getTag());
            }
        }
    };

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogListener != null) {
            dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, getTag());
        }
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

    public ListDialogFragment setterDialogListener(DialogListener listener) {
        this.dialogListener = listener;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable("listener", listener);
        setArguments(bundle);
        return this;
    }

    public ListDialogFragment setterGravity(int gravity) {
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

    public ListDialogFragment setterWidthMatch(boolean widthMatch) {
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
