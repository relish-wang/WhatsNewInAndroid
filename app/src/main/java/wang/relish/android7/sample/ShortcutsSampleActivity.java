package wang.relish.android7.sample;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import wang.relish.android7.BaseActivity;
import wang.relish.android7.R;
import wang.relish.android7.sample.shortcuts.ShortcutsAdapter;

/**
 * 6 快捷方式
 * Created by Relish on 2016/10/24.
 */
public class ShortcutsSampleActivity extends BaseActivity {


    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recycler_view_shortcuts;
    ShortcutManager mManager;
    ShortcutsAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_shortcuts);

        //初始化数据
        initViews(savedInstanceState);
    }

    private void initViews(Bundle savedInstanceState) {
        mAdapter = new ShortcutsAdapter(this);
        mManager = getSystemService(ShortcutManager.class);
        //处理添加新的shortcut的操作
        Intent intent = getIntent();
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            add(this, mAdapter, mManager);
        }
        TextView btn_add_shortcuts = (TextView) findViewById(R.id.btn_add_shortcuts);
        btn_add_shortcuts.setOnClickListener((View v) -> add(this, mAdapter, mManager));

        recycler_view_shortcuts = (RecyclerView) findViewById(R.id.recycler_view_shortcuts);
        recycler_view_shortcuts.setAdapter(mAdapter);
        recycler_view_shortcuts.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setShortcuts(getShortcuts(mManager));

        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(() ->
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... voids) {
                        mAdapter.setShortcuts(getShortcuts(mManager));
                        return null;
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        if (swipe_refresh_layout.isRefreshing())
                            swipe_refresh_layout.setRefreshing(false);
                    }
                }.execute());
    }

    public static void add(Context context, ShortcutsAdapter adapter, ShortcutManager mManager) {
        final EditText et_website = new EditText(context);
        et_website.setHint("http://www.example.com/");
        et_website.setText(R.string.http_text);
        et_website.setSelection(et_website.getText().length());
        new AlertDialog.Builder(context)
                .setTitle("添加一个网页")
                .setView(et_website)
                .setPositiveButton("添加", (DialogInterface dialogInterface, int i) -> {
                    String url = et_website.getText().toString().trim();
                    if (!url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    addDynamicShortcuts(context, url, adapter, mManager);
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
    }


    private static void addDynamicShortcuts(Context context, String url, ShortcutsAdapter adapter, ShortcutManager mManager) {
        Icon icon = Icon.createWithResource(context, R.mipmap.ic_launcher);
        addDynamicShortcuts(context, url, url,
                new Intent(Intent.ACTION_VIEW, Uri.parse(url)), icon);
        adapter.setShortcuts(getShortcuts(mManager));
    }


    /**
     * 动态添加Shortcuts
     *
     * @param id         Shortcut ID
     * @param shortLabel 显示文字
     * @param intent     触发事件
     * @param icon       显示图标
     */
    private static void addDynamicShortcuts(Context context, String id, String shortLabel, Intent intent, Icon icon) {
        try {
            ShortcutManager mManager = context.getSystemService(ShortcutManager.class);
            ShortcutInfo shortcut = new ShortcutInfo
                    .Builder(context, id)
                    //必填项，显示文字
                    .setShortLabel(shortLabel)
                    //必填项，点击后触发该意图
                    .setIntent(intent)
                    //选填，显示图标
                    .setIcon(icon)
                    .build();
            mManager.addDynamicShortcuts(Collections.singletonList(shortcut));
        } catch (IllegalArgumentException e) {
            Toast.makeText(context, "IllegalArgumentException:最多5个Shortcuts", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Log.e("addDynamicShortcuts", e.getMessage());
        }
    }

    /**
     * 获取所有快捷方式
     *
     * @return 快捷方式们
     */
    public static List<ShortcutInfo> getShortcuts(ShortcutManager mManager) {
        final List<ShortcutInfo> ret = new ArrayList<>();
        final HashSet<String> seenKeys = new HashSet<>();

        // Check existing shortcuts shortcuts
        mManager.getDynamicShortcuts().stream().filter(shortcut ->
                !shortcut.isImmutable()).forEach(shortcut -> {
            ret.add(shortcut);
            seenKeys.add(shortcut.getId());
        });
        mManager.getPinnedShortcuts().stream().filter(shortcut -> !shortcut.isImmutable() &&
                !seenKeys.contains(shortcut.getId())).forEach(shortcut -> {
            ret.add(shortcut);
            seenKeys.add(shortcut.getId());
        });
        mManager.getManifestShortcuts().stream().filter(shortcut -> shortcut.isImmutable() &&
                !seenKeys.contains(shortcut.getId())).forEachOrdered(shortcut -> {
            ret.add(shortcut);
            seenKeys.add(shortcut.getId());
        });
        return ret;
    }


}