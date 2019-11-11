package wang.relish.whatsnew.n.multiwindowplayground;

import android.os.Bundle;
import androidx.annotation.Nullable;

import wang.relish.whatsnew.n.BaseActivity;
import wang.relish.whatsnew.R;

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
