package com.demo.ck.dialogfragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.ck.dialogfragmentdemo.base.ConfirmDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.DatePickerDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.ListDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.MultiSelectDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.ProgressDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.SingleSelectDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.TimePickerDialogFragment;
import com.demo.ck.dialogfragmentdemo.base.WaitingDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

public class MainActivity extends CoreActivity {
    private final String TAG = getClass().getSimpleName();
    private final String SHOW_DIALOG_FALG = "show_dialog_flag";
    private final String SHOW_DIALOG = "1";
    private final String DISMISS_DIALOG = "2";

    private Button button;
    private Button button3;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button10;
    private Button button11;
    private CoreDialogFragment1 coreDialogFragment1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        button = findViewById(R.id.button);
        button3 = findViewById(R.id.button3);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button10 = findViewById(R.id.button10);
        button11 = findViewById(R.id.button11);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialogFragment.netInstance("title", "msg")
                        .setterDialogListener(new ConfirmDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, String tag) {
                                Log.i(TAG, "ConfirmDialogFragment,which=" + which);
                            }
                        }).show(activity);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
                ListDialogFragment.netInstance("title", items)
                        .setterDialogListener(new ListDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, String tag) {
                                Log.i(TAG, "ListDialogFragment,which=" + which);
                            }
                        }).show(activity);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
                SingleSelectDialogFragment.netInstance("title", items, 0)
                        .setterDialogListener(new SingleSelectDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, String tag) {
                                Log.i(TAG, "SingleSelectDialogFragment,which=" + which);
                            }
                        }).show(activity);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] items = {"item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
                boolean[] isSelect = {false, false, true, false, false, false};
                MultiSelectDialogFragment.netInstance("title", items, isSelect)
                        .setterDialogListener(new MultiSelectDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, boolean[] select, String tag) {
                                StringBuilder str = new StringBuilder();
                                if (select != null) {
                                    for (int i = 0; i < select.length; i++) {
                                        str.append(select[i]).append(",");
                                    }
                                }
                                Log.i(TAG, "MultiSelectDialogFragment,which=" + which + ",select=" + str.toString());
                            }
                        }).show(activity);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WaitingDialogFragment waitingDialogFragment = WaitingDialogFragment.netInstance("title", "msg");
                waitingDialogFragment.show(activity);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        waitingDialogFragment.dismissAllowingStateLoss();
                    }
                }, 2000);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int MAX_VALUE = 300;
                final ProgressDialogFragment dialogFragment = ProgressDialogFragment.netInstance("title", MAX_VALUE);
                dialogFragment.showAllowingStateLoss(activity);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int progress = 0;
                        while (progress < MAX_VALUE) {
                            try {
                                Thread.sleep(20);
                                progress = progress + 4;
                                dialogFragment.setProgress(progress);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        //加载完毕自动关闭dialog
                        dialogFragment.dismissAllowingStateLoss();
                    }
                }).start();
            }
        });
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                DatePickerDialogFragment.netInstance()
                        .setterDate(mYear, 0, 1)
                        .setterDialogListener(new DatePickerDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, String tag, int year, int month, int dayOfMonth) {
                                Log.i(TAG, "DateDialogFragment,which=" + which + ",year=" + year
                                        + ",month=" + month + ",dayOfMonth=" + dayOfMonth);
                            }
                        }).show(activity);
            }
        });
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialogFragment.netInstance()
                        .setterDialogListener(new TimePickerDialogFragment.DialogListener() {
                            @Override
                            public void onResult(int which, String tag, int hourOfDay, int minOfHour) {
                                Log.i(TAG, "TimeDialogFragment,which=" + which + ",hourOfDay=" + hourOfDay
                                        + ",minOfHour=" + minOfHour);
                            }
                        })
                        .show(activity);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogs();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回的时候处理状态
        if (mSavedInstanceState != null) {
            String showDialog = mSavedInstanceState.getString(SHOW_DIALOG_FALG);
            if (!TextUtils.isEmpty(showDialog)) {
                mSavedInstanceState.remove(SHOW_DIALOG_FALG);
            }
            if (TextUtils.equals(showDialog, SHOW_DIALOG)) {
                showDialogs();
            } else if (TextUtils.equals(showDialog, DISMISS_DIALOG)) {
                dismissDialog();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void showDialogs() {
        if (coreDialogFragment1 == null) {
            coreDialogFragment1 = CoreDialogFragment1.netInstance();
        }
        coreDialogFragment1.showAllowingStateLoss(this);
    }

    private void dismissDialog() {
        if (coreDialogFragment1 != null) {
            coreDialogFragment1.dismissAllowingStateLoss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0)
    public void event(Event event) {
        //activity 在后台的时候保存状态，等 resume 再处理
        if (!isStop()) {
            showDialogs();
        } else if (mSavedInstanceState != null) {
            mSavedInstanceState.putString(SHOW_DIALOG_FALG, SHOW_DIALOG);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 0)
    public void event(Event2 event) {
        //activity 在后台的时候保存状态，等 resume 再处理
        if (!isStop()) {
            if (coreDialogFragment1 != null) {
                coreDialogFragment1.dismissAllowingStateLoss();
            }
        } else if (mSavedInstanceState != null) {
            mSavedInstanceState.putString(SHOW_DIALOG_FALG, DISMISS_DIALOG);
        }
    }
}
