package wang.relish.whatsnew.n;

import android.os.Bundle;
import androidx.annotation.Nullable;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.scopeddirectoryaccess.ScopedDirectoryAccessFragment;

/**
 * 5 作用域目录访问
 * Created by Relish on 2016/10/14.
 */
public class ScopedDirectoryAccessSampleActivity extends BaseActivity {

    private static final String FRAGMENT_TAG = ScopedDirectoryAccessFragment.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_scoped_directory_access);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ScopedDirectoryAccessFragment.newInstance(), FRAGMENT_TAG)
                    .commit();
        }
    }
}
