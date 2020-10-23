package com.veganchen.main.util

import android.app.AppOpsManager
import android.content.Context
import android.os.Binder
import android.os.Build
import android.os.Process
import android.provider.Settings

/**
 * 权限检测工具
 * created by VegC on 2020/7/30
 */
object PermissionUtil {
    /**
     * 是否有悬浮窗权限
     *
     * @return 是否有
     */
    fun checkPermission(context: Context): Boolean {
        return when {
            Build.VERSION.SDK_INT < Build.VERSION_CODES.M -> {
                try {
                    var cls = Class.forName("android.content.Context")
                    val declaredField = cls.getDeclaredField("APP_OPS_SERVICE")
                    declaredField.isAccessible = true
                    var obj: Any? = declaredField[cls] as? String ?: return false
                    val str2 = obj as String
                    obj = cls.getMethod("getSystemService", String::class.java)
                        .invoke(context, str2)
                    cls = Class.forName("android.app.AppOpsManager")
                    val declaredField2 = cls.getDeclaredField("MODE_ALLOWED")
                    declaredField2.isAccessible = true
                    val checkOp = cls.getMethod(
                        "checkOp",
                        Integer.TYPE,
                        Integer.TYPE,
                        String::class.java
                    )
                    val result = checkOp.invoke(
                        obj,
                        24,
                        Binder.getCallingUid(),
                        context.packageName
                    ) as Int
                    result == declaredField2.getInt(cls)
                } catch (e: Exception) {
                    false
                }
            }
            Build.VERSION.SDK_INT < Build.VERSION_CODES.O -> {
                Settings.canDrawOverlays(context)
            }
            else -> {
                val appOpsMgr =
                    context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
                        ?: return false
                val mode = appOpsMgr.checkOpNoThrow(
                    "android:system_alert_window", Process.myUid(), context
                        .packageName
                )
                Settings.canDrawOverlays(context) || mode == AppOpsManager.MODE_ALLOWED
            }
        }
    }
}