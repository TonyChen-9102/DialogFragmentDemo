package com.demo.ck.dialogfragmentdemo.base;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.demo.ck.dialogfragmentdemo.core.CoreDialogFragment;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 时间选择 dialog
 */
public class TimePickerDialogFragment extends CoreDialogFragment {
    /*Default*/
    /*Util*/
    private DialogListener dialogListener;
    private TimePickerDialog timePickerDialog;
    /*Flag*/
    private String title;
    private boolean useCustomSet;
    private int hourOfDay;
    private int minOfHour;
    private boolean is24Hour;
    private String positiveText;
    private String negativeText;
    private int gravity;
    /*View*/

    public interface DialogListener extends Serializable {
        void onResult(int which, String tag, int hourOfDay, int minOfHour);
    }

    public static TimePickerDialogFragment netInstance() {
        return netInstance(null, null, null);
    }

    public static TimePickerDialogFragment netInstance(String title, String positiveText, String negativeText) {
        TimePickerDialogFragment dialogFragment = new TimePickerDialogFragment();
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
            hourOfDay = bundle.getInt("hourOfDay");
            minOfHour = bundle.getInt("minOfHour");
            is24Hour = bundle.getBoolean("is24Hour");
            gravity = bundle.getInt("gravity");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int mHourOfDay = 0;
        int mMinOfHour = 0;
        if (useCustomSet) {
            mHourOfDay = hourOfDay;
            mMinOfHour = minOfHour;
        } else {
            Calendar ca = Calendar.getInstance();
            mHourOfDay = ca.get(Calendar.HOUR_OF_DAY);
            mMinOfHour = ca.get(Calendar.MINUTE);
        }
        timePickerDialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (dialogListener != null) {
                    dialogListener.onResult(AlertDialog.BUTTON_POSITIVE, getTag(), hourOfDay, minute);
                }
            }
        }, mHourOfDay, mMinOfHour, is24Hour);

        timePickerDialog.setButton(AlertDialog.BUTTON_POSITIVE, (positiveText == null ? "确定" : positiveText), (DialogInterface.OnClickListener) null);
        timePickerDialog.setButton(AlertDialog.BUTTON_NEGATIVE, (negativeText == null ? "取消" : negativeText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, getTag(), 0, 0);
                }
            }
        });

        timePickerDialog.setTitle(title);
        updateGravity(timePickerDialog);
        return timePickerDialog;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (dialogListener != null) {
            dialogListener.onResult(AlertDialog.BUTTON_NEGATIVE, getTag(), 0, 0);
        }
    }

    public TimePickerDialogFragment setterDialogListener(DialogListener listener) {
        this.dialogListener = listener;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable("listener", listener);
        setArguments(bundle);
        return this;
    }

    public TimePickerDialogFragment setterGravity(int gravity) {
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

    public TimePickerDialogFragment setterTime(int hourOfDay, int minOfHour) {
        this.hourOfDay = hourOfDay;
        this.minOfHour = minOfHour;
        this.useCustomSet = true;

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt("hourOfDay", hourOfDay);
        bundle.putInt("minOfHour", minOfHour);
        bundle.putBoolean("useCustomSet", useCustomSet);
        setArguments(bundle);

        if (timePickerDialog != null) {
            timePickerDialog.updateTime(hourOfDay, minOfHour);
        }
        return this;
    }

    public TimePickerDialogFragment initIs24Hour(boolean is24Hour) {
        this.is24Hour = is24Hour;

        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean("is24Hour", is24Hour);
        setArguments(bundle);
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
