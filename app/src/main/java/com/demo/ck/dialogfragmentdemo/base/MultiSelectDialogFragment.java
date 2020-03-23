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
import java.util.ArrayList;
/**
 * 多选 dialog
 */
public class MultiSelectDialogFragment extends CoreDialogFragment {
    /*Default*/
    /*Util*/
    private DialogListener dialogListener;
    /*Flag*/
    private String title;
    private String positiveText;
    private String negativeText;
    private String[] items;
    private boolean[] select;
    private int gravity;
    private boolean widthMatch;

    private ArrayList<Integer> choice = new ArrayList<>();
    /*View*/

    public interface DialogListener extends Serializable {
        void onResult(int which, boolean[] select, String tag);
    }

    public static MultiSelectDialogFragment netInstance(String title, String[] items, boolean[] select) {
        return netInstance(title, items, select, null, null);
    }

    public static MultiSelectDialogFragment netInstance(String title, String[] items, boolean[] select, String positiveText, String negativeText) {
        MultiSelectDialogFragment dialogFragment = new MultiSelectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("positiveText", positiveText);
        bundle.putString("negativeText", negativeText);
        bundle.putCharSequenceArray("items", items);
        bundle.putBooleanArray("select", select);
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
            positiveText = bundle.getString("positiveText");
            negativeText = bundle.getString("negativeText");
            items = (String[]) bundle.getCharSequenceArray("items");
            select = bundle.getBooleanArray("select");
            initSelect();
            gravity = bundle.getInt("gravity");
            widthMatch = bundle.getBoolean("widthMatch");
        }
        convertToArrayList();
    }

    private void initSelect() {
        if (select == null && items != null) {
            select = new boolean[items.length];
        }
    }

    private void convertToArrayList() {
        if (select != null) {
            for (int i = 0; i < select.length; i++) {
                if (select[i]) {
                    choice.add(i);
                }
            }
        }
    }

    private void convertToBooleans() {
        if (choice != null && select != null) {
            for (int i = 0; i < select.length; i++) {
                select[i] = choice.contains(i);
            }
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(title);
        builder.setMultiChoiceItems(items, select, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    if (!choice.contains(which)) {
                        choice.add(which);
                    }
                } else {
                    if (choice.contains(which)) {
                        choice.remove(choice.indexOf(which));
                    }
                }

                convertToBooleans();
                Bundle bundle = getArguments();
                if (bundle == null) {
                    bundle = new Bundle();
                }
                bundle.putBooleanArray("select", select);
                setArguments(bundle);
            }
        });
        builder.setPositiveButton((positiveText == null ? "确定" : positiveText), onClickListener);
        builder.setNegativeButton((negativeText == null ? "取消" : negativeText), onClickListener);
        AlertDialog dialog = builder.create();
        updateGravity(dialog);
        return dialog;
    }

    private DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (dialogListener != null) {
                dialogListener.onResult(which, select, getTag());
            }
        }
    };

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogListener != null) {
            dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, select, getTag());
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

    public MultiSelectDialogFragment setterDialogListener(DialogListener listener) {
        this.dialogListener = listener;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable("listener", listener);
        setArguments(bundle);
        return this;
    }

    public MultiSelectDialogFragment setterGravity(int gravity) {
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

    public MultiSelectDialogFragment setterWidthMatch(boolean widthMatch) {
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
