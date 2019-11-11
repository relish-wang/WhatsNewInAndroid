package wang.relish.whatsnew.n.activenotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.ActiveNotificationsSampleActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * 允许通知被排列的Fragment
 */
public class ActiveNotificationsFragment extends Fragment {

    /**
     * 只要不与该应用其他request code相同，这个request code可以为任何值。
     */
    private static final int REQUEST_CODE = 2323;

    private static final String TAG = "ActiveNotificationsFragment";

    private static final String NOTIFICATION_GROUP =
            "wang.relish.whatsnew.n.activenotifications.notification_type";

    private static final int NOTIFICATION_GROUP_SUMMARY_ID = 0;

    private NotificationManager mNotificationManager;


    private TextView mNumberOfNotifications;

    /**
     * 每个通知需要一个唯一的ID，否则前一个相同ID的通知会被覆盖。
     * 这个变量在使用时会自增。
     */
    private static int sNotificationId = NOTIFICATION_GROUP_SUMMARY_ID + 1;

    private PendingIntent mDeletePendingIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification_builder, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateNumberOfNotifications();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNotificationManager = (NotificationManager) getActivity().getSystemService(
                NOTIFICATION_SERVICE);
        mNumberOfNotifications = (TextView) view.findViewById(R.id.number_of_notifications);

        View.OnClickListener onClickListener = (View v) -> {
            switch (v.getId()) {
                case R.id.add_notification:
                    addNotificationAndUpdateSummaries();
                    break;
            }
        };
        view.findViewById(R.id.add_notification).setOnClickListener(onClickListener);

        // 新建一个PendingIntent，用于删除通知时更新数量
        Intent deleteIntent = new Intent(ActiveNotificationsSampleActivity.ACTION_NOTIFICATION_DELETE);
        mDeletePendingIntent = PendingIntent.getBroadcast(getActivity(),
                REQUEST_CODE, deleteIntent, 0);
    }

    /**
     * 发送一个新的{@link Notification}
     * 之后会更新当前通知栏内notification数量。若存在超过一个notification，则创建summary notification
     */
    private void addNotificationAndUpdateSummaries() {
        // [BEGIN 创建notification]
        // 创建一个通知并发出
        final int notificationId = getNewNotificationId();
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.mipmap.ic_notification)
                .setContentTitle(getString(R.string.sample_notification_title, notificationId))
                .setContentText(getString(R.string.sample_notification_content))
                .setAutoCancel(true)
                .setDeleteIntent(mDeletePendingIntent)
                .setGroup(NOTIFICATION_GROUP);

        final Notification notification = builder.build();
        mNotificationManager.notify(notificationId, notification);
        // [END 创建notification]

        updateNumberOfNotifications();
    }

    /**
     * (增/更/删)通知数量
     */
    protected void updateNotificationSummary() {
        final StatusBarNotification[] activeNotifications = mNotificationManager
                .getActiveNotifications();

        int numberOfNotifications = activeNotifications.length;
        for (StatusBarNotification notification : activeNotifications) {
            if (notification.getId() == NOTIFICATION_GROUP_SUMMARY_ID) {
                numberOfNotifications--;
                break;
            }
        }

        if (numberOfNotifications > 1) {
            // 增/改: summary notification显示的数量更新
            String notificationContent = getString(R.string.sample_notification_summary_content,
                    "" + numberOfNotifications);
            final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                    .setSmallIcon(R.mipmap.ic_notification)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .setSummaryText(notificationContent))
                    .setGroup(NOTIFICATION_GROUP)
                    .setGroupSummary(true);
            final Notification notification = builder.build();
            mNotificationManager.notify(NOTIFICATION_GROUP_SUMMARY_ID, notification);
        } else {
            // 当通知栏中的notification只剩下一个时，将summary notification从通知栏中去除
            // 删: summary notification中显示的数量减1.
            mNotificationManager.cancel(NOTIFICATION_GROUP_SUMMARY_ID);
        }
    }

    /**
     * 从{@link NotificationManager}请求notifications的当前显示数量
     */
    public void updateNumberOfNotifications() {
        updateNotificationSummary();
        // [BEGIN get_active_notifications]
        // 查询当前显示的notifications
        final StatusBarNotification[] activeNotifications = mNotificationManager
                .getActiveNotifications();
        // [END get_active_notifications]
        final int numberOfNotifications = activeNotifications.length;
        mNumberOfNotifications.setText(getString(R.string.active_notifications,
                numberOfNotifications));

    }

    /**
     * 检索一个唯一的notification ID
     */
    public int getNewNotificationId() {
        int notificationId = sNotificationId++;

        //虽然在这个样例里不太可能发生,但是如果我们发出过多的通知int可能会溢出.
        //大多数倾向于使用一种更确定的方式来只做可识别的通知ID,比如对通知内容做一个哈希值.
        if (notificationId == NOTIFICATION_GROUP_SUMMARY_ID) {
            notificationId = sNotificationId++;
        }
        return notificationId;
    }
}