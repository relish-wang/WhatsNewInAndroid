package wang.relish.android7.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wang.relish.android7.BaseActivity;
import wang.relish.android7.R;
import wang.relish.android7.sample.directboot.SchedulerFragment;

/**
 * 4 直接启动
 * Created by Relish on 2016/10/14.
 */
public class DirectBootSampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_direct_boot);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, SchedulerFragment.newInstance())
                    .commit();
        }
    }
}
