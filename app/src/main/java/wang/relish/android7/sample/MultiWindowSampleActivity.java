package wang.relish.android7.sample;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import wang.relish.android7.R;
import wang.relish.android7.BaseActivity;
import wang.relish.android7.sample.multiwindowplayground.AdjacentActivity;
import wang.relish.android7.sample.multiwindowplayground.UnresizableActivity;

/**
 * 1 多窗口Playground
 * Created by Relish on 2016/10/14.
 */
public class MultiWindowSampleActivity extends BaseActivity {


    private TextView tv_drag_data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_multi_window);

        TextView tvWarning = (TextView) findViewById(R.id.warning_multiwindow_disabled);
        tvWarning.setVisibility(isInMultiWindowMode() ? View.GONE : View.VISIBLE);


        tv_drag_data = (TextView) findViewById(R.id.tv_drag_data);
        tv_drag_data.setOnTouchListener((View v, MotionEvent event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                /** 构造一个ClipData，将需要传递的数据放在里面 */
                String data = tv_drag_data.getText().toString();
                ClipData.Item item = new ClipData.Item(data);
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(data, mimeTypes, item);
                View.DragShadowBuilder shadow = new View.DragShadowBuilder(tv_drag_data);
                /** startDragAndDrop是Android N SDK中的新方法，替代了以前的startDrag，flag需要设置为DRAG_FLAG_GLOBAL */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData, shadow, null, View.DRAG_FLAG_GLOBAL);
                } else {
                    //noinspection deprecation
                    v.startDrag(dragData, shadow, null, View.DRAG_FLAG_GLOBAL);
                }
                return true;
            } else {
                return false;
            }
        });
    }

    @Override
    public void onMultiWindowModeChanged(boolean isInMultiWindowMode) {
        super.onMultiWindowModeChanged(isInMultiWindowMode);

        showMessage("onMultiWindowModeChanged:" +
                (isInMultiWindowMode ? "进入分屏模式" : "进入全屏模式"));
    }

    //1 不可分屏的Activity(android:resizableActivity="false")
    public void onStartUnresizableActivity(View view) {
        goActivity(UnresizableActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    //2 启动在相邻分屏窗口的Activity(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
    public void onStartAdjacentActivity(View view) {
        goActivity(
                AdjacentActivity.class,
                /*
                 * 如果仅仅添加了FLAG_ACTIVITY_LAUNCH_ADJACENT，可能会因为当前Activity和要启动的Activity的Task
                 * 相同，而导致要启动的Activity在当前Activity位置上启动。因此我们添加了FLAG_ACTIVITY_NEW_TASK
                 */
                Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    public void add(View view) {
        tv_drag_data.append("1");
    }
}
