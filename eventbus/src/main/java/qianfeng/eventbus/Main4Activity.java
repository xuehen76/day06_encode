package qianfeng.eventbus;

import android.support.v4.widget.TextViewCompat;
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

public class Main4Activity extends AppCompatActivity {

    public TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mText = (TextView) findViewById(R.id.text);
        EventBus.getDefault().register(this);
    }

    public void sendEvent(View view) {
        new Thread(){
            @Override
            public void run() {
                UserEvent event = new UserEvent();
                event.username="小熊";
                EventBus.getDefault().post(event);
            }
        }.start();

        EventBus.getDefault().post(new FinishEvent());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC) //不管发布的是什么线程 都创建子线程
    public void change4Text(final UserEvent event){
        Log.e("event","thread4:"+Thread.currentThread().getName());
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
}
