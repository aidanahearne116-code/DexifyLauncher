package com.dexify.launcher;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.ByteArrayOutputStream;

/** Returns the icon (PNG data URL) and label of an installed app, by package name. */
@CapacitorPlugin(name = "AppIcon")
public class AppIconPlugin extends Plugin {

    @PluginMethod
    public void get(PluginCall call) {
        String pkg = call.getString("package");
        if (pkg == null || pkg.isEmpty()) {
            call.reject("package required");
            return;
        }
        try {
            PackageManager pm = getContext().getPackageManager();
            Drawable d = pm.getApplicationIcon(pkg);
            int size = 192;
            Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            d.setBounds(0, 0, size, size);
            d.draw(canvas);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);

            JSObject result = new JSObject();
            result.put("icon", "data:image/png;base64," + Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP));
            CharSequence label = pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0));
            result.put("label", label == null ? "" : label.toString());
            call.resolve(result);
        } catch (PackageManager.NameNotFoundException e) {
            call.reject("not installed");
        } catch (Exception e) {
            call.reject(String.valueOf(e.getMessage()));
        }
    }
}
