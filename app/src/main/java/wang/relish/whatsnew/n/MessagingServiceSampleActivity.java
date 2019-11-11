
package wang.relish.whatsnew.n;

import android.os.Bundle;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.messagingservice.MessagingFragment;

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
