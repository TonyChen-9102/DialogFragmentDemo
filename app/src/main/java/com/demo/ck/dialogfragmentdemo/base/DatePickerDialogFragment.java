package com.demo.ck.dialogfragmentdemo.base;

import android.app.DatePickerDialog;
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
import java.util.Calendar;

/**
 * 日期选择 dialog
 */
public class DatePickerDialogFragment extends CoreDialogFragment {
    /*Default*/
    /*Util*/
    private DialogListener dialogListener;
    private DatePickerDialog datePickerDialog;
    /*Flag*/
    private String title;
    private boolean useCustomSet;
    private int year;
    private int month;
    private int day;
    private String positiveText;
    private String negativeText;
    private int gravity;
    /*View*/

    public interface DialogListener extends Serializable {
        void onResult(int which, String tag, int year, int month, int dayOfMonth);
    }

    public static DatePickerDialogFragment netInstance() {
        return netInstance(null, null, null);
    }

    public static DatePickerDialogFragment netInstance(String title, String positiveText, String negativeText) {
        DatePickerDialogFragment dialogFragment = new DatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("positiveText", positiveText);
        bundle.putString("negativeText", negativeText);
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
            useCustomSet = bundle.getBoolean("useCustomSet");
            year = bundle.getInt("year");
            month = bundle.getInt("month");
            day = bundle.getInt("day");
            gravity = bundle.getInt("gravity");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int mYear = 0;
        int mMonth = 0;
        int mDay = 0;
        if (useCustomSet) {
            mYear = year;
            mMonth = month;
            mDay = day;
        } else {
            Calendar ca = Calendar.getInstance();
            mYear = ca.get(Calendar.YEAR);
            mMonth = ca.get(Calendar.MONTH);
            mDay = ca.get(Calendar.DAY_OF_MONTH);
        }
        datePickerDialog = new DatePickerDialog(requireActivity(), null, mYear, mMonth, mDay);

        datePickerDialog.setButton(AlertDialog.BUTTON_POSITIVE, (positiveText == null ? "确定" : positiveText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectYear = datePickerDialog.getDatePicker().getYear();
                int selectMonth = datePickerDialog.getDatePicker().getMonth();
                int selectDay = datePickerDialog.getDatePicker().getDayOfMonth();
                if (dialogListener != null) {
                    dialogListener.onResult(AlertDialog.BUTTON_POSITIVE, getTag(), selectYear, selectMonth, selectDay);
                }
            }
        });
        datePickerDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (negativeText == null ? "取消" : negativeText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, getTag(), 0, 0, 0);
                }
            }
        });

        datePickerDialog.setTitle(title);
        updateGravity(datePickerDialog);
        return datePickerDialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogListener != null) {
            dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, getTag(), 0, 0, 0);
        }
    }

    public DatePickerDialogFragment setterDialogListener(DialogListener listener) {
        this.dialogListener = listener;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable("listener", listener);
        setArguments(bundle);
        return this;
    }

    public DatePickerDialogFragment setterGravity(int gravity) {
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

    public DatePickerDialogFragment setterDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.useCustomSet = true;

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("year", year);
        bundle.putInt("month", month);
        bundle.putInt("day", day);
        bundle.putBoolean("useCustomSet", useCustomSet);
        setArguments(bundle);

        if (datePickerDialog != null) {
            datePickerDialog.updateDate(year, month, day);
        }
        return this;
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

            wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
    }

}
