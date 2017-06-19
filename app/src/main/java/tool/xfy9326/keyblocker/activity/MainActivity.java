package tool.xfy9326.keyblocker.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import cyanogenmod.app.CMStatusBarManager;
import cyanogenmod.app.CustomTile;
import tool.xfy9326.keyblocker.R;
import tool.xfy9326.keyblocker.receiver.CmTileReceiver;

public class MainActivity extends Activity {
    private Button
            mBtnStart,
            mBtnSettings,
            mBtnAccessEntry;
    public static final String ACTION_KEY_BLOCK = "tool.xfy9326.keyblocker.action.CMKeyBlocker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_layout);
        SharedPreferences mSp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mSpEditor = mSp.edit();
        mSpEditor.apply();
        initView();
        initHandle();
    }

    private void initHandle() {
        mBtnStart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                createStatusBarTiles(MainActivity.this,false);
                Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
            }
        });

        mBtnAccessEntry.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.access_entry)
                        .setMessage(R.string.access_entry_use)
                        .setNegativeButton(R.string.cancel, null);
                dialog.show();
            }
        });

        mBtnSettings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("InflateParams")
    private void initView() {
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnAccessEntry = (Button) findViewById(R.id.btn_access_entry);
        mBtnSettings = (Button) findViewById(R.id.btn_settings);
    }

    public void createStatusBarTiles(Context context, boolean nowStatus) {
        Intent intent = new Intent();
        intent.setAction(ACTION_KEY_BLOCK);
        intent.putExtra("nowStatus",nowStatus);

        CustomTile customTile = new CustomTile.Builder(context)
                .shouldCollapsePanel(true)
                .setLabel(nowStatus ? R.string.app_name : R.string.app_name)
                .setIcon(nowStatus ? R.drawable.ic_appwidget_off : R.drawable.ic_appwidget_on)
                .setOnClickIntent(PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .build();

        CMStatusBarManager.getInstance(context).publishTile(1, customTile);
    }

}
