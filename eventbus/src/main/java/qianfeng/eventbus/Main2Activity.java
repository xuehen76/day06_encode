package qianfeng.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import qianfeng.eventbus.event.FinishEvent;
import qianfeng.eventbus.event.UserEvent;

public class Main2Activity extends AppCompatActivity {

    public TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mText = (TextView) findViewById(R.id.text);
        EventBus.getDefault().register(this);
    }

    public void go_Activity3(View view) {
        startActivity(new Intent(this, Main3Activity.class));
    }
    @Subscribe(threadMode = ThreadMode.POSTING) //发布过来是 什么线程 就运行在什么线程
    public void change2Text(final UserEvent event){
        Log.e("event","thread2:"+Thread.currentThread().getName());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mText.append(event.username);
            }
        });

    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finish(FinishEvent event){
        finish();
    }
}
