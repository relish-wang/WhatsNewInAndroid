package wang.relish.whatsnew.n.quicksettings;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import java.util.Locale;

import wang.relish.whatsnew.R;

/**
 * 检测开关状态变化的Service
 * Created by Relish on 2016/10/26.
 */
public class QuickSettingService extends TileService {

    private static final String SERVICE_STATUS_FLAG = "serviceStatus";
    private static final String PREFERENCES_KEY = "wang.relish.sample.quicksettings";
    private static final String TAG = "QuickSettingService";

    /**
     * 当开关被放置到快捷启动栏
     */
    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.d(TAG, "Tile added.");
    }

    /**
     * 当开关被打开
     */
    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.d(TAG, "Tile start listener");
    }

    /**
     * 当开关被点击
     */
    @Override
    public void onClick() {
        super.onClick();
        Log.d(TAG, "Tile clicked.");

        updateTile();
    }

    /**
     * 当开关被关上
     */
    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.d(TAG, "Tile stop listening.");
    }

    /**
     * 当开关被移出快捷启动栏
     */
    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.d(TAG, "Tile removed.");
    }


    private void updateTile() {

        Tile tile = this.getQsTile();
        boolean isActive = getServiceStatus();

        Icon newIcon;
        String newLabel;
        int newState;

        // Change the tile to match the service status.
        if (isActive) {

            newLabel = String.format(Locale.US,
                    "%s %s",
                    getString(R.string.tile_label),
                    getString(R.string.service_active));

            newIcon = Icon.createWithResource(getApplicationContext(),
                    R.drawable.ic_android_black_24dp);

            newState = Tile.STATE_ACTIVE;

        } else {
            newLabel = String.format(Locale.US,
                    "%s %s",
                    getString(R.string.tile_label),
                    getString(R.string.service_inactive));

            newIcon =
                    Icon.createWithResource(getApplicationContext(),
                            android.R.drawable.ic_dialog_alert);

            newState = Tile.STATE_INACTIVE;
        }

        // 改变开关的UI
        tile.setLabel(newLabel);
        tile.setIcon(newIcon);
        tile.setState(newState);

        // 通知更新
        tile.updateTile();
    }
    private boolean getServiceStatus() {

        SharedPreferences prefs =
                getApplicationContext()
                        .getSharedPreferences(PREFERENCES_KEY,
                                MODE_PRIVATE);

        boolean isActive = prefs.getBoolean(SERVICE_STATUS_FLAG, false);
        isActive = !isActive;

        prefs.edit().putBoolean(SERVICE_STATUS_FLAG, isActive).apply();

        return isActive;
    }
}
