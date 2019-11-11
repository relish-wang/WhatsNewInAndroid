package wang.relish.whatsnew.n;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.ActiveNotificationsSampleActivity;
import wang.relish.whatsnew.n.DirectBootSampleActivity;
import wang.relish.whatsnew.n.MessagingServiceSampleActivity;
import wang.relish.whatsnew.n.MultiWindowSampleActivity;
import wang.relish.whatsnew.n.QuickSettingSampleActivity;
import wang.relish.whatsnew.n.ScopedDirectoryAccessSampleActivity;
import wang.relish.whatsnew.n.ShortcutsSampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_n);
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
        goActivity(Intent.ACTION_VIEW, "https://github.com/relish-wang/DemoForN");
    }

    protected void goActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void goActivity(String action,String url){
        Intent intent = new Intent(action, Uri.parse(url));
        startActivity(intent);
    }
}
