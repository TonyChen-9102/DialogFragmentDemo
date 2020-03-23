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

import java.text.NumberFormat;
/**
 * 进度条 dialog
 */
public class ProgressDialogFragment extends CoreDialogFragment {
    /*Default*/
    public static final String NUMBER_FORMAT = "%1d/%2d";
    /*Util*/
    /*Flag*/
    private String title;
    private int maxValue;
    private int progress;
    private int gravity;
    private boolean widthMatch;
    private String numberFormat;
    private boolean showPercent;
    private NumberFormat percentFormat;
    /*View*/
    private ProgressDialog progressDialog;

    public static ProgressDialogFragment netInstance(String title, int maxValue) {
        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("maxValue", maxValue);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            maxValue = bundle.getInt("maxValue");
            gravity = bundle.getInt("gravity");
            widthMatch = bundle.getBoolean("widthMatch");
            numberFormat = bundle.getString("numberFormat");
            showPercent = bundle.getBoolean("showPercent");
            percentFormat = (NumberFormat) bundle.getSerializable("percentFormat");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setProgress(progress);
        if (numberFormat != null) {
            progressDialog.setProgressNumberFormat(numberFormat);
        } else {
            progressDialog.setProgressNumberFormat(NUMBER_FORMAT);
        }
        if (showPercent) {
            if (percentFormat == null) {
                progressDialog.setProgressPercentFormat(NumberFormat.getPercentInstance());
            } else {
                progressDialog.setProgressPercentFormat(percentFormat);
            }
        } else {
            progressDialog.setProgressPercentFormat(null);
        }
        progressDialog.setTitle(title);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(maxValue);
        setCancelable(false);
        updateGravity(progressDialog);
        return progressDialog;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (progressDialog != null) {
            progressDialog.setProgress(progress);
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

    public ProgressDialogFragment initNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString("numberFormat", numberFormat);
        setArguments(bundle);
        return this;
    }

    public ProgressDialogFragment initPercentFormat(boolean showPercent, NumberFormat numberFormat) {
        this.showPercent = showPercent;
        this.percentFormat = numberFormat;
        Bundle bundle = getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putSerializable("percentFormat", numberFormat);
        bundle.putBoolean("showPercent", showPercent);
        setArguments(bundle);
        return this;
    }

    public ProgressDialogFragment setterGravity(int gravity) {
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

    public ProgressDialogFragment setterWidthMatch(boolean widthMatch) {
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
