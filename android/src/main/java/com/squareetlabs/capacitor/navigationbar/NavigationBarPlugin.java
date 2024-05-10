package com.squareetlabs.capacitor.navigationbar;

import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.regex.Pattern;

/**
 * Capacitor plugin to control the navigation bar on Android devices.
 */
@CapacitorPlugin(name = "NavigationBar")
public class NavigationBarPlugin extends Plugin {
    private final NavigationBar implementation = new NavigationBar();
    private Boolean isTransparent = false;
    private int currentColor = Color.BLACK;

    /**
     * Hides the navigation bar.
     *
     * @param call PluginCall object of the plugin call.
     */
    @PluginMethod
    public void hide(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            Window window = getActivity().getWindow();
            WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
            windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars());

            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            notifyListeners("onHide", new JSObject());
            call.resolve();
        });
    }

    /**
     * Displays the navigation bar.
     *
     * @param call PluginCall object of the plugin call.
     */
    @PluginMethod
    public void show(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            Window window = getActivity().getWindow();
            WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
            windowInsetsController.show(WindowInsetsCompat.Type.navigationBars());

            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            notifyListeners("onShow", new JSObject());
            call.resolve();
        });
    }

    /**
     * Sets the transparency of the navigation bar.
     *
     * @param call PluginCall object of the plugin call.
     */
    @PluginMethod
    public void setTransparency(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            Boolean isTransparent = call.getBoolean("isTransparent");
            Window window = getActivity().getWindow();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Boolean.TRUE.equals(isTransparent)) {
                    WindowCompat.setDecorFitsSystemWindows(window, false);
                    window.setNavigationBarColor(Color.TRANSPARENT);
                    window.setNavigationBarContrastEnforced(false);
                } else {
                    WindowCompat.setDecorFitsSystemWindows(window, true);
                    window.setNavigationBarColor(this.currentColor);
                    window.setNavigationBarContrastEnforced(true);
                }
            } else {
                if (Boolean.TRUE.equals(isTransparent)) {
                    window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                }
            }
            this.isTransparent = isTransparent;
            call.resolve();
        });
    }

    /**
     * Sets the colour of the navigation bar.
     *
     * @param call PluginCall object of the plugin call.
     */
    @PluginMethod
    public void setColor(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            String color = call.getString("color");
            Boolean darkButtons = call.getBoolean("darkButtons");

            if (color == null || color.isEmpty()) {
                color = "#FFFFFF";
            }

            if (!this.validateHexColor(color)) {
                call.reject("Invalid Hexadecimal color");
                return;
            }

            if (darkButtons == null) {
                darkButtons = false;
            }

            Window window = getActivity().getWindow();
            WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(window, window.getDecorView());
            windowInsetsController.setAppearanceLightNavigationBars(darkButtons);

            this.currentColor = Color.parseColor(color);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || !this.isTransparent) {
                window.setNavigationBarColor(this.currentColor);
            }

            String newColor = String.format("#%08X", (this.currentColor));
            newColor = newColor.replace("#FF", "#");

            JSObject ret = new JSObject();
            ret.put("color", newColor);
            notifyListeners("onColorChange", ret);

            call.resolve();
        });
    }

    /**
     * Validates if a colour is a valid hexadecimal value.
     *
     * @param colour String colour in hexadecimal format
     * @return boolean true if colour is valid, false otherwise
     */
    private boolean validateHexColor(String color) {
        if (color == null || color.isEmpty()) {
            return false;
        }

        Pattern hexPattern = Pattern.compile("^#([A-Fa-f0-9]{6})$|^#([A-Fa-f0-9]{8})$");
        return hexPattern.matcher(color).matches();
    }

    /**
     * Gets the current colour of the navigation bar.
     *
     * @param call PluginCall object of the plugin call.
     */
    @PluginMethod
    public void getColor(PluginCall call) {
        this.getActivity().runOnUiThread(() -> {
            String color = String.format("#%08X", (this.currentColor));
            color = color.replace("#FF", "#");

            JSObject colorObject = new JSObject();
            colorObject.put("color", color);
            call.resolve(colorObject);
        });
    }
}
