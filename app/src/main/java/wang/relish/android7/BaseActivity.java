package wang.relish.android7;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 *
 * Created by Relish on 2016/10/14.
 */
public class BaseActivity extends AppCompatActivity {

    protected void goActivity(Class<?> clazz, int flag) {
        Intent intent = new Intent(this, clazz);
        intent.addFlags(flag);
        startActivity(intent);
    }

    protected void goActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void goActivity(String action,String url){
        Intent intent = new Intent(action, Uri.parse(url));
        startActivity(intent);
    }

    protected void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
