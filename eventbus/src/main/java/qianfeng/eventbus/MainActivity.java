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

import qianfeng.eventbus.event.UserEvent;

public class MainActivity extends AppCompatActivity {

    public TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText = (TextView) findViewById(R.id.text);
        EventBus.getDefault().register(this);
    }

    public void go_Activity2(View view) {
        startActivity(new Intent(this,Main2Activity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)    //不管你发布的是什么线程 我都跑在 主线程
    public void changeText(UserEvent event){
        Log.e("event","thread1:"+Thread.currentThread().getName());
        mText.append(event.username);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
