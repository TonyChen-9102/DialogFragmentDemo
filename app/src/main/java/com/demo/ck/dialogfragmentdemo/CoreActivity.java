package com.demo.ck.dialogfragmentdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

public class CoreActivity extends AppCompatActivity {
    private boolean stop;
    public Bundle mSavedInstanceState;
    protected FragmentActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSavedInstanceState = savedInstanceState;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setStop(false);
    }

    @Override
    protected void onStop() {
        setStop(true);
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mSavedInstanceState = outState;
    }
}
