package wang.relish.android7.sample.multiwindowplayground;

import android.os.Bundle;
import android.support.annotation.Nullable;

import wang.relish.android7.BaseActivity;
import wang.relish.android7.R;

/**
 * Created by Relish on 2016/10/14.
 */
public class UnresizableActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unresizable);
    }
}
