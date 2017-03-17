package tool.xfy9326.keyblocker.base;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import java.lang.reflect.Method;
import tool.xfy9326.keyblocker.config.Config;

public class BaseMethod {
	public static void KeyLockBroadcast(Context mContext) {
		Intent intent = new Intent();
        intent.setAction(Config.NOTIFICATION_ACTION);
        mContext.sendBroadcast(intent);
	}

    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }
        return false;
    }

    public static void collapseStatusBar(Context context) {
        try {
            //noinspection WrongConstant
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            collapse = statusBarManager.getClass().getMethod("collapsePanels");
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}