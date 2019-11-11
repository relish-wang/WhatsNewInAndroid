package wang.relish.whatsnew.n.multiwindowplayground;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.TextView;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.BaseActivity;

/**
 * A
 * 跳转到相邻窗口的ctivity
 * Created by Relish on 2016/10/14.
 */
public class AdjacentActivity extends BaseActivity {


    TextView tv_receive_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjacent);
        tv_receive_data = (TextView) findViewById(R.id.tv_receive_data);
        tv_receive_data.setOnDragListener((View v, DragEvent event) -> {
            String TAG = "drag_event";
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d(TAG, "Action is DragEvent.ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d(TAG, "Action is DragEvent.ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d(TAG, "Action is DragEvent.ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d(TAG, "Action is DragEvent.ACTION_DRAG_ENDED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(TAG, "ACTION_DROP event");
                    /** 3.在这里显示接收到的结果 */
                    String dragData = event.getClipData().getItemAt(0).getText().toString();
                    Log.d(TAG, "dragData = " + dragData);
                    tv_receive_data.setText(dragData);
                    break;
                default:
                    break;
            }
            return true;
        });

    }
}
