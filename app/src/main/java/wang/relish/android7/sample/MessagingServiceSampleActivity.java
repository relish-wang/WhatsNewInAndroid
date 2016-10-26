
package wang.relish.android7.sample;

import android.app.Activity;
import android.os.Bundle;

import wang.relish.android7.BaseActivity;
import wang.relish.android7.R;
import wang.relish.android7.sample.messagingservice.MessagingFragment;

public class MessagingServiceSampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_messageing_service);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MessagingFragment())
                    .commit();
        }
    }
}
