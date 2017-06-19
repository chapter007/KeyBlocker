package tool.xfy9326.keyblocker.receiver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;
import tool.xfy9326.keyblocker.R;
import tool.xfy9326.keyblocker.base.BaseMethod;

import static android.content.ContentValues.TAG;

/**
 * Created by zhangjie on 2017/6/12.
 */

public class CmTileReceiver extends BroadcastReceiver{
    public static final String ACTION_KEY_BLOCK = "tool.xfy9326.keyblocker.action.CMKeyBlocker";
    private boolean nowStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        nowStatus=intent.getBooleanExtra("nowStatus",false);
        Intent newIntent=new Intent();
        newIntent.setAction(ACTION_KEY_BLOCK);

        if(action.equals(ACTION_KEY_BLOCK)){
            BaseMethod.KeyLockBroadcast(context);
            nowStatus=!nowStatus;
        }
        newIntent.putExtra("nowStatus",nowStatus);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context, 0,
                        newIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        CustomTile customTile = new CustomTile.Builder(context)
                .setOnClickIntent(pendingIntent)
                .shouldCollapsePanel(true)
                .setContentDescription("点击即可屏蔽按键")
                .setIcon(nowStatus ? R.drawable.ic_appwidget_off : R.drawable.ic_appwidget_on)
                .setLabel(nowStatus ? R.string.key_block : R.string.key_unblock)
                .build();

        CMStatusBarManager.getInstance(context)
                .publishTile(1, customTile);
    }
}
