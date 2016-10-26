package wang.relish.android7.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import wang.relish.android7.BaseActivity;
import wang.relish.android7.R;
import wang.relish.android7.sample.activenotifications.ActiveNotificationsFragment;

/**
 * 2 活动通知
 * Created by Relish on 2016/10/14.
 */
public class ActiveNotificationsSampleActivity extends BaseActivity {


    private ActiveNotificationsFragment mFragment;

    public static final String ACTION_NOTIFICATION_DELETE
            = "wang.relish.android7.sample.activenotifications.delete";

    private BroadcastReceiver mDeleteReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mFragment == null) {
                findFragment();
            }
            mFragment.updateNumberOfNotifications();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_active_notifications);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ActiveNotificationsFragment fragment = new ActiveNotificationsFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    private void findFragment() {
        mFragment = (ActiveNotificationsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.sample_content_fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注册广播接收器，并设置过滤器
        registerReceiver(mDeleteReceiver, new IntentFilter(ACTION_NOTIFICATION_DELETE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注销广播接收器
        unregisterReceiver(mDeleteReceiver);
    }
}
