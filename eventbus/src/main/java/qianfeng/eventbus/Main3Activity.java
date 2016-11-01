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

public class Main3Activity extends AppCompatActivity {

    public TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mText = (TextView) findViewById(R.id.text);
        EventBus.getDefault().register(this);
    }
    public void go_Activity4(View view) {
        startActivity(new Intent(this,Main4Activity.class));
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND) //发布过来是 主线程 创建子线程 发布过来是 子线程直接运行在该子线程
    public void change3Text(final UserEvent event){
        Log.e("event","thread3:"+Thread.currentThread().getName());
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
