package wang.relish.whatsnew.n;

import android.os.Bundle;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.directboot.SchedulerFragment;

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
