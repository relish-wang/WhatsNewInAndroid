package wang.relish.whatsnew.n.shortcuts;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import androidx.core.content.ContextCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wang.relish.whatsnew.R;
import wang.relish.whatsnew.n.ShortcutsSampleActivity;

public class ShortcutsAdapter extends RecyclerView.Adapter<ShortcutsAdapter.MyViewHolder> {

    private List<ShortcutInfo> mShortcuts = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    private ShortcutManager mManager;

    public ShortcutsAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        mManager = mContext.getSystemService(ShortcutManager.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.rv_item_shortcuts, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShortcutInfo shortcut = mShortcuts.get(position);
        holder.tv_url.setText(shortcut.getShortLabel());
        holder.tv_state.setText("");
        holder.btn_remove.setEnabled(true);
        holder.btn_remove.setVisibility(View.VISIBLE);
        holder.btn_remove.setBackground(ContextCompat.getDrawable(mContext, R.drawable.remove));
        holder.btn_disable.setEnabled(true);
        holder.btn_disable.setText(R.string.diable_it);
        holder.btn_disable.setBackground(ContextCompat.getDrawable(mContext, R.drawable.disable));

        if (shortcut.isImmutable()) {
            holder.tv_state.append("immutable ");
        }
        if (shortcut.isDeclaredInManifest()) {
            holder.tv_state.append("declared_in_manifest ");
        }
        if (shortcut.isDynamic()) {
            holder.tv_state.append("dynamic ");
        }
        if (shortcut.isPinned()) {
            holder.tv_state.append("pinned ");
        }
        if (!shortcut.isEnabled()) {
            //不可用的shortcuts不能被移除和失效
            holder.btn_remove.setVisibility(View.GONE);
            holder.btn_disable.setText(R.string.enable_it);
            holder.btn_disable.setBackground(ContextCompat.getDrawable(
                    mContext, android.R.color.holo_orange_dark));
            holder.cardView.setEnabled(false);
        }
        holder.btn_disable.setOnClickListener((View v) -> {
            if (shortcut.isImmutable()) {
                Toast.makeText(mContext, "Immutable shortcut can't be disabled!", Toast.LENGTH_SHORT).show();
                //AndroidManifest.xml里定义的shortcuts不可被失效
                holder.btn_disable.setEnabled(false);
                holder.btn_disable.setBackground(ContextCompat.getDrawable(
                        mContext, R.color.dark_gray));
                return;
            }
            if (shortcut.isEnabled()) {
                mManager.disableShortcuts(Collections.singletonList(shortcut.getId()));
            } else {
                mManager.enableShortcuts(Collections.singletonList(shortcut.getId()));
            }
            setShortcuts(ShortcutsSampleActivity.getShortcuts(mManager));//刷新列表
        });
        holder.btn_remove.setOnClickListener((View v) -> {
            if (shortcut.isImmutable()) {
                Toast.makeText(mContext, "Immutable shortcut can't be removed!", Toast.LENGTH_SHORT).show();
                //AndroidManifest.xml里定义的shortcuts不可被移除
                holder.btn_remove.setEnabled(false);
                holder.btn_remove.setBackground(ContextCompat.getDrawable(
                        mContext, R.color.dark_gray));
                return;
            }
            if (shortcut.isPinned()) {
                Toast.makeText(mContext, "Pinned shortcut can't be removed!", Toast.LENGTH_SHORT).show();
                holder.btn_remove.setEnabled(false);
                holder.btn_remove.setBackground(ContextCompat.getDrawable(mContext, R.color.dark_gray));
                return;
            }
            mManager.removeDynamicShortcuts(Collections.singletonList(shortcut.getId()));
            setShortcuts(ShortcutsSampleActivity.getShortcuts(mManager));//刷新列表
        });
        holder.cardView.setOnClickListener((View v) -> {
            if (shortcut.isImmutable()) {
                ShortcutsSampleActivity.add(mContext, this, mManager);
            } else {
                Intent intent = shortcut.getIntent();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShortcuts == null ? 0 : mShortcuts.size();
    }

    public void setShortcuts(List<ShortcutInfo> shortcuts) {
        mShortcuts.clear();
        mShortcuts.addAll(shortcuts);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tv_url;
        TextView tv_state;
        Button btn_disable;
        Button btn_remove;

        MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            tv_url = (TextView) itemView.findViewById(R.id.tv_url);
            tv_state = (TextView) itemView.findViewById(R.id.tv_state);
            btn_disable = (Button) itemView.findViewById(R.id.btn_disable);
            btn_remove = (Button) itemView.findViewById(R.id.btn_remove);
        }
    }
}