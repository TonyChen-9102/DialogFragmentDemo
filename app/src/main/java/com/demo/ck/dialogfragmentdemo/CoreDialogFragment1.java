package com.demo.ck.dialogfragmentdemo;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.demo.ck.dialogfragmentdemo.core.CoreDialogFragment;

/**
 * 两种布局方法，一种返回布局
 */
public class CoreDialogFragment1 extends CoreDialogFragment {
    private TextView tvDemo;

    public static CoreDialogFragment1 netInstance() {
        CoreDialogFragment1 coreDialogFactory = new CoreDialogFragment1();
        Bundle args = new Bundle();
        coreDialogFactory.setArguments(args);
        return coreDialogFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_core_demo1, container, false);
        // 设置宽度为屏宽、位置在屏幕底部
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.white);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(wlp);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDemo = view.findViewById(R.id.tvDemo);

        tvDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
