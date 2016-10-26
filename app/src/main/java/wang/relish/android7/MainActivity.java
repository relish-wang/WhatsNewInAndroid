package wang.relish.android7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import wang.relish.android7.sample.ActiveNotificationsSampleActivity;
import wang.relish.android7.sample.DirectBootSampleActivity;
import wang.relish.android7.sample.MessagingServiceSampleActivity;
import wang.relish.android7.sample.MultiWindowSampleActivity;
import wang.relish.android7.sample.QuickSettingSampleActivity;
import wang.relish.android7.sample.ScopedDirectoryAccessSampleActivity;
import wang.relish.android7.sample.ShortcutsSampleActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //1 多窗口Playground
    public void goMultiWindowPlaygroundSample(View view) {
        goActivity(MultiWindowSampleActivity.class);
    }

    //2 活动通知
    public void goActiveNotificationsSample(View view) {
        goActivity(ActiveNotificationsSampleActivity.class);
    }

    //3 消息传递服务
    public void goMessagingServiceSample(View view) {
        goActivity(MessagingServiceSampleActivity.class);
    }

    //4 直接启动
    public void goDirectBootSample(View view) {
        goActivity(DirectBootSampleActivity.class);
    }

    //5 作用域目录访问
    public void goScopedDirectoryAccessSample(View view) {
        goActivity(ScopedDirectoryAccessSampleActivity.class);
    }

    //6 快捷方式
    public void goShortcutsSample(View view) {
        goActivity(ShortcutsSampleActivity.class);
    }

    //7 快速设置
    public void goQuickSettingSample(View view) {
        goActivity(QuickSettingSampleActivity.class);
    }

    // 作者GitHub地址
    public void goGitHub(View view) {
        goActivity(Intent.ACTION_VIEW, "https://github.com/relish-wang");
    }
}
